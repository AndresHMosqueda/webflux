package com.example.webfluxwe.demowebflux.controllers;

import com.example.webfluxwe.demowebflux.entities.Persona;
import com.example.webfluxwe.demowebflux.entities.RsaProps;
import com.example.webfluxwe.demowebflux.services.AuthService;
//import com.nimbusds.jose.Header;
//import com.nimbusds.jwt.JWT;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;

@RestController
//@RequestMapping("/")
public class HomeController {

    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private WebClient miVersionWebClient;
    @Autowired
    private Persona persona;
    private final String URL_SEGUNDA = "http://localhost:8082/otro";
    @Autowired
    private AuthService authService;

    @Autowired
    private RsaProps rsaProps;

    @GetMapping("/")
    public Persona home() {
        Persona resp = miVersionWebClient.get()
                .uri(URL_SEGUNDA)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(s -> s.is4xxClientError(), (e) -> e.bodyToMono(Persona.class).handle((body, handler) -> {
                    persona = body;
                })).bodyToMono(Persona.class).block();

        if (resp == null) {
            LOG.info("resp is null -> : " + resp);
            LOG.info("y el valor de persona es -> : " + persona.toString());
            return persona;
        } else {
            LOG.info("El valor de resp is not null_ " + resp);
            return resp;
        }
    }

    @GetMapping("/jwtwe")
    public boolean jwttest() throws ParseException {
        boolean flag = validateToken(authService.getToken());
        System.out.println("the flag is___" + flag);
        return flag;
    }

    private PublicKey getPublicKey() throws GeneralSecurityException, IOException {
//        String rsaPublicKey = "-----BEGIN PUBLIC KEY-----" +
//                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn7uQDPxZ899oUeg1TEAp" +
//                "y2IqHisR1VpQj1EOuT5tnKwWAAqLF64c0VEfu5yMPs1oGQIfIKhOIGsPKAAMeWMc" +
//                "CLFPTELyJK3eGplHyQTu2C3YRjVUmxvOALxVIhiBNfVYvlp3vowfSN04fF6cZf85" +
//                "WFcKOdVl/4Enz9Q0S2E29xVfTOKyDS7LmIibunJLzGoYvRsYOovhRhMcMqzC1fuv" +
//                "fu+zucOiiyMA+SRszko8o5KiVpOhdYk3w1qsiJGpq7tNDnzTwtZDGwUNXDpsZU27" +
//                "Oe3c9TpF88+vXuka72VTrMHzOMMQwpCJTCBixk+N4zUQ9gYzstwSQdm2zU8+2CFl" +
//                "TQIDAQAB" +
//                "-----END PUBLIC KEY-----";
        String rsaPublicKey = new String(Files.readAllBytes(Paths.get("src/main/resources/certs/public.pem")));
        System.out.println("rsaPublicKey__" + rsaPublicKey);
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("\n", "");
        System.out.println("rsaPublicKey__" + rsaPublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
//        return rsaPublicKey;
    }

//    private static PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
//        String rsaPrivateKey = "-----BEGIN PUBLIC KEY-----\n" +
//                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn7uQDPxZ899oUeg1TEAp\n" +
//                "y2IqHisR1VpQj1EOuT5tnKwWAAqLF64c0VEfu5yMPs1oGQIfIKhOIGsPKAAMeWMc\n" +
//                "CLFPTELyJK3eGplHyQTu2C3YRjVUmxvOALxVIhiBNfVYvlp3vowfSN04fF6cZf85\n" +
//                "WFcKOdVl/4Enz9Q0S2E29xVfTOKyDS7LmIibunJLzGoYvRsYOovhRhMcMqzC1fuv\n" +
//                "fu+zucOiiyMA+SRszko8o5KiVpOhdYk3w1qsiJGpq7tNDnzTwtZDGwUNXDpsZU27\n" +
//                "Oe3c9TpF88+vXuka72VTrMHzOMMQwpCJTCBixk+N4zUQ9gYzstwSQdm2zU8+2CFl\n" +
//                "TQIDAQAB\n" +
//                "-----END PUBLIC KEY-----\n";
//
////        rsaPrivateKey = rsaPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "");
////        rsaPrivateKey = rsaPrivateKey.replace("-----END PRIVATE KEY-----", "");
//
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey));
//        KeyFactory kf = KeyFactory.getInstance("RS256");
//        PrivateKey privKey = kf.generatePrivate(keySpec);
//        return privKey;
//    }

    public boolean validateToken(String jwt) {
        try {
//            System.out.println("getPublicKey__: " + getPublicKey());
            Jws<Claims> resp = Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
            System.out.println("resp.getBody() es__" + resp.getBody());
            System.out.println("resp.getSignature() es__: " + resp.getSignature() );
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
//			logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
//			logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
//			logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
//			logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
//			logger.error("JWT claims string is empty.");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("JWT line 153.");
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            System.out.println("JWT line 156__.");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
