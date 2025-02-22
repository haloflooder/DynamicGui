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

package com.clubobsidian.dynamicgui.core.function.impl.gui;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;

public class SetBackFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = 4999698612673673935L;

    public SetBackFunction() {
        super("setback");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        Gui gui = null;
        FunctionOwner owner = this.getOwner();
        if (owner instanceof Slot) {
            Slot slot = (Slot) owner;
            gui = slot.getOwner();
        } else if (owner instanceof Gui) {
            gui = (Gui) owner;
        }

        if (gui == null) {
            return false;
        } else if (this.getData() == null) {
            return false;
        }

        Gui backGui = GuiManager.get().getGui(this.getData());
        if (backGui == null) {
            return false;
        }
        gui.setBack(backGui);
        return true;
    }
}