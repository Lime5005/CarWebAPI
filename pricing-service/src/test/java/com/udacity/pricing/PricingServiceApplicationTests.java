package com.udacity.pricing;

import com.udacity.pricing.service.PricingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PricingServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Mock
	PricingService pricingService;
	@Test
	public void getPrice() throws Exception {
		MvcResult result = mockMvc.perform(get("/services/price?vehicleId="+1L))
				.andExpect(status().isOk()).andReturn();
		verify(pricingService).getPrice(1L);
	}

	@Test
	public void getPriceKO() throws Exception {
		mockMvc.perform(get("/services/price?vehicleId="+200L))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertEquals("404 NOT_FOUND \"Price Not Found\"; nested exception is com.udacity.pricing.service.PriceException: Cannot find price for Vehicle 200", result.getResolvedException().getMessage()));

	}

}
