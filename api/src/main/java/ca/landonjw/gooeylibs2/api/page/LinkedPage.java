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
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Represents a page that is intended for pagination.
 * <p>
 * This acts as a doubly linked list, allowing for navigation between a chain of pages.
 *
 * @author landonjw
 */
public class LinkedPage extends GooeyPage {

    public static final String CURRENT_PAGE_PLACEHOLDER = "{current}";
    public static final String TOTAL_PAGES_PLACEHOLDER = "{total}";

    private LinkedPage previous;
    private LinkedPage next;

    public LinkedPage(@NotNull Template template,
                      @Nullable InventoryTemplate inventoryTemplate,
                      @Nullable Component title,
                      @Nullable Consumer<PageAction> onOpen,
                      @Nullable Consumer<PageAction> onClose,
                      @Nullable LinkedPage previous,
                      @Nullable LinkedPage next) {
        super(template, inventoryTemplate, title, onOpen, onClose);
        this.previous = previous;
        this.next = next;
    }

    public LinkedPage getPrevious() {
        return previous;
    }

    public void setPrevious(LinkedPage previous) {
        this.previous = previous;
        update();
    }

    public LinkedPage getNext() {
        return next;
    }

    public void setNext(LinkedPage next) {
        this.next = next;
        update();
    }

    public int getCurrentPage() {
        return (previous != null) ? previous.getCurrentPage() + 1 : 1;
    }

    public int getTotalPages() {
        return (next != null) ? next.getTotalPages() : getCurrentPage();
    }

    @Override
    public Component getTitle() {
        return replace(
                replace(super.getTitle(), Pattern.compile(CURRENT_PAGE_PLACEHOLDER, Pattern.LITERAL), "" + getCurrentPage()),
                Pattern.compile(TOTAL_PAGES_PLACEHOLDER, Pattern.LITERAL),
                "" + getTotalPages()
        );
    }

    @Override
    public Template getTemplate() {
        return super.getTemplate();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GooeyPage.Builder {

        protected LinkedPage previousPage;
        protected LinkedPage nextPage;

        @Override
        public Builder title(@Nullable String title) {
            super.title(title);
            return this;
        }

        @Override
        public Builder title(@Nullable Component title) {
            super.title(title);
            return this;
        }

        @Override
        public Builder template(@NotNull Template template) {
            super.template(template);
            return this;
        }

        @Override
        public Builder inventory(@Nullable InventoryTemplate template) {
            super.inventory(template);
            return this;
        }

        @Override
        public Builder onOpen(@Nullable Consumer<PageAction> behaviour) {
            super.onOpen(behaviour);
            return this;
        }

        @Override
        public Builder onOpen(@Nullable Runnable behaviour) {
            super.onOpen(behaviour);
            return this;
        }

        @Override
        public Builder onClose(@Nullable Consumer<PageAction> behaviour) {
            super.onClose(behaviour);
            return this;
        }

        @Override
        public Builder onClose(@Nullable Runnable behaviour) {
            super.onClose(behaviour);
            return this;
        }

        public Builder nextPage(@Nullable LinkedPage next) {
            this.nextPage = next;
            return this;
        }

        public Builder previousPage(@Nullable LinkedPage previous) {
            this.previousPage = previous;
            return this;
        }

        public LinkedPage build() {
            validate();
            return new LinkedPage(template, inventoryTemplate, title, onOpen, onClose, previousPage, nextPage);
        }

    }

    private Component replace(Component parent, Pattern pattern, String replacement) {
        MutableComponent result;
        if(parent instanceof MutableComponent stc) {
            String content = stc.getString();
            if (!content.isEmpty()) {
                Matcher matcher = pattern.matcher(content);
                if (matcher.find()) {
                    content = matcher.replaceAll(replacement);
                }

                result = Component.literal(content);
                result.setStyle(parent.getStyle());
            } else {
                result = Component.literal(stc.getString());
                result.setStyle(parent.getStyle());
            }
        } else {
            result = parent.copy();
            result.setStyle(parent.getStyle());
        }

        List<MutableComponent> children = parent.getSiblings().stream()
                .filter(c -> c instanceof MutableComponent)
                .map(MutableComponent.class::cast)
                .collect(Collectors.toList());
        for(MutableComponent child : children) {
            result.append(this.replace(child, pattern, replacement));
        }

        return result;
    }

}
