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

package ca.landonjw.gooeylibs2.api;

import ca.landonjw.gooeylibs2.api.accessors.ServerPlayerAccessor;
import ca.landonjw.gooeylibs2.api.container.GooeyContainer;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.tasks.Task;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class UIManager {

    public static void openUIPassively(@NotNull ServerPlayer player, @NotNull Page page, long timeout, TimeUnit timeoutUnit) {
        AtomicLong timeOutTicks = new AtomicLong(timeoutUnit.convert(timeout, TimeUnit.SECONDS) * 20);
        Task.builder()
                .execute((task) -> {
                    timeOutTicks.getAndDecrement();
                    if (player.containerMenu.containerId == ((ServerPlayerAccessor) player).gooeylibs$getContainerCounter() || timeOutTicks.get() <= 0) {
                        openUIForcefully(player, page);
                        task.setExpired();
                    }
                })
                .infinite()
                .interval(1)
                .build();
    }

    public static void openUIForcefully(@NotNull ServerPlayer player, @NotNull Page page) {
        // Delay the open to allow sponge's annoying mixins to process previous container and not have aneurysm
        Task.builder()
                .execute(() -> {
                    try {
                        GooeyContainer container = new GooeyContainer(player, page);
                        container.open();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                })
                .build();
    }

    public static void closeUI(@NotNull ServerPlayer player) {
        Task.builder().execute(player::closeContainer).build();
    }

}
