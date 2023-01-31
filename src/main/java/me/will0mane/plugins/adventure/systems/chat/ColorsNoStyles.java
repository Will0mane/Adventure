package me.will0mane.plugins.adventure.systems.chat;

import org.bukkit.ChatColor;

public enum ColorsNoStyles {


    BLACK("%%black%%",ChatColor.BLACK.toString(), "0"),
    DARK_BLUE("%%dark_blue%%", ChatColor.DARK_BLUE.toString(), "1"),
    DARK_GREEN("%%dark_green%%", ChatColor.DARK_GREEN.toString(), "2"),
    DARK_AQUA("%%dark_aqua%%", ChatColor.DARK_AQUA.toString(), "3"),
    DARK_RED("%%dark_red%%", ChatColor.DARK_RED.toString(), "4"),
    DARK_PURPLE("%%dark_purple%%", ChatColor.DARK_PURPLE.toString(), "5"),
    GOLD("%%gold%%", ChatColor.GOLD.toString(), "6"),
    GRAY("%%gray%%", ChatColor.GRAY.toString(), "7"),
    DARK_GRAY("%%dark_gray%%", ChatColor.DARK_GRAY.toString(), "8"),
    BLUE("%%blue%%", ChatColor.BLUE.toString(), "9"),
    GREEN("%%green%%", ChatColor.GREEN.toString(), "a"),
    AQUA("%%aqua%%", ChatColor.AQUA.toString(), "b"),
    RED("%%red%%", ChatColor.RED.toString(), "c"),
    LIGHT_PURPLE("%%light_purple%%", ChatColor.LIGHT_PURPLE.toString(), "d"),
    YELLOW("%%yellow%%", ChatColor.YELLOW.toString(), "e"),
    WHITE("%%white%%", ChatColor.WHITE.toString(), "f");

    private final String r;
    private final String c;
    private final String d;

    ColorsNoStyles(String rapresenting, String color, String data){
        r = rapresenting;
        c = color;
        d = data;
    }

    public String getData() {
        return d;
    }

    public static String oldChatColor(ChatColors chatColors){
        return ChatColor.translateAlternateColorCodes('&',
                "&" + chatColors.getColor(""));
    }

    public String getColor() {
        return c;
    }

    public String getRapresenting() {
        return r;
    }

    public static String translate(String to_translate) {
        for(ColorsNoStyles chatColors : values()){
            to_translate = to_translate.replace(chatColors.getRapresenting(), chatColors.getColor());
        }
        return to_translate;
    }

}