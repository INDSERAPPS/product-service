package com.product.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.product.constants.ProductConstants;
import com.product.entity.Product;
import com.product.exceptions.ApplicationException;
import com.product.mapper.ProductMessageMapper;
import com.product.messages.AddProductRequest;
import com.product.messages.AddProductResponse;
import com.product.messages.AddProductResponseBody;
import com.product.messages.Amount;
import com.product.messages.GetProductRequest;
import com.product.messages.GetProductRequestBody;
import com.product.messages.GetProductResponse;
import com.product.messages.GetProductResponseBody;
import com.product.messages.MessageRequestHeader;
import com.product.messages.ProductDetails;
import com.product.messages.UpdateProductRequest;
import com.product.messages.UpdateProductResponse;
import com.product.messages.UpdateProductResponseBody;
import com.product.service.ProductServices;
import com.product.validators.ProductRequstValidator;

@Controller
@RequestMapping(value = "/v1.0/*")
public class ManageProductController {
	
	@Autowired
	private ProductServices prodServices ;
	
	@Autowired
	private ProductRequstValidator prodReqVal;
	
	@Autowired
	private ProductMessageMapper productMapper ;
	
	private static Logger log = LogManager.getLogger(ManageProductController.class);
	private static Logger reqRespLog =LogManager.getLogger("REQRESP") ;

	@RequestMapping(value = "/products", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<String> insertProductInfo(@RequestBody String productRequst,
			@RequestHeader(value="Content-Type",required=false) String type,
			@RequestHeader(value="Accept-Charset",required=false) String charSet,
			@RequestHeader(value="Date",required=false) String date,
			@RequestHeader(value="Server",required=false) String server,
			@RequestHeader(value="Tocken",required=false) String tocken) throws JsonProcessingException, ApplicationException
	{
		log.info("Received insert customer request and started mapping") ;
		
		Timestamp receivedTime = new Timestamp(System.currentTimeMillis());
		MessageRequestHeader reqHeader = setReqHeaderObj(type,charSet ,date, server ,tocken) ;
		
		AddProductResponse addProdResp = new AddProductResponse() ;

		HttpHeaders respHeaders =null  ;
		
				try {
			
			AddProductRequest addProdReq = productMapper.convertJSONtoInsertReq(productRequst,reqHeader);
			System.out.println("before validation");
		if(prodReqVal.validateAddProductRequest(addProdReq).equals("success"))
		{
			
			String productId = prodServices.insertProduct(addProdReq);
			log.info("Set Response Headers") ;
			respHeaders = setRespHeaders(tocken) ;
			String productResponse = buildProductInsertSuccessResponse(productId);
			displayResponseInLogger(respHeaders,productResponse) ;
			return new ResponseEntity (productResponse,respHeaders,HttpStatus.OK) ;
		}
		}catch (ApplicationException e) {
		
			log.info("Set Response Headers") ;
			respHeaders = setRespHeaders(tocken) ;
			String productResponse = buildProductInsertErrorResponse(e);
			displayResponseInLogger(respHeaders,productResponse) ;
			
			return new ResponseEntity(productResponse,respHeaders,HttpStatus.OK);
			
		} catch (IOException e) {
			log.info("Set Response Headers") ;
			respHeaders =setRespHeaders(tocken) ;
			int error_code = 700 ;
			ApplicationException e1 = new ApplicationException(error_code,"Mandatory fields are null") ;
			String productResponse = buildProductInsertErrorResponse(e1);
			displayResponseInLogger(respHeaders,productResponse) ;
			return new ResponseEntity(productResponse,respHeaders,HttpStatus.OK);	
			} 
		return null;
		 
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<String> updateProductInfo(@RequestBody String productRequest,
			@RequestHeader(value ="Content-Type",required=false) String type,
			@RequestHeader(value ="Accept-Charset",required=false) String charSet,
			@RequestHeader(value="Date",required=false) String date,
			@RequestHeader(value="Server",required=false) String server,
			@RequestHeader(value="Tocken",required=false) String tocken) throws JsonProcessingException, ApplicationException
	{
		
		log.info("Received update product request and started mapping") ;
		HttpHeaders respHeaders =null  ;
		UpdateProductResponse updateProdResp = new UpdateProductResponse();
		MessageRequestHeader reqHeader = setReqHeaderObj(type,charSet ,date, server ,tocken) ;
		
		try {
			UpdateProductRequest updateProdReq = productMapper.convertJSONtoUpdateReq(productRequest,reqHeader);
			
			if(prodReqVal.validateProductUpdateRequest(updateProdReq).equals("success"))
			{
			String isUpdate = prodServices.updateProduct(updateProdReq) ;
			if(isUpdate.equals("true"))
			{
		
				log.info("Set Response Headers") ;
				respHeaders = setRespHeaders(tocken) ;
				String productResponse = buildProductUpdateSuccessResponse(updateProdReq.getUpdateProdReqBody().getProductId());
				displayResponseInLogger(respHeaders,productResponse) ;
				return new ResponseEntity(productResponse,respHeaders,HttpStatus.OK);
			}
			}
		
		}catch (ApplicationException e) {
			log.info("Set Response Headers") ;
			respHeaders =setRespHeaders(tocken) ;
			String productResponse = buildProductUpdateErrorResponse(e);
			displayResponseInLogger(respHeaders,productResponse) ;
			return new ResponseEntity(productResponse,respHeaders,HttpStatus.OK);
		
		} 
		return null;
	
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<String> getProductInfo(
			@RequestParam(value ="productId", required =false) String productId,
			
			@RequestHeader(value="Content-Type",required=false) String type,
			@RequestHeader(value="Accept-Charset",required=false) String charSet,
			@RequestHeader(value="Date",required=false) String date,
			@RequestHeader(value="Server",required=false) String server,
			@RequestHeader(value="Tocken",required=false) String tocken) throws JsonProcessingException, ApplicationException
	{   
		Timestamp receivedTime = new Timestamp(System.currentTimeMillis());
		log.info("Received get customer request and started mapping") ;
		GetProductResponse getProdResp = new GetProductResponse();
		GetProductRequest getProdReq = new GetProductRequest() ;
		
		log.info("Mapped request headers") ;
		MessageRequestHeader reqHeader = setReqHeaderObj(type,charSet ,date, server ,tocken) ;
		GetProductRequestBody getProdReqBody = new GetProductRequestBody();
		
		
		getProdReqBody.setProductId(productId);
		
		
		HttpHeaders respHeaders =null  ;
		
		
		getProdReq.setReqHeader(reqHeader);
		getProdReq.setGetProdReqBody(getProdReqBody) ;
		
		
		try {
			//CustomerGetRequest custGetReq = messageMapper.convertJSONtoGetReq(customerRequest);
			 if(prodReqVal.validateGetProductRequest(getProdReq).equals("success"))
			 {
			Product product = new Product() ;
			product = prodServices.getProduct(getProdReq.getGetProdReqBody().getProductId()) ;
			 log.info("Set response headers..") ;
			 respHeaders = setRespHeaders(tocken) ;
			String productResponse = buildGetProductSuccessResponse(product);
			displayResponseInLogger(respHeaders,productResponse) ;
			return new ResponseEntity(productResponse,respHeaders,HttpStatus.OK);
				
			 }
		} catch (ApplicationException e) {
			log.info("Set response headers") ;
			respHeaders = setRespHeaders(tocken) ;
			String productResponse = buildGetProductErrorResponse(e) ;
			displayResponseInLogger(respHeaders,productResponse) ;
			return new ResponseEntity(productResponse,respHeaders,HttpStatus.OK);
		
		}
		return null ;
	}

	public ProductServices getProdServices() {
		return prodServices;
	}

	public void setCustServices(ProductServices prodServices) {
		this.prodServices = prodServices;
	}
	
	private String buildProductInsertSuccessResponse(String productId) throws JsonProcessingException, ApplicationException
	{
		log.info("Building product insert success response") ;
		
	AddProductResponseBody addProdRespBody = new AddProductResponseBody();
	
	addProdRespBody.setProductId(productId) ;
	addProdRespBody.setStatus(ProductConstants.SUCCESS_STATUS);
	
		String customerResponse = productMapper.convertAddRespObjToJson(addProdRespBody) ;
		log.info("Sent product insert success response") ;
		return customerResponse ;
		
	}
	
	private String buildProductUpdateSuccessResponse(String prodId) throws JsonProcessingException, ApplicationException
	{
		log.info("Building product update success response") ;
		
		UpdateProductResponseBody updateProdRespBody = new UpdateProductResponseBody();
		updateProdRespBody.setProductId(prodId);
		updateProdRespBody.setStatus(ProductConstants.SUCCESS_STATUS);
		
			String productResponse = productMapper.convertUpdateRespObjToJson(updateProdRespBody);
			log.info("Sent product update success response") ;
		return productResponse;
		
	}
	
	private String buildGetProductSuccessResponse(Product product) throws JsonProcessingException, ApplicationException
	{
		log.info("Building product get success response") ;
		//ResponseHeader respHeader = new ResponseHeader();
		GetProductResponseBody getProdRespBody = new GetProductResponseBody();
		ProductDetails prodDetails = new ProductDetails() ;
		Amount amount = new Amount() ;
		amount.setActivePrice(Long.toString(product.getActivePrice()));
		amount.setBasePrice(Long.toString(product.getBasePrice()));
		
		prodDetails.setProductName(product.getProductName());
		prodDetails.setAmount(amount);
		prodDetails.setProductBrand(product.getProductBrand());
		prodDetails.setProductCategory(product.getProductCategory());
		prodDetails.setProductId(product.getProductId());
		prodDetails.setIsActive(product.isActive());
		prodDetails.setOnSale(product.isOnSale());
		
		getProdRespBody.setProductDetails(prodDetails);
		getProdRespBody.setStatus(ProductConstants.SUCCESS_STATUS);
		
		String productResponse;
		log.info("Sent product get success response") ;
		productResponse = productMapper.convertGetRespObjToJson(getProdRespBody);
		
		return productResponse;
	}
	
	private String buildProductInsertErrorResponse(ApplicationException e) throws JsonProcessingException, ApplicationException
	{
		log.info("Building product insert error response") ;
		
		AddProductResponseBody addProdRespBody = new AddProductResponseBody();
		addProdRespBody.setErrorCode(e.getErrorCode());
		addProdRespBody.setErrorDescription(e.getMessage()) ;
		addProdRespBody.setStatus(ProductConstants.FAILURE_STATUS);
	
		String productResponse = productMapper.convertAddRespObjToJson(addProdRespBody) ;
		log.info("Sent product insert error response") ;
		return productResponse ;
		
	}
	
	private String buildProductUpdateErrorResponse(ApplicationException e) throws JsonProcessingException, ApplicationException
	{
		log.info("Building product update error response") ;
		
		UpdateProductResponseBody updateProdRespBody = new UpdateProductResponseBody();
		updateProdRespBody.setStatus(ProductConstants.FAILURE_STATUS);
		updateProdRespBody.setErrorCode(e.getErrorCode());
		updateProdRespBody.setErrorDescription(e.getMessage());
		
		String customerResponse = productMapper.convertUpdateRespObjToJson(updateProdRespBody);
		log.info("Sent product update error response") ;
		return customerResponse;
	}
	
	private String buildGetProductErrorResponse(ApplicationException e) throws JsonProcessingException, ApplicationException
	{
		log.info("Building product get error response") ;
		
		GetProductResponseBody getProdRespBody = new GetProductResponseBody();
		getProdRespBody.setStatus(ProductConstants.FAILURE_STATUS);
		getProdRespBody.setErrorCode(e.getErrorCode());
		getProdRespBody.setErrorDescription(e.getMessage());
		
		String customerResponse = productMapper.convertGetRespObjToJson(getProdRespBody);
		log.info("Sent customer get error response") ;
		return customerResponse;
	}
	
	public String getDateInGMT()
	{
		Date date = new Date();
		 SimpleDateFormat sd = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
		    sd.setTimeZone(TimeZone.getTimeZone("GMT"));
		    return sd.format(date) ;
	}
	
	public MessageRequestHeader setReqHeaderObj(String type,String charSet ,String date,String server ,String tocken)
	{
		MessageRequestHeader reqHeader = new MessageRequestHeader() ;
		reqHeader.setContentType(type) ;
		reqHeader.setAcceptCharSet(charSet) ;
		reqHeader.setCi(server) ;
		reqHeader.setTocken(tocken) ;
		reqHeader.setDate(date) ;
		return reqHeader ;
	}
	
	public HttpHeaders setRespHeaders(String tocken) throws ApplicationException 
	{
		HttpHeaders respHeader = new HttpHeaders()  ;
		respHeader.add("Accept-Charset", ProductConstants.ACCEPT_CHARSET) ;
		respHeader.add("Tocken", tocken) ;
		respHeader.add("Content-Type", ProductConstants.CONTENT_TYPE);
		respHeader.add("Date", getDateInGMT());
		
		return respHeader ;
		
	}
	
	public void displayResponseInLogger(HttpHeaders httpHeaders , String body)
	{
		
		reqRespLog.info("\n"+"Response Message"+"\n"+"Headers"+"\n"+
		"Content-Type:"+httpHeaders.get("Content-Type")+"\n"+"Accept-Charset:"+httpHeaders.get("Accept-Charset")
				+"\n"+"Date:"+httpHeaders.get("Date")+"\n"+"Server:"+
				httpHeaders.get("Server")+"\n"+"Tocken:"+httpHeaders.get("Tocken")+"/n"+"Response Body"+"\n"+body);
		
	}

}
