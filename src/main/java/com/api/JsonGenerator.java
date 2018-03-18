package com.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonGenerator {

	/**
	 * Creer un string json
     *
	 * @param  key   String
	 * @param  value String
	 * @return String
	 */
	public static String buildJsonString(String key, String value) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put(key, value);
		
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return "Une erreur est survenue lors de la construction de json!";
	}
}
