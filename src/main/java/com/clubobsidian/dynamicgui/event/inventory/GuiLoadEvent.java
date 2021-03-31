/*
 *    Copyright 2021 Club Obsidian and contributors.
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
package com.clubobsidian.dynamicgui.event.inventory;


import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.trident.Cancelable;
import com.clubobsidian.trident.Event;

public class GuiLoadEvent extends Event implements Cancelable {

    private Gui gui;
    private PlayerWrapper<?> playerWrapper;
    private boolean cancelled;

    public GuiLoadEvent(Gui gui, PlayerWrapper<?> playerWrapper) {
        this.gui = gui;
        this.playerWrapper = playerWrapper;
        this.cancelled = false;
    }

    public Gui gui() {
        return this.gui;
    }

    public PlayerWrapper<?> getPlayerWrapper() {
        return this.playerWrapper;
    }

    @Override
    public boolean isCanceled() {
        return this.cancelled;
    }

    @Override
    public void setCanceled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}