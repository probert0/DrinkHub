package logic.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import logic.bean.SponsorBean;
import logic.model.Sponsor;

public class SponsorDao {
    
    private SponsorDao() {
        throw new IllegalStateException("Utility class init");
    }
    
    public static Sponsor tryFindSponsorByUsername(String userNameSponsor) {
    	Statement state = null;
        Connection connect = null;
        DatabaseManager dbMan = MariaDBDatabaseManager.getInstance();

        int type = 0;
        Date timeline = null;
        try {
            
            connect = dbMan.openConnection();
            state = dbMan.openStatement(connect);
            
            String sql = "SELECT type, timeline FROM test.sponsor where username = '"
                    + userNameSponsor + "';";
            ResultSet rs = state.executeQuery(sql);

            if (!rs.first()) {
            	Sponsor s = new Sponsor(userNameSponsor, 0, null);
            	dbMan.closeStatement(state);
                dbMan.closeConnection(connect);
            	return s;
            }

            boolean moreThanOne = rs.first() && rs.next();
            assert !moreThanOne;
            rs.first();

            type = rs.getInt("type");
            timeline = rs.getDate("timeline");

            rs.close();
            dbMan.closeStatement(state);
            dbMan.closeConnection(connect);
        } catch (SQLException e2) {
			// Opening connection, or opening statement, or executing query failed
            e2.printStackTrace();
        }
    	return new Sponsor(userNameSponsor, type, timeline);
    
    }
    
    public static void findSponsorByUsername(SponsorBean bean) {
    	Statement state = null;
        Connection connect = null;
        DatabaseManager dbMan = MariaDBDatabaseManager.getInstance();

        int type = 0;
        Date timeline = null;
        try {
            
            connect = dbMan.openConnection();
            state = dbMan.openStatement(connect);
            
            String sql = "SELECT type, timeline FROM test.sponsor where username = '"
                    + bean.getUserSponsor() + "';";
            ResultSet rs = state.executeQuery(sql);

            if (!rs.first()) {
            	bean.setTypeSponsor(0);
            	bean.setTimeSponsor(null);
            	dbMan.closeStatement(state);
                dbMan.closeConnection(connect);
            	return;
            }

            boolean moreThanOne = rs.first() && rs.next();
            assert !moreThanOne;
            rs.first();

            type = rs.getInt("type");
            timeline = rs.getDate("timeline");

            rs.close();
            dbMan.closeStatement(state);
            dbMan.closeConnection(connect);
        } catch (SQLException e3) {
			// Opening connection, or opening statement, or executing query failed
            e3.printStackTrace();
        }
    	
    	bean.setTypeSponsor(type);
    	bean.setTimeSponsor(timeline);
    }
    
    
    public static boolean setNewSponsor(Sponsor s) {
    	Statement stat = null;
        Connection conne = null;
        DatabaseManager dbMan = MariaDBDatabaseManager.getInstance();
        try {
        	conne = dbMan.openConnection();
            stat = dbMan.openStatement(conne);
            
            String mysql = "INSERT INTO test.sponsor values('"+ s.getUsername() +"'," + s.getType() + ",'" + s.getTimeline() + "');";
            int res = stat.executeUpdate(mysql);
            
            //res=1 if success
            if(res != 1) {
            	return false;
            }
            
            dbMan.closeStatement(stat);
            dbMan.closeConnection(conne);
        } catch (SQLException e4) {
			// Opening connection, or opening statement, or executing query failed
            e4.printStackTrace();
            return false;
        }
        dbMan.closeStatement(stat);
        dbMan.closeConnection(conne);
        return true;
    }
    
    public static void deleteSponsor() {
    	Statement stat = null;
        Connection conne = null;
        DatabaseManager dbMan = MariaDBDatabaseManager.getInstance();
        try {
        	conne = dbMan.openConnection();
            stat = dbMan.openStatement(conne);
            
            String sql = "DELETE FROM test.sponsor WHERE (timeline < current_date);";
            stat.executeUpdate(sql);

            dbMan.closeStatement(stat);
            dbMan.closeConnection(conne);
        } catch (SQLException e5) {
			// Opening connection, or opening statement, or executing query failed
            e5.printStackTrace();
        }
        dbMan.closeStatement(stat);
        dbMan.closeConnection(conne);
    }
    
    
}
