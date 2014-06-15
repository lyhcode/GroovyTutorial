@Grapes([
    @Grab('org.gebish:geb-core:0.9.2'),
    @Grab('org.seleniumhq.selenium:selenium-htmlunit-driver:2.42.0'),
    @Grab('org.seleniumhq.selenium:selenium-support:2.42.0')
])
import geb.Browser
import org.openqa.selenium.htmlunit.HtmlUnitDriver


driver = 'htmlunit'
/*
driver = new HtmlUnitDriver()
dirver = {
    new HtmlUnitDriver
}
*/

Browser.drive {
    go 'http://www.codedata.com.tw/'
    println title
}
