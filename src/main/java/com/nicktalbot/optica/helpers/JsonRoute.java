package com.nicktalbot.optica.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import spark.Request;
import spark.Response;

import java.util.function.Function;
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

    static <T, R> void postJson(String path, Class<T> type, Function<T, R> action) {

        val mapper = new ObjectMapper();

        post(path, (request, response) -> {

            response.status(201);
            return action.apply(mapper.readValue(request.body(), type));
        });
    }
}
