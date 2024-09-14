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

public abstract class UpdateEmitter<T> implements Subject<T> {

    private final EventEmitter<T> eventEmitter = new EventEmitter<>();

    @SuppressWarnings("unchecked")
    public UpdateEmitter() {
        try {
            T check = (T) this;
        } catch (ClassCastException e) {
            throw new IllegalStateException("bad generic given for superclass");
        }
    }

    @Override
    public void subscribe(@NotNull Object observer, @NotNull Consumer<T> consumer) {
        this.eventEmitter.subscribe(observer, consumer);
    }

    @Override
    public void unsubscribe(@NotNull Object observer) {
        this.eventEmitter.unsubscribe(observer);
    }

    @SuppressWarnings("unchecked")
    public void update() {
        this.eventEmitter.emit((T) this);
    }

}
