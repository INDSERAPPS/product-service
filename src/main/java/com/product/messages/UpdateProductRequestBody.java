package com.product.messages;

public class UpdateProductRequestBody {
	
	private String productId ;
	private String productName ;
	private String productCategory ;
	private String productBrand ;
	private Amount amount ;
	private String isOnSale ;
	private String isActive ;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
	public Amount getAmount() {
		return amount;
	}
	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	public String getIsOnSale() {
		return isOnSale;
	}
	public void setIsOnSale(String isOnSale) {
		this.isOnSale = isOnSale;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
