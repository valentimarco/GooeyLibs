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

package ca.landonjw.gooeylibs2.api.tasks;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class Task {

    private final Consumer<Task> consumer;
    private final long interval;
    private final long iterations;

    private long currentIteration;
    private long ticksRemaining;
    private boolean isExpired;

    public Task(Consumer<Task> consumer, long delay, long interval, long iterations) {
        this.consumer = consumer;
        this.interval = interval;
        this.iterations = iterations;

        if (delay > 0) {
            ticksRemaining = delay;
        }
        TaskManager.getInstance().register(this);
    }

    void tick() {
        if (isExpired) return;

        this.ticksRemaining--;
        if (ticksRemaining > 0) return;

        consumer.accept(this);
        currentIteration++;

        if (interval > 0 && (currentIteration < iterations || iterations == -1 )) {
            this.ticksRemaining = interval;
        }
        else {
            this.isExpired = true;
        }
    }

    public boolean isExpired() {
       return this.isExpired;
    }

    public void setExpired() {
        this.isExpired = true;
    }

    public static Task.Builder builder() {
        return new Task.Builder();
    }

    public final static class Builder {

        private Consumer<Task> consumer;
        private long delay;
        private long interval;
        private long iterations = 1;

        public Builder execute(@NotNull Runnable runnable) {
            this.consumer = (task) -> runnable.run();
            return this;
        }

        public Builder execute(@NotNull Consumer<Task> consumer) {
            this.consumer = consumer;
            return this;
        }

        public Builder delay(long delay) {
            if (delay < 0) {
                throw new IllegalArgumentException("delay must not be below 0");
            }
            this.delay = delay;
            return this;
        }

        public Builder interval(long interval) {
            if (interval < 0) {
                throw new IllegalArgumentException("interval must not be below 0");
            }
            this.interval = interval;
            return this;
        }

        public Builder iterations(long iterations) {
            if (iterations < -1) {
                throw new IllegalArgumentException("iterations must not be below -1");
            }
            this.iterations = iterations;
            return this;
        }

        public Builder infinite() {
            return iterations(-1);
        }

        public Task build() {
            if (consumer == null) {
                throw new IllegalStateException("consumer must be set");
            }
            return new Task(consumer, delay, interval, iterations);
        }

    }

}
