package com.sapient.agency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sapient.agency.model.IncomeData;
import com.sapient.agency.model.OutputData;
import com.sapient.agency.utility.Enums.GENDER;
import com.sapient.agency.utility.Utility;


class AgencyApplicationTests extends AbstractTest{

	  // @Override
	 //  @Before
	   public void setUp() {
	      super.setUp();
	   }
	   

	   @Test
	   public void testDataAvgIncomeProcessing() {
		   
		   List<IncomeData> incomeDataList = new ArrayList<>();
		   
		   incomeDataList.add(new IncomeData("New Yark", "USA", "USD", 5.0, GENDER.MALE));
		   incomeDataList.add(new IncomeData("Boston", "USA", "USD", 4.0, GENDER.FEMALE));
		   incomeDataList.add(new IncomeData("New Yark", "USA", "USD", 16.0, GENDER.FEMALE));
		   incomeDataList.add(new IncomeData("Boston", "USA", "USD", 5.0, GENDER.MALE));
		   incomeDataList.add(new IncomeData("New Yark", "USA", "USD", 5.0, GENDER.MALE));
		   
		   List<OutputData> outputDatList=Utility.processData(incomeDataList);
		   Map<String, Double> result =  outputDatList.stream().collect(Collectors.groupingBy(OutputData::getGender, Collectors.summingDouble(OutputData::getAvgIncome)));
		   assertEquals(10.0d, result.get("F"), 0.1);
		   assertEquals(5.0d, result.get("M"), 0.1);
	   }
	   
	   @Test
	   public void testDataCurrencyConversionProcessing() {
		   
		   List<IncomeData> incomeDataList = new ArrayList<>();
		   
		   incomeDataList.add(new IncomeData("New Yark", "TEST", "TEST", 10.0, GENDER.MALE));
		   incomeDataList.add(new IncomeData("Boston", "TEST", "TEST", 4.0, GENDER.FEMALE));
		   incomeDataList.add(new IncomeData("New Yark", "TEST", "TEST", 16.0, GENDER.FEMALE));
		   incomeDataList.add(new IncomeData("Boston", "TEST", "TEST", 6.0, GENDER.MALE));
		   incomeDataList.add(new IncomeData("New Yark", "TEST", "TEST", 1.0, GENDER.MALE));
		   
		   List<OutputData> outputDatList=Utility.processData(incomeDataList);
		   Map<String, Double> result =  outputDatList.stream().collect(Collectors.groupingBy(OutputData::getGender, Collectors.summingDouble(OutputData::getAvgIncome)));
		   
		   assertEquals(5.0d, result.get("F"), 0.1);
		   assertEquals(3.0d, result.get("M"), 0.1);
	   }
	
}
