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

package org.neptunepowered.dynmap;

import org.dynmap.common.DynmapListenerManager.EventType;

import org.neptunepowered.dynmap.impl.CanaryWorld;
import org.neptunepowered.dynmap.impl.CanaryPlayer;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.ConnectionHook;
import net.canarymod.hook.player.DisconnectionHook;
import net.canarymod.hook.system.LoadWorldHook;
import net.canarymod.hook.system.UnloadWorldHook;
import net.canarymod.plugin.PluginListener;

public class DynmapNeptuneListener implements PluginListener {
    private DynmapNeptunePlugin plugin;

    public DynmapNeptuneListener(DynmapNeptunePlugin plugin) {
        this.plugin = plugin;
    }

    @HookHandler
    public void onWorldLoad(LoadWorldHook hook) {
        plugin.getCore().listenerManager.processWorldEvent(EventType.WORLD_LOAD, new CanaryWorld(hook.getWorld()));
    }

    @HookHandler
    public void onWorldUnload(UnloadWorldHook hook) {
        plugin.getCore().listenerManager.processWorldEvent(EventType.WORLD_UNLOAD, new CanaryWorld(hook.getWorld()));
    }

    @HookHandler
    public void onPlayerJoin(ConnectionHook hook) {
        plugin.getCore().listenerManager.processPlayerEvent(EventType.PLAYER_JOIN, new CanaryPlayer(hook.getPlayer()));
    }

    @HookHandler
    public void onPlayerQuit(DisconnectionHook hook) {
        plugin.getCore().listenerManager.processPlayerEvent(EventType.PLAYER_QUIT, new CanaryPlayer(hook.getPlayer()));
    }
}
