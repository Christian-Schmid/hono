/*******************************************************************************
 * Copyright (c) 2016, 2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.hono.tests.amqp;

import java.util.function.Consumer;

import org.apache.qpid.proton.message.Message;
import org.eclipse.hono.client.MessageConsumer;
import org.junit.runner.RunWith;

import io.vertx.core.Future;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.proton.ProtonQoS;

/**
 * An Event based integration test for the AMQP adapter.
 */
@RunWith(VertxUnitRunner.class)
public class EventAmqpIT extends AmqpUploadTestBase {

    private static final String EVENT_ENDPOINT = "event";

    @Override
    protected Future<MessageConsumer> createConsumer(final String tenantId, final Consumer<Message> messageConsumer) {
        return helper.honoClient.createEventConsumer(tenantId, messageConsumer, close -> {});
    }

    @Override
    protected ProtonQoS getProducerQoS() {
        return ProtonQoS.AT_LEAST_ONCE;
    }

    @Override
    protected String getEndpointName() {
        return EVENT_ENDPOINT;
    }

    @Override
    protected void assertAdditionalMessageProperties(final TestContext ctx, final Message msg) {
        // assert that events are marked as "durable"
        ctx.assertTrue(msg.isDurable());
    }
}
