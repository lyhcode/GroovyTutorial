@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7.1')
@Grab('commons-beanutils:commons-beanutils:1.9.1')
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.TEXT

def http = new HTTPBuilder('http://ajax.googleapis.com')

http.request(GET, JSON) {
    uri.path = '/ajax/services/search/web'
    uri.query = [ v:'1.0', q: 'Calvin and Hobbes' ]

    headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'

    response.success = { resp, json ->
        println resp.status

        json.responseData.results.each {
            println "  ${it.titleNoFormatting} : ${it.visibleUrl}"
        }
    }

    response.failure = { resp ->
        println "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}"
    }
}

