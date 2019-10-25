/*
   Copyright 2019 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.function.impl.condition;

import java.math.BigDecimal;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.udojava.evalex.Expression;
import com.udojava.evalex.Expression.LazyNumber;

public class ConditionFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3905599553938205838L;

	public ConditionFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		try
		{
			Expression expr = new Expression(this.getData());
			expr.addLazyFunction(new EqualLazyFunction());
			
			if(!expr.isBoolean())
				return false;

			return expr.eval().intValue() == 1;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public static LazyNumber ZERO = new LazyNumber() 
	{
		public BigDecimal eval() 
		{
			return BigDecimal.ZERO;
		}
		public String getString() 
		{
			return "0";
		}
	};

	public static LazyNumber ONE = new LazyNumber() 
	{
		public BigDecimal eval() 
		{
			return BigDecimal.ONE;
		}         
		public String getString() 
		{
			return null;
		}
	};  
}