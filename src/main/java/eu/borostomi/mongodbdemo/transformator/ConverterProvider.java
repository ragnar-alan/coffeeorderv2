package eu.borostomi.mongodbdemo.transformator;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class ConverterProvider {
    private static final Map<String, Supplier<UnitConverter>> TRANSFORMATORS;

    static {
        final Map<String, Supplier<UnitConverter>> transformator = new HashMap<>();
        transformator.put("g", MassConverter::new);
        transformator.put("ml", VolumeConverter::new);
        transformator.put("capsule", CapsuleConverter::new);
        TRANSFORMATORS = Collections.unmodifiableMap(transformator);
    }

    public UnitConverter getTransformator(final String type) {
        Supplier<UnitConverter> transformator = TRANSFORMATORS.get(type);
        if (transformator == null) {
            throw new RuntimeException("Invalid transformator type:" + type);
        }
        return transformator.get();
    }
}
