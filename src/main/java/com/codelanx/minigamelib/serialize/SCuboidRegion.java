/*
 * Copyright (C) 2013 Spencer Alderman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.codelanx.minigamelib.serialize;

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.regions.CuboidRegion;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * Serializable wrapper class for {@link Location}
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class SCuboidRegion extends CuboidRegion implements ConfigurationSerializable {

    /**
     * {@link SCuboidRegion} constructor for {@link ConfigurationSerializable}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param config A {@link Map} YAML representation of the class
     */
    public SCuboidRegion(Map<String, Object> config) {
        this(BukkitUtil.toVector((org.bukkit.util.Vector) config.get("minimum")),
                BukkitUtil.toVector((org.bukkit.util.Vector) config.get("maximum")));
    }

    /**
     * {@link SCuboidRegion} constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param pos1 One {@link Vector} corner
     * @param pos2 The opposite {@link Vector} corner
     */
    public SCuboidRegion(Vector pos1, Vector pos2) {
        super(pos1, pos2);
    }

    /**
     * {@link SCuboidRegion} constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param world The {@link LocalWorld} for this {@link SCuboidRegion}
     * @param pos1 One {@link Vector} corner
     * @param pos2 The opposite {@link Vector} corner
     */
    public SCuboidRegion(World world, Vector pos1, Vector pos2) {
        super(BukkitUtil.getLocalWorld(world), pos1, pos2);
    }

    /**
     * {@link SCuboidRegion} constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param region A {@link CuboidRegion} to build off of
     */
    public SCuboidRegion(CuboidRegion region) {
        this(BukkitUtil.toWorld(region.getWorld()), region.getMinimumPoint(), region.getMaximumPoint());
    }

    /**
     * A pseudo constructor for {@link ConfigurationSerializable}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param config A {@link Map} YAML representation of the class
     * @return A new {@link SCuboidRegion}
     */
    public static SCuboidRegion deserialize(Map<String, Object> config) {
        return new SCuboidRegion(config);
    }

    /**
     * A pseudo constructor for {@link ConfigurationSerializable}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param config A {@link Map} YAML representation of the class
     * @return A new {@link SCuboidRegion}
     */
    public static SCuboidRegion valueOf(Map<String, Object> config) {
        return new SCuboidRegion(config);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        org.bukkit.util.Vector minimum = new org.bukkit.util.Vector(
                this.getMinimumPoint().getBlockX(),
                this.getMinimumPoint().getBlockY(),
                this.getMinimumPoint().getBlockZ()
        );
        org.bukkit.util.Vector maximum = new org.bukkit.util.Vector(
                this.getMaximumPoint().getBlockX(),
                this.getMaximumPoint().getBlockY(),
                this.getMaximumPoint().getBlockZ()
        );
        map.put("minimum", minimum);
        map.put("maximum", maximum);
        return map;
    }

}
