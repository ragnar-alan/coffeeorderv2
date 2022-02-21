package eu.borostomi.mongodbdemo.transformator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CapsuleConverter extends UnitConverter {
    @Override
    BigDecimal convert(final double from) {
        return BigDecimal.valueOf(from).setScale(2, RoundingMode.HALF_UP);
    }
}
