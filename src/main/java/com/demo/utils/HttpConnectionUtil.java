package com.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class HttpConnectionUtil {
	private static boolean simulateMode = false;
	private static Set<String> simulate = Stream.of("eth_pendingTransactions")
		.collect(Collectors.toCollection(HashSet::new));

	public HttpURLConnection createGetConnection(String url) throws IOException {
		return createConnection(url, "GET");
	}

	public HttpURLConnection createPostConnection(String url) throws IOException {
		return createConnection(url, "POST");
	}

	private static HttpURLConnection createConnection(String url, String method) throws IOException {
		HttpURLConnection con = null;
		try {
			URL thisUrl = new URL(url);
			con = (HttpURLConnection) thisUrl.openConnection();
			con.setRequestMethod(method);
			con.setRequestProperty("Accept", "*/*");
			con.setRequestProperty("Content-Type", "application/json;");
			
			if (method.equalsIgnoreCase("POST"))
				con.setDoOutput(true);
			
		} catch (IOException i) {
			throw new IOException(i);
		}

		return con;
	}

	public static <T> T sendJsonPostRequest(String url, String methodName, Object request, Class<T> clazz)
		throws IOException {
		return sendJsonRequest(url, methodName, request, "POST", clazz);
	}

	public static <T> T sendJsonGetRequest(String url, String methodName, Object request, Class<T> clazz)
		throws IOException {
		return sendJsonRequest(url, methodName, request, "GET", clazz);
	}

	@SuppressWarnings("unchecked")
	private static <T> T sendJsonRequest(String url, String methodName, Object request, String requestMethod,
		Class<T> clazz) throws IOException {
		if (simulateMode && simulate.contains(methodName))
			return simulation(methodName, clazz);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(request);
		} catch (Exception e) {
			throw new IOException(e);
		}

		StringBuilder response = new StringBuilder();
		HttpURLConnection con = createConnection(url, requestMethod);

		try {
			
			if(requestMethod.equalsIgnoreCase("POST"))
			{
				try (OutputStream os = con.getOutputStream()) {
					byte[] input = jsonString.getBytes("utf-8");
					os.write(input, 0, input.length);
				} catch (IOException e) {
					throw new IOException(e);
				}				
			}
			
			int code = con.getResponseCode();

			if (code != 200 && code != 201) {
				throw new IOException();
			}
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
			} catch (IOException e) {
				throw new IOException(e);
			}
			
			ObjectMapper objMapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			
			Object result = null;

			try {
				JsonNode jsonNode = objMapper.readTree(response.toString());
				if (jsonNode instanceof ArrayNode)
				{
					clazz.getAnnotatedInterfaces();
					
					Collection<T> readValues = new ObjectMapper().readValue(
						response.toString(), new TypeReference<Collection<T>>() { }
					);
					result = (Object) readValues;
				}
				else 
				{
					result = objMapper.readValue(response.toString(), clazz);
				}
				
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 

			return (T)result;
		} finally {
			if (con != null)
				con.disconnect();
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T simulation(String methodName, Class<T> clazz) {
		String response = null;

		if (methodName.equalsIgnoreCase("eth_pendingTransactions"))
			response = "{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"result\":[{\"blockHash\":null,\"blockNumber\":null,\"from\":\"0xe68d1493354693e8e90db874f4b3ac08c8f4a9da\",\"gas\":\"0x186a0\",\"gasPrice\":\"0x161e70f600\",\"hash\":\"0x922b18405fed470b2e211228e763ecaa1f2dc31e316f0ec78529e5122cc95fc1\",\"input\":\"0xa9059cbb000000000000000000000000784d9c3839237f1cf40b3dc64569c5251fe78c800000000000000000000000000000000000000000000000000000000000989680\",\"nonce\":\"0x2e\",\"to\":\"0xdac17f958d2ee523a2206206994597c13d831ec7\",\"transactionIndex\":null,\"value\":\"0x0\",\"v\":\"0x25\",\"r\":\"0x9e37823ae44e5d4ae3840164a900a894fcfb617bf1d31e552551dddb5ef91c1f\",\"s\":\"0x6a6c3f72805f270c6ea9f9ff5f84c5c3f690068d0ddbca142fe5883e7d57fd21\"},{\"blockHash\":null,\"blockNumber\":null,\"from\":\"0xe68d1493354693e8e90db874f4b3ac08c8f4a9db\",\"gas\":\"0x186a0\",\"gasPrice\":\"0x161e70f600\",\"hash\":\"0xcedaea3a99c8ae87aec9ed1de4f0dc83fd9caa6c1d1b6d57cdb8813878700309\",\"input\":\"0xa9059cbb000000000000000000000000784d9c3839237f1cf40b3dc64569c5251fe78c800000000000000000000000000000000000000000000000000000000000989680\",\"nonce\":\"0x2f\",\"to\":\"0xdac17f958d2ee523a2206206994597c13d831ec7\",\"transactionIndex\":null,\"value\":\"0x0\",\"v\":\"0x25\",\"r\":\"0xe60781a4599964637ae81fb235eb00afdc59541b2f08fe5e1eb41192e7c190eb\",\"s\":\"0x4e407bd9981afad76403beeb47b510b43daf44e04a95eb194f5fbe1f535522e5\"},{\"blockHash\":null,\"blockNumber\":null,\"from\":\"0xe68d1493354693e8e90db874f4b3ac08c8f4a9db\",\"gas\":\"0x186a0\",\"gasPrice\":\"0x161e70f600\",\"hash\":\"0x792e25b9a2943d56f0f16b046ef539d247ee8f117cf8b9be55088bb8ed1f4461\",\"input\":\"0xa9059cbb000000000000000000000000784d9c3839237f1cf40b3dc64569c5251fe78c800000000000000000000000000000000000000000000000000000000000989680\",\"nonce\":\"0x30\",\"to\":\"0xdac17f958d2ee523a2206206994597c13d831ec7\",\"transactionIndex\":null,\"value\":\"0x0\",\"v\":\"0x25\",\"r\":\"0x43da483bcb8ffda8517ed5b115b7b719eca40e52ab78320fe6625736dd6b51a1\",\"s\":\"0x1666b6444006e0fa844f83d289ebe1db906dd8e3022e27ea602ada449c905f1a\"},{\"blockHash\":null,\"blockNumber\":null,\"from\":\"0xe68d1493354693e8e90db874f4b3ac08c8f4a9db\",\"gas\":\"0x186a0\",\"gasPrice\":\"0x1bf08eb000\",\"hash\":\"0x6ad73eca0a42c0958c8fbe15fb2a87af915b2af9d9d1cbbd7881058459cfc743\",\"input\":\"0xa9059cbb000000000000000000000000547005a954f7750d7ff309c24c6a248c65a04de400000000000000000000000000000000000000000000000000000000004a0538\",\"nonce\":\"0x31\",\"to\":\"0xdac17f958d2ee523a2206206994597c13d831ec7\",\"transactionIndex\":null,\"value\":\"0x0\",\"v\":\"0x25\",\"r\":\"0x99ddb689fbf8cc9301d33f659cc1682ea9e3d917fd9639679c1f400cfa63eadb\",\"s\":\"0x5d75ddadff24b7080df4073b63ee126055230468cf8f10a14f22d730cba69f71\"}]}";

		T convertedObject = (T) BeansUtil.json2Bean(response, clazz);

		return convertedObject;
	}

}
