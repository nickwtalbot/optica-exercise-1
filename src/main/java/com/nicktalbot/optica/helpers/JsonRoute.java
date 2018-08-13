package com.nicktalbot.optica.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import spark.Request;
import spark.Response;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static spark.Spark.*;

public interface JsonRoute {

    static <T> void getJson(String path, Supplier<T> action) {

        val mapper = new ObjectMapper();

        get(path, (Request request, Response response) -> {

            response.type("application/json");
            return action.get();

        }, mapper::writeValueAsString);
    }

    static <T> void postJson(String path, Class<T> type, Consumer<T> action) {

        val mapper = new ObjectMapper();

        post(path, (request, response) -> {

            action.accept(mapper.readValue(request.body(), type));

            response.status(201);
            return "";
        });
    }
}
