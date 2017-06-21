package com.product.service;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.product.entity.BaseEntity;

import com.product.entity.Product;
import com.product.messages.AddProductRequest;
import com.product.messages.GetProductRequest;
import com.product.messages.UpdateProductRequest;

@Service
@Transactional
public class ProductServices {
	
	private EntityManager entityManager;
	static Random rnd = new Random();
	private static Logger log = LogManager.getLogger(ProductServices.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DiscoveryClient discoveryClient ;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private void injectPersistenceContext() {
		BaseEntity.setEntityManager(entityManager);
	}
	

	public String insertProduct(AddProductRequest addProdReq) {
		
		injectPersistenceContext() ;
		Product product = new Product() ;
		product.setActive(Boolean.valueOf(addProdReq.getAddProdReqBody().getIsActive()));
		product.setActivePrice(Long.parseLong(addProdReq.getAddProdReqBody().getAmount().getActivePrice()));
		product.setBasePrice(Long.parseLong(addProdReq.getAddProdReqBody().getAmount().getBasePrice()));
		product.setOnSale(Boolean.valueOf(addProdReq.getAddProdReqBody().getIsOnSale()));
		product.setProductBrand(addProdReq.getAddProdReqBody().getProductBrand());
		product.setProductCategory(addProdReq.getAddProdReqBody().getProductCategory());
		product.setProductId(randomString(17));
		product.setProductName(addProdReq.getAddProdReqBody().getProductName());
		product.persist() ;
		log.info("Inserted Product details to database") ;
		return product.getProductId();
	}

	public String updateProduct(UpdateProductRequest updateProdReq) {
	
		injectPersistenceContext() ;
		Product product = new Product() ;
		product = Product.findById(updateProdReq.getUpdateProdReqBody().getProductId()) ;
		
		if(updateProdReq.getUpdateProdReqBody().getIsActive()!=null)
		{
			product.setActive(Boolean.valueOf(updateProdReq.getUpdateProdReqBody().getIsActive()));
		}
		if(updateProdReq.getUpdateProdReqBody().getIsOnSale()!=null)
		{
			product.setOnSale(Boolean.valueOf(updateProdReq.getUpdateProdReqBody().getIsOnSale()));
		}
		if(updateProdReq.getUpdateProdReqBody().getProductBrand()!=null)
		{
			product.setProductBrand(updateProdReq.getUpdateProdReqBody().getProductBrand());
		}
		if(updateProdReq.getUpdateProdReqBody().getProductCategory()!=null)
		{
			product.setProductCategory(updateProdReq.getUpdateProdReqBody().getProductCategory());
		}
		if(updateProdReq.getUpdateProdReqBody().getProductName()!=null)
		{
			product.setProductName(updateProdReq.getUpdateProdReqBody().getProductName());
		}
		if(updateProdReq.getUpdateProdReqBody().getAmount()!=null)
		{
		if(updateProdReq.getUpdateProdReqBody().getAmount().getActivePrice()!=null)
		{
			product.setActivePrice(Long.parseLong(updateProdReq.getUpdateProdReqBody().getAmount().getActivePrice()));
		}
		if(updateProdReq.getUpdateProdReqBody().getAmount().getBasePrice()!=null)
		{
			product.setBasePrice(Long.parseLong(updateProdReq.getUpdateProdReqBody().getAmount().getBasePrice()));
		}
		}
		
		return "true" ;
	}

	public Product getProduct(String productId) {
		
		injectPersistenceContext() ;
		Product product = new Product() ;
		product = Product.findById(productId) ;
		return product ;
	}
	
	private String getProdUniqueId()
	{
		
		String productId = null ;
		ServiceInstance instance = null;
		
		List<ServiceInstance> instances = discoveryClient
			    .getInstances("TCS-POC-MS-IDGENERATOR");
			 if (instances != null && instances.size() > 0) {
				 instance = instances.get(0);
				
				 System.out.println("host:"+instance.getHost());
				 System.out.println("inside instance"+instance.getUri());
				 
			 }
				/* URI productUri = URI.create(String
						   .format("http://%s:%s/idgenerator/ID?type=PR" +
						    instance.getHost(), instance.getPort()));*/
			// String custId1 = restTemplate.getForObject("http://ec2-52-87-243-207.compute-1.amazonaws.com:8080/idgenerator/ID?type=PR", String.class);
			 //System.out.println("ID:"+custId1);
			 System.out.println("String URL: "+instance.getUri()+"/idgenerator/ID?type=PR");
				 productId = restTemplate.getForObject("http://TCS-POC-MS-IDGENERATOR/idgenerator/ID?type=PR", String.class);
			
			 return productId ;
	}
	
	String randomString(int len) {
		String testStr ="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" ;
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
               sb.append(testStr.charAt(rnd.nextInt(testStr.length())));
        return sb.toString();
 }

}
