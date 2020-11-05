package com.zallpy.fileprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.zallpy.fileprocessor.dto.Client;
import com.zallpy.fileprocessor.dto.Sale;
import com.zallpy.fileprocessor.exceptions.InvalidLineException;

public class SaleTests {

	private static final String FILE_LINE_CONTENT = "003ç10ç[1-1-200, 2-2-150]çRAIMUNDO";
	
	private static final String INVALID_FILE_LINE_CONTENT = "003;10;[1-1-200, 2-2-150.5];RAIMUNDO";
	
	@Test
	void test_ReadLine_Extect_FullyClient() {
		
		Sale sale = Sale.readLine(FILE_LINE_CONTENT);
		
		assertEquals(10, sale.getSaleId());
		assertEquals(500D, sale.getTotal());
		assertEquals(2, sale.getItems().size());
		
		assertEquals(1, sale.getItems().get(0).getId());
		assertEquals(1, sale.getItems().get(0).getQuantity());
		assertEquals(200D, sale.getItems().get(0).getPrice());
		assertEquals(200D, sale.getItems().get(0).getTotal());
		
		assertEquals(2, sale.getItems().get(1).getId());
		assertEquals(2, sale.getItems().get(1).getQuantity());
		assertEquals(150D, sale.getItems().get(1).getPrice());
		assertEquals(300D, sale.getItems().get(1).getTotal());
	}
	
	@Test
	void test_ReadLine_Extect_ThrowsInvalidLineException() {
		
		assertThrows(InvalidLineException.class, () -> Client.readLine(INVALID_FILE_LINE_CONTENT));
	}
}
