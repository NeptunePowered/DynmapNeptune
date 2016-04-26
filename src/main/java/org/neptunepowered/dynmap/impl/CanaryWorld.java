/*
 * This file is part of DynmapNeptune, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2016, Jamie Mansfield <https://github.com/jamierocks>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.neptunepowered.dynmap.impl;

import net.canarymod.api.world.DimensionType;
import net.canarymod.api.world.World;
import org.dynmap.DynmapChunk;
import org.dynmap.DynmapLocation;
import org.dynmap.DynmapWorld;
import org.dynmap.utils.MapChunkCache;
import org.neptunepowered.dynmap.util.converter.LocationConverter;

import java.util.List;

public class CanaryWorld extends DynmapWorld {

    private World world;
    private boolean skylight;
    private DimensionType env;
    private DynmapLocation spawnloc = new DynmapLocation();

    public CanaryWorld(World world) {
        super(world.getFqName(), world.getHeight(), 0);
        this.world = world;
        this.env = world.getType();
        skylight = (env == DimensionType.NORMAL);
    }

    @Override
    public boolean isNether() {
        return env == DimensionType.NETHER;
    }

    @Override
    public DynmapLocation getSpawnLocation() {
        if (world != null) {
            spawnloc = LocationConverter.of(world.getSpawnLocation());
        }
        return spawnloc;
    }

    @Override
    public long getTime() {
        if (world != null) {
            return world.getTotalTime();
        } else {
            return -1;
        }
    }

    @Override
    public boolean hasStorm() {
        return false;
    }

    @Override
    public boolean isThundering() {
        return world.isThundering();
    }

    @Override
    public boolean isLoaded() {
        return world != null;
    }

    @Override
    public void setWorldUnloaded() {
        getSpawnLocation(); // Remember spawn location before unload
        world = null;
    }

    @Override
    public int getLightLevel(int x, int y, int z) {
        if (world != null) {
            return world.getLightLevelAt(x, y, z);
        } else {
            return -1;
        }
    }

    @Override
    public int getHighestBlockYAt(int x, int z) {
        if (world != null) {
            return world.getHighestBlockAt(x, z);
        } else {
            return -1;
        }
    }

    @Override
    public boolean canGetSkyLightLevel() {
        return skylight && (world != null);
    }

    @Override
    public int getSkyLightLevel(int x, int y, int z) {
        return 0;
    }

    @Override
    public String getEnvironment() {
        return env.getName();
    }

    @Override
    public MapChunkCache getChunkCache(List<DynmapChunk> chunks) {
        return null;
    }
}
