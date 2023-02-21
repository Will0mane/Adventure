package me.will0mane.plugins.adventure.systems.pets;

import com.mongodb.lang.Nullable;
import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.game.items.types.custom.pet.PetMenuItem;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.items.builder.ItemBuilder;
import me.will0mane.plugins.adventure.systems.items.handler.AdventureItemHandler;
import me.will0mane.plugins.adventure.systems.pets.type.PetType;
import me.will0mane.plugins.adventure.systems.stats.AdventureStat;
import me.will0mane.plugins.adventure.systems.stats.types.ArrayListStatistic;
import me.will0mane.plugins.adventure.systems.utils.MathUtil;
import me.will0mane.plugins.adventure.systems.workers.Worker;
import me.will0mane.plugins.adventure.systems.workers.tasks.WorkerTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class AdventurePet {

    public static final Map<UUID, List<AdventurePet>> loadedPets = new HashMap<>();
    public static final Map<UUID, AdventurePet> activePets = new HashMap<>();
    private static final Worker<?> moveWorker = new Worker<>(Adventure.getInstance());
    private static final ItemStack UNDEFINED_ITEM = new ItemBuilder(Material.DIRT).rename("&cUnknown Data!").build();
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    //GLOBAL
    public static List<AdventurePet> loadPets(UUID uuid){
        if(loadedPets.containsKey(uuid)) return loadedPets.get(uuid);

        List<AdventurePet> list = new ArrayList<>();
        Optional<AdventureStat<?>> statOptional = Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic("pets");

        if(statOptional.isEmpty()) {
            loadedPets.put(uuid, list);
            return list;
        }
        ArrayListStatistic<String> statistic = (ArrayListStatistic<String>) statOptional.get();

        for(String string : statistic.getList(uuid)){
            list.add(new AdventurePet(string, uuid));
        }

        loadedPets.put(uuid, list);
        return list;
    }

    public static void equipPet(UUID uuid, AdventurePet adventurePet){
        if(activePets.containsKey(uuid)) {
            disequipPet(uuid);
        }

        adventurePet.spawnInWorld(Bukkit.getPlayer(uuid).getLocation());
        activePets.put(uuid, adventurePet);
    }

    public static void disequipPet(UUID uuid) {
        if(!activePets.containsKey(uuid)) return;

        AdventurePet pet = activePets.get(uuid);
        pet.removeFromWorld();
        activePets.remove(uuid);
    }


    //CLASS
    private final PetType petType;
    private final UUID uuid;
    private final UUID owner;
    private int level;
    private double curXP;
    private double xpMax;
    private boolean spawned = false;

    private ArmorStand head;
    private Location currentMovingTarget;
    private WorkerTask moveTask;

    public AdventurePet(String initArguments, UUID owner){
        String[] args = initArguments.split(":");
        this.petType = PetType.valueOf(args[0]);
        this.level = Integer.parseInt(args[1].split(":")[0]);
        this.xpMax = Double.parseDouble(args[2].split(":")[0]);
        this.curXP = Double.parseDouble(args[3]);
        this.uuid = UUID.randomUUID();
        this.owner = owner;
    }

    public AdventurePet(PetType petType, UUID owner){
        this.petType = petType;
        this.level = 0;
        this.xpMax = 10;
        this.curXP = 0;
        this.uuid = UUID.randomUUID();
        this.owner = owner;
    }

    public String serialize(){
        return petType.name() +
                ":" +
                level +
                ":" +
                xpMax +
                ":" +
                curXP;
    }

    private void spawnInWorld(Location location) {
        spawned = true;
        head = Objects.requireNonNull(location.getWorld()).spawn(location, ArmorStand.class);
        head.setMarker(true);
        head.setVisible(false);
        head.setGravity(false);
        head.setSmall(true);
        Objects.requireNonNull(head.getEquipment()).setHelmet(toItemStack());

        moveTask = new WorkerTask(moveWorker) {
            final double TOTAL_TIME_SECONDS = 0.5D;
            final int TIME_IN_TICKS = (int) (TOTAL_TIME_SECONDS * 20);
            final double RAND_X = random.nextDouble(0, 2);
            final double RAND_Z = random.nextDouble(0, 2);
            @Override
            public void run() {
                if(head.isDead()) {
                    cancel();
                    return;
                }

                currentMovingTarget = getOwnerBukkit() != null ? getOwnerBukkit().getLocation() : head.getLocation();
                currentMovingTarget.add(RAND_X,0,RAND_Z);

                Location loc = head.getLocation();
                double xDist = currentMovingTarget.getX() - loc.getX();
                double yDist = currentMovingTarget.getY() - loc.getY();
                double zDist = currentMovingTarget.getZ() - loc.getZ();
                double xPerTick = xDist / TIME_IN_TICKS;
                double yPerTick = yDist / TIME_IN_TICKS;
                double zPerTick = zDist / TIME_IN_TICKS;

                loc.add(xPerTick, yPerTick, zPerTick);
                head.teleport(loc);
                makeArmorStandLookAtLocation(head, getOwnerBukkit() != null ? getOwnerBukkit().getLocation() : head.getLocation());

                Location standLoc = head.getLocation();
                Objects.requireNonNull(standLoc.getWorld()).
                        spawnParticle(petType.getParticle(), standLoc, 1,0,0,0,0);
            }
        };
        moveWorker.timer(moveTask, 0,1);
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
        if(!spawned) return;
        head.remove();
        moveWorker.cancelTask(moveTask.getUsedTaskID());
        Adventure.getRegistry().getAdventureStatManager().saveAll(owner);
        spawned = false;
    }

    public void gainXP(double xpToGain){
        if(curXP + xpToGain > xpMax){
            levelUp(Math.abs((xpToGain + curXP) - xpMax));
            return;
        }
        curXP += xpToGain;
    }

    public void levelUp(double leftXP) {
        level++;
        curXP += leftXP;
        xpMax = level * 1000D;
        sendMessage("&aYour per levelled up! &7[&b" + level + "&7]");
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
            PetMenuItem item = (PetMenuItem) AdventureItemHandler.getBuilder("pet_menu").get().getDeclaredConstructors()[0].newInstance(petType);
            item.setup();
            return item.getItemStack();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return UNDEFINED_ITEM;
    }
}
