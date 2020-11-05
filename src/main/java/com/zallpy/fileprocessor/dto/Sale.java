package com.zallpy.fileprocessor.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import com.zallpy.fileprocessor.exceptions.InvalidLineException;

import lombok.Data;

@Data
public class Sale implements Comparable<Sale> {

	private Integer saleId;
	
	private String salesmanName;
	
	private Double total = 0.0D;
	
	private List<SaleItem> items = new ArrayList<SaleItem>();

	@Override
	public int compareTo(Sale arg0) {
		try {
			return this.total.compareTo(arg0.total);
		}catch (Exception e) {
			return -1;
		}
	}
	
	public static Sale readLine(String line) {
		int countSeparator = StringUtils.countOccurrencesOf(line, "ç");
		
		try {
			if(countSeparator >= 3) {
				String[] columns = line.split("ç");
				
				Sale sale = new Sale();
				sale.setSaleId(Integer.valueOf(columns[1]));
				sale.setSalesmanName(columns[3]);
				
				String[] items = columns[2].replaceAll("\\[", "").replaceAll("\\]", "").split(",");
				
				Double[] total = { 0.0D };
				
				Arrays.asList(items).forEach(
					itemData -> {
						String[] columnsItem = itemData.split("-");
						SaleItem item = new SaleItem();
						item.setSaleId(sale.getSaleId());
						item.setId(Integer.valueOf(columnsItem[0].trim()));
						item.setQuantity(Double.valueOf(columnsItem[1].trim()));
						item.setPrice(Double.valueOf(columnsItem[2].trim()));
						
						total[0] += item.getTotal();
						
						sale.items.add(item);
					}
				);
				
				sale.setTotal(total[0]);
				
				return sale;
			}else {
				throw new InvalidLineException(line);
			}
		}catch (Exception e) {
			throw new InvalidLineException(line, e);
		}
	}
	
}
