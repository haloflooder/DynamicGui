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
import com.clubobsidian.dynamicgui.core.function.impl.SetDurabilityFunction;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetDurabilityFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        Function function = new SetDurabilityFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void nonSlotOwnerTest() throws Exception {
        Function function = new SetDurabilityFunction();
        function.setOwner(this.getFactory().createGui("test"));
        function.setData("1");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void invalidDataTest() throws Exception {
        this.getFactory().inject();
        MockPlayerWrapper player = this.getFactory().createPlayer();
        Slot slot = this.getFactory().createSlot();
        slot.buildItemStack(player);
        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        Gui gui = this.getFactory().createGui("test", slots);
        gui.buildInventory(player);
        slot.setOwner(gui);
        Function function = new SetDurabilityFunction();
        function.setOwner(slot);
        function.setData("a");
        assertFalse(function.function(player));
    }

    @Test
    public void dataTest() throws Exception {
        this.getFactory().inject();
        MockPlayerWrapper player = this.getFactory().createPlayer();
        int newDurability = 2;
        Slot slot = this.getFactory().createSlot();
        slot.buildItemStack(player);
        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        Gui gui = this.getFactory().createGui("test", slots);
        InventoryWrapper<?> inventory = gui.buildInventory(player);
        slot.setOwner(gui);
        Function function = new SetDurabilityFunction();
        function.setOwner(slot);
        function.setData("" + newDurability);
        assertTrue(function.function(player));
        assertEquals(newDurability, inventory.getItem(slot.getIndex()).getDurability());
    }
}