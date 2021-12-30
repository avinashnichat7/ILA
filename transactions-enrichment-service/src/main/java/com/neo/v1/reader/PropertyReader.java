package com.neo.v1.reader;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PropertyReader {

    @Value("${te.urbis.pendingTransactionsPageSize}")
    private int pendingTransactionsPageSize;

}
