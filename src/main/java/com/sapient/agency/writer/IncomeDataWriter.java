package com.sapient.agency.writer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.sapient.agency.model.IncomeData;
import com.sapient.agency.model.OutputData;

public interface IncomeDataWriter {

	
public void writeIncomeDataToFile(String path,  List<OutputData> outputDataList) throws IOException;

}
