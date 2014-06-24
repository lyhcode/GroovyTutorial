import geb.Page

class DashboardPage extends Page {
    static at = { heading.text() == "推文" }
    static content = {
        heading(wait: true) { $("h2#content-main-heading") }
    }
}