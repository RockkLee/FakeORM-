package sheng.orm.entity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Product {
	private Integer id;
	private String name;
	private Double price;
	private Integer discount;
	
	public Product() {}
	public Product(String name, int discount) {
		super();
		this.name = name;
		this.discount = discount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		if ( discount>100 || discount < 0) {
			var e = new IllegalArgumentException();
			Logger.getLogger("LogTool_exception").log(Level.WARNING, "discount範圍錯誤", e); 
			throw e;
		}
		this.discount = discount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getUnitPrice() {
		return price*((100.0-discount)/100.0);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name +  ", discount=" + discount + ", price=" + price + "]";
	}
}
