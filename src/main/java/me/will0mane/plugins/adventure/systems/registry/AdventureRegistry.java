package me.will0mane.plugins.adventure.systems.registry;

import dev.sergiferry.playernpc.api.NPCLib;
import lombok.Getter;
import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.commands.register.CommandRegister;
import me.will0mane.plugins.adventure.systems.items.handler.AdventureItemHandler;
import me.will0mane.plugins.adventure.systems.listeners.manager.AdventureListenerManager;
import me.will0mane.plugins.adventure.systems.moderation.Moderation;
import me.will0mane.plugins.adventure.systems.npcs.NpcManager;
import me.will0mane.plugins.adventure.systems.sessions.SessionHandler;

public class AdventureRegistry {

    private AdventureListenerManager ListenerManager;
    private CommandRegister CommandRegister;
    private AdventureItemHandler ItemHandler;
    @Getter
    private NpcManager NPCManager;
    @Getter
    private me.will0mane.plugins.adventure.systems.moderation.Moderation Moderation;
    @Getter
    private SessionHandler SessionsHandler;

    public AdventureRegistry(){
        ListenerManager = new AdventureListenerManager();
        ListenerManager.registerDefaults(Adventure.getInstance());
        CommandRegister = new CommandRegister();
        CommandRegister.registerDefaults();
        ItemHandler = new AdventureItemHandler();
        ItemHandler.registerDefaults();
        NPCManager = new NpcManager();
        NPCManager.registerDefaults();
        Moderation = new Moderation();
        SessionsHandler = new SessionHandler();

        registerNPCs();
    }

    private void registerNPCs() {
        NPCLib.getInstance().registerPlugin(Adventure.getInstance());
    }

}
