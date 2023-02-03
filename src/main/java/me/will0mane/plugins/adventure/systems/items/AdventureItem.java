package me.will0mane.plugins.adventure.systems.items;

import com.jeff_media.morepersistentdatatypes.DataType;
import lombok.Getter;
import lombok.Setter;
import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.blueprints.abs.BlueprintCAdventureItemLoreGeneration;
import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

@SuppressWarnings("unused")
public class AdventureItem {

    private static final Map<UUID, AdventureItem> items = new HashMap<>();
    private static final Map<UUID, List<ItemAbility<?>>> abilityMap = new HashMap<>();
    private static final NamespacedKey abilityKey = Adventure.getKey("abilities");

    public static Optional<AdventureItem> getItem(ItemStack item) {
        if(item == null) return Optional.empty();
        if(item.getItemMeta() == null) return Optional.empty();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer c = meta.getPersistentDataContainer();
        if(c.has(Adventure.getKey("id"), DataType.STRING)){
            String id = c.get(Adventure.getKey("id"), DataType.STRING);
            if(id == null) return Optional.empty();
            UUID uuid = UUID.fromString(id);
            if(!items.containsKey(uuid)){
                items.put(uuid, new AdventureItem(item, uuid, getAbilitiesFromStack(item)));
            }
            return Optional.of(items.get(uuid));
        }else {
            UUID uuid = UUID.randomUUID();
            c.set(Adventure.getKey("id"), DataType.STRING, uuid.toString());
            item.setItemMeta(meta);
            return getItem(item);
        }
    }

    private static List<ItemAbility<?>> getAbilitiesFromStack(ItemStack item) {
        if(item == null) return Collections.emptyList();
        if(item.getItemMeta() == null) return Collections.emptyList();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer c = meta.getPersistentDataContainer();
        if(c.has(abilityKey, DataType.STRING_ARRAY)){
            String[] abilities = c.get(abilityKey, DataType.STRING_ARRAY);
            if(abilities == null) return new ArrayList<>();
            Abilities[] abs = new Abilities[abilities.length];
            int done = 0;
            for(String s : abilities){
                abs[done] = Abilities.valueOf(s);
                done++;
            }
            List<ItemAbility<?>> list = new ArrayList<>();
            for (Abilities ab : abs) {
                list.add(ab.getAbility());
            }
            return list;
        }else {
            return Collections.emptyList();
        }
    }

    //Params
    private final ItemStack original;
    @Getter
    @Setter
    private List<ItemAbility<?>> abilities;
    @Setter
    private List<String> lore;
    @Getter
    @Setter
    private List<String> description;
    private final UUID uuid;

    public AdventureItem(ItemStack itemStack, UUID uuid, List<ItemAbility<?>> abilities){
        this.original = itemStack;
        this.abilities = abilities;
        this.lore = new ArrayList<>();
        this.description = new ArrayList<>();
        this.uuid = uuid;
        items.put(uuid, this);
    }

    public AdventureItem(ItemStack itemStack){
        this(itemStack, UUID.randomUUID(), new ArrayList<>());
    }

    public AdventureItem(ItemStack itemStack, List<ItemAbility<?>> abilities){
        this(itemStack, UUID.randomUUID(), abilities);
    }

    public AdventureItem(Material material){
        this(new ItemStack(material));
    }

    public boolean hasAnAbility(){
        return !abilities.isEmpty();
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

    public AdventureItem rename(String newName){
        ItemMeta meta = original.getItemMeta();
        if(meta == null) return this;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', newName));
        applyMeta(meta);
        return this;
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

    public AdventureItem generateLore(){
        lore = new BlueprintCAdventureItemLoreGeneration(this).run();
        return this;
    }

    public List<String> getLore(){
        return lore;
    }

    public ItemStack buildItem(){
        setKey("id", DataType.STRING, uuid.toString());
        setKey("abilities", DataType.STRING_ARRAY, getAbilitiesId());
        generateLore();
        updateLore();
        return original;
    }

    private String[] getAbilitiesId() {
        String[] abilityArray = new String[this.abilities.size()];
        int done = 0;
        for (ItemAbility<?> ability : this.abilities) {
            abilityArray[done] = ability.getEnum().name();
            done++;
        }
        return abilityArray;
    }

    <T, Z> AdventureItem setKey(String id, PersistentDataType<T, Z> dataType, Z data){
        ItemMeta meta = original.getItemMeta();
        if(meta == null) return this;
        PersistentDataContainer c = meta.getPersistentDataContainer();
        c.set(Adventure.getKey(id), dataType, data);
        original.setItemMeta(meta);
        return this;
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

    private ItemStack applyMeta(ItemMeta meta) {
        original.setItemMeta(meta);
        return original;
    }

    public String getId() {
        return uuid.toString();
    }
}
