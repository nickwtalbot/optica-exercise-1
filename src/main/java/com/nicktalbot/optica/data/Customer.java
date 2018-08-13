package com.nicktalbot.optica.data;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Customer {

    private int id;

    private String firstname;

    private String surname;
}
