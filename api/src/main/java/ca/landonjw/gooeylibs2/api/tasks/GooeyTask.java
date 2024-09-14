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

public final class GooeyTask implements Task {

    private final Consumer<Task> consumer;
    private final TaskConfiguration configuration;

    private long iteration;
    private long ticksRemaining;
    private boolean expired;

    private GooeyTask(final GooeyTaskBuilder builder) {
        this.consumer = builder.consumer;
        this.configuration = new TaskConfiguration(builder.interval, builder.iterations);
        if(builder.delay > 0) {
            this.ticksRemaining = builder.delay;
        }
    }

    @Override
    public boolean isExpired() {
        return this.expired;
    }

    @Override
    public void setExpired() {
        this.expired = true;
    }

    void tick() {
        if(!this.expired) {
            this.ticksRemaining = Math.max(0, --ticksRemaining);

            if (ticksRemaining == 0) {
                this.consumer.accept(this);
                this.iteration++;

                long iterations = this.configuration.iterations;
                if (this.configuration.interval > 0 && (this.iteration < iterations || iterations == -1)) {
                    ticksRemaining = this.configuration.interval;
                } else {
                    expired = true;
                }
            }
        }
    }

    private record TaskConfiguration(long interval, long iterations) {}

    public static final class GooeyTaskBuilder implements TaskBuilder {

        private Consumer<Task> consumer;
        private long delay;
        private long interval;
        private long iterations = 1;

        @Override
        public TaskBuilder execute(@NotNull Runnable runnable) {
            this.consumer = (task) -> runnable.run();
            return this;
        }

        @Override
        public TaskBuilder execute(@NotNull Consumer<Task> consumer) {
            this.consumer = consumer;
            return this;
        }

        @Override
        public TaskBuilder delay(long delay) {
            if (delay < 0) {
                throw new IllegalArgumentException("delay must not be below 0");
            }
            this.delay = delay;
            return this;
        }

        @Override
        public TaskBuilder interval(long interval) {
            if (interval < 0) {
                throw new IllegalArgumentException("interval must not be below 0");
            }
            this.interval = interval;
            return this;
        }

        @Override
        public TaskBuilder iterations(long iterations) {
            if (iterations < -1) {
                throw new IllegalArgumentException("iterations must not be below -1");
            }
            this.iterations = iterations;
            return this;
        }

        @Override
        public TaskBuilder infinite() {
            return this.iterations(-1);
        }

        @Override
        public Task build() {
            return new GooeyTask(this);
        }
    }
}
