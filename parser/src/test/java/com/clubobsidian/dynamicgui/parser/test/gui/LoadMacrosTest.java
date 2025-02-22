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

package com.clubobsidian.dynamicgui.parser.test.gui;

import com.clubobsidian.dynamicgui.parser.gui.GuiToken;
import com.clubobsidian.wrappy.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadMacrosTest {

    @Test
    public void testLoadMacros() {
        File slotFolder = new File("test", "gui");
        File file = new File(slotFolder, "load-macros.yml");
        Configuration config = Configuration.load(file);
        GuiToken token = new GuiToken(config);
        List<String> loadMacros = token.getLoadMacros();
        assertEquals(1, loadMacros.size());
        assertEquals("test", loadMacros.get(0));
    }
}