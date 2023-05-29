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
import java.util.List;

public class TransactionWindow extends JFrame {
    private JButton send = new JButton("send");
    private JTextField toID = new JTextField();
    private JTextField fromID = new JTextField();
    private JTextField sum = new JTextField();
    private int MIN_SZ = 40;
    private int MAX_SZ = GroupLayout.DEFAULT_SIZE;
    private Pack pack1;

    public TransactionWindow(Client client, Pack pack)
    {
        this.pack1 = pack;
        setSize(200,150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GroupLayout gl = new GroupLayout(getContentPane());
        setLayout(gl);
        gl.setHorizontalGroup(
                gl.createSequentialGroup()
                        .addGap(8,8, Integer.MAX_VALUE)
                        .addGroup(gl.createParallelGroup()
                                .addGroup(
                                        gl.createSequentialGroup()
                                                .addComponent(fromID, MIN_SZ, MIN_SZ, MIN_SZ)
                                                .addGap(8)
                                                .addComponent(sum,MAX_SZ, MAX_SZ, MAX_SZ)
                                )
                                .addGroup(
                                        gl.createSequentialGroup()
                                                .addComponent(toID, MIN_SZ, MIN_SZ, MIN_SZ)
                                                .addGap(8)
                                                .addComponent(send,MAX_SZ, MAX_SZ, MAX_SZ)
                                )
                        )
                        .addGap(8,8, Integer.MAX_VALUE)
        );
        gl.setVerticalGroup(
                gl.createSequentialGroup()
                        .addGap(8,8,Integer.MAX_VALUE)
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(fromID,MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(sum, MIN_SZ, MIN_SZ, MIN_SZ)
                        )
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(toID,MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(send, MIN_SZ, MIN_SZ, MIN_SZ)
                        )
                        .addGap(8,8,Integer.MAX_VALUE)
        );
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean check = true;

                for(int i = 0; i<pack.accounts.size();i++)
                {
                    if(Integer.parseInt(fromID.getText()) == pack.accounts.get(i).ID)
                    {
                        if(pack.accounts.get(i).money >= Integer.parseInt(sum.getText()))
                        {
                            check = false;
                        }
                    }
                }
                if(Integer.parseInt(sum.getText()) <= 0 || check)
                {
                    send.setBackground(Color.red);
                }
                else
                {
                    Pack pack2 = new Pack();
                    pack2.action = Actions.TRANSACTION;
                    pack2.personalData = pack1.personalData;
                    ArrayList<Account> a = new ArrayList<>();
                    Account b= new Account(Integer.parseInt(fromID.getText()));
                    b.money = Double.parseDouble(sum.getText());
                    Account c = new Account(Integer.parseInt(toID.getText()));
                    a.add(b);
                    a.add(c);
                    pack2.accounts = a;
                    try {
                        client.send(pack2);
                        dis();
                    } catch (IOException ex) {
                        System.out.println("Transaction" + ex.getMessage());
                    }
                }
            }
        });
    }
    private void dis()
    {
        this.dispose();
    }
}
