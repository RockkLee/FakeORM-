package sheng.orm.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.html.parser.Entity;

import sheng.orm.exception.OrmException;

public class FakeHibernate<T> {
	public List<T> read(Class<T> entityClazz, int limitNum ) throws OrmException {
		List<T> list = new ArrayList<T>();
		
		//limitNum檢查
		if (limitNum>1000 || limitNum <=0){
			var e = new IllegalArgumentException();
			Logger.getLogger("FakeHibernate_select").log(Level.SEVERE, "limitNum錯誤",e); 
			throw e;
		}
		
		//tableName字串轉換，把java entity的駝峰式命名法轉為SQL的底線命名法
		//(ex: EntityToTable -> entity_to_table)
		String tableName = entityClazz.getSimpleName();
		for (int i = 0; i < tableName.length(); i++) {
			if (i!=0 && tableName.charAt(i)>='A' && tableName.charAt(i)<='Z')
				tableName = tableName.replace(""+tableName.charAt(i), "_"+(char)(tableName.charAt(i)-'A' + 'a' ));
		}
		tableName = tableName.toLowerCase();
		
		//自動產生查詢字串
		String sql_str = "SELECT * FROM " + tableName + " LIMIT ?;";
		
		try (
				Connection con = RDBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql_str);
				){
			pstmt.setInt(1, limitNum);
			
			//ResultSet要做try,catch，預防資料庫鎖定問題
			try(
					ResultSet rs = pstmt.executeQuery();	
					){
				while (rs.next()) {
						T entity;
						try {
							entity = entityClazz.getConstructor().newInstance();
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException | SecurityException e) {
							throw new OrmException("反射建立物件失敗",e);
						}	
						
						Field[] dFields = entityClazz.getDeclaredFields();
						for (Field field : dFields) {
							field.setAccessible(true);
							try {
								if (field.getType() == Integer.class)
									field.set(entity, rs.getInt(field.getName()));
								else if (field.getType() == Double.class)
									field.set(entity, rs.getDouble(field.getName()));
								else if (field.getType() == String.class)
									field.set(entity, rs.getString(field.getName()));
							} catch (IllegalArgumentException | IllegalAccessException e) {
								throw new OrmException("物件的setter抓值失敗",e);
							}
						}
						list.add(entity);
				}
			}
		} catch (SQLException e) {
			throw new OrmException("查詢產品",e);
		}
		
		return list;
	}
	
	
	
	
	
	public void write(T entity) throws OrmException {
		Class<?> entityClazz = entity.getClass();
		
		//tableName字串轉換，把java entity的駝峰式命名法轉為SQL的底線命名法
		//(ex: EntityToTable -> entity_to_table)
		String tableName = entityClazz.getSimpleName();
		for (int i = 0; i < tableName.length(); i++) {
			if (i!=0 && tableName.charAt(i)>='A' && tableName.charAt(i)<='Z')
				tableName = tableName.replace(""+tableName.charAt(i), "_"+(char)(tableName.charAt(i)-'A' + 'a' ));
		}
		tableName = tableName.toLowerCase();
		
		//讀取entity的屬性，轉成columns、questionMarks => INSERT INTO xxx (columns) VALUES (questionMarks);
		StringJoiner columns = new StringJoiner(",");
		StringJoiner questionMarks = new StringJoiner(",");
		Field[] dFields = entityClazz.getDeclaredFields();
		for (Field field : dFields) {
			if (field.getName()!="id") {
				columns.add(field.getName());
				questionMarks.add("?");				
			}
		}
		
		//自動產生查詢字串
		String sql_str = "INSERT INTO " + tableName 
							+ " ("+ columns.toString() +") " + "VALUES"
							+ " ("+ questionMarks.toString() +");";
		
		System.out.println(sql_str);
		
		//開始進行INSERT INTO
		try (
				Connection con = RDBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql_str);
				){
			
			//反射獲取物件屬性，並讀取到pstmt
			for (int i = 0; i < dFields.length; i++) {
				try {
					dFields[i].setAccessible(true);
					if (dFields[i].getName()=="id") {}
					else if (dFields[i].getType() == Integer.class)
						pstmt.setInt(i, (int) dFields[i].get(entity));
					else if (dFields[i].getType() == Double.class)
						pstmt.setDouble(i, (double) dFields[i].get(entity));					
					else if (dFields[i].getType() == String.class)
						pstmt.setString(i, (String) dFields[i].get(entity));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new OrmException("反射獲取物件屬性失敗",e);
				}
			}
			//執行pstmt
			pstmt.executeUpdate();	
		} catch (SQLException e) {
			throw new OrmException("反射獲取物件屬性失敗",e);
		}
	}
	
	
	
	
	
}
