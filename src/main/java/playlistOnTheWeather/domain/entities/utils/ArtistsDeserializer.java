package playlistOnTheWeather.domain.entities.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ArtistsDeserializer extends JsonDeserializer<List<?>> {

    @Override
    public List<?> deserialize(JsonParser jsonParser, 
            DeserializationContext deserializationContext) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);
        List<?> result = mapper.convertValue(node.findValues("artists"), new TypeReference<List<?>>() {});
        return result;
    }

}
