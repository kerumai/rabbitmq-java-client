//  The contents of this file are subject to the Mozilla Public License
//  Version 1.1 (the "License"); you may not use this file except in
//  compliance with the License. You may obtain a copy of the License
//  at http://www.mozilla.org/MPL/
//
//  Software distributed under the License is distributed on an "AS IS"
//  basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
//  the License for the specific language governing rights and
//  limitations under the License.
//
//  The Original Code is RabbitMQ.
//
//  The Initial Developer of the Original Code is VMware, Inc.
//  Copyright (c) 2007-2011 VMware, Inc.  All rights reserved.
//


package com.rabbitmq.examples;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SimpleProducer {
    public static void main(String[] args) {
        try {
            String hostName = (args.length > 0) ? args[0] : "localhost";
            int portNumber = (args.length > 1) ? Integer.parseInt(args[1]) : AMQP.PROTOCOL.PORT;
            String message = (args.length > 2) ? args[2] :
                "the time is " + new java.util.Date().toString();
            String exchange = (args.length > 3) ? args[3] : "";
            String routingKey = (args.length > 4) ? args[4] : "SimpleQueue";

            ConnectionFactory cfconn = new ConnectionFactory();
            cfconn.setHost(hostName);
            cfconn.setPort(portNumber);
            Connection conn = cfconn.newConnection();

            Channel ch = conn.createChannel();

            if (exchange.equals("")) {
                ch.queueDeclare(routingKey, false, false, false, null);
            }
            ch.basicPublish(exchange, routingKey, null, message.getBytes());
            ch.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("Main thread caught exception: " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
