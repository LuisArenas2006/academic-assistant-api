package com.upc.apiayluis.academicassistant.repositories;

import com.upc.apiayluis.academicassistant.entities.Apunte;
import com.upc.apiayluis.academicassistant.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApunteRepository extends JpaRepository<Apunte, Long> {
    List<Apunte> findByCursoId(Long cursoId);

    List<Apunte> findByTituloContainingIgnoreCase(String titulo);

}
