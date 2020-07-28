package sf.example.spring.jsontest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class CarDetailsJsonTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private JacksonTester<CarDetails> json;

    @Test
    void testSerialize() throws Exception {
        CarDetails carDetails = new CarDetails("Audi", "A3", "gray");
        JsonContent<CarDetails> result = this.json.write(carDetails);
        System.out.println(result);
        assertThat(result).extractingJsonPathStringValue("$.type").contains("Audi", "A3", "gray");
    }

    @Test
    void testDeserialize() throws Exception {
        String jsonContent = "{\"type\":\"Audi | A3 | blue\"}";

        CarDetails result = this.json.parse(jsonContent).getObject();

        assertThat(result.getManufacturer()).isEqualTo("Audi");
        assertThat(result.getType()).isEqualTo("A3");
        assertThat(result.getColor()).isEqualTo("blue");
    }
}
