package com.coder.ecommerce.service.customReponse;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomLoginResponse {

    private String idToken;

    @JsonProperty("id_token")
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
