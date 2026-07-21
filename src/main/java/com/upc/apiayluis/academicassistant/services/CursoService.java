package com.upc.apiayluis.academicassistant.services;

import com.upc.apiayluis.academicassistant.dtos.CursoDTO;
import com.upc.apiayluis.academicassistant.entities.Curso;
import com.upc.apiayluis.academicassistant.repositories.CursoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CursoDTO saveCurso(CursoDTO cursoDTO) {
        Curso curso = modelMapper.map(cursoDTO, Curso.class);
        Curso cursoSaved = cursoRepository.save(curso);
        return modelMapper.map(cursoSaved, CursoDTO.class);
    }

    public List<CursoDTO> listarCurso() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream()
                .map(curso -> modelMapper.map(curso,CursoDTO.class))
                .collect(Collectors.toList());
    }

    public CursoDTO obtenerPorId(Long id) {
        Curso curso = cursoRepository.findById(id).orElseThrow(()-> new RuntimeException("Curso no encontrado con el ID: "+ id));

        return modelMapper.map(curso,CursoDTO.class);
    }

    public void eleminarPorId(Long id) {
        if(!cursoRepository.existsById(id)){
            throw new RuntimeException("No se puede eleiminar dicho curso, curso no encontrado con el ID: "+ id);
        }
        cursoRepository.deleteById(id);
    }
}
