package sheng.orm.test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import sheng.orm.entity.Product;
import sheng.orm.exception.OrmException;
import sheng.orm.service.FakeHibernate;

class FakeHibernateTesting {

	@Test
	void readTesting() {
		try {
			List<Product> productList = new FakeHibernate<Product>().read(Product.class, 100);
			for (int i = 0; i < productList.size(); i++) {
				System.out.println(productList.get(i));
			}
		} catch (OrmException e) {
			Logger.getLogger("selectTesting").log(Level.SEVERE, e.getMessage(),e);
		}
	}
	
	@Test
	void writeTesting() {
		Product p = new Product();
		p.setName("烤豬肉片");
		p.setPrice(120);
		p.setDiscount(0);
		try {
			new FakeHibernate<Product>().write(p);
		} catch (OrmException e) {
			Logger.getLogger("writeTesting").log(Level.SEVERE, e.getMessage(),e);
		}
	}

}
