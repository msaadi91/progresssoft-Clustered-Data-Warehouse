package com.progresssoft.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progresssoft.dao.IFileUploadDAO;
import com.progresssoft.model.AccumulativeDeal;
import com.progresssoft.model.CSVRecord;
import com.progresssoft.model.InValidDeal;
import com.progresssoft.model.ValidDeal;
import com.progresssoft.util.Logger;


/**
 * Service class for file upload 
 *
 * @author Mohammad-Alsaadi
 */

@Service
public class FileUploadService implements IFileUploadService {
	
	private static final Logger logger = Logger.getLogger(FileUploadService.class);
	
	@Autowired
	private IFileUploadDAO dao;


	/**
	 * This method insert valid bulk deal 
	 *
	 * @param dealDetails List of deal details
	 * 
	 */
	@Override
	public void saveValidData(List<CSVRecord> dealDetails) {
		logger.logInfo("Stepped into the saveValidData() method CSV record size: "+ dealDetails.size());
		List<ValidDeal> validDeals = new ArrayList<>();
		Map accumulativeValues = new HashMap<>(); 
		for(CSVRecord deal:dealDetails){
			ValidDeal target = new ValidDeal();
			if(accumulativeValues.containsKey(deal.getFromCurrency())){
				int value = Integer.parseInt(String.valueOf((accumulativeValues.get(deal.getFromCurrency()))));
				accumulativeValues.put(deal.getFromCurrency(), ++value);
			}
			else{
				accumulativeValues.put(deal.getFromCurrency(), 1);
			}
			BeanUtils.copyProperties(deal, target);
			validDeals.add(target);
		}
		List<AccumulativeDeal> accumulativeDeals = new ArrayList<>();
		for (Object key : accumulativeValues.keySet()) {
			AccumulativeDeal accumulativeDeal = new AccumulativeDeal();
			accumulativeDeal.setCount(new BigInteger(String.valueOf(accumulativeValues.get(key))));
			accumulativeDeal.setOderingCurrency(key.toString());
			accumulativeDeals.add(accumulativeDeal);
		    System.out.println("Key = " + key + " - " + accumulativeValues.get(key));
		}
		dao.bulkValidSave(validDeals, accumulativeDeals);
	}

	/**
	 * This method insert invalid bulk deal 
	 *
	 * @param dealDetails List of deal details
	 * 
	 */
	@Override
	public void saveInValidData(List<CSVRecord> dealDetails) {

		logger.logInfo("Stepped into the saveInValidData() method CSV record size: "+ dealDetails.size());
		List<InValidDeal> inValidDeals = new ArrayList<>();
		
		for(CSVRecord deal:dealDetails){
			InValidDeal target = new InValidDeal();
			BeanUtils.copyProperties(deal, target);
			inValidDeals.add(target);
		}
		dao.bulkInvalidSave(inValidDeals);
	}

	/**
	 * This method check the file name exist in Database 
	 *
	 * @param fileName 
	 * @return true or false
	 */
	@Override
	public boolean checkFileExist(String fileName) {
		logger.logInfo("Stepped into the checkFileExist() method file name: "+ fileName);
		
		return dao.fileExists(fileName);
	}

}
