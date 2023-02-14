package me.will0mane.plugins.adventure.game.npcs.types;

import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;
import lombok.Getter;
import me.will0mane.plugins.adventure.Adventure;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryType;

public class CrafterNPC implements me.will0mane.plugins.adventure.systems.npcs.NPC {

    @Getter
    private NPC.Global npcGlobal;
    private Location location;

    public CrafterNPC(){
    }

    public CrafterNPC(Location location){
        this.location = location;
    }

    @Override
    public me.will0mane.plugins.adventure.systems.npcs.NPC setup(){
        npcGlobal = NPCLib.getInstance().generateGlobalNPC(Adventure.getInstance(), "crafter", location);
        npcGlobal.setSkin(
                "eyJ0aW1lc3RhbXAiOjE1ODYwNDM1Mzc2NTgsInByb2ZpbGVJZCI6ImZkNjBmMzZmNTg2MTRmMTJiM2NkNDdjMmQ4NTUyOTlhIiwicHJvZmlsZU5hbWUiOiJSZWFkIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82OTYwOTkzODQ1MzFkYjhlZDVlZjVhZjQ5YzU4YmVhNjZhNzg0MTJjNDc0YzFmNTIyYTQxYzI0ZjY3YjIwY2I2In19fQ==",
                "XFliUwUS1VTjBgvytjxASbZG6YhAla0iEQAbKphPMsU23HEMbomUChM4611MAeB0yL3MDt9nQapZSLTmWb3DKF3T7QBq4b9cX5oqvdOHHo7RNOaKKU9lXyCO+o5sFciCOvOPUH9C690CoXBUlE36MZPB385DyDjCp52FtNpjagAoEIdxbD78xCHlqQoP02DM1DSslVJd/hoZDactryT8NpcBJDvbTao9YA4nC9JOAXorTOIlf7/jZ6C97jv9MXXSFF6Z3qZWrLxBvPGKpheEFW8n+09MiMicHXIe+3K4ZewSKLcr/0L1iCM2u98Pon+AenAXSOioUHk9mkgH6cs7kkgk3MJCrDJxg2GxLdNevUqmCSgDf8VCqMNU/KjoNgPc81SoJWEvXSPNu8gOymu/JQhaUWjctGIDuOMT5b458BVIUo/fDOlvrT27cNqngcUpXldaJSdQoNQnnwxm1daukXMlqT72mHg4IVTa4iPqabsEGNjoQ6oZEkc+6AnXgbdhIrcK2rHgMa3FoTLWw1zcqweW3fVQMd/GhH4vUI31htmTVBng1owWD2Ug2lIvAtrOruCTzYneramT43mThwh8hpP2gn6sqhu588cBZTlZ6cNRN9vDQvGMj+kHojxpjTzfQX9V5tcargOKYRGYPnA9EITGaw0XIHHyJqTyFxu0YSA="
        );
        npcGlobal.setText("&7Crafter");
        npcGlobal.setCollidable(false);
        npcGlobal.setInteractCooldown(60);
        npcGlobal.setShowOnTabList(false);
        npcGlobal.setTextOpacity(NPC.Hologram.Opacity.LOW);
        npcGlobal.addCustomClickAction(NPC.Interact.ClickType.RIGHT_CLICK, (npc, player) -> player.openInventory(Bukkit.createInventory(player, InventoryType.CRAFTING)));
        npcGlobal.createAllPlayers();
        return this;
    }


    @Override
    public me.will0mane.plugins.adventure.systems.npcs.NPC createNPC(Location location) {
        return new CrafterNPC(location).setup();
    }
}
