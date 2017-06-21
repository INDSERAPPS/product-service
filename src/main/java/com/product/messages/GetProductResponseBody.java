package com.product.messages;

public class GetProductResponseBody {
	
	private String status ;
	private ProductDetails productDetails ;
	private int errorCode ;
	private String errorDescription ;
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ProductDetails getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(ProductDetails productDetails) {
		this.productDetails = productDetails;
	}

}
