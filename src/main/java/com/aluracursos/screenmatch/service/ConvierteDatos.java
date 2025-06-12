package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();   //ObjectMapper se utiliza para convertir entre objetos Java y JSON (serialización y deserialización).

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);  // Esta línea convierte un JSON (que está en forma de cadena de texto) en un objeto Java de la clase especificada.
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
