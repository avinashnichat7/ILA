package com.neo.v1.mapper;

import com.neo.v1.model.PaymentDetails;
import com.neo.v1.model.PaymentEntry;
import com.neo.v1.model.TmsxCreateHoldRequest;
import com.neo.v1.model.account.TransferFeesRequest;
import com.neo.v1.tmsx.Amount;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.neo.v1.enums.TransferType.FAWRI;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TransferFeesRequestMapperTest {

    @InjectMocks
    private TransferFeesRequestMapper subject;

    @Test
    void whenMapIsCalledWithCreateHoldTransactionResponseExpectSubjectToRetunTmsxCreateHoldResponseData() {
        TmsxCreateHoldRequest tmsxCreateHoldRequest = TmsxCreateHoldRequest.builder()
                .paymentEntry(PaymentEntry.builder().amount(Amount.builder().value(BigDecimal.ONE).currency("BHD").build()).build())
                .paymentDetails(PaymentDetails.builder().transactionTypeCode("fawri").build())
                .build();

        TransferFeesRequest expected = TransferFeesRequest.builder()
                .amount(BigDecimal.ONE)
                .currency("BHD")
                .transactionType("fawri")
                .build();
        TransferFeesRequest result = subject.map(tmsxCreateHoldRequest, "fawri");
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void mapForPendingFawri_WithParameters_RetunTransferFeesRequest() {
        String accountId = "124563634";
        String currency = "BHD";
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .amount(BigDecimal.ONE.negate())
                .accountCurrency(Currency.builder().code(currency).build())
                .build();

        TransferFeesRequest expected = TransferFeesRequest.builder()
                .sourceAccountId(accountId)
                .amount(BigDecimal.ONE)
                .currency(currency)
                .transactionType(FAWRI.getValue())
                .build();
        TransferFeesRequest result = subject.mapForPendingFawri(accountId, accountTransaction);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }
}
