package server;

import mysqlhelper.Account;
import mysqlhelper.MySQLHelper;
import mysqlhelper.PersonalData;
import pack.Actions;
import pack.Interaction;
import pack.Pack;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectedClient {
    private Socket s;
    private Interaction inter;
    private PersonalData personalData;
    private ArrayList<Account> accounts;
    private static final ArrayList<ConnectedClient> clients = new ArrayList<>();
    private MySQLHelper help;
    public ConnectedClient(Socket client) throws Exception {
        s = client;
        inter = new Interaction(s);
        clients.add(this);
        help = new MySQLHelper();
    }
    public void start(){
        new Thread(()->{
            try{
                Pack pack =new Pack();
                pack.action = Actions.LOGIN;
                inter.send(pack);
                inter.startInteraction(this::parse);
            } catch (Exception e){
                clients.remove(this);
            }
        }).start();
    }
    public void onError(Exception e)
    {
        Pack errorPack = new Pack();
        errorPack.message = e.getMessage();
        errorPack.action = Actions.ERROR;
        try
        {
            inter.send(errorPack);
        }
        catch(Exception ignored) {

        }
    }
    private Void parse(Pack pack){
       switch (pack.action)
       {
           case REGISTRATION -> {
               try
               {
                   if(help.isExist(pack.personalData.number))
                   {
                       throw new Exception("Данный пользователь уже зарегистрирован");
                   }
                   else
                   {
                       help.insertPersonalData(new PersonalData(0,pack.personalData.name,pack.personalData.number,pack.personalData.password));
                       personalData = help.selectPersonalData(help.getID(pack.personalData.number));
                       accounts = help.selectAccount(help.getID(pack.personalData.number));
                       Pack returnedPack = new Pack();
                       returnedPack.action = Actions.LOGIN;
                       returnedPack.accounts = help.selectAccount(help.getID(pack.personalData.number));
                       returnedPack.personalData = help.selectPersonalData(help.getID(pack.personalData.number));
                       inter.send(returnedPack);
                   }
               }
               catch(Exception e)
               {
                   System.out.println(e.getMessage());
                   onError(e);
               }
           }
           case LOGIN -> {
               try {
                   if (help.isCorrect(pack.personalData.number, pack.personalData.password))
                   {
                       personalData = help.selectPersonalData(help.getID(pack.personalData.number));
                       accounts = help.selectAccount(personalData.ID);
                       personalData = help.selectPersonalData(help.getID(pack.personalData.number));;
                       Pack returnedPack = new Pack();
                       returnedPack.personalData = this.personalData;
                       returnedPack.accounts = this.accounts;
                       returnedPack.action = Actions.DEBIT;
                       inter.send(returnedPack);
                   }
               }
               catch (Exception e)
               {
                   System.out.println(e.getMessage());
                   onError(e);
               }
           }
           case DEBIT -> {
               try
               {
                   help.insertAccount(new Account(0,pack.personalData.ID,"debit card", 0.));
                   accounts = help.selectAccount(help.getID(pack.personalData.number));
                   personalData = help.selectPersonalData(pack.personalData.ID);;
                   Pack returnedPack = new Pack();
                   returnedPack.personalData = help.selectPersonalData(pack.personalData.ID);
                   returnedPack.accounts = help.selectAccount(pack.personalData.ID);
                   returnedPack.action = Actions.DEBIT;
                   inter.send(returnedPack);
               }
               catch (Exception e)
               {
                   onError(e);
               }
           }
           case CREDIT -> {
               try
               {
                   help.insertAccount(new Account(0,pack.personalData.ID,"credit card", 0.));
                   accounts = help.selectAccount(help.getID(pack.personalData.number));
                   personalData = help.selectPersonalData(pack.personalData.ID);;
                   Pack returnedPack = new Pack();
                   returnedPack.personalData = help.selectPersonalData(pack.personalData.ID);
                   returnedPack.accounts = help.selectAccount(pack.personalData.ID);
                   returnedPack.action = Actions.CREDIT;
                   inter.send(returnedPack);
               }
               catch (Exception e)
               {
                   System.out.println(e.getMessage());
                   onError(e);
               }
           }
           case DEPOSIT -> {
               try
               {
                   help.insertAccount(new Account(0,pack.personalData.ID,"deposit", 0.));
                   accounts = help.selectAccount(help.getID(pack.personalData.number));
                   personalData = help.selectPersonalData(pack.personalData.ID);;
                   Pack returnedPack = new Pack();
                   returnedPack.personalData = help.selectPersonalData(pack.personalData.ID);
                   returnedPack.accounts = help.selectAccount(pack.personalData.ID);
                   returnedPack.action = Actions.DEPOSIT;
                   inter.send(returnedPack);
               }
               catch (Exception e)
               {
                   System.out.println(e.getMessage());
                   onError(e);
               }
           }
           case DELETE -> {
               try
               {
                   help.deleteAccount(pack.accounts.get(0).ID);
                   accounts = help.selectAccount(pack.personalData.ID);
                   personalData = help.selectPersonalData(pack.personalData.ID);
                   Pack returnedPack = new Pack();
                   returnedPack.personalData = help.selectPersonalData(pack.personalData.ID);
                   returnedPack.accounts = help.selectAccount(pack.personalData.ID);
                   returnedPack.action = Actions.DEBIT;
                   inter.send(returnedPack);
               }
               catch (Exception e)
               {
                   onError(e);
               }
           }
           case TRANSACTION -> {
               try
               {
                   double tmp = pack.accounts.get(0).money;
                   pack.accounts.get(0).money =  help.selectAccount(pack.accounts.get(0).ID, 1).get(0).money - pack.accounts.get(0).money;
                   pack.accounts.get(1).money = help.selectAccount(pack.accounts.get(1).ID, 1).get(0).money + tmp;
                   help.transaction(pack.accounts.get(0), pack.accounts.get(1));
                   accounts = help.selectAccount(pack.personalData.ID);
                   personalData = help.selectPersonalData(pack.personalData.ID);;
                   Pack returnedPack = new Pack();
                   returnedPack.personalData = help.selectPersonalData(pack.personalData.ID);
                   returnedPack.accounts = help.selectAccount(pack.personalData.ID);
                   returnedPack.action = Actions.DEBIT;
                   inter.send(returnedPack);
               }
               catch (Exception e)
               {
                   onError(e);
               }
           }
           case ERROR -> {}
           default -> {}
       }
        return null;
    }

    public void sendPack(Pack pack) throws IOException {
        inter.send(pack);
    }
}
