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

package unomodding.canary.cynmap;

import net.canarymod.Canary;
import net.canarymod.api.world.World;
import net.canarymod.plugin.Plugin;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.DynmapCore;
import org.dynmap.common.DynmapListenerManager.EventType;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.modsupport.ModSupportImpl;
import unomodding.canary.cynmap.data.Constants;
import unomodding.canary.cynmap.implementation.CanaryServer;
import unomodding.canary.cynmap.implementation.CanaryWorld;

import java.io.File;

public class CynmapPlugin extends Plugin implements DynmapCommonAPI {
    private DynmapCore core;
    private CanaryServer server;
    private CanaryEnableCoreCallback config = new CanaryEnableCoreCallback();

    public CynmapPlugin() {
        ModSupportImpl.init();
    }

    @Override
    public boolean enable() {
        // init server
        server = new CanaryServer(Canary.getServer(), this);
        // init data folder
        Constants.checkDirs();
        // init core
        if (core == null) {
            core = new DynmapCore();
        }
        core.setPluginJarFile(new File(getPath()));
        core.setPluginVersion(getDescriptor().getVersion(), "CanaryLib");
        core.setMinecraftVersion(Canary.getServer().getServerVersion());
        core.setDataFolder(Constants.dataFolder);
        core.setServer(server);
        core.setTriggerDefault(null);
        core.setBiomeNames(null);
        core.setBlockNames(null);
        core.setBlockMaterialMap(null);

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
        Canary.hooks().registerListener(new CynmapListener(this), this);
        
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

    public MarkerAPI getMarkerAPI() {
        return core.getMarkerAPI();
    }

    public boolean markerAPIInitialized() {
        return core.markerAPIInitialized();
    }

    public boolean sendBroadcastToWeb(String sender, String msg) {
        return core.sendBroadcastToWeb(sender, msg);
    }

    public int triggerRenderOfVolume(String wid, int minx, int miny, int minz, int maxx, int maxy, int maxz) {
        return core.triggerRenderOfVolume(wid, minx, miny, minz, maxx, maxy, maxz);
    }

    public int triggerRenderOfBlock(String wid, int x, int y, int z) {
        return core.triggerRenderOfBlock(wid, x, y, z);
    }

    public void setPauseFullRadiusRenders(boolean dopause) {
        core.setPauseFullRadiusRenders(dopause);
    }

    public boolean getPauseFullRadiusRenders() {
        return core.getPauseFullRadiusRenders();
    }

    public void setPauseUpdateRenders(boolean dopause) {
        core.setPauseFullRadiusRenders(dopause);
    }

    public boolean getPauseUpdateRenders() {
        return core.getPauseUpdateRenders();
    }

    public void setPlayerVisiblity(String player, boolean is_visible) {
        core.setPlayerVisiblity(player, is_visible);
    }

    public boolean getPlayerVisbility(String player) {
        return core.getPlayerVisbility(player);
    }

    public void assertPlayerInvisibility(String player, boolean is_invisible, String plugin_id) {
        core.assertPlayerInvisibility(player, is_invisible, plugin_id);
    }

    public void assertPlayerVisibility(String player, boolean is_visible, String plugin_id) {
        core.assertPlayerVisibility(player, is_visible, plugin_id);
    }

    public void postPlayerMessageToWeb(String playerid, String playerdisplay, String message) {
        core.postPlayerMessageToWeb(playerid, playerdisplay, message);
    }

    public void postPlayerJoinQuitToWeb(String playerid, String playerdisplay, boolean isjoin) {
        core.postPlayerJoinQuitToWeb(playerid, playerdisplay, isjoin);
    }

    public String getDynmapCoreVersion() {
        return core.getDynmapCoreVersion();
    }

    public boolean setDisableChatToWebProcessing(boolean disable) {
        return core.setDisableChatToWebProcessing(disable);
    }

    public boolean testIfPlayerVisibleToPlayer(String player, String player_to_see) {
        return core.testIfPlayerVisibleToPlayer(player, player_to_see);
    }

    public boolean testIfPlayerInfoProtected() {
        return core.testIfPlayerInfoProtected();
    }

    public void processSignChange(int blkid, String world, int x, int y, int z, String[] lines, String playerid) {
        core.processSignChange(blkid, world, x, y, z, lines, playerid);
    }

    private class CanaryEnableCoreCallback extends DynmapCore.EnableCoreCallbacks {
        @Override
        public void configurationLoaded() {
        }
    }
}
