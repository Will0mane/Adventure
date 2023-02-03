package me.will0mane.plugins.adventure.systems.chat;

import org.bukkit.ChatColor;

import java.util.function.Function;

public enum ChatColors {

    BLACK("%%black%%", ChatColor.BLACK.toString(), "0", null),
    DARK_BLUE("%%dark_blue%%", ChatColor.DARK_BLUE.toString(), "1", null),
    DARK_GREEN("%%dark_green%%", ChatColor.DARK_GREEN.toString(), "2", null),
    DARK_AQUA("%%dark_aqua%%", ChatColor.DARK_AQUA.toString(), "3", null),
    DARK_RED("%%dark_red%%", ChatColor.DARK_RED.toString(), "4", null),
    DARK_PURPLE("%%dark_purple%%", ChatColor.DARK_PURPLE.toString(), "5", null),
    GOLD("%%gold%%", ChatColor.GOLD.toString(), "6", null),
    GRAY("%%gray%%", ChatColor.GRAY.toString(), "7", null),
    DARK_GRAY("%%dark_gray%%", ChatColor.DARK_GRAY.toString(), "8", null),
    BLUE("%%blue%%", ChatColor.BLUE.toString(), "9", null),
    GREEN("%%green%%", ChatColor.GREEN.toString(), "a", null),
    AQUA("%%aqua%%", ChatColor.AQUA.toString(), "b", null),
    RED("%%red%%", ChatColor.RED.toString(), "c", null),
    LIGHT_PURPLE("%%light_purple%%", ChatColor.LIGHT_PURPLE.toString(), "d", null),
    YELLOW("%%yellow%%", ChatColor.YELLOW.toString(), "e", null),
    WHITE("%%white%%", ChatColor.WHITE.toString(), "f", null),
    MAGIC("%%magic%%", ChatColor.MAGIC.toString(), "k", null),
    BOLD("%%bold%%", ChatColor.BOLD.toString(), "l", null),
    STRIKETHROUGH("%%strikethrough%%", ChatColor.STRIKETHROUGH.toString(), "m", null),
    UNDERLINE("%%underline%%", ChatColor.UNDERLINE.toString(), "n", null),
    ITALIC("%%italic%%", ChatColor.ITALIC.toString(), "o", null),
    RESET("%%reset%%", ChatColor.RESET.toString(), "r", null),
    RAINBOW("%%rainbow%%", "", "", ChatUtils::getRainbowVersion),
    BLACK_BOLD("%%black_bold%%",
            ChatColor.BLACK.toString() + ChatColor.BOLD.toString(), "0&l", null),
    DARK_BLUE_BOLD("%%dark_blue_bold%%",
            ChatColor.DARK_BLUE.toString() + ChatColor.BOLD.toString(), "1&l", null),
    DARK_GREEN_BOLD("%%dark_green_bold%%",
            ChatColor.DARK_GREEN.toString() + ChatColor.BOLD.toString(), "2&l", null),
    DARK_AQUA_BOLD("%%dark_aqua_bold%%",
            ChatColor.DARK_AQUA.toString() + ChatColor.BOLD.toString(), "3&l", null),
    DARK_RED_BOLD("%%dark_red_bold%%",
            ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString(), "4&l", null),
    DARK_PURPLE_BOLD("%%dark_purple_bold%%",
            ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString(), "5&l", null),
    GOLD_BOLD("%%gold_bold%%",
            ChatColor.GOLD.toString() + ChatColor.BOLD.toString(), "6&l", null),
    GRAY_BOLD("%%gray_bold%%",
            ChatColor.GRAY.toString() + ChatColor.BOLD.toString(), "7&l", null),
    DARK_GRAY_BOLD("%%dark_gray_bold%%",
            ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString(), "8&l", null),
    BLUE_BOLD("%%blue_bold%%",
            ChatColor.BLUE.toString() + ChatColor.BOLD.toString(), "9&l", null),
    GREEN_BOLD("%%green_bold%%",
            ChatColor.GREEN.toString() + ChatColor.BOLD.toString(), "a&l", null),
    AQUA_BOLD("%%aqua_bold%%",
            ChatColor.AQUA.toString() + ChatColor.BOLD.toString(), "b&l", null),
    RED_BOLD("%%red_bold%%",
            ChatColor.RED.toString() + ChatColor.BOLD.toString(), "c&l", null),
    LIGHT_PURPLE_BOLD("%%light_purple_bold%%",
            ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString(), "d&l", null),
    YELLOW_BOLD("%%yellow_bold%%",
            ChatColor.YELLOW.toString() + ChatColor.BOLD.toString(), "e&l", null),
    WHITE_BOLD("%%white_bold%%",
            ChatColor.WHITE.toString() + ChatColor.BOLD.toString(), "f&l", null),
    NEW_LINE("%%new_line%%", "\n", "\n", null);

    private final String r;
    private final String c;
    private final String d;
    private final Function<String, String> f;

    ChatColors(String representing, String color, String data,Function<String, String> function){
        r = representing;
        c = color;
        d = data;
        f = function;
    }

    public Function<String, String> getReturns() {
        return f;
    }

    public String getData() {
        return d;
    }

    public static String oldChatColor(ChatColors chatColors){
        return ChatColor.translateAlternateColorCodes('&',
                "&" + chatColors.getColor(""));
    }

    public String getColor(String color) {
        if(getReturns() != null)
            return getReturns().apply(color);
        else
            return c;

    }

    public static String stripAllColors(String s){
        for(ChatColors colors : ChatColors.values()){
            s = s.replace(colors.getRepresenting(), "");
        }
        return s;
    }

    public String getRepresenting() {
        return r;
    }

    public static String translate(String to_translate) {
        for(ChatColors chatColors : values()){
            to_translate = to_translate.replace(chatColors.getRepresenting(), chatColors.getColor(
                    stripAllColors(to_translate).replace("TheAlternativeGame >", "")));
        }
        return ChatColor.translateAlternateColorCodes('&', to_translate);
    }
}