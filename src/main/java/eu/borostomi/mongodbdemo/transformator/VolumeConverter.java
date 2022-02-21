package eu.borostomi.mongodbdemo.transformator;

import com.digidemic.unitof.UnitOf;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VolumeConverter extends UnitConverter {
    @Override
    public BigDecimal convert(final double valueFrom) {
        return BigDecimal.valueOf(
                new UnitOf.Volume()
                        .fromMilliliters(valueFrom)
                        .toFluidOuncesUS())
                .setScale(2, RoundingMode.HALF_UP);
    }
}
