package com.zallpy.fileprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.zallpy.fileprocessor.dto.Report;
import com.zallpy.fileprocessor.processor.FileProcessor;

class FileProcessorTests {
	
	private Path inDir = Path.of(System.getProperty("java.io.tmpdir"), "data", "in");
	
	private Path outDir = Path.of(System.getProperty("user.home"), "data", "out");
	
	@Test
	void testProcess_when_report_final_file_notExists() throws Exception {
		
		if(!Files.exists(inDir)) {
			Files.createDirectories(inDir);
		}
		
		Path inputFile = new File(inDir.toFile(), "Exemplo.dat").toPath();
		
		Path outPutFile = new File(outDir.toFile(), "REPORT_FINAL.done.dat").toPath();
		
		Files.deleteIfExists(inputFile);
		
		Files.deleteIfExists(outPutFile);
		
		// copy content from resources path to /in path
		Path resourceInputFilePath = Paths.get("src","test","resources", "Exemplo.dat");
		
		Files.copy(resourceInputFilePath, inputFile);
		
		FileProcessor fileProcessor = new FileProcessor();
		
		String body = Files.readString(inputFile);
		
		Report report = fileProcessor.process(body);
		
		System.out.println(report.toString());
		
		assertEquals("MARIA", report.getBestSalesman());
		assertEquals(500.0D, report.getBestSaleTotal());
		assertEquals(8, report.getBestSaleId());
		
		assertEquals("RAIMUNDO", report.getWorstSalesman());
		assertEquals(200.0D, report.getWorstSaleTotal());
		assertEquals(10, report.getWorstSaleId());
		
		assertEquals(2, report.getClients());
		assertEquals(2, report.getSalesmens());
	}
	
	@Test
	void testProcess_when_report_final_file_alredyExists() throws Exception {
		
		if(!Files.exists(inDir)) {
			Files.createDirectories(inDir);
		}
		
		if(!Files.exists(outDir)) {
			Files.createDirectories(outDir);
		}
		
		Path inputFile = new File(inDir.toFile(), "Exemplo.dat").toPath();
		
		Path outPutFile = new File(outDir.toFile(), "REPORT_FINAL.done.dat").toPath();
		
		Files.deleteIfExists(inputFile);
		
		Files.deleteIfExists(outPutFile);
		
		// copy content from resources path to /in path
		Path resourceInputFilePath = Paths.get("src","test","resources", "Exemplo.dat");
		
		Path resourceOutputFilePath = Paths.get("src","test","resources", "REPORT_FINAL.done.dat");
		
		Files.copy(resourceInputFilePath, inputFile);
		
		Files.copy(resourceOutputFilePath, outPutFile);
		
		FileProcessor fileProcessor = new FileProcessor();
		
		String body = Files.readString(inputFile);
		
		Report report = fileProcessor.process(body);
		
		System.out.println(report.toString());
		
		assertEquals("MARIA", report.getBestSalesman());
		assertEquals(500.0D, report.getBestSaleTotal());
		assertEquals(8, report.getBestSaleId());
		
		assertEquals("RAIMUNDO", report.getWorstSalesman());
		assertEquals(200.0D, report.getWorstSaleTotal());
		assertEquals(10, report.getWorstSaleId());
		
		assertEquals(4, report.getClients());
		assertEquals(4, report.getSalesmens());
	}

}
