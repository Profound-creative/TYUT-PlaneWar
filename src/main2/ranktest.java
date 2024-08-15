package main2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ranktest {
	try {
		String sql = "select id, maxscore from playerinfo";
		Connection dbConn;
		Statement stmt = ((java.sql.Connection) dbConn).createStatement();
		ResultSet res = stmt.executeQuery(sql);
		int len = 0;
        for (int i = 0; res.next(); i++) 
        len++;
	} catch(Exception e) {
		e.printStackTrace();
	}
}

class Rank{
	int rank;
	String id;
	String maxscore;
}