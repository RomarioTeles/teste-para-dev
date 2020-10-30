package com.zallpy.fileprocessor.dto;

import java.text.MessageFormat;

import org.springframework.util.StringUtils;

import com.zallpy.fileprocessor.exceptions.InvalidLineException;

import lombok.Data;

@Data
public class Report {

	private Integer clients = 0;

	private Integer salesmens = 0;

	private Integer bestSaleId;

	private Integer worstSaleId;

	private String bestSalesman;
	
	private String worstSalesman;

	private Double worstSaleTotal = 0.0D;

	private Double bestSaleTotal = 0.0D;

	@Override
	public String toString() {
		return MessageFormat.format(
				"Total Clients ç Total Salesman ç Best Sale Id ç Best Sale total ç Worst Sale Id ç Worst Sale Total ç Best Salesman Name ç Worst Salesman Name\n{0}ç{1}ç{2}ç{3}ç{4}ç{5}ç{6}ç{7}",
				clients, salesmens, bestSaleId, bestSaleTotal, worstSaleId, worstSaleTotal, bestSalesman, worstSalesman);
	}
	
	public void incrementClients(int sum) {
		clients += sum;
	}
	
	public void incrementSalesmens(int sum) {
		salesmens += sum;
	}

	public static Report readLine(String line) {
		int countSeparator = StringUtils.countOccurrencesOf(line, "ç");

		try {
			if (countSeparator >= 6) {
				String[] columns = line.split("ç");

				Report sale = new Report();
				sale.setClients(Integer.valueOf(columns[0].trim()));
				sale.setSalesmens(Integer.valueOf(columns[1].trim()));
				sale.setBestSaleId(Integer.valueOf(columns[2].trim()));
				sale.setBestSaleTotal(Double.valueOf(columns[3].trim().replace(".", "").replace(",", ".")));
				sale.setWorstSaleId(Integer.valueOf(columns[4].trim()));
				sale.setWorstSaleTotal(Double.valueOf(columns[5].trim().replace(".", "").replace(",", ".")));
				sale.setBestSalesman(columns[6]);
				sale.setWorstSalesman(columns[7]);
				
				return sale;
			} else {
				throw new InvalidLineException(line);
			}
		} catch (Exception e) {
			throw new InvalidLineException(line, e);
		}
	}

}
