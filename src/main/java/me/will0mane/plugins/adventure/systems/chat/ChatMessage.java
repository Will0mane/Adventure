package me.will0mane.plugins.adventure.systems.chat;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class ChatMessage {

    private final String m;
    private final HoverEvent hE;
    private final ClickEvent cE;

    public ChatMessage(String message, HoverEvent hoverEvent, ClickEvent clickEvent){
        m = message;
        hE = hoverEvent;
        cE = clickEvent;
    }

    public HoverEvent getHoverEvent() {
        return hE;
    }

    public ClickEvent getClickEvent() {
        return cE;
    }

    public String getMessage() {
        return m;
    }

}