package app;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class ProgramaTesting {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println("=== Test 1: Seller findById ===");
		System.out.print("Enter the ID seller: ");
		int idSeller = sc.nextInt();
		Seller seller = sellerDao.findById(idSeller);// searching data on database
		System.out.println(seller);

		System.out.println("\n=== Test 2: Seller findByDepartmentId ===");
		System.out.print("Enter the ID Department: ");
		int idDep = sc.nextInt();
		Department department = new Department(idDep, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n=== Test 3: Seller and Department findAll ===");
		list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n=== Test 4: Seller Insert ===");
		System.out.print("Enter the seller name: ");
		String name = sc.next();
		sc.nextLine();
		System.out.print("Enter the " + name + " salary: ");
		Double salary = sc.nextDouble();
		Seller newSeller = new Seller(null, name, name + "@gmail.com", new Date(), salary, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id: " + newSeller.getId());

		System.out.println("\n=== Test 5: Seller Update ===");
		System.out.print("Enter the Seller Id: ");
		idSeller = sc.nextInt();
		seller = sellerDao.findById(idSeller);
		System.out.println("Enter the new name to: " + seller.getName());
		name = sc.next();
		seller.setName(name);
		sellerDao.update(seller);
		System.out.println("Update complete! New name set as: " + sellerDao.findById(idSeller).getName());

		sc.close();
	}

}
