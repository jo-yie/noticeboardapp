package vttp.batch5.ssf.noticeboard.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	NoticeRepository noticeRepository; 
	
	@Value("${my.server.url}")
	private String url;

	// Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	public String postToNoticeServer(Notice notice) {

		RestTemplate restTemplate = new RestTemplate(); 

		// convert Notice POJO to string
		String jString = POJOToJsonString(notice);

		// build RequestEntity payload 
		RequestEntity<String> request = RequestEntity
											.post(url)
											.contentType(MediaType.APPLICATION_JSON) 
            								.accept(MediaType.APPLICATION_JSON)
											.body(jString); 

		// get ResponseEntity payload from API
		ResponseEntity<String> response;

		try {

			response = restTemplate.exchange(request, String.class);

		} catch (Exception e) {

			// send error message to controller
			return "Error: " + e.getMessage();

		}

		// insert success JSON payload into Redis database
		String postingId = noticeRepository.insertNotices(response);

		// send posting id back to controller
		return postingId;
	
	}
												
	// task 3 
	// helper method 
	// convert Notice POJO --> JSON String 
	public String POJOToJsonString(Notice notice) {

		List<String> categoryList = notice.getCategories();

		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		categoryList.stream().forEach(category -> arrayBuilder.add(category));

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
										.add("title", notice.getTitle())
										.add("poster", notice.getPoster())
										.add("postDate", dateToMillis(notice.getPostDate()))
										.add("categories", arrayBuilder)
										.add("text", notice.getText());

		JsonObject noticeObject = objectBuilder.build(); 
		String noticeString = noticeObject.toString();

		return noticeString;

	}

	// task 3 
	// helper method 
	// convert Date to long date in milliseconds 
	public long dateToMillis(Date date) { 

		return date.getTime();

	}

	// task 6 
	// helper method 
	// get random key from repo layer
	public Boolean getRandomKeyFromRepo() { 

		String randKey = noticeRepository.getRandomKeyFromRedis();

		if (randKey == null || randKey.isEmpty()) {

			return false;

		} else {

			return true; 

		}

	}

}
