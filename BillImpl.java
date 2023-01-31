package com.hari.billing;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import com.hari.billing.DatabaseConnectivity;

public class BillImpl implements Bill {
	PreparedStatement ps = null;

	@Override
	public void saveBillInfo(AddProductDAO addProductDAO) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO product_details(name,price,quantity) VALUES (?,?,?)";
		try {
			ps = DatabaseConnectivity.getConnection().prepareStatement(sql);
			ps.setString(1, addProductDAO.getName());
			ps.setInt(2, addProductDAO.getPrice());
			ps.setInt(3, addProductDAO.getQuantity());
			ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<AddProductDAO> getProducts() {
		List<AddProductDAO> productList = new ArrayList<>();
		String sql = "SELECT * FROM product_details";
		try {
			ps = DatabaseConnectivity.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				AddProductDAO bill = new AddProductDAO();
				bill.setId(rs.getInt("ID"));
				bill.setName(rs.getString("name"));
				bill.setPrice(rs.getInt("price"));
				bill.setQuantity(rs.getInt("quantity"));
				productList.add(bill);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public void deleteProductData(int id) {
		String sql = "DELETE FROM product_details WHERE ID=?";
		
		try {
			ps = DatabaseConnectivity.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<AddProductDAO> searchProduct(String name) {
		List<AddProductDAO> productList = new ArrayList<>();
		String sql = "SELECT * FROM product_details WHERE name LIKE ?";
		try {
			ps = DatabaseConnectivity.getConnection().prepareStatement(sql);
			ps.setString(1, "%" +name+ "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				AddProductDAO bill = new AddProductDAO();
				bill.setId(rs.getInt("ID"));
				bill.setName(rs.getString("name"));
				bill.setPrice(rs.getInt("price"));
				bill.setQuantity(rs.getInt("quantity"));
				productList.add(bill);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return productList;
	}

	@Override
	public void updateBillInfo(AddProductDAO addProductDAO) {
		// TODO Auto-generated method stub
		String sql = "UPDATE product_details SET name =?, price=?, quantity=? WHERE ID=?";
		try {
			ps = DatabaseConnectivity.getConnection().prepareStatement(sql);
			ps.setString(1, addProductDAO.getName());
			ps.setInt(2, addProductDAO.getPrice());
			ps.setInt(3, addProductDAO.getQuantity());
			ps.setInt(4, addProductDAO.getId());
			ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	}

 