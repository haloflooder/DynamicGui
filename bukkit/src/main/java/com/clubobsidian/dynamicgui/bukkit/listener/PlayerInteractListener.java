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

package com.clubobsidian.dynamicgui.bukkit.listener;

import com.clubobsidian.dynamicgui.bukkit.entity.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.event.player.PlayerAction;
import com.clubobsidian.dynamicgui.core.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.core.world.LocationWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void interact(final PlayerInteractEvent e) {
        if (e.getClickedBlock() != null) {
            PlayerAction action = PlayerAction.valueOf(e.getAction().toString());
            PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<Player>(e.getPlayer());
            LocationWrapper<?> locationWrapper = LocationManager.get().toLocationWrapper(e.getClickedBlock().getLocation());
            com.clubobsidian.dynamicgui.core.event.block.PlayerInteractEvent interactEvent = new com.clubobsidian.dynamicgui.core.event.block.PlayerInteractEvent(playerWrapper, locationWrapper, action);
            DynamicGui.get().getEventBus().callEvent(interactEvent);
            if (interactEvent.isCancelled()) {
                e.setCancelled(true);
            }
        }
    }
}