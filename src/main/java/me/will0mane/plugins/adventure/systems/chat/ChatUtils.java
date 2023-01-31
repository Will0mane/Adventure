package me.will0mane.plugins.adventure.systems.chat;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatUtils {

    public static String translate(String to_translate){
        return ChatColors.translate(to_translate);
    }

    public static void sendMessageTranslated(Player p, String... to_translate){
        for(String s : to_translate){
            p.sendMessage(translate(s));
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

    public static String getRainbowVersion(String to_rainbow){
        int done = 0;
        String neu = "";
        for(String s : to_rainbow.split("")){
            if(done >= ColorsNoStyles.values().length){
                done = 0;
            }
            ColorsNoStyles colorsNoStyles = ColorsNoStyles.values()[done];
            neu = neu + colorsNoStyles.getColor() + s;
            done++;
        }
        return neu;
    }
    public static String getColoredBoolean(boolean bool){
        if(bool)
            return translate("%%green%%Sì");
        else
            return translate("%%red%%No");
    }

    public static void sendMessageTranslated(CommandSender sender, String... to_translate){
        for(String s : to_translate){
            String translated = translate(s);
            sender.sendMessage(translated);
        }
    }

}