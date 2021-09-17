package com.sapient.agency.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContextExtensionsKt;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sapient.agency.model.IncomeData;
import com.sapient.agency.model.OutputData;
import com.sapient.agency.reader.IncomeDataCsvReader;
import com.sapient.agency.utility.Utility;

public class IncomeDataCsvWriter implements IncomeDataWriter{
	
	private static Logger logger = LoggerFactory.getLogger(IncomeDataCsvWriter.class);

	public IncomeDataCsvWriter() {
		
	}

	@Override
	public void writeIncomeDataToFile(String outputFileLocation,  List<OutputData> outputDataList) {
		logger.info("Writig output to csv file - " + outputFileLocation);
	    Writer filewriter=null;
	    File file=new File(outputFileLocation);
		try {
			filewriter = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] csvHeaderLine = new String[]{"City/Country","Gender","Average Income(in USD)"};
		List<String[]> inputToFile =new ArrayList<>();
		inputToFile.add(csvHeaderLine);
		
		for(OutputData od : outputDataList) {
			String[] csvBodyLine = new String[]{od.getCityOrCountry(),od.getGender(),od.getAvgIncome().toString()};
			inputToFile.add(csvBodyLine);
		}

		CSVWriter csvWriter = new CSVWriter(filewriter);
		csvWriter.writeAll(inputToFile);
		try {
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}



	  
		 
	}
	

	public static void main(String[] args) {
		IncomeDataCsvWriter csvW = new IncomeDataCsvWriter();
    	IncomeDataCsvReader csvR = new IncomeDataCsvReader();
    	List<IncomeData> incomeDataList = csvR.readIncomeData("C:\\Users\\hreamz\\Desktop\\Sapient docs\\prep\\statement\\Sample_Input.csv");
    	Function<IncomeData, String> groupByCountry = IncomeData::getCountry;
    	Function<IncomeData, String> groupByGender = id -> id.getGender().getValue();
       Map<String, List<IncomeData>> map = incomeDataList.stream().collect(Collectors.groupingBy(groupByCountry));
       
       
      Map<String, Map<String, Double>> map2 = 
    		   map.entrySet().stream().collect(Collectors.toMap(
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
      csvW.writeIncomeDataToFile("C:\\Users\\hreamz\\Desktop\\Sapient docs\\prep\\statement\\Output.csv",outputDataList.stream().sorted(Comparator
              .comparing(OutputData::getCityOrCountry)
              .thenComparing(OutputData::getGender)).collect(Collectors.toList()));
      System.out.println(outputDataList);
    }

}
