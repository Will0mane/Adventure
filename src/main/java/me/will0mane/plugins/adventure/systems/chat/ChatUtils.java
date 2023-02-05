package me.will0mane.plugins.adventure.systems.chat;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatUtils {

    public static String translate(String message){
        return ChatColors.translate(message);
    }

    public static void sendMessageTranslated(Player p, String... messages){
        for(String message : messages){
            p.sendMessage(translate(message));
        }
    }

    public static void sendTranslatedTitle(Player player, String title, String subtitle, int in, int stay, int out){
        player.sendTitle(translate(title), translate(subtitle), in, stay, out);
    }

    public static void sendTranslatedActionBar(Player player, String actionbar){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(translate(actionbar)));
    }

    public static net.md_5.bungee.api.chat.TextComponent translateCustom(ChatMessage message){
        net.md_5.bungee.api.chat.TextComponent component = new net.md_5.bungee.api.chat.TextComponent(ChatUtils.translate(
                message.getMessage()
        ));
        if(message.getHoverEvent() != null){
            component.setHoverEvent(message.getHoverEvent());
        }
        if(message.getClickEvent() != null){
            component.setClickEvent(message.getClickEvent());
        }
        return component;
    }

    public static void sendMessageTranslatedWithAction(Player p, ChatMessage... messages){
        for(ChatMessage message : messages){
            p.spigot().sendMessage(translateCustom(message));
        }
    }

    public static List<String> translateAList(List<String> lore){
        List<String> list = new ArrayList<>();
        for(String s : lore){
            String translated = translate(s);
            list.add(translated);
        }
        return list;
    }

    public static String getRainbowVersion(String message){
        int done = 0;
        StringBuilder builder = new StringBuilder();
        for(String piece : message.split("")){
            if(done >= ColorsNoStyles.values().length){
                done = 0;
            }
            ColorsNoStyles colorsNoStyles = ColorsNoStyles.values()[done];
            builder.append(colorsNoStyles.getColor()).append(piece);
            done++;
        }
        return builder.toString();
    }
    public static String getColoredBoolean(boolean bool){
        if(bool)
            return translate("%%green%%Sì");
        else
            return translate("%%red%%No");
    }

    public static void sendMessageTranslated(CommandSender sender, String... messages){
        for(String message : messages){
            sender.sendMessage(translate(message));
        }
    }

}