package com.bigdata.newmission;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobRequestConditions;
import com.azure.storage.blob.models.ParallelTransferOptions;
import com.bigdata.newmission.domain.http.HCPTokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class IlearnSpringbootTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private BlobContainerClient blobContainerClient;

	@Autowired
	private BlobServiceClient blobServiceClient;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testUpload(){
		var blobclient = blobContainerClient.getBlobClient("application_willbeexpired.yml");
		blobclient.uploadFromFile("src/main/resources/application_willbeexpired.yml");
		//C:\Users\Nguyen\Documents\dshouse\ilearnspringboot\src\main\resources\application.yml
		//C:\Users\Nguyen\Documents\dshouse\ilearnspringboot\src\resources\application.yml
	}

	@Test
	public void testBlob(){
//		var leaseId = "WYeaH1PZbgxo0SSR1gVFlMnHnYY2H4AT";
//		BlobRequestConditions requestConditions = new BlobRequestConditions()
//				.setLeaseId(leaseId);
//
//		var blobclient = blobContainerClient.getBlobClient("application_willbeexpired.yml");
//		blobclient.uploadFromFile("src/main/resources/application_willbeexpired.yml");
//
//		ParallelTransferOptions parallelTransferOptions = new ParallelTransferOptions().setBlockSizeLong(blockSize);
	}

	@Test
	public void testBlobMetadata(){
		var blobclient = blobContainerClient.getBlobClient("tmp/application_willbeexpired2.yml");
//		blobclient.uploadFromFile("src/main/resources/application.yml");
		Map<String, String> metadata = new HashMap<>();
		metadata.put("ttl","1");
		blobclient.uploadFromFile("src/main/resources/application_willbeexpired2.yml");

		blobclient.setMetadata(metadata);
	}

	@Test
	public void testBlobMetadata2() {
		var blobclient = blobContainerClient.getBlobClient("temp/application_willbeexpired2.yml");
//		blobclient.uploadFromFile("src/main/resources/application.yml");
		Map<String, String> metadata = new HashMap<>();
		metadata.put("Project", "Contoso");

		blobclient.setMetadata(metadata);
//		blobclient.setTags("ttl","oneDay");
	}

	@Test
	public void testToken(){
		var header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("client_id", "WYeaH1PZbgxo0SSR1gVFlMnHnYY2H4AT");
		body.add("client_secret", "5hcjmq3q8cATWbIzLrkRlxxg0WRahEZY7MCi-k05CKKJz8b-84vPZ-opoSojG85j");
		body.add("grant_type", "client_credentials");
		body.add("audience", "https://api.hashicorp.cloud");

		String url = "https://auth.idp.hashicorp.com/oauth2/token";

		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, header);
		var response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, HCPTokenResponse.class);

		var result = response.getBody();
		assert result != null;
	}

}
