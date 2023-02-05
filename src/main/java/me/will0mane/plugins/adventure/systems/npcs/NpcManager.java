package me.will0mane.plugins.adventure.systems.npcs;

import me.will0mane.plugins.adventure.game.npcs.types.CrafterNPC;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NpcManager {

    private final Map<String, NPC> map = new HashMap<>();

    public void registerNPC(String id, NPC npc){
        map.put(id, npc);
    }

    public Optional<NPC> getNPC(String id){
        if(!map.containsKey(id)) return Optional.empty();
        return Optional.of(map.get(id));
    }

    public void registerDefaults(){
        registerNPC("crafter", new CrafterNPC());
    }

}
