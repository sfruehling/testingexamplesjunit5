package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.core.ResolvableType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.FileCopyUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.deepoove.swagger.diff.SwaggerDiff;
import com.deepoove.swagger.diff.output.MarkdownRender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class SwaggerTest {

	@Autowired
	private MockMvc mockMvc;

	private String readSwaggerDefinition() throws Exception {
		MvcResult result = mockMvc.perform(get("/v2/api-docs")) //
				.andExpect(status().is2xxSuccessful()) //
				.andReturn();

		return result.getResponse().getContentAsString();
	}

	@Test
	void printSwaggerDefinition() throws Exception {
		String actualSwaggerDefinition = readSwaggerDefinition();

		System.out.println(readTree(actualSwaggerDefinition).toPrettyString());
	}

	@Test
	void swaggerPaths() throws Exception {
		String actualSwaggerDefinition = readSwaggerDefinition();

		JSONObject paths = (JSONObject) JSONPath.read(actualSwaggerDefinition, "$.paths");

		assertThat(paths.keySet()).containsExactly("/bookable-starships");
	}

	@Test
	void detectApiChanges() throws Exception {
		String actualSwaggerDefinition = readSwaggerDefinition();

		String referenceSwaggerDefinition = toString(getClass().getResourceAsStream("/api/swagger-definition.json"));
		SwaggerDiff diff = computeDiff(actualSwaggerDefinition, referenceSwaggerDefinition);

		String diffAsString = asMarkdown(diff);

		assertThat(diff.getNewEndpoints()).as(diffAsString).isEmpty();
		assertThat(diff.getChangedEndpoints()).as(diffAsString).isEmpty();
		assertThat(diff.getMissingEndpoints()).as(diffAsString).isEmpty();

		JsonContent<String> jsonContent = toJsonContent(actualSwaggerDefinition);
		assertThat(jsonContent).as(diffAsString).isStrictlyEqualToJson(referenceSwaggerDefinition);
	}

	private SwaggerDiff computeDiff(String actualSwaggerDefinition, String referenceSwaggerDefinition)
			throws JsonProcessingException {
		JsonNode actual = readTree(actualSwaggerDefinition);
		JsonNode reference = readTree(referenceSwaggerDefinition);
		return SwaggerDiff.compareV2(reference, actual);
	}

	private String asMarkdown(SwaggerDiff diff) {
		return new MarkdownRender().render(diff);
	}

	private JsonNode readTree(String actualSwaggerDefinition) throws JsonProcessingException {
		return new ObjectMapper().readTree(actualSwaggerDefinition);
	}

	private JsonContent<String> toJsonContent(String json) {
		return new JsonContent<>(getClass(), ResolvableType.forClass(String.class), json);
	}

	private String toString(InputStream inputStream) throws IOException {
		return FileCopyUtils.copyToString(new InputStreamReader(inputStream));
	}

}
