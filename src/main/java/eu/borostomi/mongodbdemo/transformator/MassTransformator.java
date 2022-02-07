package eu.borostomi.mongodbdemo.transformator;

import com.digidemic.unitof.UnitOf;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MassTransformator extends UnitTransformator {

    @Override
    public BigDecimal convert(double valueFrom) {
        return BigDecimal.valueOf(new UnitOf.Mass().fromGrams(valueFrom).toOuncesUS()).setScale(2, RoundingMode.HALF_UP);
    }
}
