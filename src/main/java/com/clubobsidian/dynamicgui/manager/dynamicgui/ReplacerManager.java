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
package com.clubobsidian.dynamicgui.manager.dynamicgui;

import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.registry.replacer.ReplacerRegistry;

public class ReplacerManager {

    private static ReplacerManager instance;

    private List<ReplacerRegistry> registries;

    private ReplacerManager() {
        this.registries = new ArrayList<>();
    }

    public static ReplacerManager get() {
        if (instance == null) {
            instance = new ReplacerManager();
        }
        return instance;
    }

    public String replace(String text, PlayerWrapper<?> playerWrapper) {
        String newText = text;
        for (ReplacerRegistry registry : this.registries) {
            newText = registry.replace(playerWrapper, newText);
        }

        return newText;
    }

    public void registerReplacerRegistry(ReplacerRegistry replacerRegistry) {
        this.registries.add(replacerRegistry);
    }
}