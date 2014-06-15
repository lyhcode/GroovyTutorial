@Grapes([
    @Grab('org.gebish:geb-core:0.9.2'),
    @Grab('org.seleniumhq.selenium:selenium-chrome-driver:2.42.0'),
    @Grab('org.seleniumhq.selenium:selenium-support:2.42.0')
])
import geb.Browser
import org.openqa.selenium.chrome.ChromeDriver

System.setProperty('webdriver.chrome.driver', '/tmp/chromedriver')

driver = {
   new ChromeDriver()
}

Browser.drive {
    config.reportsDir = new File('/tmp')

    go 'http://www.codedata.com.tw/'

    report 'codedata-home'
}
