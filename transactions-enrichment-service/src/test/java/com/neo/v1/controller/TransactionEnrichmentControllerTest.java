package com.neo.v1.controller;

import com.neo.core.message.GenericMessageSource;
import com.neo.v1.service.TransactionEnrichmentService;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.neo.core.utils.JsonUtils.toJson;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TransactionEnrichmentController.class)
@ContextConfiguration(classes = GenericMessageSource.class)
public class TransactionEnrichmentControllerTest {

    private static final String URI_TRANSACTION_ENRICHMENT_ACCOUNT = "/api/v1/transaction-enrichment";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionEnrichmentService transactionEnrichmentService;

    @BeforeEach
    void setMockMvc() {
        this.mockMvc = standaloneSetup(new TransactionEnrichmentController(transactionEnrichmentService))
                .build();
    }

    @Test
    void getPrizeLinkedAccount_withValidRequest_expectSuccess() throws Exception {
        mockMvc.perform(post(URI_TRANSACTION_ENRICHMENT_ACCOUNT)
                .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                .content(toJson(AccountTransactionsRequest.builder().build())))
                .andExpect(status().isOk());
    }

}
