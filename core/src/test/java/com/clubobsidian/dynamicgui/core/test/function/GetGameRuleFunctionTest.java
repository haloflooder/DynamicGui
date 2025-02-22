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

import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.GetGameRuleFunction;
import com.clubobsidian.dynamicgui.core.test.mock.plugin.MockPlatform;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import com.clubobsidian.dynamicgui.core.world.WorldWrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetGameRuleFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        Function function = new GetGameRuleFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void noCommaTest() throws Exception {
        Function function = new GetGameRuleFunction();
        function.setData("");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void lengthNotThreeTest() throws Exception {
        Function function = new GetGameRuleFunction();
        function.setData("a,b");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void worldNullTest() throws Exception {
        this.getFactory().inject().getPlatform();
        Function function = new GetGameRuleFunction();
        function.setData("test,key,value");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void hasGameRuleTest() throws Exception {
        String worldName = "test";
        MockPlatform platform = this.getFactory().inject().getPlatform();
        WorldWrapper<?> world = this.getFactory().createWorld(worldName);
        platform.addWorld(world);
        world.setGameRule("key", "value");
        Function function = new GetGameRuleFunction();
        function.setData("test,key,value");
        assertTrue(function.function(this.getFactory().createPlayer()));
    }
}