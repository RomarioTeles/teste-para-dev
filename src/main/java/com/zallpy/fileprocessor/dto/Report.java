package com.zallpy.fileprocessor.dto;

import java.text.MessageFormat;

import lombok.Data;

@Data
public class Report {

	private Integer clients;
	
	private Integer salesmens;
	
	private Integer bestSale;
	
	private String worstSalesman;

	@Override
	public String toString() {
		return MessageFormat.format("Total ClientsçTotal SalesmançbestSaleçworstSalesman\n{0}ç{1}ç{2}ç{3}", clients, salesmens, bestSale, worstSalesman);
	}
	
	
}
