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
import com.clubobsidian.dynamicgui.core.function.impl.PlayerCommandFunction;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerCommandFunctionTest extends FactoryTest {

    @Test
    public void nullDataTest() throws Exception {
        Function function = new PlayerCommandFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void validTest() throws Exception {
        String cmd = "test";
        String slashCmd = "/" + cmd;
        Function function = new PlayerCommandFunction();
        function.setData(cmd);
        MockPlayerWrapper player = this.getFactory().createPlayer();
        assertTrue(function.function(player));
        assertTrue(player.getOutgoingChat().size() == 1);
        assertEquals(slashCmd, player.getOutgoingChat().get(0));
    }
}