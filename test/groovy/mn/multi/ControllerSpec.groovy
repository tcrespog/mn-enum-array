package mn.enum

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import mn.multi.domain.Cat
import mn.multi.exchange.Pet
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class ControllerSpec extends Specification {

    @Inject
    EmbeddedApplication<?> application

    @Inject
    @Client("/")
    HttpClient client

    void 'test the application is running'() {
        expect:
        application.running
    }

    void "get the pet"() {
        given:
        def req = HttpRequest.GET("/pet")

        when:
        def res = client.toBlocking().exchange(req, Pet)
        def cat = res.body().animal as Cat

        then:
        cat.name == "Garfield"
        cat.lives == 7
    }

}
