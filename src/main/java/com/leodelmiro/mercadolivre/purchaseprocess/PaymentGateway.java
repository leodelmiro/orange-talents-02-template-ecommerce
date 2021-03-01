package com.leodelmiro.mercadolivre.purchaseprocess;

import org.springframework.web.util.UriComponentsBuilder;

public enum PaymentGateway {
    PAYPAL {
        @Override
        String createUrlReturn(Purchase purchase, UriComponentsBuilder uriComponentsBuilder) {
            String urlPaypalReturn = uriComponentsBuilder.path("/retorno-paypal/{id}").buildAndExpand(purchase.getId()).toString();

            return "paypal.com/" + purchase.getId() + "?redirectUrl=" + urlPaypalReturn;
        }
    },

    PAGSEGURO {
        @Override
        String createUrlReturn(Purchase purchase, UriComponentsBuilder uriComponentsBuilder) {
            String urlPagseguroReturn = uriComponentsBuilder.path("/retorno-pagseguro/{id}").buildAndExpand(purchase.getId()).toString();

            return "pagseguro.com/" + purchase.getId() + "?redirectUrl=" + urlPagseguroReturn;
        }

    };

    abstract String createUrlReturn(Purchase purchase,
                                    UriComponentsBuilder uriComponentsBuilder);
}
