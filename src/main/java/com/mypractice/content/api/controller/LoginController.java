package com.mypractice.content.api.controller;

import com.mypractice.content.api.dto.LoginDto;
import com.mypractice.content.api.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final JwtTokenProvider tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;

    @Autowired
    public LoginController(JwtTokenProvider tokenProvider, ReactiveAuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity> login(@Valid @RequestBody Mono<LoginDto> authRequest) {

        return authRequest
                .flatMap(login -> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()))
                        .map(this.tokenProvider::createToken)).map(jwt -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "myToken " + jwt);
                    Map tokenBody = new HashMap();
                    tokenBody.put("access_token", jwt);
                    return new ResponseEntity<>(tokenBody, httpHeaders, HttpStatus.OK);
                });
    }
}
