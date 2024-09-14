/*
 * GooeyLibs
 * Copyright (C) 201x - 2024 landonjw
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package ca.landonjw.gooeylibs.bootstrap;

import ca.landonjw.gooeylibs2.api.tasks.TaskManager;
import ca.landonjw.gooeylibs2.bootstrap.BuilderProvider;
import ca.landonjw.gooeylibs2.bootstrap.GooeyBootstrapper;
import ca.landonjw.gooeylibs2.bootstrap.InstanceProvider;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public final class FabricEntrypoint implements ModInitializer, GooeyBootstrapper {

    private final TaskManager taskManager = new TaskManager();

    @Override
    public void onInitialize() {
        this.bootstrap();
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {

        });
    }

    @Override
    public void bootstrap() {

    }

    @Override
    public InstanceProvider provider() {
        return null;
    }

    @Override
    public BuilderProvider builders() {
        return null;
    }

    @Override
    public TaskManager taskManager() {
        return this.taskManager;
    }
}
