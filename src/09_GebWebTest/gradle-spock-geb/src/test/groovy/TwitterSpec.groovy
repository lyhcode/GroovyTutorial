import geb.Page
import geb.spock.GebSpec

class TwitterSpec extends GebSpec {
    def "login to dashboard section"() {
        given:
        to LoginPage

        when:
        $('input.js-username-field').value("lyhcode")
        $('input.js-password-field').value("u04cj/6123")

        and:
        loginButton.click()

        then:
        at DashboardPage
    }
}
