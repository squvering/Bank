package gui;

import client.Client;
import pack.Actions;
import pack.Pack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CardWindow extends JFrame {
    private JButton transaction = new JButton("Отправить деньги");
    private JButton addDebit = new JButton("Добавить дебетовую карту");
    private JButton addCredit = new JButton("Добавить кредитную карту");
    private JButton addDeposit = new JButton("Добавить депозит");

    public CardWindow(Pack pack, Client client) {
        addCredit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pack pack1 = new Pack();
                pack1.action = Actions.CREDIT;
                pack1.accounts = pack.accounts;
                pack1.personalData = pack.personalData;
                try {
                    client.send(pack1);
                } catch (Exception ex) {
                    System.out.println("Credit"+ex.getMessage());
                }
            }
        });
        addDebit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pack pack1 = new Pack();
                pack1.action = Actions.DEBIT;
                pack1.accounts = pack.accounts;
                pack1.personalData = pack.personalData;
                try {
                    client.send(pack1);
                } catch (Exception ex) {
                    System.out.println("Debit"+ex.getMessage());
                }
            }
        });
        addDeposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pack pack1 = new Pack();
                pack1.action = Actions.DEPOSIT;
                pack1.accounts = pack.accounts;
                pack1.personalData = pack.personalData;
                try {
                    client.send(pack1);
                } catch (Exception ex) {
                    System.out.println("Deposit"+ex.getMessage());
                }
            }
        });
        transaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransactionWindow(client, pack).setVisible(true);
            }
        });
        this.setSize(580, 600);
        this.setMaximumSize(new Dimension(580,600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel grid = new JPanel();
        this.getContentPane().add(grid);
        grid.setMaximumSize(new Dimension(580,600));
        GridLayout layout = new GridLayout(pack.accounts.size()+4, 1, 0, 10);
        grid.setLayout(layout);
        grid.add(transaction);
        grid.add(addCredit);
        grid.add(addDeposit);
        grid.add(addDebit);
        for (int i = 0; i < pack.accounts.size(); i++) {
            grid.add(new Card(pack.accounts.get(i),client));
        }
        /*GroupLayout gl = new GroupLayout(getContentPane());
        setLayout(gl);
        switch (pack.accounts.size()) {
            case 0 -> {
                gl.setVerticalGroup(
                        gl.createSequentialGroup()
                                .addGap(10)
                                .addComponent(transaction, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addCredit, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addDebit, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addDeposit, 60, 60, 60)
                                .addGap(10)
                );
                gl.setHorizontalGroup(
                        gl.createParallelGroup()
                                .addComponent(transaction, 0, 570, 570)
                                .addComponent(addCredit, 0, 570, 570)
                                .addComponent(addDebit, 0, 570, 570)
                                .addComponent(addDeposit, 0, 570, 570)
                );
            }
            case 1 -> {
                Card a = new Card(pack.accounts.get(0));
                gl.setVerticalGroup(
                        gl.createSequentialGroup()
                                .addGap(10)
                                .addComponent(transaction, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addCredit, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addDebit, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addDeposit, 60, 60, 60)
                                .addGap(10)
                                .addComponent(a, 10, 100, 100)
                                .addGap(10)
                );
                gl.setHorizontalGroup(
                        gl.createParallelGroup()
                                .addComponent(transaction, 0, 570, 570)
                                .addComponent(addCredit, 0, 570, 570)
                                .addComponent(addDebit, 0, 570, 570)
                                .addComponent(addDeposit, 0, 570, 570)
                                .addComponent(a, 0, 580, 580)

                );
            }
            case 2 -> {
                Card a = new Card(pack.accounts.get(0));
                Card b = new Card(pack.accounts.get(1));
                gl.setVerticalGroup(
                        gl.createSequentialGroup()
                                .addGap(10)
                                .addComponent(transaction, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addCredit, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addDebit, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addDeposit, 60, 60, 60)
                                .addGap(10)
                                .addComponent(a, 10, 100, 100)
                                .addGap(10)
                                .addComponent(b, 10, 100, 100)
                                .addGap(10)
                );
                gl.setHorizontalGroup(
                        gl.createParallelGroup()
                                .addComponent(transaction, 0, 570, 570)
                                .addComponent(addCredit, 0, 570, 570)
                                .addComponent(addDebit, 0, 570, 570)
                                .addComponent(addDeposit, 0, 570, 570)
                                .addComponent(a, 0, 580, 580)
                                .addComponent(b, 0, 580, 580)
                );
            }
            default -> {
                Card a = new Card(pack.accounts.get(0));
                Card b = new Card(pack.accounts.get(1));
                Card c = new Card(pack.accounts.get(2));
                gl.setVerticalGroup(
                        gl.createSequentialGroup()
                                .addGap(10)
                                .addComponent(transaction, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addCredit, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addDebit, 60, 60, 60)
                                .addGap(10)
                                .addComponent(addDeposit, 60, 60, 60)
                                .addGap(10)
                                .addComponent(a, 10, 100, 100)
                                .addGap(10)
                                .addComponent(b, 10, 100, 100)
                                .addGap(10)
                                .addComponent(c, 10, 100, 100)
                                .addGap(10)
                );
                gl.setHorizontalGroup(
                        gl.createParallelGroup()
                                .addComponent(transaction, 0, 570, 570)
                                .addComponent(addCredit, 0, 570, 570)
                                .addComponent(addDebit, 0, 570, 570)
                                .addComponent(addDeposit, 0, 570, 570)
                                .addComponent(a, 0, 580, 580)
                                .addComponent(b, 0, 580, 580)
                                .addComponent(c, 0, 580, 580)

                );
            }
        }*/

    }
}
