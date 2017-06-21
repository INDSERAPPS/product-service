package com.product.messages;

public class UpdateProductRequest {
	
	private MessageRequestHeader reqHeader ;
	private UpdateProductRequestBody updateProdReqBody ;
	
	public MessageRequestHeader getReqHeader() {
		return reqHeader;
	}
	public void setReqHeader(MessageRequestHeader reqHeader) {
		this.reqHeader = reqHeader;
	}
	public UpdateProductRequestBody getUpdateProdReqBody() {
		return updateProdReqBody;
	}
	public void setUpdateProdReqBody(UpdateProductRequestBody updateProdReqBody) {
		this.updateProdReqBody = updateProdReqBody;
	}

}
