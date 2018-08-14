# RESTful Customer API sample TDD exercise

A sample Customer REST API that supports 3 operations:

* Add a customer
  - `POST /customer`
  - Pass a JSON body e.g. `{ "firstname": "John", "surname": "Smith" }`
* Remove a customer
  - `DELETE /customer/{id}`
  - e.g. `DELETE /customer/1`
* List all customers
  - `GET /customers`

The API is written in [Java 8](http://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html) and utilises the [Spark Java](http://sparkjava.com/) web framework and [Lombok](https://projectlombok.org/features/all) extensions.

The tests use the [Spock](http://spockframework.org/) TDD/BDD testing framework, and are written in [Groovy](http://groovy-lang.org/). 

## Running the tests

Clone to a local directory:

`git clone https://github.com/nickwtalbot/optica-exercise-1.git`

Run the tests (on Mac/Linux/Unix):

`./gradlew test`

(On Windows, run `gradlew.bat test`)


## Running the API for interactive testing (e.g. with [Postman](https://www.getpostman.com/))

`./gradlew run`

The API will be available at `http://localhost:8080/`


## Known Issues

The in-memory repository (a simple [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)) is not thread-safe as a true (e.g. database) repository, and multiple concurrent clients accessing the API currently results in undefined behaviour.
