package tech.octopusdragon.creaturecreator;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import tech.octopusdragon.creaturecreator.enums.Shape;
import tech.octopusdragon.creaturecreator.model.Creature;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreatureCreatorApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void retrieveSingleTest() {
		ResponseEntity<String> response = restTemplate.getForEntity("/creature-creator/api/101", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(101);
		String name = documentContext.read("$.name");
		assertThat(name).isEqualTo("John");
		Shape bodyShape = Shape.valueOf(documentContext.read("$.bodyShape"));
		assertThat(bodyShape).isEqualTo(Shape.SQUARE);
		String bodyColor = documentContext.read("$.bodyColor");
		assertThat(bodyColor).isEqualToIgnoringCase("#0000FF");
		Shape eyeShape = Shape.valueOf(documentContext.read("$.eyeShape"));
		assertThat(eyeShape).isEqualTo(Shape.CIRCLE);
		String eyeColor = documentContext.read("$.eyeColor");
		assertThat(eyeColor).isEqualToIgnoringCase("#FF0000");
		Boolean antenna = documentContext.read("$.antenna");
		assertThat(antenna).isEqualTo(false);
		Boolean horns = documentContext.read("$.horns");
		assertThat(horns).isEqualTo(true);
		Boolean tail = documentContext.read("$.tail");
		assertThat(tail).isEqualTo(false);
		Boolean ears = documentContext.read("$.ears");
		assertThat(ears).isEqualTo(false);
		Boolean proboscis = documentContext.read("$.proboscis");
		assertThat(proboscis).isEqualTo(false);
	}

	@Test
	void retrieveMultipleTest() {
		ResponseEntity<String> response = restTemplate.getForEntity("/creature-creator/api", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int creatureCount = documentContext.read("$.length()");
		assertThat(creatureCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..id");
		assertThat(ids).containsExactlyInAnyOrder(101, 102, 103);
		JSONArray names = documentContext.read("$..name");
		assertThat(names).containsExactlyInAnyOrder("John", "Mary", "Tom");
		JSONArray bodyShapes = documentContext.read("$..bodyShape");
		assertThat(bodyShapes).containsExactlyInAnyOrder("SQUARE", "CIRCLE", "PENTAGON");
		JSONArray bodyColors = documentContext.read("$..bodyColor");
		assertThat(bodyColors).containsExactlyInAnyOrder("#0000FF", "#FF0000", "#FFFF00");
		JSONArray eyeShapes = documentContext.read("$..eyeShape");
		assertThat(eyeShapes).containsExactlyInAnyOrder("CIRCLE", "CIRCLE", "HEART");
		JSONArray eyeColors = documentContext.read("$..eyeColor");
		assertThat(eyeColors).containsExactlyInAnyOrder("#FF0000", "#FFFF00", "#FF0000");
		JSONArray antennas = documentContext.read("$..antenna");
		assertThat(antennas).containsExactlyInAnyOrder(false, true, false);
		JSONArray horns = documentContext.read("$..horns");
		assertThat(horns).containsExactlyInAnyOrder(true, false, false);
		JSONArray tails = documentContext.read("$..tail");
		assertThat(tails).containsExactlyInAnyOrder(false, true, false);
		JSONArray ears = documentContext.read("$..ears");
		assertThat(ears).containsExactlyInAnyOrder(false, true, true);
		JSONArray proboscis = documentContext.read("$..proboscis");
		assertThat(proboscis).containsExactlyInAnyOrder(false, false, true);
	}

	@Test
	@DirtiesContext
	void createTest() {
		Creature newCreature = new Creature(
				null,
				"Sarah",
				Shape.SQUARE,
				"#00FF00",
				Shape.STAR,
				"#FF00FF",
				true,
				false,
				false,
				false,
				true);
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/creature-creator/api", newCreature, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewCreature = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewCreature, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isNotNull();
		String name = documentContext.read("$.name");
		assertThat(name).isEqualTo("Sarah");
		Shape bodyShape = Shape.valueOf(documentContext.read("$.bodyShape"));
		assertThat(bodyShape).isEqualTo(Shape.SQUARE);
		String bodyColor = documentContext.read("$.bodyColor");
		assertThat(bodyColor).isEqualToIgnoringCase("#00FF00");
		Shape eyeShape = Shape.valueOf(documentContext.read("$.eyeShape"));
		assertThat(eyeShape).isEqualTo(Shape.STAR);
		String eyeColor = documentContext.read("$.eyeColor");
		assertThat(eyeColor).isEqualToIgnoringCase("#FF00FF");
		Boolean antenna = documentContext.read("$.antenna");
		assertThat(antenna).isEqualTo(true);
		Boolean horns = documentContext.read("$.horns");
		assertThat(horns).isEqualTo(false);
		Boolean tail = documentContext.read("$.tail");
		assertThat(tail).isEqualTo(false);
		Boolean ears = documentContext.read("$.ears");
		assertThat(ears).isEqualTo(false);
		Boolean proboscis = documentContext.read("$.proboscis");
		assertThat(proboscis).isEqualTo(true);
	}

	@Test
	@DirtiesContext
	void updateTest() {
		Creature creatureUpdate = new Creature(
				null,
				"John",
				Shape.CIRCLE,
				"#0000FF",
				Shape.SQUARE,
				"#FF0000",
				false,
				true,
				false,
				false,
				false);
		HttpEntity<Creature> request = new HttpEntity<>(creatureUpdate);
		ResponseEntity<Void> UpdateResponse = restTemplate.exchange("/creature-creator/api/101", HttpMethod.PUT, request, Void.class);
		assertThat(UpdateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ResponseEntity<String> getResponse = restTemplate.getForEntity("/creature-creator/api/101", String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(101);
		String name = documentContext.read("$.name");
		assertThat(name).isEqualTo("John");
		Shape bodyShape = Shape.valueOf(documentContext.read("$.bodyShape"));
		assertThat(bodyShape).isEqualTo(Shape.CIRCLE);
		String bodyColor = documentContext.read("$.bodyColor");
		assertThat(bodyColor).isEqualToIgnoringCase("#0000FF");
		Shape eyeShape = Shape.valueOf(documentContext.read("$.eyeShape"));
		assertThat(eyeShape).isEqualTo(Shape.SQUARE);
		String eyeColor = documentContext.read("$.eyeColor");
		assertThat(eyeColor).isEqualToIgnoringCase("#FF0000");
		Boolean antenna = documentContext.read("$.antenna");
		assertThat(antenna).isEqualTo(false);
		Boolean horns = documentContext.read("$.horns");
		assertThat(horns).isEqualTo(true);
		Boolean tail = documentContext.read("$.tail");
		assertThat(tail).isEqualTo(false);
		Boolean ears = documentContext.read("$.ears");
		assertThat(ears).isEqualTo(false);
		Boolean proboscis = documentContext.read("$.proboscis");
		assertThat(proboscis).isEqualTo(false);
	}

	@Test
	@DirtiesContext
	void deleteTest() {
		ResponseEntity<Void> deleteResponse = restTemplate.exchange("/creature-creator/api/101", HttpMethod.DELETE, null, Void.class);
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ResponseEntity<String> getResponse = restTemplate.getForEntity("/creature-creator/api/101", String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
