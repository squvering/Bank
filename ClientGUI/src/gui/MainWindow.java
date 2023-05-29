package gui;


import client.BankEvent;
import client.BankListener;
import client.Client;
import mysqlhelper.Account;
import mysqlhelper.PersonalData;
import pack.Actions;
import pack.Pack;


public class MainWindow implements BankListener {
    private RegWindow regWindow;
    private LogWindow logWindow;
    private CardWindow cardWindow;
    private Client client;
    public MainWindow(String host, int port){
        this.client = new Client(host, port);
        client.addListener(this);
        try {
            client.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onError(Exception e)
    {
        Pack errorPack = new Pack();
        errorPack.message = e.getMessage();
        errorPack.action = Actions.ERROR;
        try
        {
            client.send(errorPack);
        }
        catch(Exception ignored) {

        }
    }
    public void closeAllWindows()
    {
        if (regWindow != null)
            regWindow.dispose();
        if (logWindow != null)
            logWindow.dispose();
        if (cardWindow != null)
            cardWindow.dispose();
    }
    @Override
    public void BankAvailable(BankEvent event) {
        switch (event.getMessage1().action) {
            case REGISTRATION -> {
                try {
                    closeAllWindows();
                    regWindow = new RegWindow(client);
                    regWindow.setVisible(true);
                } catch (Exception e) {
                    onError(e);
                }
            }
            case LOGIN -> {
                try {
                    closeAllWindows();
                    logWindow = new LogWindow(client);
                    logWindow.setVisible(true);
                } catch (Exception e) {
                    onError(e);
                }
            }
            case DEBIT -> {
                try {
                    closeAllWindows();
                    cardWindow = new CardWindow(event.getMessage1(),client);
                    cardWindow.setVisible(true);
                } catch (Exception e) {
                    System.out.println("Debit" + e.getMessage());
                    onError(e);
                }
            }
            case CREDIT -> {
                try {
                    closeAllWindows();
                    cardWindow = new CardWindow(event.getMessage1(),client);
                    cardWindow.setVisible(true);
                } catch (Exception e) {
                    System.out.println("CREdit" + e.getMessage());
                    onError(e);
                }
            }
            case DEPOSIT -> {
                try {
                    closeAllWindows();
                    cardWindow = new CardWindow(event.getMessage1(),client);
                    cardWindow.setVisible(true);
                } catch (Exception e) {
                    System.out.println("DEposit" + e.getMessage());
                    onError(e);
                }
            }
            case DELETE -> {
                try {
                    closeAllWindows();
                    cardWindow = new CardWindow(event.getMessage1(),client);
                } catch (Exception e) {
                    onError(e);
                }
            }
            case TRANSACTION -> {
                try {

                } catch (Exception e) {
                    onError(e);
                }
            }
            case ERROR -> {
            }
            default -> {
            }
        }
    }
}