package matthewpeach.backend;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import matthewpeach.backend.data_objects.CaesarCiphertext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
@SpringJUnitConfig
public class SerializationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void caesarCiphertext() throws JsonProcessingException {
        String plaintext = "hello world";

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 20);
        map.put("b", 3);
        map.put("c", 50);

        CaesarCiphertext text = new CaesarCiphertext(plaintext, map);
        String json = objectMapper.writeValueAsString(text);
        assertEquals("{\"text\":\"hello world\",\"letterFrequency\":{\"a\":20,\"b\":3,\"c\":50}}", json);
    }
}
