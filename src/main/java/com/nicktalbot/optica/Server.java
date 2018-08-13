package com.nicktalbot.optica;

import com.google.common.collect.ImmutableList;
import com.nicktalbot.optica.data.Customer;
import lombok.extern.slf4j.Slf4j;

import static com.nicktalbot.optica.helpers.JsonRoute.getJson;
import static spark.Spark.port;

@Slf4j
public class Server {

    public static void main(String[] args) {

        log.info("Started Optica Server ...");

        run();
    }

    public static void run() {

        port(8080);

        getJson("/customers", (req, res) -> ImmutableList.of(
                new Customer(1, "John", "Smith"),
                new Customer(2, "Dave", "Jones")
        ));
    }
}
