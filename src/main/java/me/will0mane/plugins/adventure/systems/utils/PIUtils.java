package me.will0mane.plugins.adventure.systems.utils;

import com.will0mane.apis.mathapi.api.MathUtils;
import me.will0mane.plugins.adventure.systems.executors.hash.HashExecutor;

@SuppressWarnings("unused")
public class PIUtils {

    public static final HashExecutor<Double, Double> toDecimal = new HashExecutor<>(aDouble ->
            MathUtils.multiplyMultiple(aDouble, Math.PI));

    public static final HashExecutor<Double, Double> toPI = new HashExecutor<>(aDouble ->
            MathUtils.divideMultiple(aDouble, Math.PI));

    private PIUtils(){}
}
