/*
 *    Copyright 2022 virustotalop and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clubobsidian.dynamicgui.core.function.impl.condition;

import com.udojava.evalex.AbstractLazyFunction;
import com.udojava.evalex.Expression.LazyNumber;

import java.util.List;

public class ContainsLazyFunction extends AbstractLazyFunction {

    protected ContainsLazyFunction() {
        super("STRCONTAINS", 2, true);
    }

    @Override
    public LazyNumber lazyEval(List<LazyNumber> lazyParams) {
        if (lazyParams.get(0).getString().contains(lazyParams.get(1).getString())) {
            return ConditionFunction.ONE;
        }
        return ConditionFunction.ZERO;
    }
}