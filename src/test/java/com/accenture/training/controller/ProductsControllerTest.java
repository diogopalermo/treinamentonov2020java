package com.accenture.training.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.accenture.training.MyApplication;
import com.accenture.training.dto.ProductsTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.security.xsuaa.http.HttpHeaders;

//@ActiveProfiles(profiles = { "test" })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes=MyApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductsControllerTest {

	@Autowired
	private ProductsController controller;

	@Autowired
	private WebApplicationContext context;
	
	// Mock testing variables
	private static MockMvc mockMvc;
	private static ObjectMapper mapper;
	private static ProductsTO product;

	private static void getProductTest() {
		ProductsTO productsTO = new ProductsTO();
		productsTO.setName("Product Test");
		productsTO.setManufacturer("MAnufacturerTest");
		productsTO.setValidFrom("2020-11-20T10:00:00");
		productsTO.setValidTo("2020-11-21T10:00:00");
		product = productsTO;
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		getProductTest();
	}

	@Before
	public void setUpBefore() {
		mockMvc =  MockMvcBuilders.standaloneSetup(controller).build();
				
				/*MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())  
        .build();*/
		
	}

	@Test
	public void aa_saveProduct() throws UnsupportedEncodingException, Exception {

		final byte[] productAsByteArray = mapper.writeValueAsBytes(product);

		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/Product")
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(productAsByteArray);
		request.header(HttpHeaders.AUTHORIZATION, "Basic ZGlvZ28uYS50b2JsZXJAYWNjZW50dXJlLmNvbTpTQHAuMjAxOA==");
		
		
		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final ProductsTO objResult = mapper.readValue(result, ProductsTO.class);

		assertThat(objResult.getId()).isNotEmpty();
		product.setId(objResult.getId());
	}

	@Test
	public void ab_changeProduct() throws UnsupportedEncodingException, Exception {

		String newName = "Produto Teste Alterado";
		product.setName(newName);
		final byte[] productAsByteArray = mapper.writeValueAsBytes(product);

		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/Product/"+product.getId())
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(productAsByteArray);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final ProductsTO objResult = mapper.readValue(result, ProductsTO.class);

		assertThat(objResult.getId()).isEqualTo(product.getId());
		assertThat(objResult.getName()).isEqualTo(newName);

	}

	@Test
	public void ac_getAllProducts() throws UnsupportedEncodingException, Exception {

		
		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/Product")
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final List<ProductsTO> objResult = Arrays.asList(mapper.readValue(result, ProductsTO[].class));
		assertThat(objResult.size()).isGreaterThan(0);

	}
	
	@Test
	public void acb_getAllProductsWithoutFuzzy() throws UnsupportedEncodingException, Exception {

		
		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/Product?keyword=MAnufacturerTest")
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final List<ProductsTO> objResult = Arrays.asList(mapper.readValue(result, ProductsTO[].class));
		assertThat(objResult.size()).isGreaterThan(0);

	}
	@Test
	public void acb_getAllProductsWithFuzzy() throws UnsupportedEncodingException, Exception {

		
		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/Product?keyword=teste&fuzzy=true")
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final List<ProductsTO> objResult = Arrays.asList(mapper.readValue(result, ProductsTO[].class));
		assertThat(objResult.size()).isGreaterThan(0);

	}
	
	@Test
	public void ad_getOneProduct() throws UnsupportedEncodingException, Exception {

		
		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/Product/"+product.getId())
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final ProductsTO objResult = mapper.readValue(result, ProductsTO.class);
		assertThat(objResult.getId()).isEqualTo(product.getId());

	}
	@Test
	public void az_deleteProduct() throws UnsupportedEncodingException, Exception {

		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/Product/"+product.getId())
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse()
				.getContentAsString();

	}

}
