package sheng.orm.test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.junit.jupiter.params.ParameterizedTest;

import sheng.orm.entity.Product;

abstract class BaseDao<T>{
	private Class<T> entityClass;
	public BaseDao() {
		this.entityClass = null;
		Class<?> clazz = getClass();
		Type type = clazz.getGenericSuperclass(); //獲得帶有泛型的父類的type
		System.out.println(type);
						  //parameterizedType參數化類別(List, Map...)的type
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
		}
	}
}

public class SqlNameTesting {
	public static String getSqlFieldName(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (i!=0 && str.charAt(i)>='A' && str.charAt(i)<='Z') {
				str = str.replace(""+str.charAt(i), "_"+(char)(str.charAt(i)-'A' + 'a' ));
			}
		}
		return str.toLowerCase();
	}
	
	public static void main(String[] args) {
		System.out.println( getSqlFieldName("abcAbc") );
		
		
	}
}
