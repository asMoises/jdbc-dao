package app;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		DepartmentDao depDao = DaoFactory.createDepartmentDao();

		System.out.println("=== Test 2: Department findById ===");
		System.out.print("Enter the ID Department: ");
		int idDep = sc.nextInt();
		Department department = depDao.findById(idDep);
		System.out.println(department);

		System.out.println("\n=== Test 3: Department findAll ===");
		List<Department> list = depDao.findAll();
		for (Department obj : list) {
			System.out.println(obj);
		}

		sc.close();

	}

}
