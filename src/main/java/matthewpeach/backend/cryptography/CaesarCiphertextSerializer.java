package matthewpeach.backend.cryptography;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@JsonComponent
public class CaesarCiphertextSerializer {
    public static class Serializer extends JsonSerializer<CaesarCiphertext> {
        @Override
        public void serialize(CaesarCiphertext caesarCiphertext, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("text", caesarCiphertext.getText());
            jsonGenerator.writeObjectField("letterFrequency", caesarCiphertext.getLetterFrequencies());
            jsonGenerator.writeEndObject();
        }
    }

    public static class Deserializer extends JsonDeserializer<CaesarCiphertext> {

        @Override
        public CaesarCiphertext deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
            TreeNode rootNode = jsonParser.getCodec().readTree(jsonParser);

            TextNode textNode = (TextNode) rootNode.get("text");
            String text = textNode.asText();

            Map<String, Integer> map = new HashMap<>();
            JsonNode jsonNode = (JsonNode) rootNode.path("letterFrequency");
            Iterator<JsonNode> elements = jsonNode.elements();


            return new CaesarCiphertext();
        }
    }
}
