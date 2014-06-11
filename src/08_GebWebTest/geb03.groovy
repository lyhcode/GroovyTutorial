@Grapes([
    @Grab('org.gebish:geb-core:0.9.2'),
    @Grab('org.seleniumhq.selenium:selenium-firefox-driver:2.42.0'),
    @Grab('org.seleniumhq.selenium:selenium-support:2.42.0')
])
import geb.Browser

Browser.drive {
    config.reportsDir = new File('/tmp')

    go 'http://www.codedata.com.tw/'

    report 'codedata-home'
}
