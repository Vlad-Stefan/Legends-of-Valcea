package utilz;
import Main.Game;

import java.sql.*;

public class Save {

    Game gp;

    public Save(Game gp) {
        this.gp = gp;
    }

    public void saveAll(){
        Connection c = null;
        PreparedStatement preps = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:company.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS GAME " +
                    "( Level INT)";
            stmt.execute(sql);
            stmt.close();

            // Clear previous entries (optional)
            String clearSql = "DELETE FROM GAME";
            preps = c.prepareStatement(clearSql);
            preps.executeUpdate();
            preps.close();


            // Insert new values
            String insertSql = "INSERT INTO GAME (Level) VALUES (?)";
            preps = c.prepareStatement(insertSql);
            preps.setInt(1, gp.getPlaying().getLm().getLvlI());
            preps.executeUpdate();

            preps.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Record inserted successfully");
    }

    public void loadAll(){
        Connection c = null;
        Statement stmt = null;
        int level=0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:company.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS GAME " +
                    "( Level INT)";
            stmt.execute(sql);
            stmt.close();

            sql = "SELECT COUNT(*) FROM GAME";
            ResultSet test = stmt.executeQuery(sql);
            int count = test.getInt(1);

            if(count == 0){
                System.out.println("No records found");
                System.out.println(count);
                stmt.close();
                c.close();
                gp.getPlaying().getLm().setLvlI(0);
                return;
            }

            ResultSet rs = stmt.executeQuery( "SELECT * FROM GAME;" );
            while ( rs.next() ) {
                level = rs.getInt("Level");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        gp.getPlaying().getLm().setLvlI(level);
        System.out.println(level);
    }
}

