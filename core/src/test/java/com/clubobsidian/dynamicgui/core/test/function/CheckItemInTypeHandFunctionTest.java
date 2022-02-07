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

package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.CheckItemTypeInHandFunction;
import com.clubobsidian.dynamicgui.core.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckItemInTypeHandFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void testNull() {
        String material = "stone";
        Function function = new CheckItemTypeInHandFunction();
        ItemStackWrapper<?> hand = this.factory.createItemStack(material);
        MockPlayerWrapper player = this.factory.createPlayer();
        player.setItemInHand(hand);
        assertFalse(function.function(player));
    }

    @Test
    public void testOneType() {
        String material = "stone";
        Function function = new CheckItemTypeInHandFunction();
        function.setData(material);
        ItemStackWrapper<?> hand = this.factory.createItemStack(material);
        MockPlayerWrapper player = this.factory.createPlayer();
        player.setItemInHand(hand);
        assertTrue(function.function(player));
    }

    @Test
    public void testTwoTypes() {
        String handMaterial = "stone";
        String functionMaterials = "dirt,stone";
        Function function = new CheckItemTypeInHandFunction();
        function.setData(functionMaterials);
        ItemStackWrapper<?> hand = this.factory.createItemStack(handMaterial);
        MockPlayerWrapper player = this.factory.createPlayer();
        player.setItemInHand(hand);
        assertTrue(function.function(player));
    }
}