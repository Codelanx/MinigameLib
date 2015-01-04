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
package com.codelanx.minigamelib.game;

import com.codelanx.minigamelib.implementors.Minigame;
import java.util.Observable;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * Class description for {@link Game}
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 * 
 * @param <E> The plugin used for the game
 */
public abstract class Game<E extends Plugin & Minigame> extends Observable implements Listener {

    protected final E plugin;
    protected final int id;

    public Game(E plugin, int id) {
        this.plugin = plugin;
        this.id = id;
    }

    protected abstract void handleJoin(Player p);

    public void join(Player p) {
        this.notifyObservers();
    }

    protected abstract void handleLeave(Player p);

    public void leave(Player p) {
        this.notifyObservers();
    }

    protected abstract void restart();
    protected abstract void dd();

}
