package gui;

import client.BankEvent;
import client.Client;
import com.lambdaworks.crypto.SCryptUtil;
import mysqlhelper.PersonalData;
import pack.Actions;
import pack.Pack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class RegWindow extends JFrame {
    private int MIN_SZ = GroupLayout.PREFERRED_SIZE;
    private int MAX_SZ = GroupLayout.DEFAULT_SIZE;
    private JLabel lblPhone;
    private JLabel lblName;
    private JLabel lblPassword;
    private JLabel lblPassword2;

    private JTextField tfPhone;
    private JTextField tfName;
    private JTextField tfPassword;
    private JTextField tfPassword2;

    private JButton btnReg;
    private JButton btnCancel;
    private Client client;

    public RegWindow(Client client){
        this.client = client;
        setSize(600,450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GroupLayout gl = new GroupLayout(getContentPane());
        setLayout(gl);
        lblPhone = new JLabel("Номер телефона: ");
        lblName = new JLabel("ФИО: ");
        lblPassword = new JLabel("Пароль: ");
        lblPassword2 = new JLabel("Повтор пароля: ");
        tfPhone = new JTextField();
        tfName = new JTextField();
        tfPassword = new JTextField();
        tfPassword2 = new JTextField();
        btnReg = new JButton("Зарегистрироваться");
        btnCancel = new JButton("Я уже смешарик");
        btnReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pack pack = new Pack();
                pack.action =Actions.REGISTRATION;
                try {
                    if(!tfPassword.getText().equals(tfPassword2.getText()))
                        throw new IOException("Пароли не совпадают");
                    String password = SCryptUtil.scrypt(tfPassword.getText(),8 ,10,10);
                    String f = tfPhone.getText();
                    long c = Long.parseLong(f);
                    pack.personalData = new PersonalData(0,tfName.getText(),c,password);
                    client.send(pack);
                    Pack pack1 = new Pack();
                    pack1.action = Actions.LOGIN;
                    client.fireEvent(new BankEvent(pack1));
                } catch (IOException ex) {
                    System.out.println("reg" + ex.getMessage());
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pack pack = new Pack();
                pack.action = Actions.LOGIN;
                client.fireEvent(new BankEvent(pack));
            }
        });
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
                                                .addComponent(lblName, MIN_SZ, MIN_SZ, MIN_SZ)
                                                .addGap(8)
                                                .addComponent(tfName,MAX_SZ, MAX_SZ, MAX_SZ)
                                )
                                .addGroup(
                                        gl.createSequentialGroup()
                                                .addComponent(lblPassword, MIN_SZ, MIN_SZ, MIN_SZ)
                                                .addGap(8)
                                                .addComponent(tfPassword,MAX_SZ, MAX_SZ, MAX_SZ)
                                )
                                .addGroup(
                                        gl.createSequentialGroup()
                                                .addComponent(lblPassword2, MIN_SZ, MIN_SZ, MIN_SZ)
                                                .addGap(8)
                                                .addComponent(tfPassword2,MAX_SZ, MAX_SZ, MAX_SZ)
                                )
                                .addGroup(
                                        gl.createSequentialGroup()
                                                .addGap(8,8,Integer.MAX_VALUE)
                                                .addComponent(btnReg, MIN_SZ, MIN_SZ, MIN_SZ)
                                                .addGap(8)
                                                .addComponent(btnCancel,MIN_SZ, MIN_SZ, MIN_SZ)
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
                                        .addComponent(lblName,MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(tfName, MIN_SZ, MIN_SZ, MIN_SZ)
                        )
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(lblPassword,MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(tfPassword, MIN_SZ, MIN_SZ, MIN_SZ)
                        )
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(lblPassword2,MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(tfPassword2, MIN_SZ, MIN_SZ, MIN_SZ)
                        )
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(btnReg,MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(btnCancel, MIN_SZ, MIN_SZ, MIN_SZ)
                        )
                        .addGap(8,8,Integer.MAX_VALUE)
        );
        gl.linkSize(0, lblPhone, lblName, lblPassword, lblPassword2);
        gl.linkSize(0,btnReg,btnCancel);
    }
}