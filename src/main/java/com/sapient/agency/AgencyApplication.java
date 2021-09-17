package com.sapient.agency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sapient.agency.reader.IncomeDataReader;
import com.sapient.agency.utility.Utility;
import com.sapient.agency.writer.IncomeDataWriter;
/**
 * 
 * @author Hemraj
 * This will be run as
 * java -jar -agency.jar SampleInputData.csv SampleOutputData.csv
 *
 */
@SpringBootApplication
public class AgencyApplication  implements CommandLineRunner{

	Logger logger = LoggerFactory.getLogger(AgencyApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(AgencyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		String inputFile = "";
		String outputFile = "Sample_Output.csv";
		if(args.length >0 && !"".equals(args[0])) {
			inputFile = args[0];
			logger.info("Provided input file is = " + inputFile); 
			if(args.length >1 && !"".equals(args[1])) {
				outputFile=args[1];
			}
			
			IncomeDataReader incomeDataReader = Utility.getDataReader(inputFile);
			IncomeDataWriter incomeDataWriter = Utility.getDataWriter(outputFile);
			
			String inputFileLocation = Utility.getFileLocation(inputFile);
			String outputFileLocation = Utility.getFileLocation(outputFile);
			
			incomeDataWriter.writeIncomeDataToFile(outputFileLocation, Utility.processData(incomeDataReader.readIncomeData(inputFileLocation)));
		}else{
			logger.error("Kindly provide csv file of income data"); 
			logger.error("Format is as ===> java -jar agency-0.0.1-SNAPSHOT.jar SampleInputData.csv SampleOutputData.csv");
			
		}
		
		
	}

}
