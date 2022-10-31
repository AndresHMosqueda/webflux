package com.example.webfluxwe.demowebflux.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.example.webfluxwe.demowebflux.entities.JwtBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthService {
    @Autowired
    private JwtBean tokenBean;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public String getToken() {
        if (tokenBean.getToken() == null || checkIfTokenExpired()) {
            this.fetchToken();
        }
        return tokenBean.getToken();
    }

    public String fetchToken() {
//        System.out.println("ADENTRO DEL fetchToken TOKEN!!!!");
        String token = null;
        try {
            token = webClientBuilder.build().post().uri("http://localhost:8082/token")
                    .headers(httpHeaders -> httpHeaders.setBasicAuth("andres", "password1"))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
            System.out.println("_____Linea 45 del fetchToken!!!!!! " + token);
            if (token != null) {
                tokenBean.setToken(String.valueOf(token));
            }
        } catch (Exception e) {
            System.out.println("ERROR___ " + e.getMessage());
        }
//        System.out.println("El puto token es_____ linea 48_____ " + token);
        return token;
    }

    public boolean checkIfTokenExpired() {
        System.out.println("ADENTRO DE checkIfTokenExpired_____________");
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryTime = setExpiryTime();
        boolean isTokenExpired = currentDateTime.isAfter(expiryTime);
        System.out.println("@@ YA SE EXPIRO EL TOKEN: " + isTokenExpired);
        return isTokenExpired;
    }

    public LocalDateTime setExpiryTime() {
        System.out.println("#######_ADENTRO DE setExpiryTime___");
//		String issueAt = tokenBean.getIssueAt();
//		long expiresInLong = Long.valueOf(tokenBean.getExpiresIn());
//		String issueAt = "1665329286242";
//      String issueAt = tokenBean.getIssueAt();
        long issueAt = System.currentTimeMillis();
        System.out.println("@@@@@@@_EL VALOR DE issueAt:_" + issueAt);
        long issueAtLong = Long.valueOf(issueAt);
        System.out.println("@@@@@@@_ADENTRO DE timestampLong__" + issueAtLong);
        long expiresInLong = Long.valueOf("1799");
        long expiresIn = (expiresInLong - 60) / 60;
        System.out.println("********* expiresIn : " + expiresIn);
        long expirationTimestamp = issueAtLong + TimeUnit.MINUTES.toMillis(expiresIn);
        System.out.println("expirationTimestamp##########: " + expirationTimestamp);
        LocalDateTime expiryDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(expirationTimestamp),
                TimeZone.getDefault().toZoneId());
        tokenBean.setExpiryTime(expiryDateTime);
        System.out.println("expiryDateTime dentro de setExpiryTime es:_ " + expiryDateTime);
        return expiryDateTime;

    }
}
