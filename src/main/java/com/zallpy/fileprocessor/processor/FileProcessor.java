package com.zallpy.fileprocessor.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
			
			List<Client> clients = new ArrayList<>();
			List<Salesman> salesmens = new ArrayList<>();
			List<Sale> sales = new ArrayList<>();
			
			
			String body = exchange.getIn().getBody(String.class);
			
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
			report.setBestSale(bestSale.getSaleId());
			
			Sale worstSalesman = Collections.min(sales);
			report.setWorstSalesman(worstSalesman.getSalesmanName());
			
			String newname = ((String) exchange.getIn().getHeader(Exchange.FILE_NAME)).replace(".dat", ".done.dat");
			exchange.getMessage().setHeader(Exchange.FILE_NAME, newname );
			exchange.getMessage().setBody(report.toString());
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/*private void writeFile(String sourcePath, String line) throws IOException {
		
		
		File inputFile = new File(sourcePath);
		
		String filename = File.separator + inputFile.getName().replace(".dat", "") + ".done.dat";
		
		File file = new File(filename);
		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();
		
		FileWriter myWriter = new FileWriter(filename);
	    myWriter.write(line);
	    myWriter.close();
	    
	}*/


}
