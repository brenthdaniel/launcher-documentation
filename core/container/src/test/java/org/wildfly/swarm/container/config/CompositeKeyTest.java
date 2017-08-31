/**
 * Copyright 2015-2017 Red Hat, Inc, and individual contributors.
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
package org.wildfly.swarm.container.config;

import org.junit.Test;
import org.wildfly.swarm.spi.api.config.CompositeKey;
import org.wildfly.swarm.spi.api.config.ConfigKey;
import org.wildfly.swarm.spi.api.config.SimpleKey;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Bob McWhirter
 */
public class CompositeKeyTest {

    @Test
    public void testHead() {
        CompositeKey key = new CompositeKey("foo", "bar", "baz");
        assertThat(key.head()).isEqualTo(new SimpleKey("foo"));
    }

    @Test
    public void testSubkey() {
        CompositeKey key = new CompositeKey("foo", "bar", "baz");
        assertThat(key.subkey(1).head()).isEqualTo(new SimpleKey("bar"));
        assertThat(key.subkey(2).head()).isEqualTo(new SimpleKey("baz"));
        assertThat(key.subkey(3).head()).isEqualTo(ConfigKey.EMPTY);
    }

    @Test
    public void testName() {
        CompositeKey key = new CompositeKey("foo", "bar", "baz");
        assertThat(key.name()).isEqualTo("foo.bar.baz");
    }
}
