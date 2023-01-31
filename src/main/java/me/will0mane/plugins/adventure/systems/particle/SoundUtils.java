package me.will0mane.plugins.adventure.systems.particle;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtils {

    public static void playSoundYaml(String data, Player player) {
        if(data.contains(";")){
            for(String split : data.split(";")){
                String soundName = split.split(",")[0];
                float vol = Float.parseFloat(split.split(",")[1]);
                float pitch = Float.parseFloat(split.split(",")[1]);
                player.playSound(player.getLocation(), Sound.valueOf(soundName.toUpperCase()), vol, pitch);
            }
        }else {
            String soundName = data.split(",")[0];
            float vol = Float.parseFloat(data.split(",")[1]);
            float pitch = Float.parseFloat(data.split(",")[1]);
            player.playSound(player.getLocation(), Sound.valueOf(soundName.toUpperCase()), vol, pitch);
        }
    }

}
