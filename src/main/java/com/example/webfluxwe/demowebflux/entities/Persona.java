package com.example.webfluxwe.demowebflux.entities;

import org.springframework.stereotype.Component;

@Component
public class Persona {

    private String id;
    private String name;
    private String email;
    private String located_in;

    public Persona() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLocated_in() {
        return located_in;
    }
    public void setLocated_in(String located_in) {
        this.located_in = located_in;
    }

    @Override
    public String toString() {
        return "Persona [id=" + id + ", name=" + name + ", email=" + email + ", located_in=" + located_in + "]";
    }

}
