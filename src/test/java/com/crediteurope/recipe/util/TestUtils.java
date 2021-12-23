package com.crediteurope.recipe.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtils {

	public static <T> T entityFromJsonFile(Class<T> type, String fileName) throws Exception {
		Path resourceDirectory = Paths.get("src", "test", "resources", "data", fileName);
		String absolutePath = resourceDirectory.toFile().getAbsolutePath();
		System.out.println("Reading file path: " + absolutePath);
		Reader reader = new FileReader(absolutePath);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
		JavaTimeModule module = new JavaTimeModule();
		module.addDeserializer(LocalDateTime.class, new CustomOffsetDeserializer());
		mapper.registerModule(module);
		T entity = mapper.readValue(reader, type);
		reader.close();
		return entity;
	}

	public static <T> List<T> entityListFromJsonFile(Class<T> type, String fileName) throws Exception {
		Path resourceDirectory = Paths.get("src", "test", "resources", "data", fileName);
		String absolutePath = resourceDirectory.toFile().getAbsolutePath();
		System.out.println("Reading file path: " + absolutePath);
		Reader reader = new FileReader(absolutePath);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
		JavaTimeModule module = new JavaTimeModule();
		module.addDeserializer(LocalDateTime.class, new CustomOffsetDeserializer());
		mapper.registerModule(module);
		CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, type);
		List<T> entity = mapper.readValue(reader, javaType);
		reader.close();
		return entity;
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("resource")
	public static String loadJsonData(String filePath) throws IOException, URISyntaxException {
		try {
			System.out.println("Reading file path: " + filePath);
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String readLine;
			StringBuilder stringBuilder = new StringBuilder();
			while ((readLine = reader.readLine()) != null) {
				stringBuilder.append(readLine);
			}
			return stringBuilder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
