package com.product.messages;

public class AddProductResponse {
	
	private ResponseHeader respHeader ;
	private AddProductResponseBody addProdRespBody ;
	
	public ResponseHeader getRespHeader() {
		return respHeader;
	}
	public void setRespHeader(ResponseHeader respHeader) {
		this.respHeader = respHeader;
	}
	public AddProductResponseBody getAddProdRespBody() {
		return addProdRespBody;
	}
	public void setAddProdRespBody(AddProductResponseBody addProdRespBody) {
		this.addProdRespBody = addProdRespBody;
	}

}
