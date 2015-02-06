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
import com.codelanx.codelanxlib.data.types.Yaml;
import com.sk89q.worldedit.regions.CuboidRegion;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Wrapper for a world object for use in games
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class Arena {

    /** Location of the {@link World} folder */
    protected File worldLocation;
    /** The {@link File} referencing the original arena */
    protected File origin;
    /** The actual name of the new copied folder */
    protected String worldName;
    /** The {@link World} object */
    protected World world;
    /** The {@link ConfigurationLoader} for this specific {@link Arena} */
    protected Yaml config;
    /** A {@link List} of permanent {@link CuboidRegion} wall references */
    protected final List<CuboidRegion> protect;
    /** A {@link List} of spawning {@link Location} objects */
    protected final List<Location> spawns;
    /** A {@link Location} for spectators to spawn at */
    protected Location spectator;

    /**
     * {@link Arena} constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param world The {@link World} tied to this {@link Arena}
     * @param origin The original {@link File} location of the {@link World}
     */
    Arena(World world, File origin) {
        this.origin = origin;
        this.world = world;
        this.worldName = world.getName();
        this.worldLocation = world.getWorldFolder();
        this.config = new Yaml(new File(this.worldLocation, "config.yml"));
        this.protect = Config.retrieve(this.config, ArenaConfig.PROTECT_LOCATIONS).as(List.class);
        this.spawns = Config.retrieve(this.config, ArenaConfig.SPAWN_LOCATIONS).as(List.class);
        this.spectator = Config.retrieve(this.config, ArenaConfig.SPECTATE_LOCATION).as(Location.class);
        Collections.shuffle(this.spawns);
    }

    /**
     * Returns the folder containing the world
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The {@link File} directory of the {@link World} for this
     * {@link Arena}
     */
    public final File getWorldFolder() {
        return this.worldLocation;
    }

    /**
     * Gets the raw folder name for the {@link World}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The {@link World} name in use
     */
    public final String getRawName() {
        return this.worldName;
    }

    /**
     * Returns the proper name of the arena
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The name of the arena
     */
    public final String getName() {
        return this.origin.getName();
    }

    /**
     * Returns the original {@link World} folder before being copied
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return The {@link File} folder of the original {@link World}
     */
    public final File getOriginalLocation() {
        return this.origin;
    }

    /**
     * Returns the {@link World} object that this {@link Arena} represents
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The {@link World} this {@link Arena} represents
     */
    public final World getWorld() {
        return this.world;
    }

    /**
     * Returns the {@link ConfigurationLoader} of the world, or the overall
     * {@link ConfigurationLoader} if there is no file
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The {@link ConfigurationLoader} of this {@link Arena}
     */
    public final Yaml getConfig() {
        return this.config;
    }

    /**
     * Returns a {@link List} of {@link CuboidRegion} objects that represent
     * protected walls in-game. Protected walls are never destroyed.
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return A {@link List} of {@link CuboidRegion} objects
     */
    public final List<CuboidRegion> getProtectedWalls() {
        return Collections.unmodifiableList(this.protect);
    }

    /**
     * Returns a {@link List} of spawn {@link Location} objects
     * 
     * @since 1.0.0
     * @version 1.0.0
     * 
     * @return A {@link List} of {@link Location} objects
     */
    public final List<Location> getSpawns() {
        return Collections.unmodifiableList(this.spawns);
    }

    public final Location getSpectatorSpawn() {
        return this.spectator;
    }

    /**
     * Frees resources referenced by this {@link Arena} to allow it to be collected
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    void dispose() {
        this.origin = null;
        this.spawns.clear();
        this.protect.clear();
        this.config = null;
        this.worldLocation = null;
        this.worldName = null;
        this.world = null;
        this.spectator = null;
    }

}
