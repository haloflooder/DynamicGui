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

package com.clubobsidian.dynamicgui.core.test.builder;

import com.clubobsidian.dynamicgui.core.builder.EnchantmentBuilder;
import com.clubobsidian.dynamicgui.core.enchantment.EnchantmentWrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnchantmentBuilderTest {

    @Test
    public void testName() {
        String enchantmentName = "test";
        EnchantmentWrapper enchantment = new EnchantmentBuilder()
                .setEnchantment(enchantmentName)
                .build();
        assertEquals(enchantmentName, enchantment.getEnchant());
    }

    @Test
    public void testLevel() {
        int enchantmentLevel = 1;
        EnchantmentWrapper enchantment = new EnchantmentBuilder()
                .setLevel(enchantmentLevel)
                .build();
        assertEquals(enchantmentLevel, enchantment.getLevel());
    }
}