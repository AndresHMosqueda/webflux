package com.example.webfluxwe.demowebflux.controllers;

import com.example.webfluxwe.demowebflux.entities.Mensaje;
import com.example.webfluxwe.demowebflux.entities.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
//@RequestMapping("/")
public class HomeController {

    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private WebClient miVersionWebClient;
    @Autowired
    private Persona persona;
    private final String URL_SEGUNDA = "http://localhost:8082/otro";

    @GetMapping("/")
    public Persona home() {
        Persona resp = miVersionWebClient.get()
                .uri(URL_SEGUNDA)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(s -> s.is4xxClientError(), (e) -> e.bodyToMono(Persona.class).handle((body, handler) -> {
            System.out.println("body_: " + body.toString());
            System.out.println("is4xxClientError__");
            persona = body;
        })).bodyToMono(Persona.class).block();

        if (resp == null) {
            LOG.info("resp is null -> : " + resp);
            LOG.info("y el valor de persona es -> : " + persona.toString());
            return persona;
//            return "Error 401 a seguirle";
        } else {
            LOG.info("El valor de resp is not null_ " + resp);
            return resp;
//            return segunda.getEmail();
        }
    }
}
