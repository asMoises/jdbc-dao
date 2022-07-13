package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.cj.xdevapi.PreparableStatement;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS); // returns the generated keys.

			// called place holders
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDateBirthdate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId()); // remember, it is an object with id and name. It is necessary to
														// navigate into then.
			/*
			 * Detail: the ResultSet was not used because it is not a query! It is an
			 * insert!!
			 */
			int rowsAffected = st.executeUpdate();// if the returns is different of zero, everything is OK, inserted!

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller SET Name=?, Email=?, BirthDate=?, BaseSalary=?, DepartmentId=? WHERE Id=?",
					Statement.RETURN_GENERATED_KEYS); // returns the generated keys.

			// called place holders
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDateBirthdate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId()); // remember, it is an object with id and name. It is necessary to
														// navigate into then.
			// This one is to where clause
			st.setInt(6, obj.getId());

			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) { // implementing using interface
		PreparedStatement st = null;
		ResultSet rs = null; // There is no object at zero position.

		try {
			st = conn.prepareStatement(
					"select seller.*, department.Name as DepName " + "from seller inner join department "
							+ "on seller.DepartmentId = department.Id " + "where seller.Id = ?;");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) { // if it is not null (not the first position - zero)
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);

				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	/*
	 * First, create Department obj with all fields and send it to create the Seller
	 * obj, it will create the associate between the objects.
	 */

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		// inserting fields of Seller - dao.entity - using the "rs" object from select
		// result
		obj.setId(rs.getInt("Id")); // pay attention at column name from database, between "".
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setDateBirthdate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			st = conn.prepareStatement("  Select seller.*, " + "department.Name as DepName "
					+ "from seller inner join department " + "on seller.DepartmentId = department.Id order by Name;");

			rs = st.executeQuery();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list; // must to be treated in case off any data!!!!
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) { // to remember, this department came with a null value
																	// to name field.
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// creating a list to be seeded
			List<Seller> list = new ArrayList<>();

			// creating a map to compare department verifying if it already exist
			Map<Integer, Department> map = new HashMap<>();

			// getting data from database
			st = conn.prepareStatement(
					"  Select seller.*, " + "department.Name as DepName " + "from seller inner join department "
							+ "on seller.DepartmentId = department.Id " + "where DepartmentId = ? order by Name;");

			// accessing database
			st.setInt(1, department.getId()); // getting data from a select statement
			rs = st.executeQuery(); // data formated in a table in a ResultSet object

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId")); // verify if the department already exist in the
																		// Map structure (temporarily, Just to check in
																		// the next time inside the while structure), if
																		// not, returns null into dep.

				if (dep == null) { // if do not exist, it is necessary to instantiate a new one.
					dep = instantiateDepartment(rs); // sending all the ResultSet.

					map.put(rs.getInt("DepartmentId"), dep); // inserting a department inside Map structure to be not
																// more null in the next time, so do not creating more
																// than one department using the same ID.
				}

				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);

			}
			return list; // must to be treated in case off any data!!!!

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			// don't close the connection, it can be used by another classes. It means, the
			// same data can be used.
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

}
