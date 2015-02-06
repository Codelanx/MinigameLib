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
package com.codelanx.minigamelib.arena;

import com.codelanx.codelanxlib.config.Config;
import com.codelanx.codelanxlib.serialize.SLocation;
import com.codelanx.minigamelib.internal.MinigameLang;
import com.codelanx.minigamelib.serialize.SCuboidRegion;
import com.sk89q.worldedit.regions.CuboidRegion;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

/**
 * Represents a session for a player editing an in-game arena, in-game.
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class EditSession extends Arena {

    /**
     * {@link EditSession} constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param arena The {@link Arena} being edited
     */
    EditSession(Arena arena) {
        super(arena.getWorld(), arena.getOriginalLocation());
    }

    /**
     * Adds a spawn to the {@link Arena}
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param spawn The {@link Location} to add
     * @return {@code true} if added, {@code false} otherwise
     */
    public boolean addSpawn(Location spawn) {
        return this.spawns.add(spawn);
    }

    /**
     * Adds a wall to the {@link Arena}
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @param wall The {@link CuboidRegion} to add
     * @return {@code true} if added, {@code false} otherwise
     */
    public boolean addProtectedWall(CuboidRegion wall) {
        return this.protect.add(wall);
    }

    public CuboidRegion delProtectedWall(int index) {
        return this.protect.remove(index);
    }
    
    public void writeConfigValues() {
        List<SCuboidRegion> protects = new ArrayList<>();
        List<SLocation> respawns = new ArrayList<>();
        this.protect.stream().map(SCuboidRegion::new).forEach(protects::add);
        this.spawns.stream().map(SLocation::new).forEach(respawns::add);
        Config.retrieve(this.getConfig(), ArenaConfig.PROTECT_LOCATIONS).set(protects);
        Config.retrieve(this.getConfig(), ArenaConfig.SPAWN_LOCATIONS).set(respawns);
    }
    
    public String verifyConfig() {
        int teams = Config.retrieve(this.getConfig(), ArenaConfig.TEAM_COUNT).as(int.class);
        if (this.spawns.size() <= 0) {
            return MinigameLang.ARENA_SESSION_NO_SPAWN.format();
        }
        if (teams <= 0) {
            return MinigameLang.ARENA_SESSION_INT.format("Number of teams", "non-zero");
        }
        if (teams != this.spawns.size()) {
            return MinigameLang.ARENA_SESSION_TEAMSPAWNS.format();
        }
        if (Config.retrieve(this.getConfig(), ArenaConfig.TEAM_SIZE).as(int.class) <= 0) {
            return MinigameLang.ARENA_SESSION_INT.format("Team size", "non-zero");
        }
        if (Config.retrieve(this.getConfig(), ArenaConfig.VIP_SLOT_COUNT).as(int.class) < 0) {
            return MinigameLang.ARENA_SESSION_INT.format("Vip slot count", "");
        }
        if (Config.retrieve(this.getConfig(), ArenaConfig.TIMER_PREWALL).as(int.class) < 0) {
            return MinigameLang.ARENA_SESSION_INT.format("Wall countdown", "non-zero");
        }
        if (Config.retrieve(this.getConfig(), ArenaConfig.TIMER_FULLGAME).as(int.class) < 0) {
            return MinigameLang.ARENA_SESSION_INT.format("Wall countdown", "non-zero");
        }
        if (Config.retrieve(this.getConfig(), ArenaConfig.TIMER_PREGAME).as(int.class) < 0) {
            return MinigameLang.ARENA_SESSION_INT.format("Wall countdown", "non-zero");
        }
        return null;
    }
    
    public Location delSpawn(int index) {
        return this.spawns.remove(index);
    }

}
