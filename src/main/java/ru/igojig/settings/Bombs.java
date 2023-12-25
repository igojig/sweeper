package ru.igojig.settings;

import javax.swing.*;
import java.awt.event.*;
import java.util.Optional;

public class Bombs extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner factorSpinner;

    private Double result;

    public Bombs(float factor) {
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
        setTitle("Фактор бомб");
        factorSpinner.setModel(new SpinnerNumberModel(factor, 0.05, 1.0, 0.05));
    }

    private void onOK() {
        // add your code here
        result= (Double) factorSpinner.getValue();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        result=null;
        dispose();
    }

    public Optional<Double> getResult(){
        return Optional.ofNullable(result);
    }

//    public static void main(String[] args) {
//        Bombs dialog = new Bombs();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
