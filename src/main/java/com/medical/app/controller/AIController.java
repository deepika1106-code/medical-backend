package com.medical.app.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/ai")
public class AIController {

    private final String API_KEY = "sk-xxxxxxxxxxxx"; // put your key or leave dummy

    @PostMapping("/chat")
    public String chat(@RequestBody String userMessage) {

        try {
            String url = "https://api.openai.com/v1/chat/completions";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + API_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-4o-mini");

            List<Map<String, String>> messages = new ArrayList<>();

            Map<String, String> msg = new HashMap<>();
            msg.put("role", "user");
            msg.put("content", userMessage);

            messages.add(msg);
            body.put("messages", messages);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, request, Map.class);

            Map responseBody = response.getBody();

            if (responseBody == null || !responseBody.containsKey("choices")) {
                return fallbackResponse(userMessage);
            }

            List choices = (List) responseBody.get("choices");

            if (choices == null || choices.isEmpty()) {
                return fallbackResponse(userMessage);
            }

            Map choice = (Map) choices.get(0);
            Map message = (Map) choice.get("message");

            return message.get("content").toString();

        } catch (Exception e) {
            System.out.println("AI ERROR: " + e.getMessage());
            return fallbackResponse(userMessage);
        }
    }

    // 🔥 FALLBACK AI (ALWAYS WORKS)
    private String fallbackResponse(String msg) {

        msg = msg.toLowerCase();

        if (msg.contains("cost")) {
            return "💡 Medical costs vary depending on hospital, treatment type, and city. Government hospitals are usually more affordable.";
        }

        if (msg.contains("surgery")) {
            return "💡 Surgery costs are high due to advanced equipment, experienced doctors, operation theatre charges, and post-care.";
        }

        if (msg.contains("bill")) {
            return "💡 You can upload your bill to analyze costs and identify overcharging.";
        }

        if (msg.contains("cheap") || msg.contains("affordable")) {
            return "💡 AIIMS and government hospitals generally offer lower-cost treatments compared to private hospitals.";
        }

        if (msg.contains("reduce") || msg.contains("save")) {
            return "💡 To reduce costs: compare hospitals, avoid unnecessary tests, and check insurance coverage.";
        }

        return "🤖 I can help with medical bill analysis, cost prediction, and hospital comparison. Ask me anything!";
    }
}