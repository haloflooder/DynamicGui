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

package com.clubobsidian.dynamicgui.core.inject.module;

import cloud.commandframework.CommandManager;
import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.command.CommandRegistrar;
import com.clubobsidian.dynamicgui.core.command.CommandRegistrarImpl;
import com.clubobsidian.dynamicgui.core.command.DynamicGuiCommand;
import com.clubobsidian.dynamicgui.core.command.GuiCommand;
import com.clubobsidian.dynamicgui.core.command.GuiCommandSender;
import com.clubobsidian.dynamicgui.core.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.core.manager.entity.EntityManager;
import com.clubobsidian.dynamicgui.core.manager.inventory.InventoryManager;
import com.clubobsidian.dynamicgui.core.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.core.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.core.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.core.platform.Platform;
import com.clubobsidian.dynamicgui.core.plugin.DynamicGuiPlugin;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;

public abstract class PluginModule implements Module {

    private final Class<? extends EntityManager> entityClass = this.getEntityManager();
    private final Class<? extends InventoryManager> inventoryClass = this.getInventoryManager();
    private final Class<? extends ItemStackManager> itemStackClass = this.getItemStackManager();
    private final Class<? extends MaterialManager> materialClass = this.getMaterialManager();
    private final Class<? extends LocationManager> locationClass = this.getLocationManger();
    private final DynamicGuiPlugin plugin;
    private final Platform platform;
    private final LoggerWrapper<?> logger;
    private final CommandManager<GuiCommandSender> commandManager;

    public PluginModule(DynamicGuiPlugin plugin,
                        Platform platform,
                        LoggerWrapper<?> logger,
                        CommandManager<GuiCommandSender> commandManager) {
        this.plugin = plugin;
        this.platform = platform;
        this.logger = logger;
        this.commandManager = commandManager;
    }

    public abstract Class<? extends EntityManager> getEntityManager();

    public abstract Class<? extends InventoryManager> getInventoryManager();

    public abstract Class<? extends ItemStackManager> getItemStackManager();

    public abstract Class<? extends MaterialManager> getMaterialManager();

    public abstract Class<? extends LocationManager> getLocationManger();

    @Override
    public void configure(Binder binder) {
        binder.bind(new TypeLiteral<LoggerWrapper<?>>() {}).toInstance(this.logger);

        binder.bind(new TypeLiteral<CommandManager<GuiCommandSender>>(){}).toInstance(this.commandManager);
        binder.bind(CommandRegistrar.class).to(CommandRegistrarImpl.class).asEagerSingleton();

        binder.bind(EntityManager.class).to(this.entityClass);
        binder.bind(InventoryManager.class).to(this.inventoryClass);
        binder.bind(ItemStackManager.class).to(this.itemStackClass);
        binder.bind(MaterialManager.class).to(this.materialClass);
        binder.bind(LocationManager.class).to(this.locationClass);
        binder.bind(DynamicGuiPlugin.class).toInstance(this.plugin);
        binder.bind(Platform.class).toInstance(this.platform);

        binder.bind(GuiManager.class);

        binder.bind(GuiCommand.class).asEagerSingleton();
        binder.bind(DynamicGuiCommand.class).asEagerSingleton();

        binder.requestStaticInjection(EntityManager.class);
        binder.requestStaticInjection(InventoryManager.class);
        binder.requestStaticInjection(ItemStackManager.class);
        binder.requestStaticInjection(MaterialManager.class);
        binder.requestStaticInjection(LocationManager.class);
        binder.requestStaticInjection(GuiManager.class);
        binder.requestStaticInjection(DynamicGui.class);
    }

    public boolean bootstrap() {
        Guice.createInjector(this);
        return DynamicGui.get().start();
    }
}