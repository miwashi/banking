package net.miwashi.banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountRepository {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE ACCOUNT(ID BIGINT AUTO_INCREMENT, HOLDER VARCHAR(255), BALANCE INT DEFAULT 0)";
    private static final String CONNECTION_STRING_IM = "jdbc:h2:mem:";
    private static final String CONNECTION_STRING_FILE = "jdbc:h2:./account_db";

    public static boolean createTable(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(CONNECTION_STRING_FILE);
            var stmt = con.createStatement();
            stmt.execute("DROP TABLE IF EXISTS ACCOUNT");
            return stmt.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            }catch(Exception ignore){System.err.println(ignore);}
        }
        return false;
    }

    public Account create(Account account) {
        Connection con = null;//Anslutning till databasen
        try {//Vi Försöker, fast vi vet att det kan gå fel
            con = DriverManager.getConnection(CONNECTION_STRING_FILE);//Fixa koppling till en H2 databas på fil.
            //Om vi lägger till Statement.RETURN_GENERATED_KEYS så kommer vi att kunna hämta de värden som autogenererats efter att vi kört frågan.
            var stmt = con.prepareStatement("INSERT INTO ACCOUNT(HOLDER) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, account.getHolder());//Ersätt frågetecken 1 med Holder i Account!
            stmt.execute();//Kör frågan.
            var rs = stmt.getGeneratedKeys();//Hämta det ID som genererats i databasen.
            if(rs.next()){//Om vi har en rad i resultatet, så gå till den raden.
                int accountId = rs.getInt(1);//Hämta värdet i den första kolumnen i den första raden.
                account.setId(accountId);//Lägg indexet i våran account
            }
        } catch (SQLException e) {//Oj det gick fel
            e.printStackTrace();
        } finally {//Oavsett om det gick rätt eller fel, hamnar vi här.
            try {
                con.close();
            }catch(Exception ignore){}
        }
        return account;
    }
}
