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

import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.InventoryTemplate;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GooeyPage extends PageBase {

    private final Consumer<PageAction> onOpen, onClose;

    public GooeyPage(@NotNull Template template,
                     @Nullable InventoryTemplate inventoryTemplate,
                     @Nullable Component title,
                     @Nullable Consumer<PageAction> onOpen,
                     @Nullable Consumer<PageAction> onClose) {
        super(template, inventoryTemplate, title);
        this.onOpen = onOpen;
        this.onClose = onClose;
    }

    @Override
    public void onOpen(@NotNull PageAction action) {
        if (onOpen != null) onOpen.accept(action);
    }

    @Override
    public void onClose(@NotNull PageAction action) {
        if (onClose != null) onClose.accept(action);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        protected Component title;
        protected Template template;
        protected InventoryTemplate inventoryTemplate;
        protected Consumer<PageAction> onOpen, onClose;

        public Builder title(@Nullable String title) {
            if(title == null) {
                return this;
            }

            return this.title(Component.literal(title));
        }

        public Builder title(@Nullable Component title) {
            this.title = title;
            return this;
        }

        public Builder template(@NotNull Template template) {
            if (template instanceof InventoryTemplate) {
                throw new IllegalArgumentException("you can not use an inventory template here!");
            }

            this.template = template;
            return this;
        }

        public Builder inventory(@Nullable InventoryTemplate template) {
            this.inventoryTemplate = template;
            return this;
        }

        public Builder onOpen(@Nullable Consumer<PageAction> behaviour) {
            this.onOpen = behaviour;
            return this;
        }

        public Builder onOpen(@Nullable Runnable behaviour) {
            if (behaviour == null) {
                this.onOpen = null;
            } else {
                onOpen((action) -> behaviour.run());
            }
            return this;
        }

        public Builder onClose(@Nullable Consumer<PageAction> behaviour) {
            this.onClose = behaviour;
            return this;
        }

        public Builder onClose(@Nullable Runnable behaviour) {
            if (behaviour == null) {
                this.onClose = null;
            } else {
                onClose((action) -> behaviour.run());
            }
            return this;
        }

        public GooeyPage build() {
            validate();
            return new GooeyPage(template, inventoryTemplate, title, onOpen, onClose);
        }

        protected void validate() {
            if (template == null) {
                throw new IllegalStateException("template must be defined");
            }
        }

    }

}