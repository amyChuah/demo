package com.demo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeansUtil {
	/**
	 * @param thisOrder
	 * @return
	 * @throws IOException
	 */
	public static byte[] serializeObject(Object object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		byte[] thisOrderAsBytes = baos.toByteArray();
		return thisOrderAsBytes;
	}

	public static Object deSerializeObject(byte[] input) throws IOException, ClassNotFoundException {
		Object output = null;

		ObjectInputStream objectIn = null;
		if (input != null)
			objectIn = new ObjectInputStream(new ByteArrayInputStream(input));
		output = objectIn.readObject();

		return output;
	}

	public static String bean2Json(Object log) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			result = mapper.writeValueAsString(log);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Object json2Bean(String json, Class<?> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		Object result = null;

		try {
			result = mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return result;
	}
}
