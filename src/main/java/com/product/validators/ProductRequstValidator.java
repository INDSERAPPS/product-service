package com.product.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.product.constants.ProductConstants;
import com.product.exceptions.ApplicationException;
import com.product.messages.AddProductRequest;
import com.product.messages.GetProductRequest;
import com.product.messages.MessageRequestHeader;
import com.product.messages.UpdateProductRequest;
import com.product.service.ProductServices;

@Component
public class ProductRequstValidator {
	
	@Autowired
	public ProductServices prodServices ;
	
	private static Logger log = LogManager.getLogger(ProductRequstValidator.class);

	public String validateAddProductRequest(AddProductRequest addProdReq) throws ApplicationException {
		
		log.info("Validating Customer insert request") ;
		if(!validateHeaderFields(addProdReq.getReqHeader()))
		{
			log.error("All mandatory header fields are not present") ;
			throw new ApplicationException(ProductConstants.NOT_ALL_MAND_FIELD_PRESENT,"Invalid Header details") ;
			
		}
		if(!validateHeaderFields(addProdReq.getReqHeader()))
		{
			log.error("All mandatory header fields are not present") ;
			throw new ApplicationException(ProductConstants.NOT_ALL_MAND_FIELD_PRESENT,"Invalid Header details") ;
			
		}
		if(addProdReq.getAddProdReqBody().getProductName()==null||addProdReq.getAddProdReqBody().getProductName().equals(""))
		{
			log.error("Product name field is not present or no value in it") ;
			throw new ApplicationException(ProductConstants.NULL_MAND_FIELD_VALUE,"Product name field is not present or no value in it") ;
		}
		if(addProdReq.getAddProdReqBody().getProductCategory()==null||addProdReq.getAddProdReqBody().getProductCategory().equals(""))
		{
			log.error("Product category field is not present or no value in it") ;
			throw new ApplicationException(ProductConstants.NULL_MAND_FIELD_VALUE,"Product category field is not present or no value in it") ;
		}
		if(addProdReq.getAddProdReqBody().getProductBrand()==null||addProdReq.getAddProdReqBody().getProductBrand().equals(""))
		{
			log.error("Product brand field is not present or no value in it") ;
			throw new ApplicationException(ProductConstants.NULL_MAND_FIELD_VALUE,"Product brand field is not present or no value in it") ;
		}
		if(addProdReq.getAddProdReqBody().getIsOnSale()==null||addProdReq.getAddProdReqBody().getIsOnSale().equals(""))
		{
			log.error("Product IsOnSale field is not present or no value in it") ;
			throw new ApplicationException(ProductConstants.NULL_MAND_FIELD_VALUE,"Product IsOnSale field is not present or no value in it") ;
		}
		if(addProdReq.getAddProdReqBody().getIsActive()==null||addProdReq.getAddProdReqBody().getIsActive().equals(""))
		{
			log.error("Product IsActive field is not present or no value in it") ;
			throw new ApplicationException(ProductConstants.NULL_MAND_FIELD_VALUE,"Product IsActive field is not present or no value in it") ;
		}
		if(addProdReq.getAddProdReqBody().getAmount()!=null)
		{
		if(addProdReq.getAddProdReqBody().getAmount().getActivePrice()==null||addProdReq.getAddProdReqBody().getAmount().getActivePrice().equals(""))
		{
			log.error("Product Active amount field is not present or no value in it") ;
			throw new ApplicationException(ProductConstants.NULL_MAND_FIELD_VALUE,"Product Active amount field is not present or no value in it") ;
		}
		if(addProdReq.getAddProdReqBody().getAmount().getBasePrice()==null||addProdReq.getAddProdReqBody().getAmount().getBasePrice().equals(""))
		{
			log.error("Product Base amount field is not present or no value in it") ;
			throw new ApplicationException(ProductConstants.NULL_MAND_FIELD_VALUE,"Product Base amount field is not present or no value in it") ;
		}
		}
		else
		{
			log.error("Product amount field is not present or no value in it") ;
			throw new ApplicationException(ProductConstants.NULL_MAND_FIELD_VALUE,"Product amount field is not present or no value in it") ;
		}
		if(addProdReq.getAddProdReqBody().getProductName().length()>50)
		{
			log.error("Product name filed length is greater than 50") ;
			throw new ApplicationException(ProductConstants.INVALID_PRODUCT_NAME,"Product name filed length is greater than 50") ;
		}
		
		if(addProdReq.getAddProdReqBody().getProductCategory().length()>50)
		{
			log.error("Product category filed length is greater than 50") ;
			throw new ApplicationException(ProductConstants.INVALID_PRODUCT_CATEGORY,"Product category filed length is greater than 50") ;
		}
		if(addProdReq.getAddProdReqBody().getProductBrand().length()>50)
		{
			log.error("Product brand filed length is greater than 50") ;
			throw new ApplicationException(ProductConstants.INVALID_PRODUCT_BRAND,"Product brand filed length is greater than 50") ;
		}
		if(!addProdReq.getAddProdReqBody().getIsActive().equals("true")&&!addProdReq.getAddProdReqBody().getIsActive().equals("false"))
		{
			log.error("Product IsActive fieled must be a boolean value") ;
			throw new ApplicationException(ProductConstants.INVALID_IS_ACTIVE,"Product IsActive filed must be a boolean value") ;
		}
		if(!addProdReq.getAddProdReqBody().getIsOnSale().equals("true")&&!addProdReq.getAddProdReqBody().getIsOnSale().equals("false"))
		{
			log.error("Product IsOnSale field must be a boolean value") ;
			throw new ApplicationException(ProductConstants.INVALID_IS_ON_SALE,"Product IsOnSale filed must be a boolean value") ;
		}
		if((!validateString(addProdReq.getAddProdReqBody().getProductName()))
				||(!validateString(addProdReq.getAddProdReqBody().getProductCategory()))||
				(!validateString(addProdReq.getAddProdReqBody().getProductBrand())))
			
		{
			log.error("Fields with Invalid string format") ;
			throw new ApplicationException(ProductConstants.INVALID_STRING,"Fields with Invalid string format") ;
		}
		if((!isNumeric(addProdReq.getAddProdReqBody().getAmount().getActivePrice()))
				||(!isNumeric(addProdReq.getAddProdReqBody().getAmount().getActivePrice())))
		{
			log.error("Invalid Amount fields") ;
			throw new ApplicationException(ProductConstants.INVALID_AMOUNT,"Invalid Amount fields") ;
		}
		
		return "success";
	}

	public String validateProductUpdateRequest(UpdateProductRequest updateProdReq) throws ApplicationException {
		
		if(!validateHeaderFields(updateProdReq.getReqHeader()))
		{
			log.error("All mandatory header fields are not present") ;
			throw new ApplicationException(ProductConstants.NOT_ALL_MAND_FIELD_PRESENT,"Invalid Header details") ;
			
		}
		if(updateProdReq.getUpdateProdReqBody().getProductId()==null
				||updateProdReq.getUpdateProdReqBody().getProductId().equals(""))
		{
			log.error("Product id field is not present or no value in it") ;
			throw new ApplicationException(ProductConstants.NULL_MAND_FIELD_VALUE,"Product id field is not present or no value in it") ;
		}
		if(updateProdReq.getUpdateProdReqBody().getProductId().length()!=17
				||!validateString(updateProdReq.getUpdateProdReqBody().getProductId()))
		{
			log.error("Product id field is invalid") ;
			throw new ApplicationException(ProductConstants.INVALID_PROD_ID,"Product id field is invalid") ;
		}
		if(prodServices.getProduct(updateProdReq.getUpdateProdReqBody().getProductId())==null)
		{
			log.error("Product doesn't exist") ;
			throw new ApplicationException(ProductConstants.PROD_NOT_EXIST,"Product doesn't exist") ;
		}
		
		if(updateProdReq.getUpdateProdReqBody().getProductName()!=null)
		{
			if(updateProdReq.getUpdateProdReqBody().getProductName().length()>50)
			{
				log.error("Product name filed length is greater than 50") ;
				throw new ApplicationException(ProductConstants.INVALID_PRODUCT_NAME,"Product name filed length is greater than 50") ;
			}
			if(!validateString(updateProdReq.getUpdateProdReqBody().getProductName()))
			{
				log.error("Fields with Invalid string format") ;
				throw new ApplicationException(ProductConstants.INVALID_STRING,"Fields with Invalid string format") ;
			}
		}
		if(updateProdReq.getUpdateProdReqBody().getProductCategory()!=null)
		{
			if(updateProdReq.getUpdateProdReqBody().getProductCategory().length()>50)
			{
				log.error("Product category filed length is greater than 50") ;
				throw new ApplicationException(ProductConstants.INVALID_PRODUCT_CATEGORY,"Product category filed length is greater than 50") ;
			}
			if(!validateString(updateProdReq.getUpdateProdReqBody().getProductCategory()))
			{
				log.error("Fields with Invalid string format") ;
				throw new ApplicationException(ProductConstants.INVALID_STRING,"Fields with Invalid string format") ;
			}
		}
		if(updateProdReq.getUpdateProdReqBody().getProductBrand()!=null)
		{
			if(updateProdReq.getUpdateProdReqBody().getProductBrand().length()>50)
			{
				log.error("Product brand filed length is greater than 50") ;
				throw new ApplicationException(ProductConstants.INVALID_PRODUCT_BRAND,"Product brand filed length is greater than 50") ;
			}
			if(!validateString(updateProdReq.getUpdateProdReqBody().getProductBrand()))
			{
				log.error("Fields with Invalid string format") ;
				throw new ApplicationException(ProductConstants.INVALID_STRING,"Fields with Invalid string format") ;
			}
		
		}
		if(updateProdReq.getUpdateProdReqBody().getIsActive()!=null)
		{
			if(!updateProdReq.getUpdateProdReqBody().getIsActive().equals("true")||!updateProdReq.getUpdateProdReqBody().getIsActive().equals("false"))
			{
				log.error("Product IsActive filed must be a boolean value") ;
				throw new ApplicationException(ProductConstants.INVALID_IS_ACTIVE,"Product IsActive filed must be a boolean value") ;
			}
		}
		if(updateProdReq.getUpdateProdReqBody().getIsOnSale()!=null)
		{
			if(!updateProdReq.getUpdateProdReqBody().getIsOnSale().equals("true")||!updateProdReq.getUpdateProdReqBody().getIsOnSale().equals("false"))
			{
				log.error("Product IsOnSale filed must be a boolean value") ;
				throw new ApplicationException(ProductConstants.INVALID_IS_ON_SALE,"Product IsOnSale filed must be a boolean value") ;
			}
		}
		if(updateProdReq.getUpdateProdReqBody().getAmount()!=null)
		{
		if(updateProdReq.getUpdateProdReqBody().getAmount().getActivePrice()!=null)
		{
			if(!isNumeric(updateProdReq.getUpdateProdReqBody().getAmount().getActivePrice()))
			{
				log.error("Invalid Amount fields") ;
				throw new ApplicationException(ProductConstants.INVALID_AMOUNT,"Invalid Amount fields") ;
			
			}
		}
		
		if(updateProdReq.getUpdateProdReqBody().getAmount().getBasePrice()!=null)
		{
			if(!isNumeric(updateProdReq.getUpdateProdReqBody().getAmount().getBasePrice()))
			{
				log.error("Invalid Amount fields") ;
				throw new ApplicationException(ProductConstants.INVALID_AMOUNT,"Invalid Amount fields") ;
			
			}
		}
		}
				
		return "success";
	}

	public String validateGetProductRequest(GetProductRequest getProdReq) throws ApplicationException {
		
		if(!validateHeaderFields(getProdReq.getReqHeader()))
		{
			log.error("All mandatory header fields are not present") ;
			throw new ApplicationException(ProductConstants.NOT_ALL_MAND_FIELD_PRESENT,"Invalid Header details") ;
			
		}
		if(getProdReq.getGetProdReqBody().getProductId()==null
				||getProdReq.getGetProdReqBody().getProductId().equals(""))
		{
			log.error("Product id field is not present or no value in it") ;
			throw new ApplicationException(ProductConstants.NULL_MAND_FIELD_VALUE,"Product id field is not present or no value in it") ;
		}
		if(getProdReq.getGetProdReqBody().getProductId().length()!=17
				||!validateString(getProdReq.getGetProdReqBody().getProductId()))
		{
			log.error("Product id field is invalid") ;
			throw new ApplicationException(ProductConstants.INVALID_PROD_ID,"Product id field is invalid") ;
		}
		if(prodServices.getProduct(getProdReq.getGetProdReqBody().getProductId())==null)
		{
			log.error("Product doesn't exist") ;
			throw new ApplicationException(ProductConstants.PROD_NOT_EXIST,"Product doesn't exist") ;
		}
		
		return "success";
	}
	
	private boolean validateString(String string)
	{
		String stringPattern ="^[A-Za-z0-9, ]++$" ;
		Pattern pattern = Pattern.compile(stringPattern);
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}
	
	private boolean validateHeaderFields(MessageRequestHeader reqHeader)
	{
		if((reqHeader.getAcceptCharSet()==null)||(reqHeader.getCi()==null)||(reqHeader.getContentType()==null)
				||(reqHeader.getDate()==null)||(reqHeader.getTocken()==null))
		{
			
			log.error("All mandatory header fields are not present") ;
			return false ;
		}
		if((reqHeader.getCi().equals(""))||(reqHeader.getDate().equals(""))||(reqHeader.getTocken().equals("")))
		{
			
			return false ;
		}
		if(!validateString(reqHeader.getTocken()))
		{
			
			log.error("Invalid message tocken format") ;
			return false ;
		}
		if((!reqHeader.getAcceptCharSet().equals(ProductConstants.ACCEPT_CHARSET))
				||(!reqHeader.getContentType().equals(ProductConstants.CONTENT_TYPE)))
				{
			
			return false ;
				}
				return true ;
	}
	
	public static boolean isNumeric(String str) {
	    if (str == null) {
	        return false;
	    }
	    int sz = str.length();
	    for (int i = 0; i < sz; i++) {
	        if (Character.isDigit(str.charAt(i)) == false) {
	            return false;
	        }
	    }
	    return true;
	}

}
