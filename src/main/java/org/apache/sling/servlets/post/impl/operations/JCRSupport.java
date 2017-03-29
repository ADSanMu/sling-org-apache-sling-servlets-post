/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.servlets.post.impl.operations;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.servlets.post.Modification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JCRSupport {

    public static final JCRSupport INSTANCE = new JCRSupport();

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Object supportImpl;

    public JCRSupport() {
        Object impl = null;
        try {
            impl = new JCRSupportImpl();
        } catch ( final Throwable t) {
            logger.warn("Support for JCR operations like checkin, checkout, ordering etc. is currently disabled " +
                        "in the servlets post module. Check whether the JCR API is available.");
        }
        this.supportImpl = impl;
    }

    public void orderNode(final SlingHttpServletRequest request,
            final Resource resource,
            final List<Modification> changes) throws PersistenceException {
        if ( supportImpl != null ) {
            ((JCRSupportImpl)supportImpl).orderNode(request, resource, changes);
        }
    }

    public boolean checkin(final Resource rsrc)
    throws PersistenceException {
        if ( rsrc != null && supportImpl != null ) {
            return ((JCRSupportImpl)supportImpl).checkin(rsrc);
        }
        return false;
    }
}
