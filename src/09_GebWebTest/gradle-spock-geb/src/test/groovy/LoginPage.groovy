import geb.Page

class LoginPage extends Page {
    static url = "https://twitter.com/login"
    static at = { heading.text() == "登入 Twitter" }
    static content = {
        heading { $("h1") }
        loginForm { $("form.signin") }
        loginButton(to: DashboardPage) { $("button.submit") }
    }
}
