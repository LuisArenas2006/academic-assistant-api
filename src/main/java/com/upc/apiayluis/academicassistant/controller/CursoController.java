package com.upc.apiayluis.academicassistant.controller;

import com.upc.apiayluis.academicassistant.dtos.CursoDTO;
import com.upc.apiayluis.academicassistant.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lfay/cursos")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoDTO> createCurso(@RequestBody CursoDTO cursoDTO){
        CursoDTO nuevoCurso = cursoService.saveCurso(cursoDTO);
        return new ResponseEntity<>(nuevoCurso, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> listarCursos(){
        List<CursoDTO> cursos = cursoService.listarCurso();
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> obtenerPorId(@PathVariable Long id){
        CursoDTO cursoDTO = cursoService.obtenerPorId(id);
        return new ResponseEntity<>(cursoDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id){
        cursoService.eleminarPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
