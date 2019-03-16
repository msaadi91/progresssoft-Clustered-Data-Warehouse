package com.progresssoft.dao;

import java.util.Collection;
import java.util.List;

import com.progresssoft.model.AccumulativeDeal;
import com.progresssoft.model.InValidDeal;
import com.progresssoft.model.ValidDeal;

public interface IFileUploadDAO {
	
	    boolean fileExists(String fileName);
	    
	    public <T extends ValidDeal> Collection<T> bulkValidSave(Collection<T> entities, List<AccumulativeDeal> accumulativeDeals);
	    
	    public <T extends InValidDeal> Collection<T> bulkInvalidSave(Collection<T> entities);

}
