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

package com.clubobsidian.dynamicgui.core.function.impl.cooldown;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.cooldown.CooldownManager;

public class SetCooldownFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = -3204581055961888388L;

    public SetCooldownFunction() {
        super("setcooldown");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        if (this.getData() == null || !this.getData().contains(",")) {
            return false;
        }

        String[] split = this.getData().split(",");
        if (split.length != 2) {
            return false;
        }

        String name = split[0];
        long cooldownTime;
        try {
            cooldownTime = Long.parseLong(split[1]);
        } catch (NumberFormatException ex) {
            return false;
        }

        CooldownManager.get().removeCooldown(playerWrapper, name);
        CooldownManager.get().createCooldown(playerWrapper, name, cooldownTime);
        return true;
    }
}