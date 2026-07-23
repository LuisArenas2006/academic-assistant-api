package com.upc.apiayluis.academicassistant.services;

import com.upc.apiayluis.academicassistant.dtos.ResumenIaDTO;
import com.upc.apiayluis.academicassistant.entities.Apunte;
import com.upc.apiayluis.academicassistant.repositories.ApunteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class AIService {

    @Autowired
    private ApunteRepository apunteRepository;

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional(readOnly = true)
    public ResumenIaDTO generarResumenApunte(Long apunteId) {
        // 1. Buscar el apunte en la base de datos
        Apunte apunte = apunteRepository.findById(apunteId)
                .orElseThrow(() -> new RuntimeException("Apunte no encontrado con ID: " + apunteId));

        // 2. Construir el prompt para la IA
        String prompt = "Eres un asistente académico experto. Analiza el siguiente apunte del curso '"
                + apunte.getCurso().getNombre() + "' titulado '" + apunte.getTitulo() + "'.\n\n"
                + "Contenido del apunte:\n" + apunte.getContenido() + "\n\n"
                + "Por favor genera un resumen claro de los puntos clave y sugiere 2 preguntas de estudio.";

        // 3. Preparar la URL y consumo del API de Gemini
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Estructura del Body requerida por Gemini API
        Map<String, Object> textPart = Map.of("text", prompt);
        Map<String, Object> partsMap = Map.of("parts", List.of(textPart));
        Map<String, Object> bodyMap = Map.of("contents", List.of(partsMap));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(bodyMap, headers);

        try {
            // 4. Llamada HTTP POST
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            // Extraer la respuesta de la estructura JSON devuelta por Gemini
            List candidates = (List) response.getBody().get("candidates");
            Map candidate = (Map) candidates.get(0);
            Map content = (Map) candidate.get("content");
            List parts = (List) content.get("parts");
            Map part = (Map) parts.get(0);
            String respuestaIa = (String) part.get("text");

            return new ResumenIaDTO(
                    apunte.getId(),
                    apunte.getTitulo(),
                    respuestaIa,
                    "Generado exitosamente con Gemini AI"
            );

        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con la IA: " + e.getMessage());
        }
    }
}