package com.bigdata.newmission;

import com.azure.storage.blob.BlobContainerClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IlearnSpringbootTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private BlobContainerClient blobContainerClient;

	@Test
	public void testUpload(){
		var blobclient = blobContainerClient.getBlobClient("application.yml");
		blobclient.uploadFromFile("src/main/resources/application.yml");
		//C:\Users\Nguyen\Documents\dshouse\ilearnspringboot\src\main\resources\application.yml
		//C:\Users\Nguyen\Documents\dshouse\ilearnspringboot\src\resources\application.yml
	}

}
