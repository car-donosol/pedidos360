package com.pedidos360.orders.infrastructure.adapter.in.rest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthTestController {

    @GetMapping("/api/secure/ping")
    public String securePing(@AuthenticationPrincipal Jwt jwt) {
        return "Hola " + jwt.getClaimAsString("name") + ", tu JWT es válido.";
    }
}
