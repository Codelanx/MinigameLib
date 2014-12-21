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
package com.codelanx.minigamelib.event;

import com.codelanx.minigamelib.arena.Arena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class ArenaPreDisposeEvent extends Event {

    protected static final HandlerList handlers = new HandlerList();
    protected final Arena arena;

    public ArenaPreDisposeEvent(Arena arena) {
        if (arena == null) {
            throw new IllegalArgumentException("Arena cannot be null!");
        }
        this.arena = arena;
    }

    public Arena getArena() {
        return this.arena;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
