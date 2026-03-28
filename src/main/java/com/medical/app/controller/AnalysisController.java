package com.medical.app.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;
import com.medical.app.model.Bill;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/analysis")
public class AnalysisController {

    private final String API_KEY = "sk-xxxx"; // optional

    @PostMapping
    public String analyze(@RequestBody Bill bill) {

        try {
            String prompt = "Analyze this medical bill and give insights: "
                    + "Treatment: " + bill.getTreatment()
                    + ", Amount: " + bill.getAmount()
                    + ", Hospital: " + bill.getHospitalName();

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
            msg.put("content", prompt);

            messages.add(msg);
            body.put("messages", messages);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, request, Map.class);

            Map responseBody = response.getBody();

            if (responseBody == null || !responseBody.containsKey("choices")) {
                return fallback(bill);
            }

            List choices = (List) responseBody.get("choices");

            if (choices == null || choices.isEmpty()) {
                return fallback(bill);
            }

            Map choice = (Map) choices.get(0);
            Map message = (Map) choice.get("message");

            return message.get("content").toString();

        } catch (Exception e) {
            return fallback(bill);
        }
    }

    private String fallback(Bill bill) {
        if (bill.getAmount() > 40000) {
            return "⚠️ This bill is expensive. Compare hospitals.";
        }
        return "✅ This bill is within normal range.";
    }
}