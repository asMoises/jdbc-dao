package app;

import java.util.Date;

import entities.Department;
import entities.Seller;

public class ProgramaTesting {

	public static void main(String[] args) {
		Department dep = new Department(1, "Books");
		Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.0, dep);
		System.out.println(seller);

	}

}
