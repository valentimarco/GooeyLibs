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

import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public class EventEmitter<T> implements Subject<T> {

    private final Map<Object, Consumer<T>> observers = Maps.newHashMap();

    @Override
    public void subscribe(@NotNull Object subscriber, @NotNull Consumer<T> consumer) {
        this.observers.put(subscriber, consumer);
    }

    @Override
    public void unsubscribe(@NotNull Object subscriber) {
        this.observers.remove(subscriber);
    }

    public void emit(T value) {
        this.observers.values().forEach((observer) -> observer.accept(value));
    }

}