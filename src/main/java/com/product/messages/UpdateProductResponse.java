package com.product.messages;

public class UpdateProductResponse {
	
	private ResponseHeader respHeader ;
	private UpdateProductResponseBody updateProdRespBody ;
	
	public ResponseHeader getRespHeader() {
		return respHeader;
	}
	public void setRespHeader(ResponseHeader respHeader) {
		this.respHeader = respHeader;
	}
	public UpdateProductResponseBody getUpdateProdRespBody() {
		return updateProdRespBody;
	}
	public void setUpdateProdRespBody(UpdateProductResponseBody updateProdRespBody) {
		this.updateProdRespBody = updateProdRespBody;
	}

}
