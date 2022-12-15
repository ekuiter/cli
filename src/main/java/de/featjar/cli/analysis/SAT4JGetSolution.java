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

import de.featjar.base.cli.ArgumentParser;
import de.featjar.base.data.Computation;
import de.featjar.formula.analysis.Analysis;
import de.featjar.formula.analysis.bool.BooleanClauseList;
import de.featjar.formula.analysis.bool.BooleanSolution;
import de.featjar.formula.analysis.bool.ToBooleanClauseList;
import de.featjar.formula.analysis.sat4j.SAT4JGetSolutionAnalysis;
import de.featjar.formula.transformer.ToCNF;

import java.util.List;


public class SAT4JGetSolution extends AnalysisCommand<BooleanSolution> {
    @Override
    public String getDescription() {
        return "Queries SAT4J for a solution of a given formula, if any";
    }

    @Override
    public List<ArgumentParser.Option<?>> getOptions() {
        return List.of(INPUT_OPTION, ASSIGNMENT_OPTION, CLAUSES_OPTION, TIMEOUT_OPTION);
    }

    @Override
    protected Analysis<BooleanClauseList, BooleanSolution> newAnalysis() {
        return Computation.of(formula)
                .then(ToCNF::new)
                .then(ToBooleanClauseList::new)
                .then(clauseListComputation ->
                        new SAT4JGetSolutionAnalysis(clauseListComputation)
                                .setTimeout(TIMEOUT_OPTION.parseFrom(argumentParser))
                                .setAssumedValueAssignment(Computation.of(ASSIGNMENT_OPTION.parseFrom(argumentParser)))
                                .setAssumedValueClauseList(Computation.of(CLAUSES_OPTION.parseFrom(argumentParser))));
    }

    @Override
    public String serializeResult(BooleanSolution result) {
        return result.print();
    }
}
