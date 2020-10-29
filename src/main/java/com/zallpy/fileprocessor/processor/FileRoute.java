package com.zallpy.fileprocessor.processor;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class FileRoute extends RouteBuilder {
	
	@Bean
	public GenericFileFilter myFilter(){
		  return new GenericFileFilter() { 
				public boolean accept(GenericFile pathname) {
			      return pathname.getFileName().endsWith(".dat");
			   }
		  };
	}
	
	
	@Scheduled(fixedDelay = 1000)
	@Override
	public void configure() throws Exception {
		
		System.out.println("Configure");
		
		from("file:C:\\Users\\rpires\\in?move=done&filter=#myFilter").process(new FileProcessor()).to("file:C:\\Users\\rpires\\out");
		
	}
	
}
