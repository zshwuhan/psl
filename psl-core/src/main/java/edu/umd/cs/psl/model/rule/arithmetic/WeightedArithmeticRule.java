/*
 * This file is part of the PSL software.
 * Copyright 2011-2015 University of Maryland
 * Copyright 2013-2015 The Regents of the University of California
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.umd.cs.psl.model.rule.arithmetic;

import java.util.HashMap;
import java.util.Map;

import edu.umd.cs.psl.model.formula.Formula;
import edu.umd.cs.psl.model.rule.WeightedRule;
import edu.umd.cs.psl.model.rule.arithmetic.expression.ArithmeticRuleExpression;
import edu.umd.cs.psl.model.rule.arithmetic.expression.SummationVariable;
import edu.umd.cs.psl.model.weight.NegativeWeight;
import edu.umd.cs.psl.model.weight.PositiveWeight;
import edu.umd.cs.psl.model.weight.Weight;

/**
 * A template for {@link WeightedGroundArithmeticRule WeightedGroundArithmeticRules}.
 * 
 * @author Stephen Bach
 */
public class WeightedArithmeticRule extends AbstractArithmeticRule implements WeightedRule {
	
	protected Weight weight;
	protected boolean squared;
	protected boolean mutable;
	
	public WeightedArithmeticRule(ArithmeticRuleExpression expression, double w, boolean squared) {
		this(expression, new HashMap<SummationVariable, Formula>(), w, squared);
	}

	public WeightedArithmeticRule(ArithmeticRuleExpression expression, Map<SummationVariable, Formula> selectStatements,
			double w, boolean squared) {
		super(expression, selectStatements);
		weight = (w >= 0.0) ? new PositiveWeight(w) : new NegativeWeight(w);
		this.squared = squared;
		mutable = true;
	}

	@Override
	public Weight getWeight() {
		return weight.duplicate();
	}

	@Override
	public void setWeight(Weight w) {
		if (!mutable)
			throw new IllegalStateException("Rule weight is not mutable.");
		
		weight = w;
	}

	@Override
	public boolean isWeightMutable() {
		return mutable;
	}

	@Override
	public void setWeightMutable(boolean mutable) {
		this.mutable = mutable;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(weight.getWeight());
		s.append(": ");
		s.append(expression);
		s.append((squared) ? " ^2" : " ^1");
		for (Map.Entry<SummationVariable, Formula> e : selects.entrySet()) {
			s.append("\n{");
			// We append the corresponding Variable, not the SummationVariable, to leave out the '+'
			s.append(e.getKey().getVariable());
			s.append(" : ");
			s.append(e.getValue());
			s.append("}");
		}
		return s.toString();
	}

}