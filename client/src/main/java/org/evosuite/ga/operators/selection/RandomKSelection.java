/**
 * Copyright (C) 2010-2018 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 * <p>
 * This file is part of EvoSuite.
 * <p>
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 * <p>
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.ga.operators.selection;

import org.evosuite.ga.Chromosome;
import org.evosuite.runtime.Randomness;

import java.util.List;

/**
 * Select random individual
 */
public class RandomKSelection<T extends Chromosome<T>> extends SelectionFunction<T> {

    private static final long serialVersionUID = -2459623722712044154L;

    public RandomKSelection() {
    }

    public RandomKSelection(RandomKSelection<?> other) {
        // empty copy constructor
    }

    @Override
    public int getIndex(List<T> population) {
        double r = Randomness.nextDouble();

        return (int) (r * population.size());
    }
}
