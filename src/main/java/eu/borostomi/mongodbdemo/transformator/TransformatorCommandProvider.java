package eu.borostomi.mongodbdemo.transformator;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class TransformatorCommandProvider {
    private static final Map<String, Supplier<UnitTransformator>> TRANSFORMATORS;

    static {
        final Map<String, Supplier<UnitTransformator>> transformator = new HashMap<>();
        transformator.put("g", MassTransformator::new);
        transformator.put("ml", VolumeTransformator::new);
        transformator.put("capsule", CapsuleTransformator::new);
        TRANSFORMATORS = Collections.unmodifiableMap(transformator);
    }

    public UnitTransformator getTransformator(String type) {
        Supplier<UnitTransformator> transformator = TRANSFORMATORS.get(type);
        if (transformator == null) {
            throw new RuntimeException("Invalid transformator type:" + type);
        }
        return transformator.get();
    }
}
