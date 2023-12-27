package ru.igojig.settings;

import ru.igojig.sweeper.Coordinate;

import javax.swing.*;
import java.awt.event.*;
import java.util.Optional;

public class SetDimension extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JSpinner spinnerY;
    private JSpinner spinnerX;


    private Coordinate colsRows;




    public SetDimension(int x, int y) {
        colsRows=new Coordinate();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setTitle("Размерность поля");

        spinnerX.setValue(x);
        spinnerY.setValue(y);
    }

    private void onOK() {
        // add your code here

        colsRows.setX((Integer) spinnerX.getValue());
        colsRows.setY((Integer) spinnerY.getValue());

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        colsRows=null;
        dispose();
    }

    public Optional<Coordinate> getColsRows() {
        return Optional.ofNullable(colsRows);
    }

    //    public static void main(String[] args) {
//        Settings dialog = new Settings();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
