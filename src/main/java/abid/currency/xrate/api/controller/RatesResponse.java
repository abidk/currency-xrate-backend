package abid.currency.xrate.api.controller;

import abid.currency.xrate.api.model.Rates;

public class RatesResponse {

    private final Rates rates;

    public RatesResponse(Rates rates) {
        this.rates = rates;
    }

    public Rates getRates() {
        return rates;
    }

}
