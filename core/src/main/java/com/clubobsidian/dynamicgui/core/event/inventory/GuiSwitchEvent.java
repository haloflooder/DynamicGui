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

package com.clubobsidian.dynamicgui.core.event.inventory;


import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.trident.Event;

public class GuiSwitchEvent extends Event {

    private final Gui switchFrom;
    private final Gui switchTo;
    private final PlayerWrapper<?> playerWrapper;

    public GuiSwitchEvent(Gui switchFrom, Gui switchTo, PlayerWrapper<?> playerWrapper) {
        this.switchFrom = switchFrom;
        this.switchTo = switchTo;
        this.playerWrapper = playerWrapper;
    }

    public Gui gui() {
        return this.switchFrom;
    }

    public PlayerWrapper<?> getPlayerWrapper() {
        return this.playerWrapper;
    }

    public Gui getSwitchFrom() {
        return this.switchFrom;
    }

    public Gui getSwitchTo() {
        return this.switchTo;
    }
}