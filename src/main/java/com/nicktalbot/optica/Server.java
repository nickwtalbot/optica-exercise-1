package com.nicktalbot.optica;

import com.nicktalbot.optica.data.Customer;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;

import static com.nicktalbot.optica.helpers.JsonRoute.*;
import static spark.Spark.*;

@Slf4j
public class Server {

    public static void main(String[] args) {

        log.info("Started Optica Server ...");

        run();
    }

    public static void run() {

        val repository = new ArrayList<Customer>();  //@TODO: This is NOT thread-safe as a real Repository should be

        port(8080);

        getJson("/customers", () -> repository);

        postJson("/customers", Customer.class, customer -> {

            val newId = repository.size() + 1;

            repository.add(customer.toBuilder().id(newId).build());
            return newId;
        });

        delete("/customer/:id", (request, response) -> {

            val id = Integer.valueOf(request.params("id"));
            val match = repository.stream().filter(customer -> customer.getId() == id).findAny();
            val deleted = match.map(repository::remove).orElse(false);

            response.status(deleted ? 200 : 404);
            return "";
        });
    }
}
