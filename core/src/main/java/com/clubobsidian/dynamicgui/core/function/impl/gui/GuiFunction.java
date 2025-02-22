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

import java.util.concurrent.ExecutionException;

public class GuiFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = 848178368629667482L;

    public GuiFunction() {
        super("gui", true);
    }

    @Override
    public boolean function(final PlayerWrapper<?> playerWrapper) {
        final String gui = this.getData();
        if (gui == null) {
            return false;
        }
        if (!GuiManager.get().isGuiLoaded(gui)) {
            return false;
        }

        Gui back = null;
        FunctionOwner owner = this.getOwner();
        //Find root gui
        if (owner instanceof Slot) {
            Slot slot = (Slot) owner;
            back = slot.getOwner();
        } else if (owner instanceof Gui) {
            back = (Gui) owner;
        }

        //Make it so the gui doesn't close
        if (owner instanceof Slot) {
            Slot slot = (Slot) owner;
            slot.setClose(false);
        }
        back.setClose(false);
        try {
            return GuiManager.get().openGui(playerWrapper, gui, back).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }
}