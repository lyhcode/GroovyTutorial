# Groovy Tutorial（6）HTTP 資料存取基礎 #

[Groovy Tutorial（5）使用 AntBuilder 撰寫 Script << 前情](http://www.codedata.com.tw/java/groovy-tutorial-5-antbuilder/)

存取網路資源或透過網路提供服務，在現代應用程式隨處可見，特別是以 HTTP 通訊協定傳輸資料，應用範圍非常廣泛。在 Groovy Script 程式中，撰寫 HTTP 網路程式非常容易，可以輕鬆撰寫如網路爬蟲（web crawler）或網頁內容分析程式。這篇主要介紹 Groovy 的 URL 類別，以及好用的 HTTP Builder DSL 語法。

## 從 URL 讀取資料 ##

Groovy JDK 提供的 [URL](http://groovy.codehaus.org/groovy-jdk/java/net/URL.html) 類別有更多功能擴充，可以更容易從網際網路取得資料。例如從 HTTP 或 FTP 伺服器，取得一份 HTML 或純文字資料內容，只需要一行 Groovy 程式即可完成任務。

    new URL("http://news.google.com").text
    new URL("ftp://ftp.microsoft.com/softlib/index.txt").text
    
程式、圖片或多媒體等二進位檔案，也可以傳回 byte[] 陣列型別的內容加以處理。

    new URL("https://www.google.com/images/logo.png").bytes

URL 類別提供許多方法可以搭配 Groovy 的 Closure 使用，例如使用 ``eachLine`` 方法逐行處理，找出 HTML 代碼中使用 `<h2>` 標籤的標題文字：

```
new URL("http://www.codedata.com.tw/").eachLine { line ->
    if (line.trim().startsWith("<h2>")) {
        println line.trim()
    }
}
```

也可以使用 `filterLine` 方法篩選符合條件的那幾行文字。

```
new URL("http://www.codedata.com.tw/").filterLine {
    it.trim().startsWith("<h3>")
}
```

在 Groovy 程式中透過 HTTP 存取資料，真是非常容易的一件事。不過這種做法也有些限制，例如 HTTP Request Header 預設使用的 User-Agent，可能會被某些伺服器的防火牆阻擋，利用「ifconfig.me」服務可以看到 URL 類別使用的 Header 資訊。

```
new URL("http://ifconfig.me/all").text
```

## 回傳內容的解析 ##

如果 HTTP Response 結果為純文字資料，使用 String 類別處理文字內容，或搭配 Groovy 的 [Regular Expression](http://groovy.codehaus.org/Regular+Expressions) 語法，即可將回傳內容加以處理。

這個範例逐行處理 ifconfig.me/all 回傳的純文字資料，將結果轉換成一個 Map 物件儲存。

```
def result = new URL("http://ifconfig.me/all").text

def props = [:]

result.split('\n').each {
    def token = it.split(':')
    def key = token[0].trim()
    def value = token[1].trim()
    
    props[key] = value
}

println props.user_agent
```

如果回傳的資料是標準的 XML 格式，使用 Groovy 的 [XmlParser](http://groovy.codehaus.org/Reading+XML+using+Groovy's+XmlParser) 可以解析回傳內容。

```
def result = new URL("http://ifconfig.me/all.xml").text
def info = new XmlParser().parseText(result)

println info['user_agent'].text()
```

利用 Groovy 的 [JsonSlurper](http://groovy.codehaus.org/gapi/groovy/json/JsonSlurper.html) 則可以輕鬆解析 JSON 格式的內容。

```
def result = new URL("http://ifconfig.me/all.json").text
def info = new groovy.json.JsonSlurper().parseText(result)

println info.user_agent
```

對於 HTML 內容的處理，會稍微麻煩一些，假如 HTML 代碼能夠通過 XHTML 格式的驗證，通常代表我們能將它視為 XML 內容加以解析，很可惜我們無法預期每次都能遇到乾淨且嚴謹的 HTML 原始碼。常見的解決方式是搭配 CyberNeko HTML Parser 來解析代碼，如此能增加解析器的容錯能力，方便我們截取網頁的內容。

```
@Grab('net.sourceforge.nekohtml:nekohtml:1.9.20')
import org.cyberneko.html.parsers.SAXParser
 
new XmlParser(new SAXParser())
.parse('https://news.google.com.tw/')
.depthFirst().DIV.grep {
  it.'@class'=='title'
}
```

## 檔案下載任務實現 ##

從 URL 將資料下載並儲存至磁碟，使用 Input 及 Output Stream 即可完成。`File` 類別提供的 `withOutputStream` 方法很適合派上用場，利用 Groovy 覆載「`<<`」左移運算子（left shift operator），把來自 URL 的 Input Stream 資料串流，直接寫入到檔案的 Output Stream，就可以完成檔案的下載動作。

    def url = "https://www.google.com/images/logo.png"
    
    new File("logo.png").withOutputStream {
        it << new URL(url).openStream()
    }

利用 Groovy 的 Meta Programming 支援，我們還可以擴充 URL 類別，加上一個 `downloadTo` 方法，讓這個 URL 類別直接擁有下載檔案儲存到磁碟的功能。

    def url = "https://www.google.com/images/logo.png"
    
    URL.metaClass.downloadTo = {
        it.withOutputStream {
            it << delegate.openStream()
        }
    }
    
    new URL(url).downloadTo(new File("/tmp/logo.png"))

[Groovy Category](http://groovy.codehaus.org/Groovy+Categories) 類別是撰寫 Groovy 程式常見的，在 Groovy Category 類別中提供一個靜態方法（static method），就可以在 `use` 的區塊中，使用 Category 來操作特定用途的任務。`FileBinaryCategory` 是用來執行 URL 檔案下載的類別，它可以讓檔案下載的任務更加明確被定義。

    class FileBinaryCategory {
        def static leftShift(File file, URL url) {
           url.withInputStream { is ->
                file.withOutputStream { os ->
                    def bs = new BufferedOutputStream(os)
                    bs << is
                }
            }
        }
    }
    
    def file = new File("logo.png")
    
    use (FileBinaryCategory) {
        file << "http://www.google.com/images/logo.png".toURL()
    }

## 使用 HTTP Builder ##

在前面的文章中，曾經介紹過 Builder 是 Groovy 程式常見的一種 DSL 支援。[HTTP Builder](http://groovy.codehaus.org/modules/http-builder/) 就是為了支援 HTTP 傳輸協定而設計的領域描述語言，使用 `HTTPBuilder` 類別可以更容易進行 HTTP Request 與 Response 處理。

HTTPBuilder 並非 Groovy 內建的類別，需要先使用 `@Grab` 設定 Module 的名稱與版本，目前的最新版本代碼為 0.7.1。

備註：由於 Grapes 對於 `commons-beanutils` 預設相依性的解析問題，可能無法正常完成套件下載，所以特別加上 commons-beanutils:1.9.1 避免問題發生。

```
@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7.1')
@Grab('commons-beanutils:commons-beanutils:1.9.1')
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
```

以下的範例省略上述 @Grab 及 import 的設定，為讓程式能正常執行，請務必自行加上這些設定。

第一個 HTTPBuilder 的範例，我們對網頁伺服器送出請求，用 DSL 語法來處理回傳結果，當伺服器回應成功（HTTP Status 200）時，就將 HTTP Response Header 的 Content-Type 資訊印出，並將回應內容全部印出。

```
def http = new HTTPBuilder('http://ifconfig.me/')

http.request(GET) { req ->
    response.success = { resp, content ->
        println resp.headers.'Content-Type'
        println content
    }
}
```

這個範例執行後，會顯示 Content-Type 為 text/html 類型。試著將網址替換成以下不同設定，可以發現 Content-Type 顯示的值也跟著改變。

* http://ifconfig.me/all
* http://ifconfig.me/all.xml
* http://ifconfig.me/all.json

HTTPBuilder 常見的 ContentType 有以下幾種類型：

* ANY
* BINARY
* HTML
* JSON
* TEXT
* URLENC
* XML

在不指定的狀況下，HTTPBuilder 會預設 ContentType 為 ANY 類型，也就是自動偵測伺服器回傳的 Content-Type，這會影響到 `content` 物件使用哪種方式處理回傳內容。假設伺服器回應「application/json」類型，那麼 `content` 值就是一個經過解析的 JSON 物件，被支援的類型如 JSON 或 XML 都會被自動解析。

我們可以在 `http.request` 的第二個參數，明確指定使用何種 Content-Type 來處理回傳內容。

```
def http = new HTTPBuilder('http://ifconfig.me/all.json')

http.request(GET, JSON) { req ->
    response.success = { resp, content ->
        println resp.headers.'Content-Type'
        println content
    }
}
```

HTTP Builder 提供的 DSL 語法，讓我們可以依照伺服器回應的不同代碼，例如 404 代表網頁資源不存在，可以分別設計不同的 Closure 進行處理。

```
def http = new HTTPBuilder('http://google.com/error')

http.request(GET) { req ->
    response.success = { resp, content ->
        println resp.headers.'Content-Type'
        println content
    }
    response.'404' = { resp ->
        println "Error Code = ${resp.status}"
    }
}
```

某些情況下我們需要讓 HTTP Client 程式偽裝成某種瀏覽器，需要修改 Request Header 的 User-Agent 設定，HTTPBuilder 可以很容易做到。實務上常見的 User-Agent 代碼，可以參考 [User Agent String.Com](http://www.useragentstring.com/pages/useragentstring.php) 網站。

```
def http = new HTTPBuilder('http://ifconfig.me/all.json')

http.request(GET) { req ->
    
    headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'
    
    response.success = { resp, content ->
        println resp.headers.'Content-Type'
        println content.user_agent
    }
}
```

HTTPBuilder 也可以用於 Web Services 的請求處理，以下的範例設定 GET 的參數資料，並且修改預設的 User-Agent 模擬成 Firefox 瀏覽器，並且將伺服器回傳結果自動轉為 JSON 資料物件。

```
def http = new HTTPBuilder('http://ajax.googleapis.com')

http.request(GET, JSON) {
    uri.path = '/ajax/services/search/web'
    uri.query = [ v:'1.0', q: 'Calvin and Hobbes' ]

    headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'

    response.success = { resp, json ->
        println resp.status

        json.responseData.results.each {
            println "${it.titleNoFormatting} : ${it.visibleUrl}"
        }
    }

    response.failure = { resp ->
        println "Error: ${resp.status} : ${resp.statusLine.reasonPhrase}"
    }
}
```

除送出 HTTP 的 GET 請求外，也可以用 POST 方式存取 URL。

```
def http = new HTTPBuilder('http://restmirror.appspot.com/')

http.request( POST ) {
    uri.path = '/'
    requestContentType = URLENC
    body =  [name: 'kyle', title: 'software developer']
 
    response.success = { resp ->
        println "Response: ${resp.statusLine}"
    }
}
```