/*
 * Copyright (C) 2022 Elias Kuiter
 *
 * This file is part of cli.
 *
 * cli is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License,
 * or (at your option) any later version.
 *
 * cli is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with cli. If not, see <https://www.gnu.org/licenses/>.
 *
 * See <https://github.com/FeatureIDE/FeatJAR-cli> for further information.
 */
package de.featjar.cli.analysis;

import de.featjar.formula.analysis.sat4j.CoreDeadAnalysis;
import de.featjar.formula.structure.map.TermMap;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CoreDeadAlgorithm extends AlgorithmWrapper<CoreDeadAnalysis> {

    @Override
    protected CoreDeadAnalysis newAlgorithm() {
        return new CoreDeadAnalysis();
    }

    @Override
    public Object parseResult(Object result, Object arg) {
        SortedIntegerList sortedIntegerList = (SortedIntegerList) result;
        TermMap termMap = (TermMap) arg;
        return String.format(
                "core:\n%s\ndead:\n%s\n",
                Arrays.stream(sortedIntegerList.getPositives().getIntegers())
                        .mapToObj(l -> termMap.getVariable(l).get().getName())
                        .collect(Collectors.joining("\n")),
                Arrays.stream(sortedIntegerList.getNegatives().getIntegers())
                        .mapToObj(l -> termMap.getVariable(-l).get().getName())
                        .collect(Collectors.joining("\n")));
    }

    @Override
    public String getName() {
        return "core-dead";
    }

    @Override
    public String getHelp() {
        final StringBuilder helpBuilder = new StringBuilder();
        helpBuilder.append("\t");
        helpBuilder.append(getName());
        helpBuilder.append(": reports the feature model's core and dead features\n");
        return helpBuilder.toString();
    }
}
