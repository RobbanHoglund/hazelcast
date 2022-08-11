/*
 * Copyright (c) 2008-2022, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.config;

import com.hazelcast.datastore.ExternalDataStoreFactory;
import com.hazelcast.jet.pipeline.BatchSource;
import com.hazelcast.jet.pipeline.Sink;
import com.hazelcast.map.MapLoader;
import com.hazelcast.map.MapStore;
import com.hazelcast.spi.annotation.Beta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Properties;

import static com.hazelcast.internal.util.Preconditions.checkHasText;
import static com.hazelcast.internal.util.Preconditions.checkNotNull;

/**
 * Contains configuration of an external data store that can be used as a
 * <ul>
 * <li>{@link BatchSource} and {@link Sink} in Jet JDBC connector.</li>
 * <li>Datastore for {@link MapStore} and {@link MapLoader} </li>
 * </ul>
 *
 * @since 5.2
 */
@Beta
public class ExternalDataStoreConfig implements NamedConfig {

    private String name;
    private String className;
    private boolean shared = true;
    private Properties properties = new Properties();

    public ExternalDataStoreConfig() {
    }

    public ExternalDataStoreConfig(ExternalDataStoreConfig config) {
        name = config.name;
        className = config.className;
        shared = config.shared;
        properties.putAll(config.getProperties());
    }

    public ExternalDataStoreConfig(String name) {
        this.name = checkNotNull(name, "Name must not be null");
    }

    @Override
    public ExternalDataStoreConfig setName(String name) {
        this.name = checkNotNull(name, "Name must not be null");
        return this;
    }

    public String getName() {
        return name;
    }


    /**
     * Returns the name of the MapStore implementation class
     *
     * @return the name of the MapStore implementation class
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the name for the MapStore implementation class
     *
     * @param className the name to set for the MapStore implementation class
     */
    public ExternalDataStoreConfig setClassName(@Nonnull String className) {
        this.className = checkHasText(className, "Data store class name must contain text");
        return this;
    }

    /**
     * {@code true} if an instance of the external data store will be reused. {@code false} when on each usage
     * the data store instance should be created. The default is {@code true}
     *
     * @return if the data store instance should be reused
     */
    public boolean isShared() {
        return shared;
    }

    /**
     * {@code true} if an instance of the external data store will be reused. {@code false} when on each usage
     * the data store instance should be created
     *
     * @param shared if the data store instance should be reused
     * @return this ExternalDataStoreConfig
     */
    public ExternalDataStoreConfig setShared(boolean shared) {
        this.shared = shared;
        return this;
    }

    /**
     * Returns all the properties
     *
     * @return all the properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Returns single the property
     *
     * @param key the property key
     * @return property value or null if the given key doesn't exist
     */
    @Nullable
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Sets the properties of a datastore. See implementations of {@link ExternalDataStoreFactory}
     * for supported values
     *
     * @param properties the properties to be set
     * @return this ExternalDataStoreConfig
     */
    public ExternalDataStoreConfig setProperties(Properties properties) {
        this.properties = checkNotNull(properties, "Data store properties cannot be null!");
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExternalDataStoreConfig that = (ExternalDataStoreConfig) o;
        return shared == that.shared && Objects.equals(name, that.name)
                && Objects.equals(className, that.className)
                && Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, className, shared, properties);
    }

    @Override
    public String toString() {
        return "ExternalDataStoreConfig{"
                + "name='" + name + '\''
                + ", className='" + className + '\''
                + ", shared=" + shared
                + ", properties=" + properties
                + '}';
    }
}