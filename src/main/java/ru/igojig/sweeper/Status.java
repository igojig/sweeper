package ru.igojig.sweeper;

import lombok.Getter;

import java.awt.*;

@Getter
public enum Status {
    PLAYED ("Game is started", Color.DARK_GRAY),
    BOMBED("You are bombed ((( ", Color.RED),
    WIN("You are WIN !!! ", Color.BLUE);

    private final String name;
    private final Color labelColor;

    Status(String name, Color labelColor){
        this.name=name;
        this.labelColor = labelColor;
    }

}
