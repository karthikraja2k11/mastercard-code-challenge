package io.swagger.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ConnectedApiController.class)
public class ConnectedApiControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ConnectedService connectedService;
	
	private String YES = "yes";
	private String NO = "no";

	@Test
	public void testConnectionAvailableRoute() throws Exception {
		Mockito.when(connectedService.areConnected(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/connected?destination=Boston&origin=Philadelphia").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(YES, result.getResponse().getContentAsString());
	}

	@Test
	public void testConnectionNotAvailableRoute() throws Exception {
		Mockito.when(connectedService.areConnected(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/connected?destination=Boston&origin=Philadelphia").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(NO, result.getResponse().getContentAsString());
	}

	@Test
	public void testInvalidCityRoute() throws Exception {
		Mockito.when(connectedService.areConnected(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/connected?destination=abc&origin=xyz").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(NO, result.getResponse().getContentAsString());
	}

}
