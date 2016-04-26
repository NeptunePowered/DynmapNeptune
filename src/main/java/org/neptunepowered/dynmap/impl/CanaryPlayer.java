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

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import org.dynmap.DynmapLocation;
import org.dynmap.common.DynmapPlayer;
import org.neptunepowered.dynmap.util.converter.LocationConverter;

import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CanaryPlayer implements DynmapPlayer {

    private Player player;

    public CanaryPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean hasPermissionNode(String perm) {
        return player.hasPermission(perm);
    }

    @Override
    public boolean hasPrivilege(String priv) {
        return player.hasPermission(priv);
    }

    @Override
    public boolean isConnected() {
        return player.isOnline();
    }

    @Override
    public boolean isOp() {
        return Canary.ops().isOpped(player);
    }

    @Override
    public void sendMessage(String msg) {
        player.message(msg);
    }

    @Override
    public InetSocketAddress getAddress() {
        return new InetSocketAddress(player.getIP(), 0);
    }

    @Override
    public int getArmorPoints() {
        return 0;
    }

    @Override
    public DynmapLocation getBedSpawnLocation() {
        return LocationConverter.of(player.getSpawnPosition());
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }

    @Override
    public long getFirstLoginTime() {
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date firstJoined = null;
        try {
            firstJoined = format.parse(player.getFirstJoined());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return firstJoined.getTime();
    }

    @Override
    public double getHealth() {
        return player.getHealth();
    }

    @Override
    public long getLastLoginTime() {
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date lastJoined = null;
        try {
            lastJoined = format.parse(player.getLastJoined());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lastJoined.getTime();
    }

    @Override
    public DynmapLocation getLocation() {
        return LocationConverter.of(player.getLocation());
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public int getSortWeight() {
        return 0;
    }

    @Override
    public String getWorld() {
        return player.getWorld().getName();
    }

    @Override
    public boolean isInvisible() {
        return player.isInvisible();
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public boolean isSneaking() {
        return player.isSneaking();
    }

    @Override
    public void setSortWeight(int arg0) {

    }
}
