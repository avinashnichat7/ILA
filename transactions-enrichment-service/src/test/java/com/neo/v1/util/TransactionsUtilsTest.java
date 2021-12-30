package com.neo.v1.util;

import org.junit.jupiter.api.Test;

import static com.neo.v1.util.TransactionsUtils.decodeString;
import static org.assertj.core.api.Assertions.assertThat;

class TransactionsUtilsTest {

    @Test
    void decodeString_withEncodedString_returnDecodedString() {
        String result = decodeString("Duaa%2B%40%20test%24%2A%28%29");
        assertThat(result).isEqualTo("Duaa+@ test$*()");
    }


    @Test
    void decodeString_withNull_returnNull() {
        String result = decodeString(null);
        assertThat(result).isEqualTo(null);
    }
    @Test
    void decodeString_withEmptyString_returnEmptyString() {
        String result = decodeString("");
        assertThat(result).isEqualTo("");
    }
}
