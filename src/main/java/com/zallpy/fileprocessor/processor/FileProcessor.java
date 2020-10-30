package com.zallpy.fileprocessor.processor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.zallpy.fileprocessor.dto.Client;
import com.zallpy.fileprocessor.dto.Report;
import com.zallpy.fileprocessor.dto.Sale;
import com.zallpy.fileprocessor.dto.Salesman;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileProcessor implements Processor{
	
	public void process(Exchange exchange) {
		try {
			log.info("Processando arquivo...");
			
			List<Client> clients = new ArrayList<>();
			List<Salesman> salesmens = new ArrayList<>();
			List<Sale> sales = new ArrayList<>();
			
			
			String body = exchange.getIn().getBody(String.class);
			
			log.info("Conteudo:\n{}", body);
			
			final List<String> lines = Arrays.asList(body.split("\\r?\\n"));
			
			lines.forEach(line -> {
				
				if(line.startsWith("001")) {
					salesmens.add(Salesman.readLine(line));
				}else if (line.startsWith("002")) {
					clients.add(Client.readLine(line));
				}else if (line.startsWith("003")) {
					sales.add(Sale.readLine(line));
				}else {
					log.info("Invalid format line: {}.", line);
				}
				
			});
				
			Report report = new Report();
			report.setClients(clients.size());
			report.setSalesmens(salesmens.size());
			
			Sale bestSale = Collections.max(sales);
			report.setBestSaleId(bestSale.getSaleId());
			report.setBestSaleTotal(bestSale.getTotal());
			report.setBestSalesman(bestSale.getSalesmanName());
			
			Sale worstSalesman = Collections.min(sales);
			report.setWorstSalesman(worstSalesman.getSalesmanName());
			report.setWorstSaleId(worstSalesman.getSaleId());
			report.setWorstSaleTotal(worstSalesman.getTotal());
			
			String reportName = "REPORT_FINAL.done.dat"; // ((String) exchange.getIn().getHeader(Exchange.FILE_NAME)).replace(".dat", ".done.dat");
			
			// Ler valor atual do arquivo 
			
			String filename = System.getProperty("user.home")+File.separator+"data"+File.separator+"out"+File.separator+reportName;
			File fileReport = new File(filename);
			
			//Compara os dados do arquivo atual com o do relatorio em processamento.
			if(fileReport.exists()) {
				List<String> linesReport = Files.readAllLines(Path.of(filename));
				Report currentReport = Report.readLine(linesReport.get(1));
				
				Double bestSaleTotalMax = Math.max(currentReport.getBestSaleTotal(), report.getBestSaleTotal());
				Double worstSaleTotalMin = Math.min(currentReport.getWorstSaleTotal(), report.getWorstSaleTotal());
				
				if(bestSaleTotalMax.equals(report.getBestSaleTotal())) {
					currentReport.setBestSaleTotal(report.getBestSaleTotal());
					currentReport.setBestSaleId(report.getBestSaleId());
					currentReport.setBestSalesman(report.getBestSalesman());
				}
				
				if(worstSaleTotalMin.equals(report.getWorstSaleTotal())) {
					currentReport.setWorstSaleTotal(report.getWorstSaleTotal());
					currentReport.setWorstSaleId(report.getWorstSaleId());
					currentReport.setWorstSalesman(report.getWorstSalesman());
				}
				
				currentReport.incrementClients(report.getClients());
				currentReport.incrementSalesmens(report.getSalesmens());
				
				report = currentReport;
			}
			
			exchange.getMessage().setHeader(Exchange.FILE_NAME, reportName );
			
			log.info("Relatório:\n{}", report.toString());
			
			exchange.getMessage().setBody(report.toString());
			
			log.info("Relatorio foi salvo em: %HOMEPATH%/data/out/"+reportName);
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
}
