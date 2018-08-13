package com.nicktalbot.optica.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.get;

public interface JsonRoute {

    static void getJson(String path, Route route) {

        val mapper = new ObjectMapper();

        get(path, (Request request, Response response) -> {

            val result = route.handle(request, response);

            response.type("application/json");
            return result;

        }, mapper::writeValueAsString);
    }
}
