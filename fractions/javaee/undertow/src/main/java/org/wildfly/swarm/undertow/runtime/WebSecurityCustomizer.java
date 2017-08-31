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
package org.wildfly.swarm.undertow.runtime;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.wildfly.swarm.config.Security;
import org.wildfly.swarm.config.security.Flag;
import org.wildfly.swarm.config.security.SecurityDomain;
import org.wildfly.swarm.config.security.security_domain.ClassicAuthorization;
import org.wildfly.swarm.config.security.security_domain.authorization.PolicyModule;
import org.wildfly.swarm.spi.api.Customizer;
import org.wildfly.swarm.spi.runtime.annotations.Post;

@Post
@ApplicationScoped
public class WebSecurityCustomizer implements Customizer {

    @Inject
    private Instance<Security> securityInstance;

    @Override
    public void customize() {
        if (!securityInstance.isUnsatisfied()) {
            Security security = securityInstance.get();

            SecurityDomain webPolicy = security.subresources().securityDomains().stream().filter((e) -> e.getKey().equals("jboss-web-policy")).findFirst().orElse(null);
            if (webPolicy == null) {
                webPolicy = new SecurityDomain("jboss-web-policy")
                        .cacheType(SecurityDomain.CacheType.DEFAULT)
                        .classicAuthorization(new ClassicAuthorization()
                                .policyModule(new PolicyModule("Delegating")
                                        .code("Delegating")
                                        .flag(Flag.REQUIRED)));
                security.securityDomain(webPolicy);
            }
        }
    }
}
