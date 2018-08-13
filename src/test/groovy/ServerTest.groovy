import com.nicktalbot.optica.Server
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import spock.lang.Specification

class ServerTest extends Specification {

    def "add a customer"() {

        setup:
        Server.run()

        when:
        def result = new RESTClient("http://localhost:8080/").post(
                path: "customers",
                body: [firstname: "John", surname: "Smith"],
                requestContentType: ContentType.JSON
        )

        then:
        result.status == 201
    }

    def "delete a customer"() {

        setup:
        Server.run()

        when:
        def result = new RESTClient("http://localhost:8080/").delete(path: "customer/1")

        then:
        result.status == 200
    }

    def "list all customers"() {

        setup:
        Server.run()

        when:
        def result = new RESTClient("http://localhost:8080/").get(path: "customers")

        then:
        result.status == 200
        result.data.size == 2
    }
}
