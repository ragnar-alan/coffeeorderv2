package eu.borostomi.mongodbdemo.transformator;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class UnitConverter {
    private static final Map<String, String> UNITS;

    static {
        final Map<String, String> units = new HashMap<>();
        units.put("g", "oz");
        units.put("ml", "fl oz");
        units.put("capsule", "capsule");
        UNITS = Collections.unmodifiableMap(units);
    }

    abstract BigDecimal convert(double from);

    String convertUnit(final String from) {
        String result = UNITS.get(from);
        if (result == null) {
            throw new RuntimeException("Invalid unit: " + from);
        }
        return result;
    }
}
