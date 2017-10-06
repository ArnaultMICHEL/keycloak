/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
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
package org.keycloak.connections.httpclient;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.protocol.HttpContext;
import org.jboss.logging.Logger;

/**
 * A {@link DefaultRoutePlanner} that determines the proxy to use for a given target hostname by consulting a {@link ProxyMapping}.
 *
 * @author <a href="mailto:thomas.darimont@gmail.com">Thomas Darimont</a>
 */
public class ProxyMappingAwareRoutePlanner extends DefaultRoutePlanner {

  private static final Logger LOG = Logger.getLogger(ProxyMappingAwareRoutePlanner.class);

  private final ProxyMapping proxyMapping;

  public ProxyMappingAwareRoutePlanner(ProxyMapping proxyMapping) {
    super(DefaultSchemePortResolver.INSTANCE);
    this.proxyMapping = proxyMapping;
  }

  @Override
  protected HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {

    HttpHost proxy = proxyMapping.getProxyFor(target.getHostName());

    LOG.debugf("Returning proxy=%s for targetHost=%s", proxy ,target.getHostName());

    return proxy;
  }
}