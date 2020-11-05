package com.zallpy.fileprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.zallpy.fileprocessor.dto.Client;
import com.zallpy.fileprocessor.dto.Salesman;
import com.zallpy.fileprocessor.exceptions.InvalidLineException;

public class SalesmanTests {

	private static final String FILE_LINE_CONTENT = "001ç1234567891234çPedroç50000";
	
	private static final String INVALID_FILE_LINE_CONTENT = "001;1234567891234;Pedro;50000";
	
	@Test
	void test_ReadLine_Extect_FullyClient() {
		
		Salesman salesman = Salesman.readLine(FILE_LINE_CONTENT);
		
		assertEquals("1234567891234", salesman.getCPF());
		assertEquals("Pedro", salesman.getName());
		assertEquals(50000D, salesman.getSalary());
	}
	
	@Test
	void test_ReadLine_Extect_ThrowsInvalidLineException() {
		
		assertThrows(InvalidLineException.class, () -> Client.readLine(INVALID_FILE_LINE_CONTENT));
	}
}
