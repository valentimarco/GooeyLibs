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

package ca.landonjw.gooeylibs2.api.template.slot;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.data.EventEmitter;
import ca.landonjw.gooeylibs2.api.data.Subject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public final class TemplateSlotDelegate implements Subject<TemplateSlotDelegate> {

    private final EventEmitter<TemplateSlotDelegate> eventEmitter = new EventEmitter<>();
    private Button button;
    private final int index;

    public TemplateSlotDelegate(@Nullable Button button, int index) {
        this.button = button;
        this.index = index;
    }

    public Optional<Button> getButton() {
        return Optional.ofNullable(this.button);
    }

    public void setButton(@Nullable Button button) {
        if (this.button != null) {
            this.button.unsubscribe(this);
        }

        this.button = button;
        if (this.button != null) {
            this.button.subscribe(this, this::update);
        }

        this.update();
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public void subscribe(@NotNull Object observer, @NotNull Consumer<TemplateSlotDelegate> consumer) {
        this.eventEmitter.subscribe(observer, consumer);
    }

    @Override
    public void unsubscribe(@NotNull Object observer) {
        this.eventEmitter.unsubscribe(observer);
    }

    public void update() {
        this.eventEmitter.emit(this);
    }
}
