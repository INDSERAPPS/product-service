package com.product.entity;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;



@Component
@Entity
@Table(name= "Products")
public class Product extends BaseEntity {
	
	@Id
	@Column(name = "PRODUCT_ID")
	private String productId ;
	
	@Column(name = "BASE_PRICE")
	private long basePrice ;
	
	@Column(name = "ACTIVE_PRICE")
	private long activePrice ;
	
	@Type(type="yes_no")
	@Column(name = "IS_ON_SALE",columnDefinition = "char")
	private boolean isOnSale ;
	
	@Type(type="yes_no")
	@Column(name = "IS_ACTIVE",columnDefinition = "char")
	private boolean isActive ;
	
	@Column(name = "PRODUCT_NAME")
	private String productName ;
	
	@Column(name = "PRODUCT_CATEGORY")
	private String productCategory ;
	
	@Column(name = "PRODUCT_BRAND")
	private String productBrand ;
	
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
	public long getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(long basePrice) {
		this.basePrice = basePrice;
	}
	public long getActivePrice() {
		return activePrice;
	}
	public void setActivePrice(long activePrice) {
		this.activePrice = activePrice;
	}
	public boolean isOnSale() {
		return isOnSale;
	}
	public void setOnSale(boolean isOnSale) {
		this.isOnSale = isOnSale;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Nullable
	public static Product findById(String productId)  {
		
	        return em().find(Product.class, productId);
		
	    }

}
