package com.product.messages;

import com.product.messages.Amount;

public class ProductDetails {
	
	private String productId ;
	private Amount amount ;
	private boolean isOnSale ;
	private boolean isActive ;
	private String productName ;
	private String productCategory ;
	private String productBrand ;
	
	public Amount getAmount() {
		return amount;
	}
	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	public boolean isOnSale() {
		return isOnSale;
	}
	public void setOnSale(boolean isOnSale) {
		this.isOnSale = isOnSale;
	}
	public boolean isIsActive() {
		return isActive;
	}
	public void setIsActive(boolean iaActive) {
		this.isActive = iaActive;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

}
