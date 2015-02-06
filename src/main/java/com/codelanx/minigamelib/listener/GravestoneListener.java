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
package com.codelanx.minigamelib.listener;

import com.codelanx.codelanxlib.listener.SubListener;
import com.codelanx.codelanxlib.util.Protections;
import com.codelanx.codelanxlib.util.Scheduler;
import com.codelanx.minigamelib.CodelanxMinigame;
import com.codelanx.minigamelib.internal.ConfigValue;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Optionally creates gravestone markers
 *
 * @since 0.0.1
 * @author 1Rogue
 * @version 0.0.1
 */
public class GravestoneListener extends SubListener<CodelanxMinigame<?>> {

    private final Set<Location> protect = new HashSet<>();

    public GravestoneListener(CodelanxMinigame<?> plugin) {
        super(plugin);
    }

    @EventHandler
    @SuppressWarnings("empty-statement")
    public void onDeath(PlayerDeathEvent event) {
        if (!ConfigValue.GRAVESTONES_ENABLED.as(boolean.class)) {
            return;
        }
        Location loc = event.getEntity().getLocation().clone();
        //Purposely empty, will floor Y coordinate until ground is found or nothing
        for (; loc.getBlockY() < 0 || loc.getBlock().getType() != Material.AIR; loc.add(0, -1, 0));
        //If ground is not found, don't place a gravestone
        if (loc.getBlockY() < 0) {
            return;
        }
        //Getting our "headstone" for the player's head
        loc.getBlock().setType(ConfigValue.GRAVESTONE_MATERIAL.as(Material.class));
        Location skullLoc = loc.add(0, 1, 0);
        //Set a skull
        skullLoc.getBlock().setType(Material.SKULL);
        Skull skull = (Skull) loc.getBlock();
        skull.setSkullType(SkullType.PLAYER);
        //Uses the server's skin cache, but may invoke a blocking web request
        Scheduler.runAsyncTask(() -> skull.setOwner(event.getEntity().getName()), 0);
        //Position skull atop grave headstone
        loc.getBlock().setData((byte) 0x1, true);
        //Add some effects for flair
        loc.getWorld().playEffect(loc, Effect.SMOKE, 4);
        loc.getWorld().playEffect(loc, Effect.ENDER_SIGNAL, 1);
        //lastly, protect the gravestone for 5 minutes
        Protections.protect(loc);
        Protections.protect(skullLoc);
        Scheduler.runAsyncTask(() -> {
            Protections.unprotect(loc);
            Protections.unprotect(skullLoc);
        }, TimeUnit.MINUTES.toSeconds(5));
    }

}
