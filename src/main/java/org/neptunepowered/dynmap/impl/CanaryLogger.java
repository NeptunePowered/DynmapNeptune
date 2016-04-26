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

import net.canarymod.logger.Logman;
import org.dynmap.utils.DynmapLogger;

public class CanaryLogger implements DynmapLogger {

    private final Logman logman;

    public CanaryLogger(Logman logman) {
        this.logman = logman;
    }

    @Override
    public void info(String msg) {
        this.logman.info(msg);
    }

    @Override
    public void verboseinfo(String msg) {
        this.logman.info(msg);
    }

    @Override
    public void severe(Throwable e) {
        this.logman.error(e);
    }

    @Override
    public void severe(String msg) {
        this.logman.error(msg);
    }

    @Override
    public void severe(String msg, Throwable e) {
        this.logman.error(msg, e);
    }

    @Override
    public void warning(String msg) {
        this.logman.warn(msg);
    }

    @Override
    public void warning(String msg, Throwable e) {
        this.logman.warn(msg, e);
    }
}
