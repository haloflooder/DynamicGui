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

package com.clubobsidian.dynamicgui.core.manager.dynamicgui;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.event.plugin.DynamicGuiReloadEvent;
import com.clubobsidian.trident.EventHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.util.HashMap;
import java.util.Map;

public class MiniMessageManager {

    public static MiniMessageManager instance;

    public static MiniMessageManager get() {
        if (instance == null) {
            instance = new MiniMessageManager();
        }
        return instance;
    }

    private final Map<String, String> json = new HashMap<>();
    private final MiniMessage miniMessage = MiniMessage.builder().build();
    private final GsonComponentSerializer serializer = GsonComponentSerializer.builder().build();

    private MiniMessageManager() {
        DynamicGui.get().getEventBus().registerEvents(this);
    }

    public String toJson(String data) {
        String cached = this.json.get(data);
        if (cached == null) {
            Component component = this.miniMessage.deserialize(data);
            cached = this.serializer.serialize(component);
            this.json.put(data, cached);
        }
        return cached;
    }

    @EventHandler
    public void onReload(DynamicGuiReloadEvent event) {
        this.json.clear();
    }
}