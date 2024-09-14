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
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TemplateSlot extends Slot {

    private final TemplateSlotDelegate delegate;

    public TemplateSlot(Container container, @NotNull TemplateSlotDelegate delegate, int xPosition, int yPosition) {
        super(container, delegate.getIndex(), xPosition, yPosition);
        this.delegate = delegate;
    }

    public TemplateSlotDelegate getDelegate() {
        return this.delegate;
    }

    public void setButton(@Nullable Button button) {
        this.delegate.setButton(button);
    }

    @Override
    public ItemStack getItem() {
        return this.delegate.getButton().map(Button::getDisplay).orElse(ItemStack.EMPTY);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }

}