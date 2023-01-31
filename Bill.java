package com.hari.billing;

import java.util.List;

public interface Bill {
	public void saveBillInfo(AddProductDAO addProductDAO);
	public List<AddProductDAO> getProducts();
	public void deleteProductData(int id);
	public List<AddProductDAO> searchProduct(String name);
	public void updateBillInfo(AddProductDAO addProductDAO);
}
