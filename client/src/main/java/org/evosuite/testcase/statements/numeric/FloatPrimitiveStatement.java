/*
 * Copyright (C) 2010-2018 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package org.evosuite.testcase.statements.numeric;

import org.evosuite.Properties;
import org.evosuite.seeding.ConstantPool;
import org.evosuite.seeding.ConstantPoolManager;
import org.evosuite.testcase.TestCase;
import org.evosuite.runtime.Randomness;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <p>
 * FloatPrimitiveStatement class.
 * </p>
 *
 * @author Gordon Fraser
 */
public class FloatPrimitiveStatement extends NumericalPrimitiveStatement<Float> {

    private static final long serialVersionUID = 708022695544843828L;

    /**
     * <p>
     * Constructor for FloatPrimitiveStatement.
     * </p>
     *
     * @param tc    a {@link org.evosuite.testcase.TestCase} object.
     * @param value a {@link java.lang.Float} object.
     */
    public FloatPrimitiveStatement(TestCase tc, Float value) {
        super(tc, float.class, value);
    }

    /**
     * <p>
     * Constructor for FloatPrimitiveStatement.
     * </p>
     *
     * @param tc a {@link org.evosuite.testcase.TestCase} object.
     */
    public FloatPrimitiveStatement(TestCase tc) {
        super(tc, float.class, 0.0F);
    }

    /* (non-Javadoc)
     * @see org.evosuite.testcase.PrimitiveStatement#zero()
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public void zero() {
        value = (float) 0.0;
    }

    /* (non-Javadoc)
     * @see org.evosuite.testcase.PrimitiveStatement#delta()
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public void delta() {
        double P = Randomness.nextDouble();
        if (P < 1d / 3d) {
            value += (float) Randomness.nextGaussian() * Properties.MAX_DELTA;
        } else if (P < 2d / 3d) {
            value += (float) Randomness.nextGaussian();
        } else {
            int precision = Randomness.nextInt(7);
            chopPrecision(precision);
        }
    }

    private void chopPrecision(int precision) {
        if (value.isNaN() || value.isInfinite())
            return;

        BigDecimal bd = new BigDecimal(value).setScale(precision, RoundingMode.HALF_EVEN);
        this.value = bd.floatValue();
    }

    /* (non-Javadoc)
     * @see org.evosuite.testcase.PrimitiveStatement#increment(java.lang.Object)
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public void increment(long delta) {
        value = value + delta;
    }

    /* (non-Javadoc)
     * @see org.evosuite.testcase.PrimitiveStatement#increment(java.lang.Object)
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public void increment(double delta) {
        value = value + (float) delta;
    }

    /* (non-Javadoc)
     * @see org.evosuite.testcase.PrimitiveStatement#randomize()
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public void randomize() {
        if (Randomness.nextDouble() >= Properties.PRIMITIVE_POOL) {
            value = (float) (Randomness.nextGaussian() * Properties.MAX_INT);
            int precision = Randomness.nextInt(7);
            chopPrecision(precision);
        } else {
            ConstantPool constantPool = ConstantPoolManager.getInstance().getConstantPool();
            value = constantPool.getRandomFloat();
        }
    }

    /* (non-Javadoc)
     * @see org.evosuite.testcase.PrimitiveStatement#increment()
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public void increment() {
        increment(1.0F);
    }

    /* (non-Javadoc)
     * @see org.evosuite.testcase.NumericalPrimitiveStatement#setMid(java.lang.Object, java.lang.Object)
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMid(Float min, Float max) {
        value = min + ((max - min) / 2);
    }

    /* (non-Javadoc)
     * @see org.evosuite.testcase.NumericalPrimitiveStatement#decrement()
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrement() {
        increment(-1);
    }

    /* (non-Javadoc)
     * @see org.evosuite.testcase.NumericalPrimitiveStatement#isPositive()
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPositive() {
        return value > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void negate() {
        value = -value;
    }
}
