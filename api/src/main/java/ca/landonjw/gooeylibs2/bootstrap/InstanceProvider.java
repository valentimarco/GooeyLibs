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

package ca.landonjw.gooeylibs2.bootstrap;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public interface InstanceProvider {

    /**
     * Attempts to provide a factory instance responsible for creating new versions
     * of a target typing.
     *
     * @param type The type of factory this manager should provide
     * @return The instance representing the target factory
     * @param <T> The type of factory
     * @throws NoSuchElementException If the target factory does not exist
     */
    <T> T provide(Class<T> type) throws NoSuchElementException;

    /**
     * Registers a factory to this manager under the specified typing. This typing should
     * match that of the target instance.
     *
     * @param type The class type of the factory
     * @param instance An actual instance of the factory
     * @return <code>true</code> if the factory instance was accepted, <code>false</code> otherwise
     * @param <T> The typing of the factory
     */
    <T> boolean register(Class<T> type, T instance);

    /**
     * Registers a factory to this manager under the specified typing. This typing should
     * match that of the target instance.
     *
     * @param type The class type of the factory
     * @param instance A supplier of an instance, useful for unique objects per request
     * @return <code>true</code> if the factory instance was accepted, <code>false</code> otherwise
     * @param <T> The typing of the factory
     */
    <T> boolean register(Class<T> type, Supplier<T> instance);

}
