package mysqlhelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import com.lambdaworks.crypto.SCryptUtil;
public class MySQLHelper {
    private Connection con;
    private String user = "root";
    private String password = "root";
    private String host = "localhost";
    private int port = 3306;
    private String connStr = "jdbc:mysql://" + host + ":" + port;
    public MySQLHelper() throws Exception {
        con = DriverManager.getConnection(connStr, user, password);

    }
    public boolean isExist(long number) throws SQLException {
        String sql = "select count(*) a from bank.personaldata where number = " + number;
        ResultSet a = con.createStatement().executeQuery(sql);
        a.next();
        return (a.getInt(1) == 1);
    }
    public ArrayList<Account> selectAccount(int personalID) throws Exception {
        String sql = "select * from bank.account where personalid = " + personalID;
        ResultSet a = con.createStatement().executeQuery(sql);
        ArrayList<Account> accs = new ArrayList<>();
        while (a.next())
        {
            accs.add(new Account(a.getInt(1),a.getInt(2),a.getString(3), a.getDouble(4)));
        }
        return accs;
    }
    public ArrayList<Account> selectAccount(int ID, int prostoTakMneLenPisat) throws Exception {
        String sql = "select * from bank.account where id = " + ID;
        ResultSet a = con.createStatement().executeQuery(sql);
        ArrayList<Account> accs = new ArrayList<>();
        while (a.next())
        {
            accs.add(new Account(a.getInt(1),a.getInt(2),a.getString(3), a.getDouble(4)));
        }
        return accs;
    }
    public PersonalData selectPersonalData(int ID) throws SQLException {
        String sql = "select * from bank.personaldata where ID = " + ID;
        ResultSet a = con.createStatement().executeQuery(sql);
        a.next();
        return new PersonalData(a.getInt(1),a.getString(4), a.getLong(2),a.getString(3));
    }
    public void insertAccount(Account chel) throws SQLException {
        String sql = "insert into bank.account(ID, personalid, type, money) values(" + chel.ID + ","+ chel.personalID +",'" + chel.type + "'," + chel.money+")";
        con.createStatement().execute(sql);
    }
    public void insertPersonalData(PersonalData chel) throws SQLException {
        String sql = "insert into bank.personaldata(ID, name, number, password) values(" + chel.ID + "," + chel.name + "," + chel.number+",'"+chel.password+"' )";
        con.createStatement().execute(sql);
    }
    public void updateAccount(Account chel) throws SQLException {
        String sql = "update bank.account set " +
                (chel.type != null ? "type = " + chel.type + "," : "") +
                (chel.money != null ? "money = " + chel.money : "") +
                (chel.personalID != null ? "personalid = " + chel.personalID : "") +
                "where ID = " + chel.ID;
        con.createStatement().execute(sql);
    }
    public void updatePersonalData(PersonalData chel) throws SQLException {
        String sql = "update bank.personaldata set " +
                (chel.number != null ? "type = " + chel.number + "," : "") +
                (chel.name != null ? "money = " + chel.name : "") +
                (chel.password != null ? "money = " + chel.password : "") +
                "where ID = " + chel.ID;
        con.createStatement().execute(sql);
    }
    public String getName(int ID) throws SQLException {
        String sql = "select name from bank.account where id =" + ID;
        ResultSet a = con.createStatement().executeQuery(sql);
        a.next();
        return a.getString(1);
    }
    public int getID(long number) throws SQLException {
        String sql = "select ID from bank.personalData where number =" + number;
        ResultSet a = con.createStatement().executeQuery(sql);
        a.next();
        return a.getInt(1);
    }
    public void deleteAccount(int ID) throws SQLException {
        String sql = "delete from bank.account where id =" + ID;
        con.createStatement().execute(sql);
    }
    public boolean isCorrect(long number, String password) throws SQLException {
        String sql = "SELECT * FROM bank.personalData where number = " + number;
        ResultSet set = con.createStatement().executeQuery(sql);
        set.next();
        boolean a =SCryptUtil.check(set.getString(2), password);
        return a;
    }
    public void transaction(Account first, Account second) throws SQLException {




       /* String sql =
                " update bank.account set " +
                (first.type != null ? "type = '" + first.type + "'," : "") +
                (first.money != null ? "money = " + first.money : "") +
                (first.personalID != null ? "personalid = " + first.personalID : "") +
                " where ID = " + first.ID + ";" +
                " update bank.account set " +
                (second.type != null ? "type = '" + second.type + "'," : "") +
                (second.money != null ? "money = " + second.money : "") +
                (second.personalID != null ? "personalid = " + second.personalID : "") +
                " where ID = " + second.ID + ";" ;*/
        var st1  = con.prepareStatement("update bank.account set money = ? where ID = ? ");
        var st2  = con.prepareStatement("update bank.account set money = ? where ID = ? ");
        st1.setDouble(1,first.money);
        st1.setInt(2,first.ID);
        st2.setDouble(1,second.money);
        st2.setInt(2,second.ID);
        con.createStatement().executeQuery("START TRANSACTION ");
        st1.executeUpdate();
        st2.executeUpdate();
        con.createStatement().executeQuery("COMMIT ");
    }
}
