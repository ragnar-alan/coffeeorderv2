package eu.borostomi.mongodbdemo.transformator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CapsuleTransformator extends UnitTransformator {
    @Override
    BigDecimal convert(double from) {
        return BigDecimal.valueOf(from).setScale(2, RoundingMode.HALF_UP);
    }
}
