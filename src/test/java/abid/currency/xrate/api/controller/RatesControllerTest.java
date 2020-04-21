package abid.currency.xrate.api.controller;

import abid.currency.xrate.api.model.Currency;
import abid.currency.xrate.api.model.Quote;
import abid.currency.xrate.api.model.Rate;
import abid.currency.xrate.api.model.Rates;
import abid.currency.xrate.api.service.ExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RatesController.class)
public class RatesControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ExchangeService exchangeService;

    @BeforeEach
    public void setUp() {
        when(exchangeService.retrieveRates(Currency.of("EUR"), LocalDate.of(2011, 1, 1)))
                .thenReturn(new Rates(Currency.of("EUR"), List.of(new Rate(Currency.of("USD"), Quote.of("2")))));
    }

    @Test
    public void whenCallingEndpointWithIncorrectlyFormattedCurrency() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/rates/eur/2011-01-01")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCallingEndpointWithIncorrectlyFormattedDate() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/rates/EUR/01-02-2011")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCallingEndpointShouldReturnExpectedResponse() throws Exception {
        final MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get("/rates/EUR/2011-01-01")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final String response = mvcResult.getResponse().getContentAsString();
        assertEquals("{\"rates\":{\"base\":{\"value\":\"EUR\"},\"rates\":[{\"currency\":{\"value\":\"USD\"},\"quote\":{\"value\":\"2\"}}]}}", response);
    }
}
