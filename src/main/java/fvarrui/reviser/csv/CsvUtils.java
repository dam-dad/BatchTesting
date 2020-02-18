package fvarrui.reviser.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class CsvUtils {
	
	private static final char SEPARATOR = ',';

	public static void resultsToCsv(List<CsvResult> results, File csvFile) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
	    Writer writer = new FileWriter(csvFile, Charset.forName("UTF-8"));
	    StatefulBeanToCsv<CsvResult> sbc = new StatefulBeanToCsvBuilder<CsvResult>(writer)
	    		.withSeparator(SEPARATOR)
	    		.build();
	    sbc.write(results);
	    writer.close();
	}

	public static List<CsvStudent> csvToStudents(File csvFile) throws IOException, CsvException {
		
		HeaderColumnNameMappingStrategy<CsvStudent> ms = new HeaderColumnNameMappingStrategy<>();
		ms.setType(CsvStudent.class);
				
		FileReader reader = new FileReader(csvFile, Charset.forName("UTF-8"));
		CsvToBean<CsvStudent> cb = new CsvToBeanBuilder<CsvStudent>(reader)
				.withMappingStrategy(ms)
				.withSeparator(SEPARATOR)
				.build();
		List<CsvStudent> students = cb.parse();
	    reader.close();
	    
	    return students;
		
	}

}
