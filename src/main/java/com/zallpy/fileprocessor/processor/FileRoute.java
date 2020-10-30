package com.zallpy.fileprocessor.processor;

import java.io.File;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FileRoute extends RouteBuilder {
	
	@SuppressWarnings("rawtypes")
	@Bean
	public GenericFileFilter myFilter(){
		  return new GenericFileFilter() { 
				public boolean accept(GenericFile pathname) {
			      return pathname.getFileName().endsWith(".dat");
			   }
		  };
	}
	
	
	//@Scheduled(fixedDelay = 1000)
	@Override
	public void configure() throws Exception {
		
		String in = "file:"+System.getProperty("user.home")+File.separator+"data"+File.separator+"in?move=done&filter=#myFilter";
		String out = "file:"+System.getProperty("user.home")+File.separator+"data"+File.separator+"out";
		
		log.info("Configurando observer. Pasta de origem: "+ in +". Pasta de destino: "+ out +"." );
		
		from(in).process(new FileProcessor()).to(out);
		
	}
	
}
