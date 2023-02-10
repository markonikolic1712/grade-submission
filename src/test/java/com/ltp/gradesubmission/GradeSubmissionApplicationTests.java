package com.ltp.gradesubmission;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.matches;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ltp.gradesubmission.web.GradeController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// anotacija @SpringBootTest pokrece aplikaciju tako sto poziva metodu contextLoads() i skenira aplikaciju za binovima

@SpringBootTest
@AutoConfigureMockMvc
class GradeSubmissionApplicationTests {

	// GradeController je ucitan u spring kontejner pa se inject-uje u GradeSubmissionApplicationTests
	// nije neophodno da se injectuje controller pa je zato zakomentarisano
	// @Autowired
	// private GradeController controller;

	// ovo je MockMvc koji simulira request i response
	@Autowired
	private MockMvc mockMvc;

	// nakon ucitavanja spring kontejnera life cycle metoda (contextLoads()) se pokrece i prvo sto se u njoj radi je da se proverava da li je controller injectovan tj. da li je null
	@Test
	void contextLoads() {
		// proverava se da li je bean controller injectovan - ako test prodje controller je uspesno inject-ovan
		//assertNotNull(controller);

		// proverava se da li je MockMvc inject-ovan
		assertNotNull(mockMvc);
	}

	// test za request na url "/" (moze biti sa i bez id-a) - kada se posalje ovajrequest controller treba da vrati view form - da se prikaze forma
	@Test
	public void testShowGradeForm() throws Exception {
		// pravi se mock reuqest
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/?id=123");

		// koristi se mockMvc da se izvrsi reuest. Kada se posalje request njega hendluje kontroler koji poziva service koji koristi repository koji vraca podatke service-u. Service vraca podatak controller-u i ovaj vraca view. Zato se ovde radi provera (assert) response statusa, da li je dobar view i da li je dobar model.
		mockMvc.perform(requestBuilder)
		.andExpect(status().is2xxSuccessful()) // import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
		.andExpect(view().name("form")) // ocekujemo da bude vracen view form
		.andExpect(model().attributeExists("grade")); // ocekujemo da model ima atribut grade
	}

	// test kada korisnik post requestom posalje ispravan grade objekat i on se submituje
	@Test
	public void testSuccessfulSubmission() throws Exception {
		// kreira se mock post request
		// u param() se prosledjuju iem propertya i value od kojih tyrebaju da se kreira objekat Grade 
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/handleSubmit")
		.param("name", "Harry")
		.param("subject", "Potions")
		.param("score", "A+");

		// sa perform() imitiramo post request i nakon uspernog submitovanja grade objekta ocekujemo redirect na /grade
		mockMvc.perform(requestBuilder)
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/grades"));
	}

	// test kada korisnik post requestom posalje grade obejata koji nije validan i submit ne treba da bude uspesan
	// ako korisnik pokusa da submituje nevalidan objekat status koji se vraca je ipak 200 (OK) ali s ekorisnik preusmerava na view /form
	@Test
	public void testUnsuccessfulSubmission() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/handleSubmit")
		.param("name", "	")
		.param("subject", "	")
		.param("score", "	");

		mockMvc.perform(requestBuilder)
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("form"));
	}

	@Test
	public void testGetGrades() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/grades");

		mockMvc.perform(requestBuilder)
		.andExpect(status().is2xxSuccessful()) 
		.andExpect(view().name("grades")) 
		.andExpect(model().attributeExists("grades")); 
	}
}
