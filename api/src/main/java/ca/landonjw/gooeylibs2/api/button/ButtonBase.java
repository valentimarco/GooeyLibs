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

import ca.landonjw.gooeylibs2.api.data.UpdateEmitter;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

public abstract class ButtonBase extends UpdateEmitter<Button> implements Button {

    private ItemStack display;

    protected ButtonBase(@NotNull ItemStack display) {
        this.display = requireNonNull(display);
    }

    public final ItemStack getDisplay() {
        return display;
    }

    public void setDisplay(@NotNull ItemStack display) {
        this.display = display;
        update();
    }

}