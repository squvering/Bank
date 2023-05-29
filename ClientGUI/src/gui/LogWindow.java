package gui;

import client.BankEvent;
import client.Client;
import com.lambdaworks.crypto.SCrypt;
import com.lambdaworks.crypto.SCryptUtil;
import mysqlhelper.Account;
import mysqlhelper.PersonalData;
import pack.Actions;
import pack.Pack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class LogWindow extends JFrame {
    private int MIN_SZ = GroupLayout.PREFERRED_SIZE;
    private int MAX_SZ = GroupLayout.DEFAULT_SIZE;
    private JLabel lblPhone;
    private JLabel lblPassword;

    private JTextField tfPhone;
    private JTextField tfPassword;

    private JButton btnReg;
    private JButton btnEnter;
    private Client client;

    public LogWindow(Client client){
        this.client = client;
        setSize(600,450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GroupLayout gl = new GroupLayout(getContentPane());
        setLayout(gl);
        lblPhone = new JLabel("Номер телефона: ");
        lblPassword = new JLabel("Пароль: ");
        tfPhone = new JTextField();
        tfPassword = new JTextField();
        btnReg = new JButton("Зарегистрироваться");
        btnEnter = new JButton("Войти");

        gl.setHorizontalGroup(
                gl.createSequentialGroup()
                        .addGap(8,8, Integer.MAX_VALUE)
                        .addGroup(gl.createParallelGroup()
                                .addGroup(
                                        gl.createSequentialGroup()
                                                .addComponent(lblPhone, MIN_SZ, MIN_SZ, MIN_SZ)
                                                .addGap(8)
                                                .addComponent(tfPhone,MAX_SZ, MAX_SZ, MAX_SZ)
                                )
                                .addGroup(
                                        gl.createSequentialGroup()
                                                .addComponent(lblPassword, MIN_SZ, MIN_SZ, MIN_SZ)
                                                .addGap(8)
                                                .addComponent(tfPassword,MAX_SZ, MAX_SZ, MAX_SZ)
                                )
                                .addGroup(
                                        gl.createSequentialGroup()
                                                .addGap(8,8,Integer.MAX_VALUE)
                                                .addComponent(btnReg, MIN_SZ, MIN_SZ, MIN_SZ)
                                                .addGap(8)
                                                .addComponent(btnEnter,MIN_SZ, MIN_SZ, MIN_SZ)
                                                .addGap(8,8,Integer.MAX_VALUE)
                                )
                        )
                        .addGap(8,8, Integer.MAX_VALUE)
        );
        gl.setVerticalGroup(
                gl.createSequentialGroup()
                        .addGap(8,8,Integer.MAX_VALUE)
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(lblPhone,MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(tfPhone, MIN_SZ, MIN_SZ, MIN_SZ)
                        )
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(lblPassword,MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(tfPassword, MIN_SZ, MIN_SZ, MIN_SZ)
                        )
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(btnReg,MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(btnEnter, MIN_SZ, MIN_SZ, MIN_SZ)
                        )
                        .addGap(8,8,Integer.MAX_VALUE)
        );
        gl.linkSize(0, lblPhone, lblPassword);
        gl.linkSize(0,btnReg,btnEnter);
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pack pack = new Pack();
                pack.action = Actions.LOGIN;
                String password = SCryptUtil.scrypt(tfPassword.getText(),8,10,10);
                pack.personalData = new PersonalData(0,null,Long.parseLong(tfPhone.getText()),password);
                try {
                    client.send(pack);
                } catch (IOException ex) {
                    System.out.println("Войти " + ex.getMessage());
                }
            }
        });
        btnReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pack pack = new Pack();
                pack.action = Actions.REGISTRATION;
                client.fireEvent(new BankEvent(pack));
            }
        });
    }
}
