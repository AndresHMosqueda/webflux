package com.example.webfluxwe.demowebflux.controllers;

import com.example.webfluxwe.demowebflux.entities.Persona;
import com.example.webfluxwe.demowebflux.services.AuthService;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class HomeControllerTest {

    private final String URL_SEGUNDA = "http://localhost:8082/token";

    @InjectMocks
    private HomeController homeController;

    @InjectMocks
    AuthService authService;

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;

    //    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    //    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    @Mock
    private Mono<Persona> postResponseMock;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
//        MockitoAnnotations.initMocks(this);
    }

    //    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Prueba del HomeController")
    public void shouldGetHome() {
        Persona persona = new Persona();
        persona.setId("1");
        persona.setEmail("dos@email.com");
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.accept(ArgumentMatchers.any())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.onStatus(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<Persona>>notNull())).thenReturn(Mono.just(persona));
        Persona response = homeController.home();
        System.out.println("EL response es: " + response);
        Assertions.assertEquals(persona, response);

    }

}