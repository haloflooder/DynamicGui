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

package com.clubobsidian.dynamicgui.core.manager.dynamicgui.cooldown;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.platform.Platform;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class CooldownManager {

    private static CooldownManager instance;

    public static CooldownManager get() {
        if (instance == null) {
            instance = new CooldownManager();
        }

        return instance;
    }

    private final Map<UUID, Map<String, Cooldown>> cooldowns = new ConcurrentHashMap<>();
    private final AtomicBoolean updateConfig = new AtomicBoolean(false);
    private final Configuration cooldownConfig;

    private CooldownManager() {
        this.cooldownConfig = this.loadConfig();
        this.scheduleCooldownUpdate();
        this.scheduleConfigUpdate();
    }

    private Configuration loadConfig() {
        File dataFolder = DynamicGui.get().getPlugin().getDataFolder();
        File cooldownsFile = new File(dataFolder, "cooldowns.yml");
        Configuration config = Configuration.load(cooldownsFile);
        for (String uuidStr : config.getKeys()) {
            ConfigurationSection section = config.getConfigurationSection(uuidStr);
            Map<String, Cooldown> cooldownMap = new ConcurrentHashMap<>();
            for (String cooldownName : section.getKeys()) {
                long time = section.getLong(cooldownName + ".time");
                long cooldown = section.getLong(cooldownName + ".cooldown");
                Cooldown cooldownObj = new Cooldown(cooldownName, time, cooldown);
                if (this.getRemainingCooldown(cooldownObj) != -1L) {
                    cooldownMap.put(cooldownName, cooldownObj);
                } else {
                    section.set(cooldownName, null);
                }
            }
            if (section.isEmpty()) {
                config.set(uuidStr, null);
            }
            if (cooldownMap.size() > 0) {
                UUID uuid = UUID.fromString(uuidStr);
                this.cooldowns.put(uuid, cooldownMap);
            }
        }
        config.save();
        return config;
    }

    public long getRemainingCooldown(PlayerWrapper<?> playerWrapper, String name) {
        return this.getRemainingCooldown(playerWrapper.getUniqueId(), name);
    }

    public long getRemainingCooldown(UUID uuid, String name) {
        Map<String, Cooldown> cooldownMap = this.cooldowns.get(uuid);
        if (cooldownMap == null) {
            return -1L;
        }

        Cooldown cooldown = cooldownMap.get(name);
        if (cooldown == null) {
            return -1L;
        }

        return this.getRemainingCooldown(cooldown);
    }

    public long getRemainingCooldown(Cooldown cooldown) {
        long currentTime = System.currentTimeMillis();
        long cooldownTime = cooldown.getTime();
        long cooldownAmount = cooldown.getCooldown();

        if ((currentTime - cooldownTime) >= cooldownAmount) {
            return -1L;
        } else {
            return cooldownAmount - (currentTime - cooldownTime);
        }
    }

    public boolean isOnCooldown(PlayerWrapper<?> playerWrapper, String name) {
        return this.isOnCooldown(playerWrapper.getUniqueId(), name);
    }

    public boolean isOnCooldown(UUID uuid, String name) {
        return this.getRemainingCooldown(uuid, name) != -1L;
    }

    public List<Cooldown> getCooldowns(PlayerWrapper<?> playerWrapper) {
        UUID uuid = playerWrapper.getUniqueId();
        return this.getCooldowns(uuid);
    }

    public List<Cooldown> getCooldowns(UUID uuid) {
        Map<String, Cooldown> cooldowns = this.cooldowns.get(uuid);
        if (cooldowns == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(cooldowns.values());
    }

    public Cooldown createCooldown(PlayerWrapper<?> playerWrapper, String name, long cooldownTime) {
        UUID uuid = playerWrapper.getUniqueId();
        return this.createCooldown(uuid, name, cooldownTime);
    }

    public Cooldown createCooldown(UUID uuid, String name, long cooldownTime) {
        long cooldownRemaining = this.getRemainingCooldown(uuid, name);
        if (cooldownRemaining == -1L) {
            long currentTime = System.currentTimeMillis();
            Cooldown cooldown = new Cooldown(name, currentTime, cooldownTime);
            Map<String, Cooldown> cooldownMap = this.cooldowns.get(uuid);
            if (cooldownMap == null) {
                cooldownMap = new ConcurrentHashMap<>();
                this.cooldowns.put(uuid, cooldownMap);
            }
            this.updateConfig.set(true);
            cooldownMap.put(name, cooldown);
            return cooldown;
        }
        return null;
    }

    public boolean removeCooldown(PlayerWrapper<?> playerWrapper, String name) {
        return this.removeCooldown(playerWrapper.getUniqueId(), name);
    }

    public boolean removeCooldown(UUID uuid, String name) {
        Map<String, Cooldown> cooldownMap = this.cooldowns.get(uuid);
        if (cooldownMap == null) {
            return false;
        }

        boolean removed = cooldownMap.remove(name) != null;
        if (removed) {
            this.updateConfig.set(true);
        }
        return removed;
    }

    public void shutdown() {
        this.updateAndSaveConfig();
    }

    private void updateAndSaveConfig() {
        Iterator<Entry<UUID, Map<String, Cooldown>>> it = this.cooldowns.entrySet().iterator();
        while (it.hasNext()) {
            Entry<UUID, Map<String, Cooldown>> next = it.next();
            UUID uuid = next.getKey();
            String uuidStr = uuid.toString();
            Map<String, Cooldown> cooldownMap = next.getValue();
            cooldownMap.forEach((cooldownName, cooldownObj) -> {
                long time = cooldownObj.getTime();
                long cooldown = cooldownObj.getCooldown();
                ConfigurationSection cooldownSection = this.cooldownConfig.getConfigurationSection(uuidStr + "." + cooldownName);
                cooldownSection.set("time", time);
                cooldownSection.set("cooldown", cooldown);
            });
        }

        this.cooldownConfig.save();
    }

    private void updateCache() {
        Iterator<Entry<UUID, Map<String, Cooldown>>> it = this.cooldowns.entrySet().iterator();
        boolean modified = false;
        while (it.hasNext()) {
            Entry<UUID, Map<String, Cooldown>> next = it.next();
            UUID uuid = next.getKey();
            String uuidStr = uuid.toString();
            Map<String, Cooldown> cooldownMap = next.getValue();
            Iterator<Entry<String, Cooldown>> cooldownIt = cooldownMap.entrySet().iterator();
            while (cooldownIt.hasNext()) {
                Entry<String, Cooldown> cooldownNext = cooldownIt.next();
                String cooldownName = cooldownNext.getKey();
                Cooldown cooldown = cooldownNext.getValue();
                long cooldownRemaining = this.getRemainingCooldown(cooldown);
                if (cooldownRemaining == -1L) {
                    cooldownIt.remove();
                    this.cooldownConfig.set(uuidStr + "." + cooldownName, null);
                    modified = true;
                }
            }
        }
        if (modified) {
            this.updateConfig.set(true);
        }
    }

    private void scheduleCooldownUpdate() {
        DynamicGui dynamicGui = DynamicGui.get();
        Platform server = dynamicGui.getPlatform();
        server.getScheduler().scheduleSyncRepeatingTask(() -> {
            this.updateCache();
        }, 1L, 1L);
    }

    private void scheduleConfigUpdate() {
        DynamicGui dynamicGui = DynamicGui.get();
        Platform server = dynamicGui.getPlatform();
        server.getScheduler().scheduleAsyncRepeatingTask(() -> {
            if (this.updateConfig.get()) {
                this.updateConfig.set(false);
                this.updateAndSaveConfig();
            }
        }, 1L, 1L);
    }
}