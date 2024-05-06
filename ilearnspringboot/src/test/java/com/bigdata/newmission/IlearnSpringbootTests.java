package com.bigdata.newmission;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class IlearnSpringbootTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private BlobContainerClient blobContainerClient;

	@Test
	public void testUpload(){
		var blobclient = blobContainerClient.getBlobClient("application_willbeexpired.yml");
		blobclient.uploadFromFile("src/main/resources/application_willbeexpired.yml");
		//C:\Users\Nguyen\Documents\dshouse\ilearnspringboot\src\main\resources\application.yml
		//C:\Users\Nguyen\Documents\dshouse\ilearnspringboot\src\resources\application.yml
	}

	@Test
	public void testBlobMetadata(){
		var blobclient = blobContainerClient.getBlobClient("application_willbeexpired2.yml");
//		blobclient.uploadFromFile("src/main/resources/application.yml");
		Map<String, String> metadata = new HashMap<>();
		metadata.put("ttl","1");
		blobclient.uploadFromFile("src/main/resources/application_willbeexpired2.yml");

		blobclient.setMetadata(metadata);
	}

	@Test
	public void testBlobMetadata2() {
		var blobclient = blobContainerClient.getBlobClient("application_willbeexpired2.yml");
//		blobclient.uploadFromFile("src/main/resources/application.yml");
		Map<String, String> metadata = new HashMap<>();
		metadata.put("Project", "Contoso");

		blobclient.setMetadata(metadata);
//		blobclient.setTags("ttl","oneDay");
	}

}
