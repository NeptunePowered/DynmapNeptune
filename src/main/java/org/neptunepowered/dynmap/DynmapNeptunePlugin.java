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

package org.neptunepowered.dynmap;

import net.canarymod.Canary;
import net.canarymod.api.world.World;
import net.canarymod.plugin.Plugin;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.DynmapCore;
import org.dynmap.Log;
import org.dynmap.common.DynmapListenerManager.EventType;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.modsupport.ModSupportImpl;
import org.neptunepowered.dynmap.impl.CanaryLogger;
import org.neptunepowered.dynmap.impl.CanaryWorld;
import org.neptunepowered.dynmap.data.Constants;
import org.neptunepowered.dynmap.impl.CanaryServer;
import org.neptunepowered.dynmap.util.helper.MinecraftHelper;

import java.io.File;

public class DynmapNeptunePlugin extends Plugin implements DynmapCommonAPI {

    private DynmapCore core;
    private CanaryServer server;
    private CanaryEnableCoreCallback config = new CanaryEnableCoreCallback();

    public DynmapNeptunePlugin() {
        ModSupportImpl.init();
    }

    @Override
    public boolean enable() {
        // set Logger
        Log.setLogger(new CanaryLogger(this.getLogman()));

        // init server
        server = new CanaryServer(Canary.getServer(), this);

        // init data folder
        Constants.checkDirs();

        // init core
        if (core == null) {
            core = new DynmapCore();
        }
        core.setPluginJarFile(new File(getPath()));
        core.setPluginVersion(getDescriptor().getVersion(), Canary.getImplementationTitle());
        core.setMinecraftVersion(Canary.getServer().getServerVersion());
        core.setDataFolder(Constants.DATA_DIRECTORY);
        core.setServer(server);
        core.setTriggerDefault(null);
        core.setBiomeNames(MinecraftHelper.getBiomeNames());
        core.setBlockNames(MinecraftHelper.getBlockNames());
        core.setBlockMaterialMap(MinecraftHelper.getBlockMaterialMap());

        // Load configuration
        if (!core.initConfiguration(config)) {
            return false;
        }
        
        // Enable core
        if (!core.enableCore(config)) {
            return false;
        }
        
        // Add worlds
        for(World world : Canary.getServer().getWorldManager().getAllWorlds()) {
            CanaryWorld w = new CanaryWorld(world);
            if(core.processWorldLoad(w)) {
                core.listenerManager.processWorldEvent(EventType.WORLD_LOAD, w);
            }
        }

        // Register listener
        Canary.hooks().registerListener(new DynmapNeptuneListener(this), this);
        
        // Core is ready - notify API availability
        DynmapCommonAPIListener.apiInitialized(this);
        core.serverStarted();
        
        return true;
    }

    @Override
    public void disable() {
        // Core is being disabled - notify API disable
        DynmapCommonAPIListener.apiTerminated();

        // Disable core
        core.disableCore();
    }

    public DynmapCore getCore() {
        return core;
    }

    @Override
    public MarkerAPI getMarkerAPI() {
        return core.getMarkerAPI();
    }

    @Override
    public boolean markerAPIInitialized() {
        return core.markerAPIInitialized();
    }

    @Override
    public boolean sendBroadcastToWeb(String sender, String msg) {
        return core.sendBroadcastToWeb(sender, msg);
    }

    @Override
    public int triggerRenderOfVolume(String wid, int minx, int miny, int minz, int maxx, int maxy, int maxz) {
        return core.triggerRenderOfVolume(wid, minx, miny, minz, maxx, maxy, maxz);
    }

    @Override
    public int triggerRenderOfBlock(String wid, int x, int y, int z) {
        return core.triggerRenderOfBlock(wid, x, y, z);
    }

    @Override
    public void setPauseFullRadiusRenders(boolean dopause) {
        core.setPauseFullRadiusRenders(dopause);
    }

    @Override
    public boolean getPauseFullRadiusRenders() {
        return core.getPauseFullRadiusRenders();
    }

    @Override
    public void setPauseUpdateRenders(boolean dopause) {
        core.setPauseFullRadiusRenders(dopause);
    }

    @Override
    public boolean getPauseUpdateRenders() {
        return core.getPauseUpdateRenders();
    }

    @Override
    public void setPlayerVisiblity(String player, boolean is_visible) {
        core.setPlayerVisiblity(player, is_visible);
    }

    @Override
    public boolean getPlayerVisbility(String player) {
        return core.getPlayerVisbility(player);
    }

    @Override
    public void assertPlayerInvisibility(String player, boolean is_invisible, String plugin_id) {
        core.assertPlayerInvisibility(player, is_invisible, plugin_id);
    }

    @Override
    public void assertPlayerVisibility(String player, boolean is_visible, String plugin_id) {
        core.assertPlayerVisibility(player, is_visible, plugin_id);
    }

    @Override
    public void postPlayerMessageToWeb(String playerid, String playerdisplay, String message) {
        core.postPlayerMessageToWeb(playerid, playerdisplay, message);
    }

    @Override
    public void postPlayerJoinQuitToWeb(String playerid, String playerdisplay, boolean isjoin) {
        core.postPlayerJoinQuitToWeb(playerid, playerdisplay, isjoin);
    }

    @Override
    public String getDynmapCoreVersion() {
        return core.getDynmapCoreVersion();
    }

    @Override
    public boolean setDisableChatToWebProcessing(boolean disable) {
        return core.setDisableChatToWebProcessing(disable);
    }

    @Override
    public boolean testIfPlayerVisibleToPlayer(String player, String player_to_see) {
        return core.testIfPlayerVisibleToPlayer(player, player_to_see);
    }

    @Override
    public boolean testIfPlayerInfoProtected() {
        return core.testIfPlayerInfoProtected();
    }

    @Override
    public void processSignChange(int blkid, String world, int x, int y, int z, String[] lines, String playerid) {
        core.processSignChange(blkid, world, x, y, z, lines, playerid);
    }

    private class CanaryEnableCoreCallback extends DynmapCore.EnableCoreCallbacks {
        @Override
        public void configurationLoaded() {
        }
    }
}
