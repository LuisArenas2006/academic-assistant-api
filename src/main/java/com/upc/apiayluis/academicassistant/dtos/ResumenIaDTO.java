package com.upc.apiayluis.academicassistant.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResumenIaDTO {
    private Long apunteId;
    private String tituloApunte;
    private String resumenGenerado;
    private String sugerenciasEstudio;
}
