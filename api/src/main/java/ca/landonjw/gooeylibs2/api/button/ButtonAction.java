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

import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class ButtonAction {

    private final ServerPlayer player;
    private final ButtonClick clickType;
    private final Button button;
    private final Template template;
    private final Page page;
    private final int slot;

    public ButtonAction(@NotNull ServerPlayer player,
                        @NotNull ButtonClick clickType,
                        @NotNull Button button,
                        @NotNull Template template,
                        @NotNull Page page,
                        int slot) {
        this.player = Objects.requireNonNull(player);
        this.clickType = Objects.requireNonNull(clickType);
        this.button = Objects.requireNonNull(button);
        this.template = Objects.requireNonNull(template);
        this.page = Objects.requireNonNull(page);
        this.slot = slot;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public ButtonClick getClickType() {
        return clickType;
    }

    public Button getButton() {
        return button;
    }

    public Template getTemplate() {
        return template;
    }

    public Page getPage() {
        return page;
    }

    public int getSlot() {
        return slot;
    }

    public boolean isSlotInInventory() {
        return page.getInventoryTemplate().isPresent() && slot >= template.getSize();
    }

    public Optional<Integer> getInventorySlot() {
        if (isSlotInInventory()) {
            return Optional.of(slot - template.getSize());
        } else {
            return Optional.empty();
        }
    }

}