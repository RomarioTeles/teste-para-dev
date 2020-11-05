package com.zallpy.fileprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.zallpy.fileprocessor.dto.Client;
import com.zallpy.fileprocessor.exceptions.InvalidLineException;

public class ClientTests {

	private static final String FILE_LINE_CONTENT = "002ç2345675434544345çJose da SilvaçRural";
	
	private static final String INVALID_FILE_LINE_CONTENT = "002;2345675434544345;Jose da Silva;Rural";
	
	@Test
	void test_ReadLine_Extect_FullyClient() {
		
		Client client = Client.readLine(FILE_LINE_CONTENT);
		
		assertEquals("2345675434544345", client.getCNPJ());
		assertEquals("Jose da Silva", client.getName());
		assertEquals("Rural", client.getBusinessArea());
	}
	
	@Test
	void test_ReadLine_Extect_ThrowsInvalidLineException() {
		
		assertThrows(InvalidLineException.class, () -> Client.readLine(INVALID_FILE_LINE_CONTENT));
	}
}
