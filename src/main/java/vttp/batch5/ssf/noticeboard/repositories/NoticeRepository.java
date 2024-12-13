package vttp.batch5.ssf.noticeboard.repositories;

import java.io.Serializable;
import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp.batch5.ssf.noticeboard.models.SuccessPayload;

@Repository
public class NoticeRepository implements Serializable {

	@Autowired
	@Qualifier("notice")
	RedisTemplate<String, Object> redisTemplate;

	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hset notices hashKey payload
	 *
	 *
	 */
	// send success JSON payload to Redis as JSON String 
	public void insertNotices(ResponseEntity<String> response) {

		// get body of success payload as string
		String payload = response.getBody(); 

		// convert payload into SuccessPayload POJO to get ID 
		// for hashKey
		SuccessPayload sp = jsonStringToPOJO(payload);
		String hashKey = sp.getId();

		// save into Redis 
		redisTemplate.opsForHash().put("notices", hashKey, payload);

		System.out.println("successfully put into redis");

	}

	// task 4 
	// helper method 
	// convert json string -> SuccessPayload POJO
	public SuccessPayload jsonStringToPOJO(String jString) {

		StringReader sr = new StringReader(jString);
		JsonReader jr = Json.createReader(sr);
		JsonObject jo = jr.readObject();

		// map JSON fields to SuccessPayload POJO
		SuccessPayload successPayload = new SuccessPayload(
			jo.getString("id"), 
			jo.getJsonNumber("timestamp").longValue());

		return successPayload;

	}

	// task 4 
	// helper method 
	// POJO to String 
	public Object POJOToJsonString(SuccessPayload sp) {

		JsonObjectBuilder builder = Json.createObjectBuilder()
										.add("id", sp.getId())
										.add("timestamp", sp.getTimestamp());

		JsonObject jObject = builder.build(); 
		// return jObject;
		return jObject.toString(); 

	}



}
