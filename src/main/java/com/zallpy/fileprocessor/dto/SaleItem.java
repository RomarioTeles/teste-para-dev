package com.zallpy.fileprocessor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleItem {

	private Integer id;
	
	private Double quantity;
	
	private Double price;
	
	private Integer saleId;
	
	public Double getTotal() {
		try {
			return quantity * price;
		}catch (Exception e) {
			return 0.0D;
		}
	}
	
}
