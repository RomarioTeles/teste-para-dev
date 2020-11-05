package com.zallpy.fileprocessor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileProcessorApplicationTests {

	@Test
	void test() {
		FileProcessorApplication.main(new String[] {
			"--spring.profiles.active=test",
			"--spring.main.web-environment=false",
			"--logging.level.root=OFF",
        });
	}

}
