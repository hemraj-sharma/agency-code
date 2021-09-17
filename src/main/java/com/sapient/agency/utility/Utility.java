package com.sapient.agency.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.agency.AgencyApplication;
import com.sapient.agency.model.IncomeData;
import com.sapient.agency.model.OutputData;
import com.sapient.agency.reader.IncomeDataCsvReader;
import com.sapient.agency.reader.IncomeDataReader;
import com.sapient.agency.reader.IncomeDataXmlReader;
import com.sapient.agency.writer.IncomeDataCsvWriter;
import com.sapient.agency.writer.IncomeDataWriter;
import com.sapient.agency.writer.IncomeDataXmlWriter;

@Component
public class Utility {
	
	
	private final static String USD_CURRENCY="USD";
	private final static String FILE_TYPE_CSV="csv";
	private final static String FILE_TYPE_XML="xml";
	private final static String CURRENCY_RATES_JSON_FILE="currencyRates.json";
	
	private static Logger logger = LoggerFactory.getLogger(Utility.class);
	
	public Utility() throws IOException {
		
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Double> loadCurrencyRates(){ 
	 Map<String, Double> currencyRates =null;
	 try {
		try (InputStream is = new ClassPathResource(CURRENCY_RATES_JSON_FILE).getInputStream()){
		 ObjectMapper mapper = new ObjectMapper();

		    
		 currencyRates = mapper.readValue(is, Map.class);
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	 return currencyRates;
      
 }

	public static void main(String[] args) throws IOException {
    }
	
	public static Double convertCurrenyToUSD(String currencyType, Double currencyValue) {
		BigDecimal usdValue;
		BigDecimal originalCurrencyValue
            = new BigDecimal(currencyValue);
        BigDecimal divisor
            = new BigDecimal(loadCurrencyRates().get(currencyType));
  
        
        usdValue = originalCurrencyValue.divide(divisor, RoundingMode.CEILING);
  
		return usdValue.doubleValue();
	}
	
	public static List<OutputData> processData(List<IncomeData> incomeDataList){
		logger.info("Processing Data to Output "); 
		List<IncomeData> allIncomeInUsdDataList = incomeDataList.stream().map(d2 ->{
			if(!USD_CURRENCY.equals(d2.getCurrency()))
			 d2.setIncome(Utility.convertCurrenyToUSD(d2.getCurrency(), d2.getIncome()));
			return d2;
		}).collect(Collectors.toList());
		
    	Function<IncomeData, String> groupByCountry = IncomeData::getCountry;
    	Function<IncomeData, String> groupByGender = id -> id.getGender().getValue();
       
      Map<String, Map<String, Double>> map2 = allIncomeInUsdDataList.stream().collect(Collectors.groupingBy(groupByCountry))
    		   .entrySet().stream().collect(Collectors.toMap(
    				   e->e.getKey(),
    				   e ->{
    					   List<IncomeData> lst = e.getValue();
    					   return lst.stream().collect(Collectors.groupingBy(groupByGender, Collectors.averagingDouble(IncomeData::getIncome)));
    				   }
    				   ));
      

      List<OutputData> outputDataList = new ArrayList<>();
      for(Map.Entry<String, Map<String, Double>> entry:map2.entrySet()) {
    	  
    	  for(Map.Entry<String, Double> entry2: entry.getValue().entrySet()) {
    		  OutputData outputData = new  OutputData();
        	  outputData.setCityOrCountry(entry.getKey());
        	  outputData.setGender(entry2.getKey());
        	  outputData.setAvgIncome(entry2.getValue());
        	  outputDataList.add(outputData);
    	  }
    	  
      }
      
      return outputDataList.stream().sorted(Comparator
              .comparing(OutputData::getCityOrCountry)
              .thenComparing(OutputData::getGender)).collect(Collectors.toList());
	}


	public static IncomeDataReader getDataReader(String inputFile) {
		IncomeDataReader incomeDataReader;
		String ext = inputFile.substring(inputFile.indexOf('.')+1);
		if(FILE_TYPE_XML.equals(ext)) {
			incomeDataReader = new IncomeDataXmlReader();
			logger.info("Providing Xml Reader "); 
		}else {
			incomeDataReader = new IncomeDataCsvReader();
			logger.info("Providing Csv Reader "); 
		}
		
		return incomeDataReader;
	}


	public static IncomeDataWriter getDataWriter(String outputFile) {
		IncomeDataWriter incomeDataWriter;
		String ext = outputFile.substring(outputFile.indexOf('.')+1);
		if(FILE_TYPE_XML.equals(ext)) {
			incomeDataWriter = new IncomeDataXmlWriter();
			logger.info("Providing Xml Writer "); 
		}else {
			incomeDataWriter = new IncomeDataCsvWriter();
			logger.info("Providing Csv Writer "); 
		}
		
		return incomeDataWriter;
	}


	public static String getFileLocation(String file) {
		
		return file;
	}
}
