package zaarour.dev.cashcard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser(username = "sarah1", authorities = {"SCOPE_cashcard:read"})
@AutoConfigureMockMvc
class CashCardApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("Verify retrieval of a saved cash card")
	void shouldReturnACashCardWhenDataIsSaved() throws Exception {
		this.mvc.perform(get("/cashcards/99"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(99))
				.andExpect(jsonPath("$.owner").value("sarah1"));
	}

	@WithMockUser(username="esuez5", authorities = {"SCOPE_cashcard:read", "SCOPE_cashcard:write"})
	@Test
	@DirtiesContext
	@DisplayName("Verify creation of a new cash card")
	void shouldCreateANewCashCard() throws Exception {
		String location = this.mvc.perform(post("/cashcards")
						.with(csrf())// Ensure CSRF token is included
						.contentType("application/json")
						.content("""
                        {
                            "amount" : 250.00
                        }
                        """))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"))
				.andReturn().getResponse().getHeader("Location");

		this.mvc.perform(get(location))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.amount").value(250.00))
				.andExpect(jsonPath("$.owner").value("esuez5"));
	}

	@Test
	@DisplayName("Verify retrieval of all cash cards")
	void shouldReturnAllCashCardsWhenListIsRequested() throws Exception {
		this.mvc.perform(get("/cashcards"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$..owner").value(everyItem(equalTo("sarah1"))));
	}

	@WithMockUser(username = "esuez5", authorities = {"SCOPE_cashcard:read"})
	@Test
	@DisplayName("Verify forbidden access when card belongs to someone else")
	void shouldReturnForbiddenWhenCardBelongsToSomeoneElse() throws Exception {
		this.mvc.perform(get("/cashcards/99"))
				.andExpect(status().isForbidden());
	}
}
