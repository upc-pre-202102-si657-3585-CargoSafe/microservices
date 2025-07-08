package com.dynoware.cargosafe.iamservice.kafka.config;

import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class ConditionalDeserializer implements Deserializer<Object> {

    // Deserializadores para manejar String y objetos complejos
    private final ErrorHandlingDeserializer<String> stringDeserializer =
            new ErrorHandlingDeserializer<>(new StringDeserializer());

    private final ErrorHandlingDeserializer<Object> jsonDeserializer =
            new ErrorHandlingDeserializer<>(new JsonDeserializer<>(Object.class));

    @Override
    public Object deserialize(String topic, byte[] data) {
        // Primero intentamos deserializar el mensaje como un String
        Object deserializedValue = stringDeserializer.deserialize(topic, data);

        if (deserializedValue == null) {
            // Si falló la deserialización como String, intentamos deserializarlo como JSON
            deserializedValue = jsonDeserializer.deserialize(topic, data);
        }

        return deserializedValue;
    }

    @Override
    public void close() {
        // Método de cierre (si es necesario)
    }
}