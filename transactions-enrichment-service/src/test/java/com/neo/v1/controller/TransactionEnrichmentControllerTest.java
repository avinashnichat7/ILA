package com.neo.v1.controller;

import com.neo.core.message.GenericMessageSource;
import com.neo.v1.service.TransactionEnrichmentService;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.CreateCategoryRequest;
import com.neo.v1.transactions.enrichment.model.TransactionLinkResponse;
import com.neo.v1.transactions.enrichment.model.UpdateCategoryRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TransactionEnrichmentController.class)
@ContextConfiguration(classes = GenericMessageSource.class)
 class TransactionEnrichmentControllerTest {

    private static final String URI_TRANSACTION_ENRICHMENT_ACCOUNT = "/api/v1/transactions-enrichment";
    private static final String URI_GET_CATEGORIES = "/api/v1/transactions-enrichment/category";
    private static final String URI_LINK_CATEGORIES = "/api/v1/transactions-enrichment/link";
    private static final String URI_HOLD_CATEGORIES = "/api/v1/transactions-enrichment/hold";

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
    void postTransactionEnrichment_withValidRequest_expectSuccess() throws Exception {
        mockMvc.perform(post(URI_TRANSACTION_ENRICHMENT_ACCOUNT)
                .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                .content(toJson(AccountTransactionsRequest.builder().build())))
                .andExpect(status().isOk());
    }

    @Test
    void getTransactionEnrichmentCategory_withValidRequest_expectSuccess() throws Exception {
        mockMvc.perform(get(URI_GET_CATEGORIES)
                        .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void postTransactionEnrichmentCategory_withValidRequest_expectSuccess() throws Exception {
        mockMvc.perform(post(URI_GET_CATEGORIES)
                        .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                        .content(toJson(CreateCategoryRequest.builder().build())))
                .andExpect(status().isOk());
    }

    @Test
    void putTransactionEnrichmentCategory_withValidRequest_expectSuccess() throws Exception {
        mockMvc.perform(put(URI_GET_CATEGORIES + "/" + 1)
                        .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                        .content(toJson(UpdateCategoryRequest.builder().build())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTransactionEnrichmentCategory_withValidRequest_expectSuccess() throws Exception {
        mockMvc.perform(delete(URI_GET_CATEGORIES + "/" + 1)
                        .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void postTransactionsEnrichmentLink_withValidRequest_expectSuccess() throws Exception {
        mockMvc.perform(post(URI_LINK_CATEGORIES)
                        .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                        .content(toJson(TransactionLinkResponse.builder().build())))
                .andExpect(status().isOk());
    }
    
    @Test
    void postTransactionsEnrichmentHold_withValidRequest_expectSuccess() throws Exception {
        mockMvc.perform(post(URI_HOLD_CATEGORIES)
                        .header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                        .content(toJson(TransactionLinkResponse.builder().build())))
                .andExpect(status().isOk());
    }

}
