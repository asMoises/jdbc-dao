package app;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbIntegrityException;

public class DeleteProgram {

	public static void main(String[] args) {

		PreparedStatement st = null;

		try {
			st = DB.getConnection().prepareStatement("delete from seller where id = ?");

			st.setInt(1, 10);
			int rowsAffeted = st.executeUpdate();

			System.out.println("Done! Affeted rows = " + rowsAffeted);

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
