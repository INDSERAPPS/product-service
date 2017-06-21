package com.product.messages;

public class AddProductRequest {
	
	private MessageRequestHeader reqHeader ;
	private AddProductRequestBody addProdReqBody ;
	
	public MessageRequestHeader getReqHeader() {
		return reqHeader;
	}
	public void setReqHeader(MessageRequestHeader reqHeader) {
		this.reqHeader = reqHeader;
	}
	public AddProductRequestBody getAddProdReqBody() {
		return addProdReqBody;
	}
	public void setAddProdReqBody(AddProductRequestBody addProdReqBody) {
		this.addProdReqBody = addProdReqBody;
	}

}
