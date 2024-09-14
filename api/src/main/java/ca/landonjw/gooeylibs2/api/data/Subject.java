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

package ca.landonjw.gooeylibs2.api.data;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface Subject<T> {

    void subscribe(@NotNull Object observer, @NotNull Consumer<T> consumer);

    default void subscribe(@NotNull Object observer, @NotNull Runnable runnable) {
        this.subscribe(observer, t -> runnable.run());
    }

    void unsubscribe(@NotNull Object observer);

}