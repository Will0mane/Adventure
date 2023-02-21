package me.will0mane.plugins.adventure.systems.gui.builder;

import lombok.Getter;

public class Builder {
    @Getter
    private int column;
    @Getter
    private int rows;
    @Getter
    private String title;


    public Builder setTitle(String title) {
        this.title = title;
        return this;
    }

    public Builder setColumn(int column) {
        this.column = column;
        return this;
    }

    public Builder setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public Builder setSize(int column, int rows){
        setColumn(column);
        setRows(rows);
        return this;
    }
}
