package com.product.mapper;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.constants.ProductConstants;
import com.product.exceptions.ApplicationException;
import com.product.messages.AddProductRequest;
import com.product.messages.AddProductRequestBody;
import com.product.messages.AddProductResponseBody;
import com.product.messages.GetProductResponseBody;
import com.product.messages.MessageRequestHeader;
import com.product.messages.UpdateProductRequest;
import com.product.messages.UpdateProductRequestBody;
import com.product.messages.UpdateProductResponseBody;

@Component
public class ProductMessageMapper {
	
	private static ObjectMapper jacksonMapper = new ObjectMapper();
	private static Logger log = LogManager.getLogger(ProductMessageMapper.class);
	private static Logger reqRespLog =LogManager.getLogger("REQRESP") ;

	public AddProductRequest convertJSONtoInsertReq(String productRequst, MessageRequestHeader reqHeader) throws ApplicationException {
		
		displayRequestinLogger(productRequst,reqHeader);
		AddProductRequest addProdReq = new AddProductRequest();
		AddProductRequestBody addProdReqBody = null ;
		try {
			addProdReqBody = jacksonMapper.readValue(productRequst, AddProductRequestBody.class);
			addProdReq.setReqHeader(reqHeader);
			addProdReq.setAddProdReqBody(addProdReqBody);
			log.info("Mapped customer insert request to object CustomerInsertRequest") ;
		} 
		 catch (IOException e) {
			 log.error("Invalid json format :"+e.getMessage()) ;
			throw new ApplicationException(ProductConstants.CLIENT_ERROR,"Invalid JSON Format");
		}
		return addProdReq;
	}

	public UpdateProductRequest convertJSONtoUpdateReq(String productRequest, MessageRequestHeader reqHeader) throws ApplicationException {
		
		displayRequestinLogger(productRequest,reqHeader);
		UpdateProductRequest updateProdReq = new UpdateProductRequest() ;
		UpdateProductRequestBody updateProdReqBody =null ;
		try {
			updateProdReqBody = jacksonMapper.readValue(productRequest, UpdateProductRequestBody.class);
			updateProdReq.setReqHeader(reqHeader);
			updateProdReq.setUpdateProdReqBody(updateProdReqBody);
			log.info("Mapped customer update request to object CustomerInsertRequest") ;
		} 
		 catch (IOException e) {
			 log.error("Invalid json format :"+e.getMessage()) ;
			throw new ApplicationException(ProductConstants.CLIENT_ERROR,"Invalid JSON Format");
		}
		return updateProdReq;
	}

	public String convertAddRespObjToJson(AddProductResponseBody addProdRespBody) throws ApplicationException {
		String addProductResponse = null ;
		try {
			addProductResponse = jacksonMapper.writeValueAsString(addProdRespBody);
			 log.info("Created customer insert response") ;
		} catch (IOException e) {
			log.error("Unable to convert Object to Json:"+e.getMessage()) ;
			throw new ApplicationException(ProductConstants.SYSTEM_ERROR,"Unable to generate Response");
			
		}
		return addProductResponse ;
	}

	public String convertUpdateRespObjToJson(UpdateProductResponseBody updateProdRespBody) throws ApplicationException {
		
		String UpdateProductResponse = null ;
		try {
			UpdateProductResponse = jacksonMapper.writeValueAsString(updateProdRespBody);
			log.info("Created customer update response") ;
		} catch (IOException e) {
			log.error("Unable to convert Object to Json:"+e.getMessage()) ;
			throw new ApplicationException(ProductConstants.SYSTEM_ERROR,"Unable to generate Response");
			
		}
		return UpdateProductResponse ;
	}

	public String convertGetRespObjToJson(GetProductResponseBody getProdRespBody) throws ApplicationException {
		
		String GetProductResponse = null ;
		try {
			GetProductResponse = jacksonMapper.writeValueAsString(getProdRespBody);
			log.info("Created customer get response") ;
		} catch (IOException e) {
			log.error("Unable to convert Object to Json:"+e.getMessage()) ;
			throw new ApplicationException(ProductConstants.SYSTEM_ERROR,"Unable to generate Response");
			
		}
		return GetProductResponse ;
	}
	
	public void displayRequestinLogger(String body ,MessageRequestHeader reqHeader)
	{
		
		reqRespLog.info("\n"+"Request Message:"+"\n"+"Headers"+"\n"+"Content-Type:"
		+reqHeader.getContentType()+"\n"+"Accept-Charset:"+reqHeader.getAcceptCharSet()
				+"\n"+"Date:"+reqHeader.getDate()+"\n"+"Server:"+reqHeader.getCi()+"\n"+"Tocken:"+reqHeader.getTocken()
				+"\n"+"Response Body"+"\n"+body);
		
	}

}
