package com.product.messages;

public class GetProductResponse {
	
	private ResponseHeader respHeader ;
	private GetProductResponseBody getProdRepBody ;
	
	public ResponseHeader getRespHeader() {
		return respHeader;
	}
	public void setRespHeader(ResponseHeader respHeader) {
		this.respHeader = respHeader;
	}
	public GetProductResponseBody getGetProdRepBody() {
		return getProdRepBody;
	}
	public void setGetProdRepBody(GetProductResponseBody getProdRepBody) {
		this.getProdRepBody = getProdRepBody;
	}

}
