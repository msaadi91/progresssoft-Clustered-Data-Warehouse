package com.progresssoft.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hamcrest.beans.HasProperty;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.progresssoft.controller.UploadController;
import com.progresssoft.model.CSVRecord;
import com.progresssoft.service.FileUploadService;
import com.progresssoft.util.Logger;

public class FileUploadControllerTest extends Mockito{

    private final Logger logger = Logger.getLogger(FileUploadControllerTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;
    
	@Mock
	FileUploadService service;
	
	@Mock
	MessageSource message;
	
	@InjectMocks
	UploadController fileUploadController;
	
	@Spy
	List<CSVRecord> validDeals = new ArrayList<CSVRecord>();
	
	@Spy
	List<CSVRecord> inValidDeals = new ArrayList<CSVRecord>();

	@Spy
	ModelMap model;
	
	@Mock
	BindingResult result;
	
	String fileName = "sample.csv";
	
	File file;
	
    HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class);   
	
	@BeforeClass
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		readCSVFile(getFileList());
	}
	
	
	@Test
	public void newFileUpload() throws FileNotFoundException, IOException{
		String METHOD_NAME = "newStorage";
		logger.logInfo(METHOD_NAME+ " started..");
		
        MockMultipartFile multipartFile = new MockMultipartFile(fileName, 
				org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(file)));

		
		 MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	        try {
				mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload")
				                .file(multipartFile))
				            .andExpect(MockMvcResultMatchers.status().is(200))
				            		.andReturn();
			} catch (Exception e) {
				e.printStackTrace();
			}		
	                    		
		Assert.assertNotNull(model.get("documents"));
	}

	
	public List<String[]> getFileList() {
		
		ClassLoader classLoader = getClass().getClassLoader();
		file = new File(classLoader.getResource(fileName).getFile());
		System.out.println(file.getAbsolutePath());
		return fileUploadController.readCSVFile(file, fileName);
		
	}
	
	public void readCSVFile(List<String[]> lines){
		for(String[] line:lines) {
        	CSVRecord csvRecord = fileUploadController.extractData(line);
        	csvRecord.setFileName(fileName);
        	
        	if(StringUtils.isEmpty(csvRecord.getFromCurrency()) ||
        	StringUtils.isEmpty(csvRecord.getToCurrency()) ||
        	StringUtils.isEmpty(csvRecord.getDealDate()) ||
        	StringUtils.isEmpty(csvRecord.getAmount()) 
        	){
        		inValidDeals.add(csvRecord);
        	}
        	else{
        		validDeals.add(csvRecord);
        		
        	}
        
    }
	}
}
