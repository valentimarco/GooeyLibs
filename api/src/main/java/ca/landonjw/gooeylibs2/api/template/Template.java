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

package ca.landonjw.gooeylibs2.api.template;

import ca.landonjw.gooeylibs2.api.data.UpdateEmitter;
import ca.landonjw.gooeylibs2.api.template.slot.TemplateSlotDelegate;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Template extends UpdateEmitter<Template> {

    protected final TemplateType templateType;
    private final TemplateSlotDelegate[] slots;

    protected Template(@NotNull TemplateType templateType, @NotNull TemplateSlotDelegate[] slots) {
        this.templateType = templateType;
        this.slots = slots;
    }

    public final TemplateType getTemplateType() {
        return this.templateType;
    }

    public final int getSize() {
        return this.slots.length;
    }

    public final TemplateSlotDelegate getSlot(int index) {
        return this.slots[index];
    }

    public final List<TemplateSlotDelegate> getSlots() {
        return ImmutableList.copyOf(this.slots);
    }

    public final void setSlot(int index, TemplateSlotDelegate slot) {
        this.slots[index] = slot;
    }

    public abstract Template clone();

}