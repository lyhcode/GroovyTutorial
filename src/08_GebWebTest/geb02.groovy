@Grapes([
    @Grab('org.gebish:geb-core:0.9.2'),
    @Grab('org.seleniumhq.selenium:selenium-firefox-driver:2.42.0'),
    @Grab('org.seleniumhq.selenium:selenium-support:2.42.0')
])
import geb.Browser

Browser.drive {
    go 'https://www.google.com/recaptcha/demo/ajax'

    $('input', value: 'Click Me').click()

    waitFor {
        $('#recaptcha_area').size() > 0
    }

    println $('#recaptcha_challenge_image').attr('src')

}
