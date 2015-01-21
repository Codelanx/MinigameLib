/*
 * Copyright (C) 2015 Codelanx, All Rights Reserved
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 *
 * This program is protected software: You are free to distrubute your
 * own use of this software under the terms of the Creative Commons BY-NC-ND
 * license as published by Creative Commons in the year 2015 or as published
 * by a later date. You may not provide the source files or provide a means
 * of running the software outside of those licensed to use it.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the Creative Commons BY-NC-ND license
 * long with this program. If not, see <https://creativecommons.org/licenses/>.
 */
package com.codelanx.minigamelib;

import com.codelanx.codelanxlib.config.Config;
import com.codelanx.codelanxlib.data.FileDataType;
import com.codelanx.codelanxlib.data.types.Yaml;
import org.bukkit.Material;

/**
 * Class description for {@link ConfigValue}
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public enum ConfigValue implements Config<ConfigValue> {

    GRAVESTONES_ENABLED("gravestone.enabled", false),
    GRAVESTONE_MATERIAL("gravestone.type", Material.NETHER_FENCE);

    private static Yaml yaml;
    private final String path;
    private final Object def;

    private ConfigValue(String path, Object def) {
        this.path = path;
        this.def = def;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public Object getDefault() {
        return this.def;
    }

    @Override
    public FileDataType getConfig() {
        if (ConfigValue.yaml == null) {
            ConfigValue.yaml = this.init(Yaml.class);
        }
        return ConfigValue.yaml;
    }

}
