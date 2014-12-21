/*
 * Copyright (C) 2014 Codelanx, All Rights Reserved
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 *
 * This program is protected software: You are free to distrubute your
 * own use of this software under the terms of the Creative Commons BY-NC-ND
 * license as published by Creative Commons in the year 2014 or as published
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
package com.codelanx.minigamelib.arena;

import com.codelanx.codelanxlib.config.Config;
import com.codelanx.codelanxlib.data.FileDataType;
import com.codelanx.codelanxlib.data.types.Yaml;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Class description for {@link ArenaConfig}
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public enum ArenaConfig implements Config<ArenaConfig> {

    /** Number of slots reserved for VIP players */
    VIP_SLOT_COUNT("signs.vip-slots", 3),
    /** The amount of time in seconds before the game begins */
    TIMER_PREGAME("game.timer.pregame", 60),
    /** The amount of time in seconds before walls collapse */
    TIMER_PREWALL("game.timer.prewall", 900),
    /** The amount of time in seconds before endgame (after walls collapse) */
    TIMER_FULLGAME("game.timer.fullgame", 720),
    /** Number of teams in the arena */
    TEAM_COUNT("teams.number", 4),
    /** Number of players per team */
    TEAM_SIZE("teams.size", 6),
    /** Whether or not a player can join the game before the walls collapse */
    JOIN_PREWALL("game.join-prewall", true),
    /** Whether or not spectators can fly */
    SPECTATOR_FLIGHT("game.spectator-flight", false),
    /** List of commands to execute on beginning of game for VIPs */
    VIP_COMMANDS("game.vip-commands", new ArrayList<>()),
    /** Locations of protected blocks in an {@link Arena} */
    PROTECT_LOCATIONS("game.protect", new HashMap<>()),
    /** Locations of team spawns in-game */
    SPAWN_LOCATIONS("game.spawns", new HashMap<>()),
    /** Where to teleport players that want to spectate a game */
    SPECTATE_LOCATION("game.spectate-location", null),
    /** The spawn location for editing an {@link Arena} */
    EDIT_SPAWN("editing.spawn", null);

    private static Yaml yaml;
    private final String path;
    private final Object def;

    private ArenaConfig(String path, Object def) {
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
        if (ArenaConfig.yaml == null) {
            ArenaConfig.yaml = this.init(Yaml.class);
        }
        return ArenaConfig.yaml;
    }
}
