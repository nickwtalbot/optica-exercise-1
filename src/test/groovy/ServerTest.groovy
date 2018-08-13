import com.nicktalbot.optica.Server
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spark.Spark
import spock.lang.Specification

class ServerTest extends Specification {

    private RESTClient client

    def setup() {

        Server.run()
        client = new RESTClient("http://localhost:8080/")
    }

    def "add a customer"() {

        when:
        def added = client.post(
                path: "customers",
                body: [firstname: "John", surname: "Smith"],
                requestContentType: ContentType.JSON
        )

        then:
        added.status == 201
    }

    def "add and retrieve a customer"() {

        setup:
        def added = client.post(
                path: "customers",
                body: [firstname: "John", surname: "Smith"],
                requestContentType: ContentType.JSON
        )

        when:
        def retrieved = client.get(path: "customers")

        then:
        added.status == 201
        retrieved.status == 200
        retrieved.data.size == 1
        retrieved.data.get(0).firstname == "John"
        retrieved.data.get(0).surname == "Smith"
    }

    def "add retrieve and delete a customer"() {

        setup:
        def added = client.post(
                path: "customers",
                body: [firstname: "John", surname: "Smith"],
                requestContentType: ContentType.JSON
        )
        def retrieved = client.get(path: "customers")

        when:
        def deleted = client.delete(path: "customer/${retrieved.data.get(0).id}")

        then:
        deleted.status == 200
    }

    def "customer can only be deleted once"() {

        setup:
        def added = client.post(
                path: "customers",
                body: [firstname: "John", surname: "Smith"],
                requestContentType: ContentType.JSON
        )
        def retrieved = client.get(path: "customers")
        def deleted = client.delete(path: "customer/${retrieved.data.get(0).id}")

        when:
        def attempt = client.delete(path: "customer/${retrieved.data.get(0).id}")

        then:
        HttpResponseException ex = thrown() // 404 response
    }

    def "list all customers"() {

        setup:
        def first = client.post(
                path: "customers",
                body: [firstname: "John", surname: "Smith"],
                requestContentType: ContentType.JSON
        )
        def second = client.post(
                path: "customers",
                body: [firstname: "Dave", surname: "Jones"],
                requestContentType: ContentType.JSON
        )

        when:
        def customers = client.get(path: "customers")

        then:
        first.status == 201
        second.status == 201
        customers.status == 200
        customers.data.size == 2

        customers.data.get(0) == [id: 1, firstname: "John", surname: "Smith"]
        customers.data.get(1) == [id: 2, firstname: "Dave", surname: "Jones"]
    }

    def "deleting a non existing customer fails"() {

        when:
        def customer = client.delete(path: "customer/1")

        then:
        HttpResponseException ex = thrown() // 404 response
    }

    def cleanup() {
        Spark.stop()
        Thread.sleep(1000)
    }
}
