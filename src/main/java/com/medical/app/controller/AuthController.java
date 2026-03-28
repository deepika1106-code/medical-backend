package com.medical.app.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    // 🔐 LOGIN
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> data) {

        String username = data.get("username");
        String password = data.get("password");

        Map<String, Object> response = new HashMap<>();

        // ✅ YOUR CUSTOM LOGIN
        if ("admin".equals(username) && "admin123".equals(password)) {

            response.put("token", "dummy-token");

            Map<String, String> user = new HashMap<>();
            user.put("username", username);

            response.put("user", user);
            response.put("message", "Login successful");

        } else {
            response.put("error", "Invalid credentials");
        }

        return response;
    }

    // 🔁 RESET PASSWORD
    @PostMapping("/reset-password")
    public Map<String, String> resetPassword(@RequestBody Map<String, String> data) {

        Map<String, String> response = new HashMap<>();
        response.put("message", "Reset link sent to " + data.get("email"));

        return response;
    }
}