package com.dynoware.cargosafe.profileservice.kafka.config;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.DeserializationException;

public class ConditionalDeserializer implements Deserializer<Object> {

    private final LongDeserializer longDeserializer = new LongDeserializer();
    private final JsonDeserializer<Object> objectDeserializer = new JsonDeserializer<>(Object.class);

    @Override
    public Object deserialize(String topic, byte[] data) {
        try {
            // Intentamos deserializar como Long
            try {
                return longDeserializer.deserialize(topic, data);
            } catch (DeserializationException e) {
                // Si no es Long, intentamos deserializar como Object
                return objectDeserializer.deserialize(topic, data);
            }
        } catch (Exception e) {
            // Manejo de la excepción correctamente
            throw new DeserializationException("Error deserializando mensaje", data, false, e);
        }
    }
}
