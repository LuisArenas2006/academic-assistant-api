package com.upc.apiayluis.academicassistant.services;

import com.upc.apiayluis.academicassistant.dtos.ApunteDTO;
import com.upc.apiayluis.academicassistant.entities.Apunte;
import com.upc.apiayluis.academicassistant.entities.Curso;
import com.upc.apiayluis.academicassistant.repositories.ApunteRepository;
import com.upc.apiayluis.academicassistant.repositories.CursoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApunteService {
    @Autowired
    private ApunteRepository apunteRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ApunteDTO saveApunte(ApunteDTO apunteDTO) {
        Curso curso = cursoRepository.findById(apunteDTO.getCursoId())
                .orElseThrow(()-> new RuntimeException("El curso con ID "+apunteDTO.getCursoId()+" no existe"));

        Apunte apunte = modelMapper.map(apunteDTO, Apunte.class);
        apunte.setCurso(curso);
        Apunte savedApunte = apunteRepository.save(apunte);
        return convertToDTO(savedApunte);
    }
    private ApunteDTO convertToDTO(Apunte apunte) {
        ApunteDTO dto = modelMapper.map(apunte, ApunteDTO.class);
        if (apunte.getCurso() != null) {
            dto.setCursoId(apunte.getCurso().getId());
            dto.setCursoNombre(apunte.getCurso().getNombre());
        }
        return dto;
    }

    public List<ApunteDTO> listarPorCurso(Long cursoId) {
        List<Apunte> apuntes = apunteRepository.findByCursoId(cursoId);
        return apuntes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
