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

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public enum TemplateType {
    CHEST(template -> {
        switch (template.getSize() / 9) {
            case 1:
                return MenuType.GENERIC_9x1;
            case 2:
                return MenuType.GENERIC_9x2;
            case 3:
                return MenuType.GENERIC_9x3;
            case 4:
                return MenuType.GENERIC_9x4;
            case 5:
                return MenuType.GENERIC_9x5;
            default:
                return MenuType.GENERIC_9x6;
        }
    }),
    FURNACE(template -> MenuType.FURNACE),
    BREWING_STAND(template -> MenuType.BREWING_STAND),
    HOPPER(template -> MenuType.HOPPER),
    DISPENSER(template -> MenuType.GENERIC_3x3),
    CRAFTING_TABLE(template -> MenuType.CRAFTING);

    private final Function<Template, MenuType<? extends AbstractContainerMenu>> containerTypeSupplier;

    TemplateType(@NotNull Function<Template, MenuType<? extends AbstractContainerMenu>> containerTypeSupplier) {
        this.containerTypeSupplier = containerTypeSupplier;
    }

    public MenuType<? extends AbstractContainerMenu> getContainerType(@NotNull Template template) {
        return containerTypeSupplier.apply(template);
    }

}