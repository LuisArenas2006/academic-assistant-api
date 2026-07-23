package com.upc.apiayluis.academicassistant.controller;

import com.upc.apiayluis.academicassistant.dtos.ApunteDTO;
import com.upc.apiayluis.academicassistant.services.ApunteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lfay/apuntes")
public class ApunteController {
    @Autowired
    private ApunteService apunteService;

    @PostMapping
    public ResponseEntity<ApunteDTO> createApunte(@RequestBody ApunteDTO dto) {
        ApunteDTO nuevoApunte = apunteService.saveApunte(dto);
        return new ResponseEntity<>(nuevoApunte, HttpStatus.CREATED);
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<ApunteDTO>> getApuntes(@PathVariable Long cursoId) {
        List<ApunteDTO> apuntes = apunteService.listarPorCurso(cursoId);
        return new ResponseEntity<>(apuntes, HttpStatus.OK);
    }
}
