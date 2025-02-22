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

package com.clubobsidian.dynamicgui.core.test.mock.entity.player;

import com.clubobsidian.dynamicgui.core.effect.ParticleWrapper;
import com.clubobsidian.dynamicgui.core.effect.SoundWrapper;
import com.clubobsidian.dynamicgui.core.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.core.world.LocationWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class MockPlayer {

    private final String name;
    private final UUID uuid;
    private final List<String> incomingChat = new ArrayList<>();
    private final List<String> outgoingChat = new ArrayList<>();
    private final Collection<String> permissions = new HashSet<>();
    private ItemStackWrapper<?> hand;
    private int level = 0;
    private LocationWrapper<?> location;
    private final List<ParticleWrapper.ParticleData> particles = new ArrayList<>();
    private final List<SoundWrapper.SoundData> sounds = new ArrayList<>();
    private boolean online = true;

    public MockPlayer(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public MockPlayer() {
        this("test", UUID.randomUUID());
    }

    public String getName() {
        return this.name;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public void chat(String message) {
        this.outgoingChat.add(message);
    }

    public void sendMessage(String message) {
        this.incomingChat.add(message);
    }

    public List<String> getIncomingChat() {
        return this.incomingChat;
    }

    public List<String> getOutgoingChat() {
        return this.outgoingChat;
    }

    public boolean hasPermission(String permission) {
        return this.permissions.contains(permission);
    }

    public boolean addPermission(String permission) {
        return this.permissions.add(permission);
    }

    public boolean removePermission(String permission) {
        return this.permissions.remove(permission);
    }

    public ItemStackWrapper<?> getItemInHand() {
        return this.hand;
    }

    public void setItemInHand(ItemStackWrapper<?> hand) {
        this.hand = hand;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LocationWrapper<?> getLocation() {
        return this.location;
    }

    public void setLocation(LocationWrapper<?> location) {
        this.location = location;
    }

    public void playEffect(ParticleWrapper.ParticleData particleData) {
        this.particles.add(particleData);
    }

    public List<ParticleWrapper.ParticleData> getParticles() {
        return this.particles;
    }

    public void playSound(SoundWrapper.SoundData soundData) {
        this.sounds.add(soundData);
    }

    public List<SoundWrapper.SoundData> getSounds() {
        return this.sounds;
    }

    public boolean isOnline() {
        return this.online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}