package com.sapient.agency.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.sapient.agency.model.IncomeData;
import com.sapient.agency.utility.Utility;
import com.sapient.agency.utility.Enums.GENDER;

public class IncomeDataCsvReader implements IncomeDataReader {
	
	private static Logger logger = LoggerFactory.getLogger(IncomeDataCsvReader.class);

	public IncomeDataCsvReader() {
		
	}
    @Override
	public List<IncomeData> readIncomeData(String fileLocation) {
    	logger.info("Reading Csv Data... "); 
    	List<IncomeData> incomeDataList =  new ArrayList<>();
    	try {
           
            FileReader filereader = new FileReader(fileLocation);
 
           
            CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
 
            
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                                      .withCSVParser(parser)
                                      .build();
 
           
            List<String[]> allData = csvReader.readAll();
            allData.remove(0);
            
            incomeDataList = allData.stream().map(row->{
            	IncomeData incomeData = new IncomeData();
            	incomeData.setCity(row[0]);
            	if(row[1]==null ||"".equals(row[1])) {
            		incomeData.setCountry(row[0]);
            	}else {
            		incomeData.setCountry(row[1]);
            	}
            	incomeData.setGender(GENDER.MALE.getValue().equals(row[2])?GENDER.MALE:GENDER.FEMALE);
            	incomeData.setCurrency(row[3]);
            	double income=0.0;
            	try {
            		income = Double.parseDouble(row[4]);
            	}catch(Exception e) {
            		e.printStackTrace();
            	}
            	
            	incomeData.setIncome(income);
            	return incomeData;
            }).collect(Collectors.toList());
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		
		return incomeDataList;

	}
    
    public static void main(String[] args) {
    	IncomeDataCsvReader csvR = new IncomeDataCsvReader();
    	csvR.readIncomeData("");
    }

}
