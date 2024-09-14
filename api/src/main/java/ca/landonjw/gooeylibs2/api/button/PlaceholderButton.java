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

package ca.landonjw.gooeylibs2.api.button;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PlaceholderButton implements Button {

    private final Button button;

    public PlaceholderButton(@NotNull Button button) {
        this.button = button;
    }

    public PlaceholderButton() {
        this(
                GooeyButton.builder()
                        .display(ItemStack.EMPTY)
                        .build()
        );
    }

    public static PlaceholderButton of(@NotNull Button button) {
        return new PlaceholderButton(button);
    }

    @Override
    public ItemStack getDisplay() {
        return button.getDisplay();
    }

    @Override
    public void onClick(@NotNull ButtonAction action) {
        button.onClick(action);
    }

    @Override
    public void subscribe(@NotNull Object observer, @NotNull Consumer<Button> consumer) {
        button.subscribe(observer, consumer);
    }

    @Override
    public void unsubscribe(@NotNull Object observer) {
        button.unsubscribe(observer);
    }

}