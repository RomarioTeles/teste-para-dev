package com.zallpy.fileprocessor.dto;

import org.springframework.util.StringUtils;

import com.zallpy.fileprocessor.exceptions.InvalidLineException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Comparable<Client>{

	private String CNPJ;
	
	private String name;
	
	private String businessArea;
	
	public static Client readLine(String line) {
		try {
			int countSeparator = StringUtils.countOccurrencesOf(line, "ç");
			
			if(countSeparator >= 3) {
				String[] columns = line.split("ç");
				return new Client(columns[1], columns[2], columns[3]);
			}else {
				throw new InvalidLineException(line);
			}
		}catch (Exception e) {
			throw new InvalidLineException(e.getMessage(), e);
		}
	}

	@Override
	public int compareTo(Client arg0) {
		try {
			return this.CNPJ.compareTo(arg0.CNPJ);
		}catch (Exception e) {
			return -1;
		}
	}
}
