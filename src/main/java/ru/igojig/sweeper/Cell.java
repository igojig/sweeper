package ru.igojig.sweeper;

import lombok.Getter;
import lombok.Setter;
import ru.igojig.JavaSweeper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

@Getter
public enum Cell {
    ZERO("zero"),
    NUM1("num1"),
    NUM2("num2"),
    NUM3("num3"),
    NUM4("num4"),
    NUM5("num5"),
    NUM6("num6"),
    NUM7("num7"),
    NUM8("num8"),

    BOMB("bomb"),

    FLAGGED("flagged"),
    INFORM("inform"),

    OPENED("opened"),
    CLOSED("closed"),

    BOMBED("bombed"),
    NOBOMB("nobomb"),


    ICON("icon");

    private final String name;

    private Image image;

    Cell(String name){
        this.name=name;
        loadImage();
    }

    private void loadImage(){
        String fileName=String.format("/img/%s.png", name);
        ImageIcon icon=new ImageIcon(Objects.requireNonNull(Cell.class.getResource(fileName)));
        image= icon.getImage().getScaledInstance(JavaSweeper.IMAGE_SIZE, JavaSweeper.IMAGE_SIZE, Image.SCALE_AREA_AVERAGING);
    }

}
