package com.neo.v1.util;

import com.neo.core.exception.ServiceException;
import com.neo.core.provider.ServiceKeyMapping;
import org.junit.jupiter.api.Test;

import static com.neo.v1.enums.TransactionsServiceKeyMapping.FILTER_DECODING_ERROR;
import static com.neo.v1.util.TransactionsUtils.decodeString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionsUtilsTest {

    @Test
    void decodeString_withEncodedString_returnDecodedString() {
        String result = decodeString("Duaa%2B%40%20test%24%2A%28%29");
        assertThat(result).isEqualTo("Duaa+@ test$*()");
    }


    @Test
    void decodeString_withNull_returnNull() {
        String result = decodeString(null);
        assertThat(result).isNull();
    }
    @Test
    void decodeString_withEmptyString_returnEmptyString() {
        String result = decodeString("");
        assertThat(result).isEmpty();
    }

    @Test
    void decodeString_throwException() {
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> decodeString("23%//////2323")).getKeyMapping();
        assertEquals(FILTER_DECODING_ERROR, keyMapping);
    }
}
