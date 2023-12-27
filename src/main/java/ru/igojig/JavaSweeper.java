package ru.igojig;


import ru.igojig.settings.Bombs;
import ru.igojig.settings.SetDimension;
import ru.igojig.sweeper.Cell;
import ru.igojig.sweeper.Coordinate;
import ru.igojig.sweeper.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;


public class JavaSweeper extends JFrame {

    private int colsX = 10;
    private int rowsY = 10;
    private float bombsFactor = 0.15f;


    public static int IMAGE_SIZE = 50;
//    private final int IMAGE_SIZE = 50;

    private final Game game;

    private JPanel panel;
    private JLabel label;
    private JMenu menu, menuRestart;
    private JMenuBar menuBar;
    private JMenuItem menuItem1, menuItem2;

    public static void main(String[] args) {
        new JavaSweeper();
    }

    public JavaSweeper() {
        game = new Game(colsX, rowsY);
        game.start(calculateBombsCount());
        initWindow();
        setIconImage(Cell.ICON.getImage());
    }

    public void restart() {
        game.setSize(colsX, rowsY);
        game.start(calculateBombsCount());
    }

    public void initWindow() {
        intiPanel();
        initLabel();
        initMenu();
        initFrame();
    }

    private void initMenu() {

        menuBar = new JMenuBar();
        menu = new JMenu("Настройки");
        menuRestart = new JMenu("Перезапуск");
        menuItem1 = new JMenuItem("Размерность поля");
        menuItem2 = new JMenuItem("Количество бомб");


        menuRestart.addItemListener(e -> restart());

        menuItem1.addActionListener(actionEvent -> {
            SetDimension setDimension = new SetDimension(colsX, rowsY);
            setDimension.pack();
            setDimension.setLocationRelativeTo(panel);
            setDimension.setVisible(true);
            Optional<Coordinate> colsRows = setDimension.getColsRows();
            if (colsRows.isPresent()) {
                colsX = colsRows.get().getX();
                rowsY = colsRows.get().getY();
                panel.setPreferredSize(new Dimension(colsX * IMAGE_SIZE, rowsY * IMAGE_SIZE));
                pack();
                restart();
            }
        });

        menuItem2.addActionListener(actionEvent -> {
            Bombs bombs = new Bombs(bombsFactor);
            bombs.pack();
            bombs.setLocationRelativeTo(panel);
            bombs.setVisible(true);
            Optional<Double> result = bombs.getResult();
            if (result.isPresent()) {
                bombsFactor = result.get().floatValue();
                restart();
            }
        });

        menu.add(menuItem1);
        menu.add(menuItem2);

        menuBar.add(menu);
        menuBar.add(menuRestart);
        add(menuBar, BorderLayout.NORTH);
    }

    private void initLabel() {
        label = new JLabel();
        label.setText(game.getInfoStr());
        add(label, BorderLayout.SOUTH);
    }

    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void intiPanel() {

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                game.getGameField()
                        .forEach(c -> g.drawImage(game.getBox(c).getImage(),
                                c.getX() * IMAGE_SIZE, c.getY() * IMAGE_SIZE, this));

                label.setText(game.getInfoStr());
                label.setForeground(game.getStatus().getLabelColor());
                repaint();
            }

        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;

                switch (e.getButton()) {
                    case MouseEvent.BUTTON1 -> game.leftMouse(x, y);
                    case MouseEvent.BUTTON3 -> game.rightMouse(x, y);
                    case MouseEvent.BUTTON2 -> restart();
                }
            }
        });

        panel.setPreferredSize(new Dimension(colsX * IMAGE_SIZE, rowsY * IMAGE_SIZE));
        add(panel);
    }

    private int calculateBombsCount() {
        return (int) (colsX * rowsY * bombsFactor);
    }
}
