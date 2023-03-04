package me.will0mane.plugins.adventure.systems.pets;

import com.mongodb.lang.Nullable;
import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.game.items.types.custom.pet.PetItem;
import me.will0mane.plugins.adventure.game.items.types.custom.pet.PetMenuItem;
import me.will0mane.plugins.adventure.lib.morepersistentdatatypes.DataType;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.genericevents.GenericEvent;
import me.will0mane.plugins.adventure.systems.gui.item.Item;
import me.will0mane.plugins.adventure.systems.hologram.Hologram;
import me.will0mane.plugins.adventure.systems.items.builder.ItemBuilder;
import me.will0mane.plugins.adventure.systems.items.handler.AdventureItemHandler;
import me.will0mane.plugins.adventure.systems.particle.SoundUtils;
import me.will0mane.plugins.adventure.systems.pets.rarity.AdventureRarity;
import me.will0mane.plugins.adventure.systems.pets.type.PetType;
import me.will0mane.plugins.adventure.systems.stats.AdventureStat;
import me.will0mane.plugins.adventure.systems.stats.types.ArrayListStatistic;
import me.will0mane.plugins.adventure.systems.utils.ListUtils;
import me.will0mane.plugins.adventure.systems.workers.Worker;
import me.will0mane.plugins.adventure.systems.workers.tasks.WorkerTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

public class AdventurePet {

    private static final NamespacedKey PET_KEY = Adventure.getKey("petPiece");
    public static final Map<UUID, List<AdventurePet>> loadedPets = new HashMap<>();
    public static final Map<UUID, AdventurePet> activePets = new HashMap<>();
    private static final Worker<?> moveWorker = new Worker<>(Adventure.getInstance());
    private static final ItemStack UNDEFINED_ITEM = new ItemBuilder(Material.DIRT).rename("&cUnknown Data!").build();
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    //GLOBAL
    public static List<AdventurePet> loadPets(UUID uuid) {
        if (loadedPets.containsKey(uuid)) return loadedPets.get(uuid);

        List<AdventurePet> list = new ArrayList<>();
        Optional<AdventureStat<?>> statOptional = Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic("pets");

        if (statOptional.isEmpty()) {
            loadedPets.put(uuid, list);
            return list;
        }
        ArrayListStatistic<String> statistic = (ArrayListStatistic<String>) statOptional.get();

        for (String string : statistic.getList(uuid)) {
            list.add(new AdventurePet(string, uuid));
        }

        loadedPets.put(uuid, list);
        return list;
    }

    public static void equipPet(UUID uuid, AdventurePet adventurePet) {
        if (activePets.containsKey(uuid)) {
            disequipPet(uuid);
        }

        adventurePet.spawnInWorld(Objects.requireNonNull(Bukkit.getPlayer(uuid)).getLocation());
        activePets.put(uuid, adventurePet);
        ChatUtils.sendMessageTranslated(Bukkit.getPlayer(uuid), "&aEquipped your pet!");
    }

    public static void disequipPet(UUID uuid) {
        if (!activePets.containsKey(uuid)) return;

        AdventurePet pet = activePets.get(uuid);
        pet.removeFromWorld();
        activePets.remove(uuid);
        ChatUtils.sendMessageTranslated(Bukkit.getPlayer(uuid), "&cUnequipped your pet!");
    }

    public static void disequipPetAndGiveItem(AdventurePet pet, UUID uuid) {
        pet.removeFromWorld();

        List<AdventurePet> pets = loadedPets.get(uuid);
        pets.remove(pet);
        loadedPets.put(uuid, pets);
        Player player = Bukkit.getPlayer(uuid);
        if(player == null) return;
        ItemStack itemStack = pet.toPetItem();
        player.getInventory().addItem(itemStack);
    }


    //CLASS
    private final PetType petType;
    private final UUID uuid;
    private final UUID owner;
    private AdventureRarity rarity;
    private int level;
    private double curXP;
    private double xpMax;
    private boolean spawned = false;
    private List<BiConsumer<AdventurePet, GenericEvent>> consumers;

    private ArmorStand head;
    private Location currentMovingTarget;
    private WorkerTask moveTask;
    private Hologram nameHologram;

    public AdventurePet(String initArguments, UUID owner) {
        String[] args = initArguments.split(":");
        this.petType = PetType.valueOf(args[0]);
        this.consumers = petType.getConsumers();
        this.level = Integer.parseInt(args[1].split(":")[0]);
        this.xpMax = Double.parseDouble(args[2].split(":")[0]);
        this.curXP = Double.parseDouble(args[3].split(":")[0]);
        this.rarity = AdventureRarity.valueOf(args[4]);
        this.uuid = UUID.randomUUID();
        this.owner = owner;
    }

    public AdventurePet(PetType petType, UUID owner) {
        this.petType = petType;
        this.level = 0;
        this.xpMax = 10;
        this.curXP = 0;
        this.rarity = petType.getRarity();
        this.uuid = UUID.randomUUID();
        this.owner = owner;
    }

    public String serialize() {
        return petType.name() +
                ":" +
                level +
                ":" +
                xpMax +
                ":" +
                curXP +
                ":" +
                rarity.name();
    }

    private void spawnInWorld(Location location) {
        spawned = true;
        head = Objects.requireNonNull(location.getWorld()).spawn(location, ArmorStand.class);
        head.setMarker(true);
        head.setVisible(false);
        head.setGravity(false);
        head.setSmall(true);
        head.getPersistentDataContainer().set(PET_KEY, DataType.ADVENTURE_PET, true);

        nameHologram = new Hologram(location, generatePetHologram());
        nameHologram.addPersistentData(PET_KEY, DataType.ADVENTURE_PET, true);

        Objects.requireNonNull(head.getEquipment()).setHelmet(toItemStack());

        moveTask = new WorkerTask(moveWorker) {
            final double TOTAL_TIME_SECONDS = 0.5D;
            final int TIME_IN_TICKS = (int) (TOTAL_TIME_SECONDS * 20);
            final double RAND_X = random.nextDouble(1, 2);
            final double RAND_Z = random.nextDouble(1, 2);
            final double TP_THRESHOLD = 20D;
            @Override
            public void run() {
                if (head.isDead()) {
                    cancel();
                    return;
                }

                currentMovingTarget = getOwnerBukkit() != null ? getOwnerBukkit().getLocation() : head.getLocation();
                currentMovingTarget.add(RAND_X, 0, RAND_Z);

                Location loc = head.getLocation();

                    double xDist = currentMovingTarget.getX() - loc.getX();
                    double yDist = currentMovingTarget.getY() - loc.getY();
                    double zDist = currentMovingTarget.getZ() - loc.getZ();
                    double xPerTick = xDist / TIME_IN_TICKS;
                    double yPerTick = yDist / TIME_IN_TICKS;
                    double zPerTick = zDist / TIME_IN_TICKS;

                    loc.add(xPerTick, yPerTick, zPerTick);
                    head.teleport(loc);

                if (currentMovingTarget.distance(loc) > TP_THRESHOLD) {
                    head.teleport(currentMovingTarget);
                }

                makeArmorStandLookAtLocation(head, getOwnerBukkit() != null ? getOwnerBukkit().getLocation() : head.getLocation());

                Location standLoc = head.getLocation();

                Location particleLoc = standLoc.clone();
                particleLoc.setY(particleLoc.getY() + 0.5);

                Location holoLoc = standLoc.clone();
                holoLoc.setY(holoLoc.getY() + 1);

                Objects.requireNonNull(standLoc.getWorld()).
                        spawnParticle(petType.getParticle(), particleLoc, 1, 0, 0, 0, 0);
                nameHologram.setLocation(holoLoc);
                nameHologram.setLine(0, generatePetHologram());
            }
        };
        moveWorker.timer(moveTask, 0, 1);
    }

    private String generatePetHologram() {
        return ChatUtils.translate(
                rarity.getColorPrefix() +
                        petType.getName() + " &7[&b" + level + "&7]");
    }

    private void makeArmorStandLookAtLocation(ArmorStand stand, Location location) {
        Location lookDir = location.subtract(stand.getLocation());
        EulerAngle poseAngle = directionToEuler(lookDir);
        stand.setHeadPose(poseAngle);
    }

    private EulerAngle directionToEuler(Location dir) {
        double yaw = -Math.atan2(dir.getX(), dir.getZ());
        double pitch = -Math.atan2(dir.getY(), Math.hypot(dir.getX(), dir.getZ()));
        return new EulerAngle(pitch, yaw, 0);
    }

    private void removeFromWorld() {
        if (!spawned) return;
        head.remove();
        nameHologram.removeHologram();
        nameHologram = null;
        moveWorker.cancelTask(moveTask.getUsedTaskID());
        Adventure.getRegistry().getAdventureStatManager().saveAll(owner);
        spawned = false;
    }

    public void gainXP(double xpToGain) {
        if (curXP + xpToGain >= xpMax && level != rarity.getPetLevelThreshold()) {
            levelUp(Math.abs((xpToGain + curXP) - xpMax));
            return;
        }
        curXP += xpToGain;
    }

    public void levelUp(double leftXP) {
        if(level > rarity.getPetLevelThreshold()){
            sendMessage("&cYour pet can't level up more! Upgrade its rarity!");
        }

        level++;
        curXP = 0;
        xpMax = level * 1000D;

        sendMessage("&aYour pet levelled up! &7[&b" + level + "&7]");
        if(getOwnerBukkit() != null){
            SoundUtils.playSoundYaml("ENTITY_PLAYER_LEVELUP,1,1", getOwnerBukkit());
        }
        gainXP(leftXP);
    }

    private void sendMessage(String... messages) {
        ChatUtils.sendMessageTranslated(getOwnerBukkit(), messages);
    }

    @Nullable
    private Player getOwnerBukkit() {
        return Bukkit.getPlayer(owner);
    }

    public ItemStack toItemStack() {
        try {
            PetMenuItem item = (PetMenuItem) AdventureItemHandler.getBuilder("pet_menu").get().getDeclaredConstructors()[1].newInstance(rarity, petType);
            item.setup();
            item.getItem().setDescription(ListUtils.mergeLists(item.getItem().getDescription(),
                    Arrays.asList(
                            "",
                            "&7Statistics:",
                            "&7- &aXP: &b" + Math.round(curXP * 10) / 10d,
                            "&7- &aLevel: &b" + level,
                            "&7- &aMax XP: &b" + xpMax
                    ), Arrays.asList(
                            "",
                            "&eLeft-click to equip or",
                            "&edisequip.",
                            "&eRight-click to transform pet",
                            "&einto an item."
                    )));
            return item.getItemStack();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return UNDEFINED_ITEM;
    }

    private ItemStack toPetItem() {
        try {
            PetItem item = (PetItem) AdventureItemHandler.getBuilder("pet_item").get().getDeclaredConstructors()[1].newInstance(this);
            item.setup();
            item.getItem().setDescription(ListUtils.mergeLists(item.getItem().getDescription(),
                    Arrays.asList(
                            "",
                            "&7Statistics:",
                            "&7- &aXP: &b" + Math.round(curXP * 10) / 10d,
                            "&7- &aLevel: &b" + level,
                            "&7- &aMax XP: &b" + xpMax
                    )));
            return item.getItemStack();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return UNDEFINED_ITEM;
    }

    public void inputEvent(GenericEvent event) {
        consumers.forEach(consumer -> consumer.accept(this, event));
    }

    public UUID getOwner() {
        return owner;
    }

    public int getLevel() {
        return level;
    }

    public AdventureRarity getRarity() {
        return rarity;
    }

    public void setRarity(AdventureRarity rarity) {
        this.rarity = rarity;
    }

    public void setCurXP(double curXP) {
        this.curXP = curXP;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXpMax(double xpMax) {
        this.xpMax = xpMax;
    }

    public ArmorStand getHead() {
        return head;
    }

    public double getCurXP() {
        return curXP;
    }

    public PetType getType() {
        return petType;
    }

    public double getMaxXP() {
        return xpMax;
    }
}

