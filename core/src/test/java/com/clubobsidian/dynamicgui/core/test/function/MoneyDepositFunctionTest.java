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

package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.MoneyDepositFunction;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyDepositFunctionTest extends FactoryTest {

    @Test
    public void testInvalidData() throws Exception {
        this.getFactory().inject();
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        Function function = new MoneyDepositFunction();
        function.setData("a");
        assertFalse(function.function(player));
    }

    @Test
    public void testNullEconomy() throws Exception {
        this.getFactory().inject().getPlugin().economy = null;
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        Function function = new MoneyDepositFunction();
        function.setData("10");
        assertFalse(function.function(player));
    }

    @Test
    public void testValidDeposit() throws Exception {
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        this.getFactory().inject();
        Function function = new MoneyDepositFunction();
        function.setData("10");
        assertTrue(function.function(player));
    }
}
