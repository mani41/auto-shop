package org.shop.autoshoppingagent.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shop.autoshoppingagent.request.CreateShoppingSessionRequest;
import org.shop.autoshoppingagent.response.ShoppingSessionResponse;
import org.shop.autoshoppingagent.service.ShoppingSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/shopping/sessions")
@RequiredArgsConstructor
public class ShoppingSessionController {

    private final ShoppingSessionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingSessionResponse create(
            @Valid @RequestBody CreateShoppingSessionRequest request) {

        return service.create(request);
    }

    @GetMapping("/{sessionId}")
    public ShoppingSessionResponse get(
            @PathVariable UUID sessionId) {

        return service.get(sessionId);
    }
}