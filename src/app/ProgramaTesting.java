package app;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class ProgramaTesting {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("=== Test 1: Seller findById ===");
		System.out.print("Enter the ID seller: ");
		int idSeller = sc.nextInt();
		SellerDao sellerDao = DaoFactory.createSellerDao();
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

		sc.close();
	}

}
