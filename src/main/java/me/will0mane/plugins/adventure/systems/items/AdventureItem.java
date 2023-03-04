package me.will0mane.plugins.adventure.systems.items;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.game.executors.ExecutorItemRename;
import me.will0mane.plugins.adventure.lib.morepersistentdatatypes.DataType;
import me.will0mane.plugins.adventure.lib.morepersistentdatatypes.datatypes.MapSerializable;
import me.will0mane.plugins.adventure.systems.exceptions.adventureitem.abs.NotHeadItemException;
import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.items.blueprints.BlueprintCAdventureItemHeadData;
import me.will0mane.plugins.adventure.systems.items.blueprints.BlueprintCAdventureItemLoreGeneration;
import me.will0mane.plugins.adventure.systems.items.enchant.ItemEnchant;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public class AdventureItem implements ConfigurationSerializable , Listener {

    //GLOBAL
    //KEYS
    private static final String BUILDER_KEY = "builder";
    private static final String ID_KEY = "id";
    private static final String DATA_KEY = "item";
    private static final String ABILITIES_KEY = "abilities";
    private static final String ENCHANTMENT_KEY = "enchants";
    private static final String STATS_KEY = "stats";

    //Maps
    @Getter
    private static final Map<UUID, AdventureItem> items = new HashMap<>();
    private static final Map<UUID, List<ItemAbility<?>>> abilityMap = new HashMap<>();

    //Executors
    private static final ExecutorItemRename itemRename = new ExecutorItemRename();

    //Namespaces
    private static final NamespacedKey abilityKey = Adventure.getKey(ABILITIES_KEY);

    @SneakyThrows
    public static Optional<AdventureItem> getItem(ItemStack item) {
        if(item == null) return Optional.empty();
        if(item.getItemMeta() == null) return Optional.empty();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer c = meta.getPersistentDataContainer();

        NamespacedKey key = Adventure.getKey(DATA_KEY);
        if(c.has(key, DataType.ADVENTURE_ITEM)){
            Map<String,Object> map = Objects.requireNonNull(c.get(key, DataType.ADVENTURE_ITEM)).getMap();

            String id = (String) map.get(ID_KEY);
            if(id == null) return Optional.empty();

            String builder = (String) map.get(BUILDER_KEY);
            UUID uuid = UUID.fromString(id);

            if(!items.containsKey(uuid)){
                Map<String,Object> data = new HashMap<>();
                map.forEach((s, o) -> {
                    if(!(s.equals("==") && !(o instanceof MapSerializable))) data.put(s, o);
                });
                items.put(uuid, new AdventureItem(item, uuid, getAbilitiesFromStack(item), data));
            }
            return Optional.of(items.get(uuid));
        }else {
            return Optional.empty();
        }
    }

    private static List<ItemAbility<?>> getAbilitiesFromStack(ItemStack item) {
        if(item == null) return Collections.emptyList();
        if(item.getItemMeta() == null) return Collections.emptyList();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer c = meta.getPersistentDataContainer();
        NamespacedKey key = Adventure.getKey(DATA_KEY);
        if(c.has(key, DataType.ADVENTURE_ITEM)){
            Map<String, Object> data = Objects.requireNonNull(c.get(key, DataType.ADVENTURE_ITEM)).getMap();
            String abilityKey = (String) data.get(ABILITIES_KEY);
            String[] abilities = abilityKey.split(";");

            Abilities[] abs = new Abilities[abilities.length];
            int done = 0;
            for(String s : abilities){
                if(s.contains(";")){
                    abs[done] = Abilities.valueOf(s.split(";")[0]);
                }else {
                    abs[done] = Abilities.valueOf(s);
                }
                done++;
            }
            List<ItemAbility<?>> list = new ArrayList<>();
            for (Abilities ab : abs) {
                list.add(ab.getAbility());
            }
            return list;
        }else {
            return new ArrayList<>();
        }
    }

    //Object
    //Fields
    @Getter
    private final ItemStack original;
    @Getter
    @Setter
    private List<ItemAbility<?>> abilities;
    private Map<Integer, ItemEnchant> enchants;
    private List<String> lore;
    @Getter
    private List<String> description;
    @Getter
    private final Map<String,Object> data;
    private final Map<String, Double> stats;
    @Getter
    private final UUID uuid;

    //Constructors
    public AdventureItem(ItemStack itemStack, UUID uuid, List<ItemAbility<?>> abilities, Map<String, Object> data){
        //Fields
        this.original = itemStack;
        this.abilities = abilities;
        this.lore = new ArrayList<>();
        this.description = new ArrayList<>();
        this.uuid = uuid;
        this.data = data;

        //Enchants
        if(data.containsKey(ENCHANTMENT_KEY)){
            this.enchants = (Map<Integer, ItemEnchant>) data.get(ENCHANTMENT_KEY);
        }else {
            this.enchants = new HashMap<>();
        }

        //Statistics
        if(data.containsKey(STATS_KEY)){
            this.stats = (Map<String, Double>) data.get(STATS_KEY);
        }else {
            this.stats = new HashMap<>();
        }

        //Global map
        items.put(uuid, this);
    }

    public AdventureItem(ItemStack itemStack){
        this(itemStack, UUID.randomUUID(), new ArrayList<>(), new HashMap<>());
    }

    public AdventureItem(ItemStack itemStack, List<ItemAbility<?>> abilities){
        this(itemStack, UUID.randomUUID(), abilities, new HashMap<>());
    }

    public AdventureItem(Material material){
        this(new ItemStack(material));
    }

    //Methods

    //ItemStack
    public ItemStack buildItem(){
        setKey(ID_KEY, uuid.toString());
        setKey(ABILITIES_KEY, getAbilitiesId());
        setKey(ENCHANTMENT_KEY, enchants);
        setKey(STATS_KEY, stats);
        generateLore();
        updateLore();
        return original;
    }

    public AdventureItem rename(String newName){
        return itemRename.process(this, newName);
    }

    @SneakyThrows
    public AdventureItem setHead(String head){
        if(original.getType() != Material.PLAYER_HEAD) {
            throw new NotHeadItemException(uuid.toString());
        }
        new BlueprintCAdventureItemHeadData(this, head).execPin();
        return this;
    }

    //Abilities
    private String getAbilitiesId() {
        StringBuilder builder = new StringBuilder();
        int done = 0;
        for (ItemAbility<?> ability : this.abilities) {
            if(done != 0) builder.append(";");
            builder.append(ability.getEnum().name());
            done++;
        }
        return builder.toString();
    }

    public boolean hasAnAbility(){
        return !abilities.isEmpty();
    }


    public void inputAbility(Class<?> inputType, Object... arguments) {
        if(!hasAnAbility()){
            abilities = getAbilitiesFromStack(original);
            return;
        }
        for (ItemAbility<?> ability : abilities) {
            if(ability.getType().getTypeName().split("<")[1].split(">")[0].equals(inputType.getName())){
                ability.triggerWithArgs(inputType, arguments);
            }
        }
    }


    public Optional<ItemAbility<?>> getAbilityFromQuery(String id){
        for (ItemAbility<?> itemAbility : abilities) {
            if(itemAbility.getId().equals(id)){
                return Optional.of(itemAbility);
            }
        }
        return Optional.empty();
    }

    public AdventureItem addAbility(ItemAbility<?> ability){
        abilities.add(ability);
        return this;
    }

    public AdventureItem removeAbility(String id){
        Optional<ItemAbility<?>> optionalAbility = getAbilityFromQuery(id);
        if(optionalAbility.isEmpty()) return this;
        ItemAbility<?> ability = optionalAbility.get();
        return removeAbility(ability);
    }

    public AdventureItem removeAbility(ItemAbility<?> ability){
        this.abilities.remove(ability);
        return this;
    }

    //Lore
    public AdventureItem setDescription(List<String> description) {
        this.description = description;
        return this;
    }

    public AdventureItem generateLore(){
        lore = new BlueprintCAdventureItemLoreGeneration(this).run();
        return this;
    }

    public AdventureItem setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public List<String> getLore(){
        return lore;
    }

    private void updateLore() {
        ItemMeta meta = original.getItemMeta();
        if(meta == null) return;
        meta.setLore(translateLore());
        applyMeta(meta);
    }

    private List<String> translateLore() {
        List<String> strings = new ArrayList<>();
        for (String s : getLore()) {
            strings.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return strings;
    }

    //Meta
    public ItemStack applyMeta(ItemMeta meta) {
        original.setItemMeta(meta);
        return original;
    }

    //Stats
    public Map<String, Double> getStats() {
        return stats;
    }

    public AdventureItem setStatistic(String id, double data){
        stats.put(id, data);
        setKey(STATS_KEY, stats);
        return this;
    }

    //Data
    public <Z> AdventureItem setKey(String id, Z dataValue){
        data.put(id, dataValue);
        applyData();
        return this;
    }

    private AdventureItem applyData() {
        ItemMeta meta = original.getItemMeta();
        if(meta == null) {
            buildItem();
            return applyData();
        }

        PersistentDataContainer c = meta.getPersistentDataContainer();
        Map<String,Object> objectMap = new HashMap<>();
        data.forEach((s, o) -> {
            if(!(s.equals("=="))) objectMap.put(s,o);
        });
        c.set(Adventure.getKey(DATA_KEY), DataType.ADVENTURE_ITEM, new MapSerializable(objectMap));
        original.setItemMeta(meta);
        return this;
    }

    public <Z> Z get(String id){
        return (Z) data.get(id);
    }

    public <Z> boolean has(String id) {
        return get(id) == null;
    }

    //ID
    public String getId() {
        return uuid.toString();
    }

    //Serialization
    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return data;
    }
}
