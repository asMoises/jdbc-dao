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

		System.out.println("\n=== Test 4: Department Insert ===");
		System.out.print("Enter the Department name: ");
		String name = sc.next();
		Department newDep = new Department(null, name);
		depDao.insert(newDep);
		System.out.println("Inserted! New id: " + newDep.getId());

		System.out.println("\n=== Test 5: Department Update ===");
		System.out.print("Enter the Department Id: ");
		idDep = sc.nextInt();
		newDep = depDao.findById(idDep);
		System.out.println("Enter the new name to: " + newDep.getName());
		name = sc.next();
		newDep.setName(name);
		depDao.update(newDep);
		System.out.println("Update complete! New name set as: " + depDao.findById(idDep).getName());

		System.out.println("\n=== Test 6: Department Delete ===");
		System.out.print("Enter the Department Id to be deleted: ");
		idDep = sc.nextInt();
		depDao.deleteById(idDep);

		list = depDao.findAll();
		for (Department obj : list) {
			System.out.println(obj);
		}

		sc.close();

	}

}
