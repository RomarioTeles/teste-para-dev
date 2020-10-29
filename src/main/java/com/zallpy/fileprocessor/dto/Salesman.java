package com.zallpy.fileprocessor.dto;

import org.springframework.util.StringUtils;

import com.zallpy.fileprocessor.exceptions.InvalidLineException;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Salesman {

	private String name;
	
	private String CPF;
	
	private Double salary;
	
	public static Salesman readLine(String line) {
		try {
			int countSeparator = StringUtils.countOccurrencesOf(line, "ç");
			
			if(countSeparator >= 3) {
				String[] columns = line.split("ç");
				return new Salesman(columns[1], columns[2], Double.valueOf(columns[3]));
			}else {
				throw new InvalidLineException(line);
			}
		}catch (Exception e) {
			throw new InvalidLineException(line, e);
		}
	}
}
