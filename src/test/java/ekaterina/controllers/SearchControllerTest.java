package ekaterina.controllers;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;
import ekaterina.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ContextConfiguration(classes = TestConfiguration.class)
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class SearchControllerTest extends ScenarioTest<SearchControllerTest.Given, SearchControllerTest.When, SearchControllerTest.Then> {

	static class Given extends Stage<Given> {

		@ScenarioState
		WebApplicationContext webApplicationContext;

		@ScenarioState
		MockMvc mockMvc;

		@ProvidedScenarioState
		String search;

		@ProvidedScenarioState
		Long deviceId;


		@As("web application")
		Given runWith(WebApplicationContext wac) {
			webApplicationContext = wac;
			return self();
		}

		Given userEnterEmptySearchString(String searchStr, Long currentDeviceId) {
			mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
			search = searchStr;
			deviceId = currentDeviceId;
			return self();
		}

		Given userEnterCorrectSearchString(String searchStr, Long currentDeviceId) {
			mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
			search = searchStr;
			deviceId = currentDeviceId;
			return self();
		}
	}

	static class When extends Stage<When> {
		@ScenarioState
		MockMvc mockMvc;

		@ExpectedScenarioState
		String search;

		@ExpectedScenarioState
		Long deviceId;

		@ProvidedScenarioState
		ModelAndView modelAndView;

		When executeSearch() throws Exception {
			modelAndView = mockMvc
					.perform(get("/search").param("search-str", search).param("currentDeviceId", deviceId.toString()))
					.andReturn().getModelAndView();
			return when();
		}

	}

	static class Then extends Stage<Then> {
		@ExpectedScenarioState
		ModelAndView modelAndView;

		Then resultSizeIsPositive(Integer size) {
			assertNotNull(modelAndView);
			assertEquals("searchResult", modelAndView.getViewName());
			assertTrue(modelAndView.getModel().containsKey("sensorsFromSearch"));
			List list = (List) modelAndView.getModel().get("sensorsFromSearch");
			assertEquals(size, Integer.valueOf(list.size()));
			return self();
		}

		Then resultSizeIsNull(Integer size) {
			assertNotNull(modelAndView);
			assertEquals("searchResult", modelAndView.getViewName());
			assertFalse(modelAndView.getModel().containsKey("sensorsFromSearch"));
			return self();
		}
	}

	@Autowired
	WebApplicationContext webApplicationContext;

	@Test
	@WithMockUser(roles = "USER", username = "test-user")
	@Sql(value = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/test-data-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void search() throws Exception {
		given().runWith(webApplicationContext).userEnterEmptySearchString("", 1L);
		when().executeSearch();
		then().resultSizeIsNull(null);
	}

	@Test
	@WithMockUser(roles = "USER", username = "test-user")
	@Sql(value = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/test-data-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void searchWithParam() throws Exception {
		given().runWith(webApplicationContext).userEnterCorrectSearchString("test", 1L);
		when().executeSearch();
		then().resultSizeIsPositive(1);
	}
}