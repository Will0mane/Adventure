package me.will0mane.plugins.adventure.game.items.types.custom.backpacks;

public enum BackpackType {

    LEATHER(1,9,"&7", "Leather", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBiMWI1MzY3NDkxODM5MWEwN2E5ZDAwNTgyYzA1OGY5MjgwYmM1MjZhNzE2Yzc5NmVlNWVhYjRiZTEwYTc2MCJ9fX0="),
    COPPER(2,9, "&8","Copper", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWU1ODNjYjc3MTU4MWQzYjI3YjIzZjYxN2M3YjhhNDNkY2Q3MjIwNDQ3ZmY5NWZmMTk2MDQxNGQyMzUwYmRiOSJ9fX0="),
    IRON(3,9, "&a","Iron", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGRhZjhlZGMzMmFmYjQ2MWFlZTA3MTMwNTgwMjMxMDFmOTI0ZTJhN2VmYTg4M2RhZTcyZDVkNTdkNGMwNTNkNyJ9fX0="),
    GOLD(4,9,"&e","Gold", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y4NzUyNWFkODRlZmQxNjgwNmEyNmNhMDE5ODRiMjgwZTViYTY0MDM1MDViNmY2Yzk4MDNjMjQ2NDJhYmZjNyJ9fX0="),
    DIAMOND(5,9,"&a","Diamond", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBkMWIwNzMyYmY3YTcwZGU0ZGMwMTU1OWNjNWM5ODExMDY4ZWY3YjYwOTUwMTAzODI3MDlmOTQwOTM5MjdmNiJ9fX0="),
    NETHERITE(6,9, "&c","Netherite", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM1ZDdjYzA5ZmZmYmNhM2UxYzAwZDQyMWFmYWE0MzJjZjcxZmNiMDk1NTVmNTQ1MjNlNTIyMGQxYWYwZjk3ZCJ9fX0="),
    ;

    private final int[] size;
    private final String headValue;
    private final String name;
    private final String colorPrefix;

    BackpackType(int rows, int columns, String colorPrefix, String name, String headValue){
        this.size = new int[]{rows, columns};
        this.headValue = headValue;
        this.colorPrefix = colorPrefix;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getHeadValue() {
        return headValue;
    }

    public int[] getSize() {
        return size;
    }

    public String getColorPrefix() {
        return colorPrefix;
    }
}
