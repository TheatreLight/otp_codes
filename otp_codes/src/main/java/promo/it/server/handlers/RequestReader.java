package promo.it.server.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RequestReader {
    private ObjectMapper mapper;
    private JsonNode node;

    public RequestReader(InputStream input) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int ch; (ch = input.read()) != -1; ) {
            sb.append((char) ch);
        }

        mapper = new ObjectMapper();
        node = mapper.readTree(sb.toString());
    }


    public String getValue(String key) {
        if (node == null) {
            return "";
        }

        return node.get(key).asText();
    }

    public List<String> getArray(String key) {
        List<String> result = new ArrayList<>();
        if (node == null || !node.has(key)) {
            return result;
        }

        JsonNode arrayNode = node.get(key);
        if (arrayNode.isArray()) {
            for (JsonNode item : arrayNode) {
                result.add(item.asText());
            }
        }
        return result;
    }
}
