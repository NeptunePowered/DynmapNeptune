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

package org.neptunepowered.dynmap.util.helper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.List;

public final class MinecraftHelper {

    public static String[] getBlockNames() {
        String[] blockNames = new String[4096];

        for (int i = 0; i < 4096; i++) {
            Block b = Block.getBlockById(i);

            if (b != null) {
                blockNames[i] = b.getUnlocalizedName();
                if (blockNames[i].startsWith("tile.")) {
                    blockNames[i] = blockNames[i].substring(5);
                }
            }
        }

        return blockNames;
    }

    public static String[] getBiomeNames() {
        String[] biomeNames = new String[256];
        BiomeGenBase[] biomes = BiomeGenBase.getBiomeGenArray();

        for (int i = 0; i < 256; i++) {
            BiomeGenBase biome = biomes[i];
            if (biome == null) {
                continue;
            }
            biomeNames[i] = biome.biomeName;
        }

        return biomeNames;
    }

    public static int[] getBlockMaterialMap() {
        int[] blockMaterials = new int[4096];
        List<Material> byid = new ArrayList<>();

        for (int i = 0; i < 4096; i++) {
            Block block = Block.getBlockById(i);

            if (block != null) {
                Material material = block.getMaterial();
                int idx = byid.indexOf(material);
                if (idx < 0) {
                    idx = byid.size();
                    byid.add(material);
                }
                blockMaterials[i] = idx;
            }
        }

        return blockMaterials;
    }
}
