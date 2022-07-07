package app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class SelectFromSample {

	public static void main(String[] args) {
		Statement st = null;
		ResultSet rs = null;

		try {
			st = DB.getConnection().createStatement();
			// rs = st.executeQuery("select Id, Name, BaseSalary from seller"); // selecting specified fields
			rs = st.executeQuery("select * from seller");

			while (rs.next()) {
				System.out.println("ID: " + rs.getInt("Id") + ", " + rs.getString("Name") + ", R$ "
						+ String.format("%.2f", rs.getDouble("BaseSalary")));
			}

		} catch (SQLException s) {
			s.printStackTrace();
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			DB.closeConnection();
		}
	}
}
