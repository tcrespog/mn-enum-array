package mn.enumarray

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest(application = Application, transactional = false)
class ControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient client

    void "contact a secured endpoint"() {
        given:
        def req = HttpRequest.GET("/")

        when:
        def res = client.toBlocking().exchange(req)

        then:
        res.status() == HttpStatus.OK
    }

}
