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

package ca.landonjw.gooeylibs2.api.helpers;

import ca.landonjw.gooeylibs2.api.container.GooeyContainer;
import ca.landonjw.gooeylibs2.api.tasks.Task;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility functions that are used to conveniently modify player's inventories when using GooeyLibs pages.
 *
 * @author landonjw
 */
public class InventoryHelper {

    /**
     * Sets an itemstack in the player's inventory at a supplied inventory template slot.
     * This will automatically update opened pages of the player.
     *
     * @param player        the player to set inventory stack for
     * @param inventorySlot the inventory template slot to set itemstack at.
     *                      to get this, you should typically use ButtonAction#getInventorySlot.
     * @param stack         the itemstack to set in the player's inventory.
     */
    public static void setToInventorySlot(@NotNull ServerPlayer player, int inventorySlot, @Nullable ItemStack stack) {
        if (inventorySlot < 0) return;

        // Empty slots are always populated with ItemStack.EMPTY instead of null.
        if (stack == null) stack = ItemStack.EMPTY;

        /*
         * Offset hotbar and main inventory since their implementation differs from concept of template slots.
         * Hotbar in inventory is always placed before main inventory slots in player inventory, where
         * the hotbar in template is always after the main inventory slots.
         */
        if (inventorySlot >= 27) {
            player.getInventory().setItem(inventorySlot - 27, stack.copy());
        } else {
            player.getInventory().setItem(inventorySlot + 9, stack.copy());
        }

        if (player.containerMenu instanceof GooeyContainer) {
            GooeyContainer container = (GooeyContainer) player.containerMenu;
            Task.builder().execute(container::refresh).build();
        }
    }

    /**
     * Sets an itemstack in the player's inventory at a supplied row and column in the inventory.
     * This will automatically update opened pages of the player.
     *
     * @param player       the player to set inventory stack for
     * @param inventoryRow the row to set stack in, starting at 0. hotbar is 3rd row.
     * @param inventoryCol the column to set stack in, starting at 0
     * @param stack        the itemstack to set in the player's inventory.
     */
    public static void setToInventorySlot(@NotNull ServerPlayer player, int inventoryRow, int inventoryCol, @Nullable ItemStack stack) {
        setToInventorySlot(player, inventoryRow * 9 + inventoryCol, stack);
    }

    /**
     * Adds an itemstack in the player's inventory.
     * This will automatically update opened pages of the player.
     *
     * @param player the player to add inventory stack to
     * @param stack  the itemstack to add to player's inventory
     */
    public static void addToInventorySlot(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
        if (stack == ItemStack.EMPTY) return;

        player.getInventory().add(stack.copy());

        if (player.containerMenu instanceof GooeyContainer) {
            GooeyContainer container = (GooeyContainer) player.containerMenu;
            Task.builder().execute(container::refresh).build();
        }
    }

    /**
     * Gets the current itemstack in the player's inventory from a given inventory template slot.
     * If there is no item in that slot, this will return an ItemStack.EMPTY.
     *
     * @param player        the player to get itemstack from
     * @param inventorySlot the inventory template slot to get itemstack from
     * @return an itemstack at the given inventory template slot location, or ItemStack.EMPTY if no item in slot
     */
    @NotNull
    public static ItemStack getStackAtSlot(@NotNull ServerPlayer player, int inventorySlot) {
        /*
         * Offset hotbar and main inventory since their implementation differs from concept of template slots.
         * Hotbar in inventory is always placed before main inventory slots in player inventory, where
         * the hotbar in template is always after the main inventory slots.
         */
        if (inventorySlot >= 27) {
            return player.getInventory().getItem(inventorySlot - 27);
        } else {
            return player.getInventory().getItem(inventorySlot + 9);
        }
    }

}