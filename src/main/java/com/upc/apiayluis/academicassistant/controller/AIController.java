package com.upc.apiayluis.academicassistant.controller;

import com.upc.apiayluis.academicassistant.dtos.ResumenIaDTO;
import com.upc.apiayluis.academicassistant.services.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lfay/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/resumen/{apunteId}")
    public ResponseEntity<ResumenIaDTO> generarResumen(@PathVariable Long apunteId) {
        ResumenIaDTO resumen = aiService.generarResumenApunte(apunteId);
        return new ResponseEntity<>(resumen, HttpStatus.OK);
    }
}