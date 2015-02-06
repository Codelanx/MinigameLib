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

import com.codelanx.minigamelib.internal.MinigameLang;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * A Builder class for making {@link Arena} objects
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class ArenaBuilder {

    /** The name of the new {@link Arena} */
    private String name;
    /** The {@link Location} an editor will spawn in */
    private Location spawn;
    /** The {@link World} to use for this {@link ArenaBuilder} */
    private World world;

    /**
     * {@link ArenaBuilder} constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param world The {@link World} for this {@link ArenaBuilder}
     */
    public ArenaBuilder(World world) {
        this.world = world;
    }

    /**
     * Sets the {@link World} for this {@link ArenaBuilder}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param world The {@link World} to set
     * @return This {@link ArenaBuilder}
     */
    public ArenaBuilder world(World world) {
        this.world = world;
        return this;
    }

    /**
     * Sets the {@link Location} that an editor will spawn in
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param spawn The {@link Location} to set
     * @return This {@link ArenaBuilder}
     */
    public ArenaBuilder spawn(Location spawn) {
        this.spawn = spawn;
        return this;
    }

    /**
     * Sets the name for the new {@link Arena} being made
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param name The name to set
     * @return This {@link ArenaBuilder}
     */
    public ArenaBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * A string representation of this {@link ArenaBuilder} that can be
     * sent to the player directly
     * <br \><br \>
     * {@inheritDoc}
     * 
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return This {@link ArenaBuilder} in string form
     */
    @Override
    public String toString() {
        String prefix = MinigameLang.BUILDER_PREFIX.format();
        StringBuilder sb = new StringBuilder(prefix);
        sb.append('\n').append(prefix);
        sb.append(MinigameLang.BUILDER_FIELD.format("arena", "name", this.name));
        sb.append('\n').append(prefix);
        sb.append(MinigameLang.BUILDER_FIELD.format("arena", "spawn", this.getTag(this.spawn)));
        sb.append('\n').append(prefix);
        sb.append(MinigameLang.BUILDER_FIELD.format("arena", "world", this.getTag(this.world)));
        sb.append('\n').append(prefix);
        return sb.toString();
    }
    
    private String getTag(Object o) {
        return o != null ? "Set!" : "Not Set!";
    }

    /**
     * Returns the currently set {@link World} for this {@link ArenaBuilder}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The {@link World} for this {@link ArenaBuilder}
     */
    World getWorld() {
        return this.world;
    }

    /**
     * Returns the currently set name for this {@link ArenaBuilder}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The name for this {@link ArenaBuilder}
     */
    String getName() {
        return this.name;
    }

    /**
     * Returns the currently set spawn {@link Location} for this
     * {@link ArenaBuilder}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The spawn {@link Location} for this {@link ArenaBuilder}
     */
    Location getSpawn() {
        return this.spawn;
    }

    /**
     * Trigger for throwing an {@link UnfinishedException}, if this
     * {@link ArenaBuilder} is incomplete upon an attempt to build.
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @throws UnfinishedException Thrown if any fields are not set
     */
    void canBuild() throws UnfinishedException {
        if (this.name == null || this.spawn == null || this.world == null) {
            throw new UnfinishedException("Arena is missing parameters!");
        }
    }

}
