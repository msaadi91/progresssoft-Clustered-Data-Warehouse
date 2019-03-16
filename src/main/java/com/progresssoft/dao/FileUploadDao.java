package com.progresssoft.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.progresssoft.model.AccumulativeDeal;
import com.progresssoft.model.DealModel;
import com.progresssoft.model.InValidDeal;
import com.progresssoft.model.ValidDeal;
import com.progresssoft.service.FileUploadService;
import com.progresssoft.util.Logger;

/**
 * Data access layer for file upload 
 *
 * @author Mohammad-Alsaadi
 */
@Transactional
@Repository
public class FileUploadDao implements IFileUploadDAO {
	
	private static final Logger logger = Logger.getLogger(FileUploadService.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${hibernate.jdbc.batch_size}")
	private int batchSize;

	/**
	 * This method check the file name exist in Database 
	 *
	 * @param fileName 
	 * @return true or false
	 */
	@Override
	public boolean fileExists(String fileName) {
		Session session = sessionFactory.openSession();
		return ((Long)session.createQuery("select count(*) from ValidDeal where fileName=:fileName").setParameter("fileName", fileName).uniqueResult()).intValue() > 0 ? true : false;
	}

	/**
	 * This method insert invalid bulk deal 
	 *
	 * @param entities ValidBulkDeal Collection
	 * 
	 */
	@Override
	public <T extends InValidDeal> Collection<T> bulkInvalidSave(Collection<T> entities) {
		String METHOD_NAME = "bulkInvalidSave";
		logger.logInfo( METHOD_NAME +" started...");
		return saveEntities(entities);
	}
	
	/**
	 * This method insert valid bulk deal 
	 *
	 * @param entities ValidBulkDeal Collection
	 * @param accumulativeDeals List of AccumulativeDeal 
	 * 
	 */
	@Override
	public <T extends ValidDeal> Collection<T> bulkValidSave(Collection<T> entities, List<AccumulativeDeal> accumulativeDeals) {
		String METHOD_NAME = "bulkValidSave";
		logger.logInfo( METHOD_NAME +" started...");
		
		saveAccumulativeDeals(accumulativeDeals);
		return saveEntities(entities);
		
		
	}
	
	/**
	 * This method insert entities into the Database
	 *
	 * @param entities Collection
	 * 
	 */
	private <T extends DealModel>Collection<T> saveEntities(Collection<T> entities) {
		String METHOD_NAME = "bulkValidSave";
		logger.logInfo( METHOD_NAME +" started...");
		final List<T> savedEntities = new ArrayList<T>(entities.size());
		  
		  Session session = sessionFactory.openSession();
		   Transaction tx = session.beginTransaction();
		    
		  int i = 0;
		  for (T t : entities) {
			  session.save(t);
		    i++;
		    if (i % batchSize == 0) {
		      // Flush a batch of inserts and release memory.
		    	 session.flush(); //flush the batch of insert
		         session.clear(); //release memory
		    }
		  }

		   tx.commit();
		   session.close();
		   logger.logInfo( METHOD_NAME +" ended...");
		  return savedEntities;
	
	}
	
	/**
	 * This method save all AccumulativeDeals
	 *
	 * @param list
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void saveAccumulativeDeals(List list)throws HibernateException{
	    Session session = sessionFactory.openSession();
	    Transaction tx = session.beginTransaction();
	    Iterator it = list.iterator();
	    int i = 0;
	    while(it.hasNext()){ 
	        i++;
	        AccumulativeDeal accumulativeDeal = (AccumulativeDeal)it.next();
	        List<AccumulativeDeal> deals = session.createQuery("from AccumulativeDeal where oderingCurrency=:oderingCurrency")
	        							.setParameter("oderingCurrency", accumulativeDeal.getOderingCurrency()).list();
	        if(deals.size() > 0){
	        	AccumulativeDeal deal = deals.get(0);
	        	deal.setCount(deal.getCount().add(accumulativeDeal.getCount()));
	        	session.saveOrUpdate(deal);
	        }
	        else{
	        	session.persist(accumulativeDeal);
	        }
	        
	        
	        if (i % batchSize == 0) { session.flush(); session.clear(); }
	    }
	    tx.commit();
		   session.close();
	}
	
	
}