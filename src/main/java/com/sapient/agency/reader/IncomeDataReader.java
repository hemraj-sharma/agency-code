package com.sapient.agency.reader;

import java.util.List;

import com.sapient.agency.model.IncomeData;

public interface IncomeDataReader {
	
	public List<IncomeData> readIncomeData(String fileLocation);

}
