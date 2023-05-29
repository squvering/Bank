package pack;

import mysqlhelper.Account;
import mysqlhelper.PersonalData;

import java.io.Serializable;
import java.util.ArrayList;

public class Pack implements Serializable {
    public ArrayList<Account> accounts;
    public PersonalData personalData;
    public Actions action;
    public String message;

}
