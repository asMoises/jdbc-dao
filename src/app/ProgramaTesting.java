package app;

import java.util.Date;

import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

public class ProgramaTesting {

	public static void main(String[] args) {
		Department dep = new Department(1, "Books");
		Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.0, dep);

		SellerDao sellerDao = DaoFactory.createSellerDao();
		System.out.println(seller);

	}

}
