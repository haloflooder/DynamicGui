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

package com.clubobsidian.dynamicgui.core.test.mock.world;

import java.util.HashMap;
import java.util.Map;

public class MockWorld {

    private final String name;
    private final Map<String, String> gameRules = new HashMap<>();

    public MockWorld(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGameRule(String rule, String value) {
        this.gameRules.put(rule, value);
    }

    public String getGameRule(String rule) {
        return this.gameRules.get(rule);
    }
}
