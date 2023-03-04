package me.will0mane.plugins.adventure.systems.registry;

import dev.sergiferry.playernpc.api.NPCLib;
import lombok.Getter;
import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.game.databases.mongodb.AdventureGameMongoDB;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.commands.register.CommandRegister;
import me.will0mane.plugins.adventure.systems.database.mongodb.MongoDBSettings;
import me.will0mane.plugins.adventure.systems.items.handler.AdventureItemHandler;
import me.will0mane.plugins.adventure.systems.listeners.manager.AdventureListenerManager;
import me.will0mane.plugins.adventure.systems.moderation.Moderation;
import me.will0mane.plugins.adventure.systems.npcs.NpcManager;
import me.will0mane.plugins.adventure.systems.sessions.SessionHandler;
import me.will0mane.plugins.adventure.systems.stats.AdventureStatManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AdventureRegistry {

    private final AdventureListenerManager ListenerManager;
    private final CommandRegister CommandRegister;
    private final AdventureItemHandler ItemHandler;
    @Getter
    private final NpcManager NPCManager;
    @Getter
    private final me.will0mane.plugins.adventure.systems.moderation.Moderation Moderation;
    @Getter
    private final SessionHandler SessionsHandler;
    @Getter
    private final AdventureGameMongoDB mongoDB;
    @Getter
    private AdventureStatManager adventureStatManager;
    @Getter
    private boolean serverOpen = false;

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

        mongoDB = new AdventureGameMongoDB(new MongoDBSettings(
                "mongodb+srv://Admin:bus0vm1KGF5rKK9@entrodb.qexb8.mongodb.net/?retryWrites=true&w=majority"
        ));

        registerNPCs();

        new BukkitRunnable(){

            @Override
            public void run() {
                adventureStatManager = new AdventureStatManager();
                adventureStatManager.registerDefaultsMongo();
                new BukkitRunnable(){

                    @Override
                    public void run() {
                        serverOpen = true;
                        Bukkit.broadcastMessage(ChatUtils.translate("&aThe server is now ready!"));
                    }
                }.runTaskLater(Adventure.getInstance(), 20);
            }
        }.runTaskLater(Adventure.getInstance(), 60);
    }

    private void registerNPCs() {
        NPCLib.getInstance().registerPlugin(Adventure.getInstance());
    }

}
