package com.medical.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import com.medical.app.model.Bill;
import com.medical.app.service.BillService;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "http://localhost:8081") // ✅ IMPORTANT
public class BillController {

    @Autowired
    private BillService service;

    // Save bill
    @PostMapping
    public Bill save(@RequestBody Bill bill) {
        return service.saveBill(bill);
    }

    // Get all bills
    @GetMapping
    public List<Bill> getAll() {
        return service.getAllBills();
    }

    // ✅ FILE UPLOAD (FINAL WORKING)
    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) {

        Map<String, Object> res = new HashMap<>();

        try {
            if (file.isEmpty()) {
                res.put("message", "File is empty ❌");
                return res;
            }

            String fileName = file.getOriginalFilename();

            // (Optional) You can save file here later

            res.put("message", "File uploaded successfully ✅");
            res.put("fileName", fileName);
            res.put("status", "success");

        } catch (Exception e) {
            res.put("message", "Upload failed ❌");
            res.put("status", "error");
        }

        return res;
    }
}