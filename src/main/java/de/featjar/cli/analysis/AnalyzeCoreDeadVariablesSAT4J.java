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

import de.featjar.base.computation.IComputation;
import de.featjar.formula.analysis.VariableMap;
import de.featjar.formula.analysis.bool.BooleanAssignment;
import de.featjar.formula.analysis.bool.BooleanClauseList;
import de.featjar.formula.analysis.value.*;

import static de.featjar.base.computation.Computations.async;


public class AnalyzeCoreDeadVariablesSAT4J extends SAT4JAnalysisCommand<ValueAssignment, BooleanAssignment> {
    @Override
    public String getDescription() {
        return "Queries SAT4J for all core and dead variables of a given formula, if any";
    }

    @Override
    public de.featjar.formula.analysis.sat4j.AnalyzeCoreDeadVariablesSAT4J newAnalysis(IComputation<BooleanClauseList> clauseList) {
        return new de.featjar.formula.analysis.sat4j.AnalyzeCoreDeadVariablesSAT4J(clauseList);
    }

    @Override
    public IComputation<ValueAssignment> interpret(IComputation<BooleanAssignment> booleanAssignment, IComputation<VariableMap> variableMap) {
        return async(booleanAssignment, variableMap)
                .map(ComputeValueRepresentation.OfAssignment::new);
    }

    @Override
    public String serializeResult(ValueAssignment valueAssignment) {
        return valueAssignment.print();
    }

}
