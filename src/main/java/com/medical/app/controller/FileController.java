package com.medical.app.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/file")
public class FileController {

    @PostMapping("/upload")
    public String uploadPDF(@RequestParam("file") MultipartFile file) {

        try {
            PDDocument document = PDDocument.load(file.getInputStream());
            PDFTextStripper pdfStripper = new PDFTextStripper();

            String text = pdfStripper.getText(document);
            document.close();

            return text;

        } catch (IOException e) {
            return "Error reading PDF: " + e.getMessage();
        }
    }
}