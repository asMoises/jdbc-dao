package app;

import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

public class ProgramaTesting {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();

		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

	}

}
