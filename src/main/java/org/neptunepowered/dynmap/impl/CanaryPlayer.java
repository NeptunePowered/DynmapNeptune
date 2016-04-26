/*
 * This file is part of DynmapNeptune, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015-2016, Jamie Mansfield <https://github.com/jamierocks>
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

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import org.dynmap.DynmapLocation;
import org.dynmap.common.DynmapPlayer;

import java.net.InetSocketAddress;

public class CanaryPlayer implements DynmapPlayer {
    private Player player;

    public CanaryPlayer(Player player) {
        this.player = player;
    }

    public boolean hasPermissionNode(String perm) {
        return player.hasPermission(perm);
    }

    public boolean hasPrivilege(String priv) {
        return player.hasPermission(priv);
    }

    public boolean isConnected() {
        return player.isOnline();
    }

    public boolean isOp() {
        return Canary.ops().isOpped(player);
    }

    public void sendMessage(String msg) {
        player.message(msg);
    }

    public InetSocketAddress getAddress() {
        return new InetSocketAddress(player.getIP(), 0);
    }

    public int getArmorPoints() {
        return 0;
    }

    public DynmapLocation getBedSpawnLocation() {
        return new CanaryLocation(player.getSpawnPosition());
    }

    public String getDisplayName() {
        return player.getDisplayName();
    }

    public long getFirstLoginTime() {
        return 0;
    }

    public int getHealth() {
        return Math.round(player.getHealth());
    }

    public long getLastLoginTime() {
        return 0;
    }

    public DynmapLocation getLocation() {
        return new CanaryLocation(player.getLocation());
    }

    public String getName() {
        return player.getName();
    }

    public int getSortWeight() {
        return 0;
    }

    public String getWorld() {
        return player.getWorld().getName();
    }

    public boolean isInvisible() {
        return player.isInvisible();
    }

    public boolean isOnline() {
        return player.isOnline();
    }

    public boolean isSneaking() {
        return player.isSneaking();
    }

    public void setSortWeight(int arg0) {

    }
}
