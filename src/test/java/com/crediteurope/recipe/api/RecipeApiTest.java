package com.crediteurope.recipe.api;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
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
import com.crediteurope.recipe.entity.Category;
import com.crediteurope.recipe.entity.Recipe;
import com.crediteurope.recipe.entity.User;
import com.crediteurope.recipe.repository.CategoryRepository;
import com.crediteurope.recipe.repository.IngredientRepository;
import com.crediteurope.recipe.repository.RecipeRepository;
import com.crediteurope.recipe.repository.UserRepository;
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
		RecipeCreate recipeCreate = TestUtils.entityFromJsonFile(RecipeCreate.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD1_CREATE_RECIPE_SUCCESSFULLY);
		
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
		                         jsonPath("$.isVegetarian", is(recipeCreate.getIsVegetarian())),
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
		Recipe recipe = TestUtils.entityFromJsonFile(Recipe.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD2_CREATE_RECIPE_SUCCESSFULLY);
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
		                         jsonPath("$..isVegetarian", hasItem(recipe.getIsVegetarian())),
		                         jsonPath("$..recipeInstruction").exists(),
		                         jsonPath("$..recipeIngredient").exists(),
		                         jsonPath("$..category").exists(),
		                         jsonPath("$..user").exists(),
		                         jsonPath("$..name", hasItem(recipe.getName())),
		                         jsonPath("$..description", hasItem(recipe.getDescription())),
		                         jsonPath("$..prepTime", hasItem(recipe.getPrepTime())),
		                         jsonPath("$..serving",hasItem(recipe.getServing()))
		                ));
	}
	
	@Test
	void givenValidRecipeId_whenRetrieveRecipe_thenRetrieveRecipeSuccessfully() throws Exception {
		// given
		Recipe recipe = TestUtils.entityFromJsonFile(Recipe.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD3_CREATE_RECIPE_SUCCESSFULLY);
		subResourceData.prepareSubResources(recipe);
		recipe = recipeRepository.save(recipe);
		
		// when
		mvc.perform(
				 get(TestConstant.GET_RECIPE_PATH + "/" + recipe.getId())
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
		                         jsonPath("$..isVegetarian", hasItem(recipe.getIsVegetarian())),
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
	
	@Test
	void givenValidRecipeId_whenDeleteRecipe_thenDeleteRecipeSuccessfully() throws Exception {
		// given
		Recipe recipe = TestUtils.entityFromJsonFile(Recipe.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD1_CREATE_RECIPE_SUCCESSFULLY);
		subResourceData.prepareSubResources(recipe);
		recipe = recipeRepository.save(recipe);
		
		// when
		mvc.perform(
				delete(TestConstant.DELETE_RECIPE_PATH + "/" + recipe.getId())
                         .header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                         .header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER))
				 // then
				 .andExpect(
		                 matchAll(
		                		 status().is(HttpStatus.NO_CONTENT.value())
		                ));
	}
	
    @Test
    void givenValidRecipeJsonPatchPayload_whenPatchRecipe_thenUpdateRecipeSuccessfully() throws Exception {
        //given
		Recipe recipe = TestUtils.entityFromJsonFile(Recipe.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD3_CREATE_RECIPE_SUCCESSFULLY);
		subResourceData.prepareSubResources(recipe);
		recipe = recipeRepository.save(recipe);
		
		RecipeUpdate recipeUpdate = TestUtils.entityFromJsonFile(RecipeUpdate.class, TestConstant.JSON_RECIPE_PARTIAL_PAYLOAD1_PATCH_RECIPE_SUCCESSFULLY);
        
        // when
        mvc.perform(
                patch(TestConstant.PATCH_RECIPE_PATH + "/" + recipe.getId())
                        		.header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                        		.header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER)
                                .content(TestUtils.asJsonString(recipeUpdate)))
                // then
                .andExpect(
                        matchAll(
                                status().is(HttpStatus.OK.value()),
                                jsonPath("$", notNullValue()),
                                status().is2xxSuccessful(),
                                jsonPath("$.id").isNotEmpty(),
                                jsonPath("$.name", is(recipeUpdate.getName())),
                                jsonPath("$.description", is(recipeUpdate.getDescription())),
                                jsonPath("$.cookTime", is(recipeUpdate.getCookTime())),
                                jsonPath("$.prepTime", is(recipeUpdate.getPrepTime())),
                                jsonPath("$.serving", is(recipeUpdate.getServing())),
                                jsonPath("$.isVegetarian", is(recipeUpdate.getIsVegetarian()))
                        ));
    	
    }
    
    @Test
    void givenValidCreatePayloadWithoutRecipeInstruction_whenPostRecipe_thenRejectCreate() throws Exception {
		// given
		RecipeCreate recipeCreate = TestUtils.entityFromJsonFile(RecipeCreate.class, TestConstant.JSON_RECIPE_PAYLOAD_WITHOUT_RECIPE_INSTRUCTION);
		
		// when
		mvc.perform(
				 post(TestConstant.POST_RECIPE_PATH)
                         .header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                         .header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER)
                         .content(TestUtils.asJsonString(recipeCreate)))
				 // then
				 .andExpect(
		                 matchAll(
		                         status().is(HttpStatus.BAD_REQUEST.value()),
		                         jsonPath("$", notNullValue()),
		                         status().is4xxClientError(),
		                         jsonPath("$.code", is("ERR400")),
		                         jsonPath("$.message").isNotEmpty()
		                ));
    }
    
    @Test
    void givenValidCreatePayloadWithoutRecipeIngredient_whenPostRecipe_thenRejectCreate() throws Exception {
		// given
		RecipeCreate recipeCreate = TestUtils.entityFromJsonFile(RecipeCreate.class, TestConstant.JSON_RECIPE_PAYLOAD_WITHOUT_RECIPE_INGREDIENT);
		
		// when
		mvc.perform(
				 post(TestConstant.POST_RECIPE_PATH)
                         .header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                         .header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER)
                         .content(TestUtils.asJsonString(recipeCreate)))
				 // then
				 .andExpect(
		                 matchAll(
		                         status().is(HttpStatus.BAD_REQUEST.value()),
		                         jsonPath("$", notNullValue()),
		                         status().is4xxClientError(),
		                         jsonPath("$.code", is("ERR400")),
		                         jsonPath("$.message").isNotEmpty()
		                ));    	
    }
    
    @Test
    void givenValidCreatePayloadWithEmptyRecipeInstruction_whenPostRecipe_thenRejectCreateRecipe() throws Exception {
		// given
		RecipeCreate recipeCreate = TestUtils.entityFromJsonFile(RecipeCreate.class, TestConstant.JSON_RECIPE_PAYLOAD_WITH_EMPTY_RECIPE_INSTRUCTION_LIST);
		
		// when
		mvc.perform(
				 post(TestConstant.POST_RECIPE_PATH)
                         .header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                         .header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER)
                         .content(TestUtils.asJsonString(recipeCreate)))
				 // then
				 .andExpect(
		                 matchAll(
		                         status().is(HttpStatus.BAD_REQUEST.value()),
		                         jsonPath("$", notNullValue()),
		                         status().is4xxClientError(),
		                         jsonPath("$.code", is("ERR400")),
		                         jsonPath("$.message").isNotEmpty()
		                ));       	
    }
    
    @Test
    void givenValidCreatePayloadWithEmptyRecipeIngredient_whenPostRecipe_thenRejectCreateRecipe() throws Exception {
		// given
		RecipeCreate recipeCreate = TestUtils.entityFromJsonFile(RecipeCreate.class, TestConstant.JSON_RECIPE_PAYLOAD_WITH_EMPTY_RECIPE_INGREDIENT_LIST);
		
		// when
		mvc.perform(
				 post(TestConstant.POST_RECIPE_PATH)
                         .header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                         .header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER)
                         .content(TestUtils.asJsonString(recipeCreate)))
				 // then
				 .andExpect(
		                 matchAll(
		                         status().is(HttpStatus.BAD_REQUEST.value()),
		                         jsonPath("$", notNullValue()),
		                         status().is4xxClientError(),
		                         jsonPath("$.code", is("ERR400")),
		                         jsonPath("$.message").isNotEmpty()
		                ));       	
    }

    
    @Test
    void givenInvalidMergePayloadWithEmptyRecipeInstruction_whenPatchRecipe_thenRejectUpdate() throws Exception {
		// given
		Recipe recipe = TestUtils.entityFromJsonFile(Recipe.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD2_CREATE_RECIPE_SUCCESSFULLY);
		subResourceData.prepareSubResources(recipe);
		recipe = recipeRepository.save(recipe);
		
		RecipeUpdate recipeUpdate = TestUtils.entityFromJsonFile(RecipeUpdate.class, TestConstant.JSON_RECIPE_PARTIAL_PAYLOAD_WITH_EMPTY_RECIPE_INSTRUCTION_LIST);
		
		// when
		mvc.perform(
                 patch(TestConstant.PATCH_RECIPE_PATH + "/" + recipe.getId())
		        		.header("Accept", TestConstant.JSON_ACCEPT_HEADER)
		        		.header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER)
		                .content(TestUtils.asJsonString(recipeUpdate)))
				 // then
				 .andExpect(
		                 matchAll(
		                         status().is(HttpStatus.BAD_REQUEST.value()),
		                         jsonPath("$", notNullValue()),
		                         status().is4xxClientError(),
		                         jsonPath("$.code", is("ERR400")),
		                         jsonPath("$.message").isNotEmpty()
		                ));     
    }
    
    @Test
    void givenInvalidMergePayloadWithEmptyRecipeIngredient_whenPatchRecipe_thenRejectUpdate() throws Exception {
		// given
		Recipe recipe = TestUtils.entityFromJsonFile(Recipe.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD2_CREATE_RECIPE_SUCCESSFULLY);
		subResourceData.prepareSubResources(recipe);
		recipe = recipeRepository.save(recipe);
		
		RecipeUpdate recipeUpdate = TestUtils.entityFromJsonFile(RecipeUpdate.class, TestConstant.JSON_RECIPE_PARTIAL_PAYLOAD_WITH_EMPTY_RECIPE_INGREDIENT_LIST);
		
		// when
		mvc.perform(
                patch(TestConstant.PATCH_RECIPE_PATH + "/" + recipe.getId())
		        		.header("Accept", TestConstant.JSON_ACCEPT_HEADER)
		        		.header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER)
		                .content(TestUtils.asJsonString(recipeUpdate)))
				 // then
				 .andExpect(
		                 matchAll(
		                         status().is(HttpStatus.BAD_REQUEST.value()),
		                         jsonPath("$", notNullValue()),
		                         status().is4xxClientError(),
		                         jsonPath("$.code", is("ERR400")),
		                         jsonPath("$.message").isNotEmpty()
		                ));        	
    }
    
    
    @Test
    void givenValidGetRequest_whenGetRecipeListWithLimit_thenGetRecipeListSuccessfully() throws Exception {
		// given
		Recipe recipe = TestUtils.entityFromJsonFile(Recipe.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD2_CREATE_RECIPE_SUCCESSFULLY);
		subResourceData.prepareSubResources(recipe);
		List<Recipe> recipeList = new ArrayList<>();
		
		for(int i = 0; i < 10; i++) {	
			Recipe recipeNew = new Recipe();
			BeanUtils.copyProperties(recipe, recipeNew);
			recipeNew.setName("RecipeName" + i);
			recipeList.add(recipeNew);
		}
		recipeRepository.saveAll(recipeList);

        int limit = 5;
        
        // when
        mvc.perform(
                 get(TestConstant.GET_RECIPE_PATH + "?limit=" + limit)
                        .header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                        .header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER))

                // then
                .andExpect(
                        matchAll(
                                 status().is(HttpStatus.OK.value()),
                                 jsonPath("$", notNullValue()),
                                 jsonPath("$.*", hasSize(5)),
		                         jsonPath("$..id").isNotEmpty(),
		                         jsonPath("$..createdDate").isNotEmpty(),
		                         jsonPath("$..updatedDate").isNotEmpty(),
		                         jsonPath("$..isVegetarian").isNotEmpty(),
		                         jsonPath("$..recipeInstruction").exists(),
		                         jsonPath("$..recipeIngredient").exists(),
		                         jsonPath("$..category").exists(),
		                         jsonPath("$..user").exists(),
		                         jsonPath("$..name").isNotEmpty(),
		                         jsonPath("$..description").isNotEmpty(),
		                         jsonPath("$..prepTime").isNotEmpty(),
		                         jsonPath("$..serving").isNotEmpty()
                        ));
		
    }
    
    @Test
    void givenValidGetRequest_whenGetRecipeListWithNameQueryParam_thenGetRecipeSuccessfully() throws Exception {
		// given
		Recipe recipe = TestUtils.entityFromJsonFile(Recipe.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD1_CREATE_RECIPE_SUCCESSFULLY);
		subResourceData.prepareSubResources(recipe);
		recipe.assignParentToChilds();
		List<Recipe> recipeList = new ArrayList<>();
		
		for(int i = 0; i < 10; i++) {	
			Recipe recipeNew = new Recipe();
			BeanUtils.copyProperties(recipe, recipeNew);
			recipeNew.setName("RecipeName" + i);
			recipeList.add(recipeNew);
		}
		recipeRepository.saveAll(recipeList);
		
		String nameQueryParam = "RecipeName1";

        // when
        mvc.perform(
                 get(TestConstant.GET_RECIPE_PATH + "?name=" + nameQueryParam)
                        .header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                        .header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER))

                // then
                .andExpect(
                        matchAll(
                                 status().is(HttpStatus.OK.value()),
                                 jsonPath("$", notNullValue()),
                                 jsonPath("$.*", hasSize(1)),
		                         jsonPath("$..id").isNotEmpty(),
		                         jsonPath("$..name",  hasItem(nameQueryParam)),
		                         jsonPath("$..createdDate").isNotEmpty(),
		                         jsonPath("$..updatedDate").isNotEmpty(),
		                         jsonPath("$..isVegetarian").isNotEmpty(),
		                         jsonPath("$..recipeInstruction").exists(),
		                         jsonPath("$..recipeIngredient").exists(),
		                         jsonPath("$..category").exists(),
		                         jsonPath("$..user").exists(),
		                         jsonPath("$..description").isNotEmpty(),
		                         jsonPath("$..cookTime").isNotEmpty(),
		                         jsonPath("$..prepTime").isNotEmpty(),
		                         jsonPath("$..serving").isNotEmpty()
                        ));
		
    }
    
    @Test
    void givenValidGetRequest_whenGetRecipeListWithCategoryQueryParam_thenGetRecipeSuccessfully() throws Exception {
		// given
		Recipe recipe = TestUtils.entityFromJsonFile(Recipe.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD1_CREATE_RECIPE_SUCCESSFULLY);
		subResourceData.prepareSubResources(recipe);
		List<Recipe> recipeList = new ArrayList<>();
		
		for(int i = 0; i < 10; i++) {	
			Recipe recipeNew = new Recipe();
			BeanUtils.copyProperties(recipe, recipeNew);
			Category category = new Category();
			category.setName("Category" + i);
			recipeNew.setCategory(category);
			subResourceData.prepareSubResourceForCategory(recipeNew);
			recipeNew.assignParentToChilds();
			recipeList.add(recipeNew);
		}
		recipeRepository.saveAll(recipeList);
		
		String categoryQueryParam = "Category1";

        // when
        mvc.perform(
                 get(TestConstant.GET_RECIPE_PATH + "?category.name=" + categoryQueryParam)
                        .header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                        .header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER))

                // then
                .andExpect(
                        matchAll(
                                 status().is(HttpStatus.OK.value()),
                                 jsonPath("$", notNullValue()),
                                 jsonPath("$.*", hasSize(1)),
		                         jsonPath("$..id").isNotEmpty(),
		                         jsonPath("$..category.name", hasItem(categoryQueryParam)),
		                         jsonPath("$..createdDate").isNotEmpty(),
		                         jsonPath("$..updatedDate").isNotEmpty(),
		                         jsonPath("$..isVegetarian").isNotEmpty(),
		                         jsonPath("$..recipeInstruction").exists(),
		                         jsonPath("$..recipeIngredient").exists(),
		                         jsonPath("$..category").exists(),
		                         jsonPath("$..user").exists(),
		                         jsonPath("$..name").exists(),
		                         jsonPath("$..description").isNotEmpty(),
		                         jsonPath("$..cookTime").isNotEmpty(),
		                         jsonPath("$..prepTime").isNotEmpty(),
		                         jsonPath("$..serving").isNotEmpty()
                        ));
		
    }
    
    @Test
    void givenValidGetRequest_whenGetRecipeListWithUserQueryParam_thenGetRecipeSuccessfully() throws Exception {
		// given
		Recipe recipe = TestUtils.entityFromJsonFile(Recipe.class, TestConstant.JSON_RECIPE_FULL_PAYLOAD1_CREATE_RECIPE_SUCCESSFULLY);
		subResourceData.prepareSubResources(recipe);
		List<Recipe> recipeList = new ArrayList<>();
		
		for(int i = 0; i < 10; i++) {	
			Recipe recipeNew = new Recipe();
			BeanUtils.copyProperties(recipe, recipeNew);
			User user = new User();
			user.setName("User" + i);
			recipeNew.setUser(user);
			subResourceData.prepareSubResourceForUser(recipeNew);
			recipeNew.assignParentToChilds();
			recipeList.add(recipeNew);
		}
		recipeRepository.saveAll(recipeList);
		
		String userQueryParam = "User1";

        // when
        mvc.perform(
                 get(TestConstant.GET_RECIPE_PATH + "?user.name=" + userQueryParam)
                        .header("Accept", TestConstant.JSON_ACCEPT_HEADER)
                        .header("Content-Type", TestConstant.JSON_CONTENT_TYPE_HEADER))

                // then
                .andExpect(
                        matchAll(
                                 status().is(HttpStatus.OK.value()),
                                 jsonPath("$", notNullValue()),
                                 jsonPath("$.*", hasSize(1)),
		                         jsonPath("$..id").isNotEmpty(),
		                         jsonPath("$..user.name", hasItem(userQueryParam)),
		                         jsonPath("$..category").exists(),
		                         jsonPath("$..createdDate").isNotEmpty(),
		                         jsonPath("$..updatedDate").isNotEmpty(),
		                         jsonPath("$..isVegetarian").isNotEmpty(),
		                         jsonPath("$..recipeInstruction").exists(),
		                         jsonPath("$..recipeIngredient").exists(),
		                         jsonPath("$..category").exists(),
		                         jsonPath("$..name").exists(),
		                         jsonPath("$..description").isNotEmpty(),
		                         jsonPath("$..cookTime").isNotEmpty(),
		                         jsonPath("$..prepTime").isNotEmpty(),
		                         jsonPath("$..serving").isNotEmpty()
                        ));
		
    }

}

