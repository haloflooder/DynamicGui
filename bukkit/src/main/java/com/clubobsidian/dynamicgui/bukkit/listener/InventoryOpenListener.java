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
import com.clubobsidian.dynamicgui.bukkit.inventory.BukkitInventoryWrapper;
import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.inventory.InventoryWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class InventoryOpenListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void inventoryOpen(InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player player = (Player) e.getPlayer();
            PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<Player>(player);
            InventoryWrapper<?> inventoryWrapper = new BukkitInventoryWrapper<Inventory>(e.getInventory());
            com.clubobsidian.dynamicgui.core.event.inventory.InventoryOpenEvent inventoryOpenEvent = new com.clubobsidian.dynamicgui.core.event.inventory.InventoryOpenEvent(playerWrapper, inventoryWrapper);
            DynamicGui.get().getEventBus().callEvent(inventoryOpenEvent);
        }
    }
}