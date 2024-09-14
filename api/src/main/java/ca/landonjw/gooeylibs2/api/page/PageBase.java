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

package ca.landonjw.gooeylibs2.api.page;

import ca.landonjw.gooeylibs2.api.data.EventEmitter;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.InventoryTemplate;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public abstract class PageBase implements Page {

    private final EventEmitter<Page> eventEmitter = new EventEmitter<>();
    private Template template;
    private InventoryTemplate inventoryTemplate;
    private Component title;

    public PageBase(@NotNull Template template,
                    @Nullable InventoryTemplate inventoryTemplate,
                    @Nullable Component title) {
        this.template = template;
        this.inventoryTemplate = inventoryTemplate;
        this.title = (title != null) ? title : Component.empty();
    }

    public Template getTemplate() {
        if (template == null) throw new IllegalStateException("template could not be found on the page!");
        return template;
    }

    public void setTemplate(@NotNull Template template) {
        this.template = template;
        update();
    }

    @Override
    public Optional<InventoryTemplate> getInventoryTemplate() {
        return Optional.ofNullable(inventoryTemplate);
    }

    public void setPlayerInventoryTemplate(@Nullable InventoryTemplate inventoryTemplate) {
        this.inventoryTemplate = inventoryTemplate;
    }

    public Component getTitle() {
        return this.title;
    }

    public void setTitle(@Nullable String title) {
        this.setTitle(title == null ? null : Component.literal(title));
    }

    public void setTitle(@Nullable Component title) {
        this.title = (title == null) ? Component.empty() : title;
        update();
    }

    public void subscribe(@NotNull Object observer, @NotNull Consumer<Page> consumer) {
        this.eventEmitter.subscribe(observer, consumer);
    }

    public void unsubscribe(@NotNull Object observer) {
        this.eventEmitter.unsubscribe(observer);
    }

    public void update() {
        this.eventEmitter.emit(this);
    }

}