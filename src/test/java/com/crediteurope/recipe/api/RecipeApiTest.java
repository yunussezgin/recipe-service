package com.crediteurope.recipe.api;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.crediteurope.recipe.RecipeApplication;
import com.crediteurope.recipe.repository.RecipeRepository;
import com.crediteurope.recipe.util.TestConstant;
import com.crediteurope.recipe.util.TestUtils;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = RecipeApplication.class, loader = SpringBootContextLoader.class)
@ActiveProfiles("unittest")
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase(provider = ZONKY)
@DirtiesContext
public class RecipeApiTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private RecipeRepository recipeRepository;
	
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    	recipeRepository.deleteAll();
    }
    
	@Test
	void givenValidRecipeFullPayload_whenPostRecipe_thenCreateRecipeSuccessfully() throws Exception {
		// given
		RecipeCreate recipeCreate = TestUtils.entityFromJsonFile(RecipeCreate.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD_CREATE_RECIPE_SUCCESSFULLY);
		
		// when
		 mvc.perform(
				 post(TestConstant.POST_RECIPE_PATH)
                         .header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                         .header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER)
                         .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                         .content(TestUtils.asJsonString(recipeCreate)))
		 // then
		 .andExpect(
                 matchAll(
                         status().is(HttpStatus.CREATED.value())));
	}

}
