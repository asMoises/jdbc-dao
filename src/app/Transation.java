package app;

import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Transation {

	public static void main(String[] args) {

		Statement st = null;

		try {
			st = DB.getConnection().createStatement();
			DB.getConnection().setAutoCommit(false); // do not auto commit

			int rows1 = st.executeUpdate("update seller set BaseSalary = 2090.0 where DepartmentId = 1");

			/*
			 * int x = 1; if (x == 1) { throw new SQLException("Fake error"); }
			 */

			int rows2 = st.executeUpdate("update seller set BaseSalary = 4090.0 where DepartmentId = 2");

			System.out.println("Rows1 = " + rows1);
			System.out.println("Rows2 = " + rows2);

			DB.getConnection().commit();

		} catch (SQLException e) {
			try {
				DB.getConnection().rollback();
				throw new DbException("Transaction rolled back! Cause by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Cause by: " + e1.getMessage());
			}
		} finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
