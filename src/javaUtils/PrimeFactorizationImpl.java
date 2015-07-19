package javaUtils;

import java.util.Map;

//TODO add implementation

/**
 * implementation of @link PrimeFactorization
 */
public class PrimeFactorizationImpl implements PrimeFactorization {
    Map<Long, Integer> powers;

    public PrimeFactorizationImpl(long n) {
        powers = IntegerUtils.getPrimeFactorization(n);
    }

    @Override
    public int getMinimumExponent() {

        return 0;
    }

    @Override
    public int getAllPrimeFactors() {
        return 0;
    }

    @Override
    public int getNumberOfPrimeFactors() {
        return 0;
    }

    @Override
    public int getNthPrimeFactor() {
        return 0;
    }

    @Override
    public int getExponent() {
        return 0;
    }
}
