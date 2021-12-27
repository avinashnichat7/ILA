FROM mcr.microsoft.com/mssql/server:2017-latest

RUN mkdir -p /neo_service_transactions_enrichment/config
WORKDIR /neo_service_transactions_enrichment/config

COPY config /neo_service_transactions_enrichment/config

RUN chmod +x /neo_service_transactions_enrichment/config/entrypoint.sh
RUN chmod +x /neo_service_transactions_enrichment/config/setup.sh

ENTRYPOINT ["./entrypoint.sh"]