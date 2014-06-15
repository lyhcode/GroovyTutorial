@Grapes([
    @Grab('org.gebish:geb-core:0.9.2'),
    @Grab('org.seleniumhq.selenium:selenium-firefox-driver:2.42.0'),
    @Grab('org.seleniumhq.selenium:selenium-support:2.42.0')
])
import geb.Browser
import geb.Page

class LoginPage extends Page {
    static url = "http://google.com/"
    static at = { title == "Google" }
    static content = {
        heading { $("h1") }
        loginForm { $("form.login") }
        loginButton(to: AdminPage) { loginForm.login() }
    }
}
 
class AdminPage extends Page {
    static at = { heading.text() == "Admin Section" }
    static content = {
        heading { $("h1") }
    }
}

Browser.drive {
    go 'http://www.codedata.com.tw/'

    $('input', name: 'keyword').value('groovy')
    $('input', name: 'btnSearch').click()

    $('.list .block h3 a').each { elem ->
        println elem.text()
    }
}
