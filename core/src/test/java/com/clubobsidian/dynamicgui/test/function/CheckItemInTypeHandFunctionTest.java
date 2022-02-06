package com.clubobsidian.dynamicgui.test.function;

import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.function.impl.CheckItemTypeInHandFunction;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.test.mock.MockPlayerWrapper;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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