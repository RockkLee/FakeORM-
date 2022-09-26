package sheng.orm.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sheng.orm.entity.Product;

class ProductTesting {

	@Test
	void DiscountTesting() {
		Product p  = new Product("黑橋牌香腸", 0);
		p.setPrice(120.0);
		p.setDiscount(0);
		assertEquals(120, p.getUnitPrice());
		p.setDiscount(10);
		assertEquals(120*0.9, p.getUnitPrice());
	}
	
	@Test
	void NameTesting() {
		Product p  = new Product("黑橋牌香腸", 0);
		System.out.println(p.getName());
		assertEquals("黑橋牌香腸", p.getName());
	}
}
