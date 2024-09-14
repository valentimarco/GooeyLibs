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

public enum FlagType
{
    /**
     * ENCHANTS only hides tool enchants, as book ones are classified as stored enchantments.
     * EXTRAS hides multiple things, according to the minecraft wiki (https://minecraft.fandom.com/wiki/Tutorials/Command_NBT_tags)
     */
    Reforged(0),
    Generations(0),
    Enchantments(1),
    Attribute_Modifiers(2),
    Unbreakable(4),
    Can_Destroy(8),
    Can_Place_On(16),
    Extras(32),
    Dyed_Leather(64),
    All(127);

    private final int value;

    FlagType(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }
}
