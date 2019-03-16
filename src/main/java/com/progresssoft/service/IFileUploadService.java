package com.progresssoft.service;

import java.util.List;

import com.progresssoft.model.CSVRecord;
import com.progresssoft.model.DealModel;
import com.progresssoft.model.ValidDeal;
/**
 * @author Mohammad-Alsaadi
 *
 */
public interface IFileUploadService {

	void saveValidData(List<CSVRecord> dealDetails);
	
	void saveInValidData(List<CSVRecord> dealDetails);
	
	boolean checkFileExist(String fileName);

}
