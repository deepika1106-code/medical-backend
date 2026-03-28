package com.medical.app.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/predict")
public class PredictController {

    @PostMapping
    public Map<String, Object> predict(@RequestBody Map<String, Object> input) {

        String hospital = (String) input.get("hospitalName");
        String treatment = (String) input.get("treatment");

        int baseAmount = 5000;

        if (treatment != null) {
            if (treatment.toLowerCase().contains("surgery")) {
                baseAmount = 50000;
            } else if (treatment.toLowerCase().contains("scan")) {
                baseAmount = 8000;
            } else if (treatment.toLowerCase().contains("consult")) {
                baseAmount = 2000;
            }
        }

        if (hospital != null && hospital.toLowerCase().contains("private")) {
            baseAmount += 10000;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("predictedAmount", baseAmount);
        response.put("confidence", 0.85);

        Map<String, Integer> range = new HashMap<>();
        range.put("min", baseAmount - 2000);
        range.put("max", baseAmount + 5000);

        response.put("range", range);

        List<String> factors = new ArrayList<>();
        factors.add("Hospital type");
        factors.add("Treatment complexity");
        factors.add("Average historical costs");

        response.put("factors", factors);

        return response;
    }
}