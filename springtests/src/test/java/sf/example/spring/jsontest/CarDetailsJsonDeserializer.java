package sf.example.spring.jsontest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class CarDetailsJsonDeserializer extends JsonDeserializer<CarDetails> {
    @Override
    public CarDetails deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String typeFieldName = jsonParser.nextFieldName();
        if(typeFieldName.equals("type")) {
            String text = jsonParser.nextTextValue();
            String[] split = text.split(" \\| ");
            String type = split[0];
            String manufacturer = split[1];
            String color = split[2];
            return new CarDetails(type, manufacturer, color);
        }
        throw new JsonParseException(jsonParser, "Could not parse Json!");
    }
}
