package whiteberries.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

public class OnStop extends JFrame {
    public OnStop() {
        initComponents();
    }

    private void button1ActionPerformed(ActionEvent e) {
        this.dispose();
    }

    private void initComponents() {

        label1 = new JLabel();
        label2 = new JLabel();
        button1 = new JButton();

        //======== this ========
        setTitle("LWhiteBerries");
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Please sell the berries for atleast mid price");

        //---- label2 ----
        label2.setText("else prices will crash and this method will stop being profitable");

        //---- button1 ----
        button1.setText("OK!");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button1ActionPerformed(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(label1)
                                        .addComponent(label2)
                                        .addComponent(button1))
                                .addContainerGap(79, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(label1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addComponent(button1)
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());

    }

    private JLabel label1;
    private JLabel label2;
    private JButton button1;
}