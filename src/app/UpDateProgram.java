package app;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;

public class UpDateProgram {

	public static void main(String[] args) {

		PreparedStatement st = null;

		try {
			st = DB.getConnection().prepareStatement(
					"update seller " 
			      + "set BaseSalary = BaseSalary + ? "
			      + "where departmentId = ?");
			
			st.setDouble(1, 1.0);
			st.setInt(2, 1);
			int rowsAffeted = st.executeUpdate();

			System.out.println("Done! Affeted rows = " + rowsAffeted);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
  			DB.closeConnection();
		}
	}
}
