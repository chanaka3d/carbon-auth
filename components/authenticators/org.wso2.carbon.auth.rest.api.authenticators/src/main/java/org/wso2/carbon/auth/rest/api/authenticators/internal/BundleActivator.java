/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.auth.rest.api.authenticators.internal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.auth.rest.api.authenticators.SecurityConfigurationService;
import org.wso2.carbon.auth.rest.api.authenticators.interceptors.RESTAPISecurityInterceptor;
import org.wso2.carbon.auth.rest.api.authenticators.interceptors.RestAPIAuthCORSInterceptor;
import org.wso2.carbon.config.provider.ConfigProvider;
import org.wso2.msf4j.interceptor.OSGiInterceptorConfig;

/**
 * Bundle activator component responsible for retrieving the JNDIContextManager OSGi service
 * and reading its datasource configuration.
 */
@Component(
        name = "org.wso2.carbon.auth.rest.api.authenticators",
        service = OSGiInterceptorConfig.class,
        immediate = true
)
public class BundleActivator extends OSGiInterceptorConfig {

    private static final Logger log = LoggerFactory.getLogger(BundleActivator.class);

    @Activate
    protected void start(BundleContext bundleContext) {

        addGlobalRequestInterceptors(new RestAPIAuthCORSInterceptor(), new RESTAPISecurityInterceptor());
        bundleContext.registerService(SecurityConfigurationService.class, SecurityConfigurationService.getInstance(),
                null);
    }

    /**
     * Get the ConfigProvider service.
     * This is the bind method that gets called for ConfigProvider service registration that satisfy the policy.
     *
     * @param configProvider the ConfigProvider service that is registered as a service.
     */
    @Reference(
            name = "carbon.config.provider",
            service = ConfigProvider.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unregisterConfigProvider")
    protected void registerConfigProvider(ConfigProvider configProvider) {

        ServiceReferenceHolder.getInstance().setConfigProvider(configProvider);

    }

    /**
     * This is the unbind method for the above reference that gets called for ConfigProvider instance un-registrations.
     *
     * @param configProvider the ConfigProvider service that get unregistered.
     */
    protected void unregisterConfigProvider(ConfigProvider configProvider) {

        ServiceReferenceHolder.getInstance().setConfigProvider(null);
    }
}
