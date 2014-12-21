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

import com.codelanx.codelanxlib.serialize.SLocation;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class SSignReference implements ConfigurationSerializable {
    
    private final SLocation loc;
    private final int gameID;
    private final String arena;
    
    /**
     * {@link SSignReference} constructor for {@link ConfigurationSerializable}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param config A {@link Map} YAML representation of the class
     */
    public SSignReference(Map<String, Object> config) {
        this((SLocation) config.get("location"),
                (int) config.get("gameID"),
                (String) config.get("arena"));
    }
    
    public SSignReference(SLocation loc, int gameID, String arena) {
        this.loc = loc;
        this.gameID = gameID;
        this.arena = arena;
    }
    
     /**
     * A pseudo constructor for {@link ConfigurationSerializable}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param config A {@link Map} YAML representation of the class
     * @return A new {@link SLocation}
     */
    public static SSignReference valueOf(Map<String, Object> config) {
        return new SSignReference(config);
    }

    /**
     * A pseudo constructor for {@link ConfigurationSerializable}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param config A {@link Map} YAML representation of the class
     * @return A new {@link SLocation}
     */
    public static SSignReference deserialize(Map<String, Object> config) {
        return new SSignReference(config);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> back = new HashMap<>();
        back.put("location", this.loc);
        back.put("gameID", this.gameID);
        back.put("arena", this.arena);
        return back;
    }
    
    public final Location getLocation() {
        return this.loc.toLocation();
    }
    
    public final int getGameID() {
        return this.gameID;
    }
    
    public final String getArenaName() {
        return this.arena;
    }

}
