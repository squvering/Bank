package gui;

import client.Client;
import mysqlhelper.Account;
import mysqlhelper.PersonalData;
import pack.Actions;
import pack.Pack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Card extends JPanel{
    public Integer ID;
    public Integer personalID;
    public JButton exit = new JButton("X");
    public JLabel type = new JLabel();
    public JLabel money = new JLabel();
    public JLabel number = new JLabel();
    public Card(Account card, Client client) {
        this.type.setText(card.type);
        this.money.setText(card.money.toString());
        ID = card.ID;
        personalID = card.personalID;
        this.number.setText(ID.toString());
                GroupLayout gl = new GroupLayout(this);
        setLayout(gl);
        this.setBackground(Color.PINK);
        this.number.setFont(this.type.getFont().deriveFont (32.0f));
        this.type.setFont(this.type.getFont().deriveFont (32.0f));
        this.money.setFont(this.money.getFont().deriveFont (32.0f));
        gl.setVerticalGroup(
                gl.createParallelGroup()
                        .addComponent(this.number,10,85,85)
                        .addComponent(this.type,10,85,85)
                        .addComponent(this.money,10,85,85)
                        .addComponent(this.exit,10,100,100)
        );
        gl.setHorizontalGroup(
                gl.createSequentialGroup()
                        .addComponent(this.number,10,100,100)
                        .addComponent(this.type,10,250,250)
                        .addComponent(this.money,10,150,150)
                        .addComponent(this.exit,10,100,100)
        );
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pack pack1 = new Pack();
                pack1.action = Actions.DELETE;
                ArrayList<Account> a = new ArrayList<>();
                a.add(new Account(ID));
                pack1.accounts = a;
                PersonalData b = new PersonalData();
                b.ID = personalID;
                pack1.personalData = b;
                try {
                    client.send(pack1);
                } catch (IOException ex) {
                    System.out.println("Exit "+ ex.getMessage());
                }
            }
        });
    }

}