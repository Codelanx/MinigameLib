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

import static java.lang.System.arraycopy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

/**
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class VoidGenerator extends ChunkGenerator {

    private short[] layer;
    private byte[] layerDataValues;

    public VoidGenerator() {
        this(null);
    }

    public VoidGenerator(String id) {
        layerDataValues = null;
        layer = new short[65];
        Arrays.fill(layer, 0, 65, (short) Material.AIR.getId());
    }

    @Override
    public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        int maxHeight = world.getMaxHeight();
        if (layer.length > maxHeight) {
            short[] newLayer = new short[maxHeight];
            arraycopy(layer, 0, newLayer, 0, maxHeight);
            layer = newLayer;
        }
        short[][] result = new short[maxHeight / 16][]; // 16x16x16 chunks
        for (int i = 0; i < layer.length; i += 16) {
            result[i >> 4] = new short[4096];
            for (int y = 0; y < Math.min(16, layer.length - i); y++) {
                Arrays.fill(result[i >> 4], y * 16 * 16, (y + 1) * 16 * 16, layer[i + y]);
            }
        }

        return result;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        if (layerDataValues != null) {
            return Arrays.asList((BlockPopulator) new VoidPopulator(layerDataValues));
        } else {
            // This is the default, but just in case default populators change to stock minecraft populators by default...
            return new ArrayList<>();
        }
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        if (!world.isChunkLoaded(0, 0)) {
            world.loadChunk(0, 0);
        }

        if ((world.getHighestBlockYAt(0, 0) <= 0) && (world.getBlockAt(0, 0, 0).getType() == Material.AIR)) // SPACE!
        {
            return new Location(world, 0, 64, 0); // Lets allow people to drop a little before hitting the void then shall we?
        }

        return new Location(world, 0, world.getHighestBlockYAt(0, 0), 0);
    }

}

class VoidPopulator extends BlockPopulator {

    byte[] layerDataValues;

    protected VoidPopulator(byte[] layerDataValues) {
        this.layerDataValues = layerDataValues;
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if (layerDataValues != null) {
            int x = chunk.getX() << 4;
            int z = chunk.getZ() << 4;

            for (int y = 0; y < layerDataValues.length; y++) {
                byte dataValue = layerDataValues[y];
                if (dataValue == 0) {
                    continue;
                }
                for (int xx = 0; xx < 16; xx++) {
                    for (int zz = 0; zz < 16; zz++) {
                        world.getBlockAt(x + xx, y, z + zz).setData(dataValue);
                    }
                }
            }
        }
    }

}
