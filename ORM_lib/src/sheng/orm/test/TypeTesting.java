package sheng.orm.test;

import java.lang.reflect.Type;

import sheng.orm.entity.Product;


public class TypeTesting {
	
	public static void main(String[] args) {
		Product p1 = new Product("甜不辣",0);
		Class clazz_p1 = p1.getClass();
		Type type_p1 = clazz_p1;

		Product p2 = new Product("豆干",0);
		Class clazz_p2 = p2.getClass();
		Type type_p2 = clazz_p2.getGenericSuperclass();
		
		System.out.println("type_p1: " + type_p1);
		System.out.println("type_p2: " + type_p2);
		System.out.print("type_p1==type_p2: ");
		System.out.println(type_p1==type_p2);
		System.out.print("type_p1.equals(type_p2): ");
		System.out.println(type_p1.equals(type_p2));
		
		System.out.println("\n=======================================\n");
		
		Integer varInt = 100;
		Class clazz_varInt = varInt.getClass();
		System.out.println(clazz_varInt);
	}

}
