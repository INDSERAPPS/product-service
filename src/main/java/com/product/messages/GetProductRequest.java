package com.product.messages;

public class GetProductRequest {
	
	private MessageRequestHeader reqHeader ;
	private GetProductRequestBody getProdReqBody ;
	
	public MessageRequestHeader getReqHeader() {
		return reqHeader;
	}
	public void setReqHeader(MessageRequestHeader reqHeader) {
		this.reqHeader = reqHeader;
	}
	public GetProductRequestBody getGetProdReqBody() {
		return getProdReqBody;
	}
	public void setGetProdReqBody(GetProductRequestBody getProdReqBody) {
		this.getProdReqBody = getProdReqBody;
	}

}
