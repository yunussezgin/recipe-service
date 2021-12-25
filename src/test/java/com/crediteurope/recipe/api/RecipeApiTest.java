package com.crediteurope.recipe.api;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import com.crediteurope.recipe.databuilder.SubResourceData;
import com.crediteurope.recipe.entity.Recipe;
import com.crediteurope.recipe.repository.CategoryRepository;
import com.crediteurope.recipe.repository.IngredientRepository;
import com.crediteurope.recipe.repository.RecipeRepository;
import com.crediteurope.recipe.repository.UserRepository;
import com.crediteurope.recipe.util.TestConstant;
import com.crediteurope.recipe.util.TestUtils;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SubResourceData subResourceData;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    	recipeRepository.deleteAll();
    	categoryRepository.deleteAll();
    	userRepository.deleteAll();
    	ingredientRepository.deleteAll();
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
                         .content(TestUtils.asJsonString(recipeCreate)))
		 // then
		 .andExpect(
                 matchAll(
                         status().is(HttpStatus.CREATED.value()),
                         jsonPath("$", notNullValue()),
                         status().is2xxSuccessful(),
                         jsonPath("$.id").isNotEmpty(),
                         jsonPath("$.createdDate").isNotEmpty(),
                         jsonPath("$.updatedDate").isNotEmpty(),
                         jsonPath("$.vegetarianFlag", is(recipeCreate.getVegetarianFlag())),
                         jsonPath("$.recipeInstruction").exists(),
                         jsonPath("$.recipeIngredient").exists(),
                         jsonPath("$.category").exists(),
                         jsonPath("$.user").exists(),
                         jsonPath("$.name", is(recipeCreate.getName())),
                         jsonPath("$.description", is(recipeCreate.getDescription())),
                         jsonPath("$.cookTime", is(recipeCreate.getCookTime())),
                         jsonPath("$.prepTime", is(recipeCreate.getPrepTime())),
                         jsonPath("$.serving", is(recipeCreate.getServing())),
                         jsonPath("$.image").exists()
                ));
	}
	
	@Test
	void givenValidRecipeUrl_whenGetRecipe_thenGetRecipeSuccessfully() throws Exception {
		// given
		Recipe recipe = TestUtils.entityFromJsonFile(Recipe.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD_CREATE_RECIPE_SUCCESSFULLY);
		subResourceData.prepareSubResources(recipe);
		recipe = recipeRepository.save(recipe);
		
		// when
		mvc.perform(
				get(TestConstant.GET_RECIPE_PATH)
                         .header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                         .header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER))
		 // then
		 .andExpect(
                 matchAll(
                         status().is(HttpStatus.OK.value()),
                         jsonPath("$", notNullValue()),
                         status().is2xxSuccessful(),
                         jsonPath("$..id").isNotEmpty(),
                         jsonPath("$..createdDate").isNotEmpty(),
                         jsonPath("$..updatedDate").isNotEmpty(),
                         jsonPath("$..vegetarianFlag", hasItem(recipe.getVegetarianFlag())),
                         jsonPath("$..recipeInstruction").exists(),
                         jsonPath("$..recipeIngredient").exists(),
                         jsonPath("$..category").exists(),
                         jsonPath("$..user").exists(),
                         jsonPath("$..name", hasItem(recipe.getName())),
                         jsonPath("$..description", hasItem(recipe.getDescription())),
                         jsonPath("$..cookTime", hasItem(recipe.getCookTime())),
                         jsonPath("$..prepTime", hasItem(recipe.getPrepTime())),
                         jsonPath("$..serving",hasItem(recipe.getServing()))
                ));
	}

}
