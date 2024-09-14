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

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimitedButton extends ButtonBase {

    private final Button button;
    private final int limit;
    private final long timeInterval;
    private final TimeUnit timeUnit;

    private final LinkedList<Instant> lastActionTimes = new LinkedList<>();

    protected RateLimitedButton(@NotNull Button button, int limit, long timeInterval, @NotNull TimeUnit timeUnit) {
        super(button.getDisplay());
        this.button = button;
        this.limit = limit;
        this.timeInterval = timeInterval;
        this.timeUnit = timeUnit;
    }

    @Override
    public void onClick(@NotNull ButtonAction action) {
        if (isRateLimited()) return;

        if (isTopElementAboveTimeThreshold()) lastActionTimes.remove();
        button.onClick(action);
        lastActionTimes.add(Instant.now());
    }

    public boolean isRateLimited() {
        if (lastActionTimes.size() < limit) return false;
        return !isTopElementAboveTimeThreshold();
    }

    public int getAllowedRemainingClicks() {
        AtomicInteger allowed = new AtomicInteger(limit);
        lastActionTimes.forEach(instant -> {
            if (Duration.between(instant, Instant.now()).toMillis() <= timeUnit.toMillis(timeInterval)) {
                allowed.getAndDecrement();
            }
        });
        return allowed.get();
    }

    private boolean isTopElementAboveTimeThreshold() {
        if (lastActionTimes.isEmpty()) return false;
        Instant earliestActionTime = lastActionTimes.peek();
        return Duration.between(earliestActionTime, Instant.now()).toMillis() > timeUnit.toMillis(timeInterval);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Button button;
        private int limit;
        private long timeInterval;
        private TimeUnit timeUnit;

        public Builder button(Button button) {
            this.button = button;
            return this;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder interval(long time, @NotNull TimeUnit timeUnit) {
            this.timeInterval = time;
            this.timeUnit = timeUnit;
            return this;
        }

        public RateLimitedButton build() {
            return new RateLimitedButton(button, limit, timeInterval, timeUnit);
        }

    }

}
