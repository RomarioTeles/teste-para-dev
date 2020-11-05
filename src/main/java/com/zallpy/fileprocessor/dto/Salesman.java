package com.zallpy.fileprocessor.dto;

import org.springframework.util.StringUtils;

import com.zallpy.fileprocessor.exceptions.InvalidLineException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salesman implements Comparable<Salesman>{

	private String name;
	
	private String CPF;
	
	private Double salary;
	
	public static Salesman readLine(String line) {
		try {
			int countSeparator = StringUtils.countOccurrencesOf(line, "ç");
			
			if(countSeparator >= 3) {
				String[] columns = line.split("ç");
				return new Salesman(columns[2], columns[1], Double.valueOf(columns[3]));
			}else {
				throw new InvalidLineException(line);
			}
		}catch (Exception e) {
			throw new InvalidLineException(line, e);
		}
	}

	@Override
	public int compareTo(Salesman arg0) {
		try {
			return this.CPF.compareTo(arg0.CPF);
		}catch (Exception e) {
			return -1;
		}
	}
}
