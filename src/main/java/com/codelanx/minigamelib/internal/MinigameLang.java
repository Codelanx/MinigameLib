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
package com.codelanx.minigamelib.internal;

import com.codelanx.codelanxlib.annotation.PluginClass;
import com.codelanx.codelanxlib.annotation.RelativePath;
import com.codelanx.codelanxlib.internal.InternalLang;
import com.codelanx.codelanxlib.config.Lang;
import com.codelanx.codelanxlib.data.FileDataType;
import com.codelanx.codelanxlib.data.types.Yaml;
import com.codelanx.minigamelib.MinigameLib;

/**
 * Class description for {@link MinigameLang}
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
@PluginClass(MinigameLib.class)
@RelativePath("minigame-lang.yml")
public enum MinigameLang implements Lang {

    ARENA_FACTORY_UNLOAD("arena.factory.unload", "Arena being unloaded!"),
    ARENA_SESSION_NO_SPAWN("arena.session.nospawn", "You have not set any spawn points!"),
    ARENA_SESSION_INT("arena.session.int", "%s must be a %s positive integer!"),
    ARENA_SESSION_TEAMSPAWNS("arena.session.teamspawns", "Number of teams does not match number of spawns!"),
    BUILDER_PREFIX("builder.prefix", "&e>> "),
    BUILDER_FIELD("builder.field", "&7New %s %s: &9%s"),
    ;

    private static Yaml yaml;
    private final String def;
    private final String path;

    /**
     * {@link Lang} private constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param path The path to the value
     * @param def The default value
     */
    private MinigameLang(String path, String def) {
        this.path = path;
        this.def = def;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getDefault() {
        return this.def;
    }

    @Override
    public Lang getFormat() {
        return InternalLang.FORMAT;
    }

    @Override
    public Yaml getConfig() {
        if (MinigameLang.yaml == null) {
            MinigameLang.yaml = this.init(Yaml.class);
        }
        return MinigameLang.yaml;
    }

}
