<div style="text-align:center"><img src="https://lh5.googleusercontent.com/4YM_JnVm8NLgBxNF6WTRGnJbKnjWcXRFqpv7iS_iNKU=w2000-h998-no" alt="" style="max-width:100%" /></div>

> Groovy - very good and enjoyable programming language

韋氏字典（Merriam-Webster）對於 groovy 單字的定義是「very good and enjoyable」。在學習 Groovy 程式語言的過程中，能感受到恰如其名的「令人愉快」，簡單易學、輕鬆完成任務。

Groovy 程式語言誕生於 2003 年，它的語法類似 Java 語言，但是比 Java 容易學習與使用，更適合剛入門學習寫程式的初學者。已經熟悉 Java 的開發者，也能很快學會使用 Groovy 寫程式，許多用 Java 寫起來很複雜的程式，改用 Groovy 能用更輕鬆簡單的方法完成。

## A First Look at Groovy ##

Groovy 可以當作 Scripting Language 執行，不必像 Java 需要宣告啓動類別與 ``main(String[] args)`` 主程式。因此最簡單的 Hello World 程式用 Groovy 撰寫只要一行，就像 Python 或 Ruby 一樣簡單。

    println "Hello Groovy" 

Groovy 是物件導向程式語言（Object-oriented Programming Language），假如我們要定義一個 Person 類別，具有 name 與 age 兩個屬性，並且在物件建構時指派屬性的初值，可以很簡單的做到：

```
class Person {
    def name, age
}

def p = new Person(name: "John", age: 21)

println "${p.name}: ${p.age}"
```

這段 Groovy 程式碼很容易理解，並不需要多做解釋，即使從來沒有學過 Groovy 語言，大概也能猜出每一行程式碼的用意。

但是相同的程式若改用 Java 撰寫，事情就變得複雜許多。現在的 Java 已發展成過度工程導向（over-engineered）的程式語言，有著初學者不容易跨越的學習門檻。

```
public class Person {

    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
    	return name;
    }
    
    public int getAge() {
    	return age;
    }
    
    public static void main(String[] args) {
        Person p = new Person("John", 21);
        System.out.printf("%s: %d\n", p.name, p.age);
    }
}
```

有趣的是上面這段標準的 Java 程式碼，其實也是一段符合 Groovy 語法規則的程式，可以直接使用 Groovy 以直譯方式執行它。對學習者來說，從 Groovy 開始學習，未來只要陸續熟悉更多 Java 語言的知識，就不難

Groovy 提供函數型程式設計（Functional Programming）的必要功能，例如 Lambda 及 Closure 的支援。函數型程式設計在 Java SE 8 才開始支援，但其實只要改用 Groovy 寫程式，使用 Java SE 6 或 7 + Groovy，就能開始撰寫函數型風格的程式。

```
def func = { a, b -> 2 * a + 3 * b }

func(1, 2)  // return 8
```

Map 與 List 是 Java 程式常用的資料結構，但是必須使用 Java Collections API，使得 Java 程式碼無法像 PHP 或 Ruby 那樣簡潔，在 Groovy 則有更加易讀易寫的語法。

```
list1 = [1, 2]
list1 += [3, 4, 5]
list1 << 6
list1.each { println it }

map1 = [name: "book1", year: 2010]
map1['name'] = "book1 2e"
map1.year = 2013
map1.author = "John"
map1.each { key, value -> println "${key} = ${value}" }
```

Groovy 適合用於多種類型的軟體開發，包括桌面視窗程式、網路服務程式、網站或資料庫應用程式等。

以打造圖形介面（視窗）應用程式為例，Groovy 可以直接使用 Java 的 Swing GUI Toolkit。以下是使用 Groovy 風格撰寫的 Swing Hello World 範例。

```
import javax.swing.*

frame = new JFrame("HelloWorldSwing")
label = new JLabel("Hello World")
frame.contentPane << label
frame.pack()
frame.visible = true
```

上面的程式碼看起來已經相當精簡，但是可別以為 Groovy 只是簡化版的 Java 語言。還記得大師 Martin Fowler 曾寫一本「[Domain Specific Languages](http://martinfowler.com/books/dsl.html)」（領域描述語言，簡稱 DSL）的經典著作嗎？也許你早已知道 DSL 的美好，但是卻不知道怎麼實際應用它。

從學習 Groovy 的那一刻起，就會開始不斷與 DSL 打交道。Groovy 有許多不同用途的 Builder，可以讓你輕鬆發揮 DSL 的優勢，我們將 Swing 範例程式改用 SwingBuilder 重寫成 DSL 的風格。

```
import groovy.swing.SwingBuilder

new SwingBuilder().edt {
    frame(title: "HelloWorldSwing", pack: true, show: true) {
        label(text: "Hello World")
    }
}
```

利用 DSL 風格的 Builder 寫程式，讓程式碼更易讀也更易寫，可以讓程式設計師的生產力 Level Up。舉例來說，在 Groovy 程式產生 JSON 輸出，可以使用 ``JsonBuilder`` 輕鬆完成。

```
def builder = new groovy.json.JsonBuilder()
builder.people {
    person {
        firstName 'Kyle'
        lastName 'Lin'
        address(
            city: 'Taichung',
            country: 'Taiwan'
        )
    }
}
println builder.toPrettyString()
```

Groovy 提供靜態語言（如 Java）較難達成的 Metaprogramming 功能，利用 ``metaClass`` 我們可以幫一個已經定義好的類別擴充新的物件方法，例如最基本的 Java API 的 String 類別，幫它添加一個 ``hello()`` 方法，就可以讓一個字串擁有主動打招呼的能力。

```
String.metaClass.hello = { "Hello ${delegate}" }
"John".hello()  // Say Hello to John
```

有許多常用的 Java API 類別，例如 String 與 URL，Groovy 已經提供功能經過擴充的版本，利用 Metaprogramming 的特性，程式中並不需要另外匯入（import）其他擴充功能的新類別，既有的類別可以直接擁有新的能力。

```
// Execute external command and get results
execResult = "echo hello".execute().text

// Fetch html response from a url
htmlText = new URL("http://google.com/").text
```

## Java 的美麗與哀愁 ##

TIOBE 提供程式語言熱門程度的排行榜，從這份統計資料不難發現 Java 始終名列前茅。

[TIOBE Programming Community Index](http://www.tiobe.com/index.php/content/paperinfo/tpci/index.html)

Java 不僅有眾多 Open Source 社群開發者的支持，也有多家跨國大型軟體公司力挺，形成的 Ecosystem 以被其他新興語言撼動；我們可以大膽預期 Java 在未來幾年仍會有舉足輕重的地位，在 Web-based 及 Cloud 應用成為主流的今日，Java 在 Server-side 所扮演的重要角色已不易被取代。

從 1995 年誕生迄今，Java 已算是個邁入中年的程式語言，被廣泛使用於各類型軟體開發，發展出豐富的 Java API 及大量的函式庫和框架，Java 提供的解決方案能滿足企業級的開發需求。

傳統的 Java 語言已經變得太過複雜；但儘管許多開發者渴望更時髦的 Java 語言，多年來一直無法如願以償，歷史的包袱使得 Java 語言不可能快速地進化。如果期望有一天寫 Java 也能夠像 Ruby 或 Python 同樣愉悅，那肯定是在夢境裡才會實現。

Groovy 誕生的那一刻，已經為 Java 的世界重新打開一扇窗。

Groovy 受到其他程式語言的啟發，將許多語言的優點融合，像是 Smalltalk、Ruby 或 Python 等，甚至可以像 Perl 一樣直接在程式碼使用 Regular Expression。

```
new File('.').eachFileMatch( ~/.*\.groovy/ ) {
    println it
}
```

使用 Groovy 能夠同時享受其他現代語言才有的便利特性，但同時又能與既有的 Java 知識相輔相成，這可是程式設計習武之人夢寐以求的事。

## Polyglot Programming in the JVM ##

原來 Java 的世界不只有 Java 程式語言！？

先談談什麼是 Polyglot Programming 吧？

以微軟的 .NET 開發平台為例，MSDN 文件提供 C# / C++ / F# / JScript / VB 等五種不同語言的使用範例。雖然使用不同的程式語言撰寫，但它們都使用相同的 .NET Framework API。

<div style="text-align:center"><img src="https://lh3.googleusercontent.com/-ZgFl-MH97-w/Urb9A4JjK3I/AAAAAAAAL9E/Fq0P7Pfd1Ts/w1936-h712-no/dot-net-api.jpg" alt="" style="max-width:70%" /></div>

Java 虛擬機器（Java Virtual Machine，簡稱 JVM）的設計亦支援 Polyglot Programming，就是不管用什麼語言撰寫程式碼，只要最後編譯成 Java Bytecode，就能夠在 JVM 虛擬機器平台中執行。

在官方的 Java API 文件中，無法找到使用其他程式語言寫程式的範例，坊間一般的書籍也鮮少介紹 JVM 上面的其他語言。但實際上 Java 世界用其他語言寫程式，早已盛行多年而且可用於實際產品開發。

常見的 JVM Languages 包括：

* Groovy
* Clojure
* Scala
* JRuby
* Jython
* Rhino
* ⋯還有更多。

Polyglot Programming 給 Java 程式設計師更多的語言選擇，例如可以用 JavaScript 來寫 Java 程式，並且在 JavaScript 程式碼中使用 Java Thread API 建立多執行緒。

```
var obj = { run: function () { print(“Hello Thread\n"); } }
var r = new java.lang.Runnable(obj);
var t = new java.lang.Thread(r);
t.start();
```

（註：這段 JavaScript 程式碼必須使用 Rhino 才能執行。）

## JVM Scripting Language ##

Ousterhout's dichotomy 將程式語言分類為以下兩種：

系統程式語言（System Programming Language）

* 靜態型別
* 可支援建立複雜資料結構
* 程式被編譯成機器碼

腳本語言（Scripting Language）

* 動態型別
* 較不適合複雜資料結構
* 程式以直譯方式執行

（[Oursterhout, 1998](http://en.wikipedia.org/wiki/Ousterhout's_dichotomy)，資料來源為 Wikipedia）

Groovy 兼顧上述兩種不同語言的特性，既可當作 Scripting Language 以直譯方式執行，也可以先將 Groovy 的類別編譯為 Java Byte Code；後者的執行方式與一般 Java Class 無異。

Java Scripting API 最早是從 Java SE 6 這個版本開始提供，它提供 ScriptEngine 介面類別，讓不同的 JVM Scripting Language 有統一的管理方式，以下是使用 ScriptEngine 執行 JavaScript 程式碼的範例。

```
ScriptEngineManager factory = new ScriptEngineManager();
ScriptEngine engine = factory.getEngineByName("JavaScript");
engine.eval("print('Hello');");
```

JDK 並沒有內建 Groovy 或其他 JVM Scripting Language 的實作，若要執行其他語言的程式碼，需要先將符合 JSR-223 規範的 Library 加到 CLASSPATH 才能執行，以下是 ScriptingEngine 執行 Groovy 程式碼的範例，執行前需要先取得 [groovy-all-2.2.1.jar](http://repo1.maven.org/maven2/org/codehaus/groovy/groovy-all/2.2.1/groovy-all-2.2.1.jar) 檔案。

RunGroovy.java

```
import javax.script.*;

public class RunGroovy {
    public static void main(String[] args) throws Exception {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("groovy");
        engine.eval("println 'Hello'");
    }
}
```

執行方法：

    javac RunGroovy.java
    java -cp '.:groovy-all-2.2.1.jar' RunGroovy

## Why Groovy? ##

目前在 Java 世界已隨處可見 Groovy 的實際應用，不用擔心這只是曇花一現或是趕流行的新語言。Groovy 的出現不是為了取代 Java，而是讓那些原本在 Java 不容易做到的事情，能夠利用 Groovy 來達成，只要 Java 不死、開發社群對 Groovy 的應用就只會愈來愈多。

iReport 與 JasperReport 是功能強大的報表設計工具及報表引擎，它利用 Groovy 簡化報表製作時的程式撰寫；在處理報表資料顯示時，可以利用 Groovy Scripting 進行程式運算。

[iReport Designer - Groovy](http://community.jaspersoft.com/wiki/ireport-designer-groovy)

Gradle 是新一代的專案自動化建置工具，它直接使用 Groovy 設定專案及定義任務，可程式（programmable）的彈性遠高於過去 Ant / Maven 採用 XML 格式的設定檔，Groovy DSL 的特性也讓 Gradle 設定檔看起來十分友善。

[Gradle The Enterprise Build Automation Tool](http://www.gradle.org/)

Jenkins CI 是廣受歡迎的持續整合工具，為軟體開發團隊提供二十四小時無休的管家服務，幫忙處理單元測試或每日的自動化建置，它提供的 Script Console 可利用 Groovy 進行程式化的維護管理。

[Jenkins Script Console](https://wiki.jenkins-ci.org/display/JENKINS/Jenkins+Script+Console)

Spock Framework 提供以更簡潔的 Groovy 語法撰寫測試案例，可以用於 Java 或 Groovy 程式碼的單元測試。用更簡單乾淨易懂的語法撰寫單元測試程式，相信是很多 Java 開發者共同的渴望。

[Spock Enterprise-ready Testing and Specification Framework](http://spockframework.org)

Geb 大概是目前最強大的 Web UI Testing 工具，它可以撰寫簡單的 Groovy 程式來操作瀏覽器，完成網站各項畫面或表單的操作，利用類似 jQuery 的語法取得畫面的內容加以驗證，還能夠將網頁畫面截圖保存。Geb 對瀏覽器的自動化操作實際是透過 Selenium WebDriver 完成，但是它利用 Groovy DSL 的特性讓 Script 的撰寫變得十分容易。

[Geb - Very Groovy Browser Automation](http://www.gebish.org/)

## For Windows 安裝指南 ##

請先安裝最新版本的 [Java SE Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) 軟體，因為目前的 Groovy for Windows 安裝程式只有支援 32 位元
，如果使用 64 位元的 Windows 電腦，仍需要安裝一份 32 位元的 JDK 版本。

在安裝 JDK 之後必須設定 JAVA_HOME 環境變數，將其內容正確設定為 JDK 安裝的資料夾（例如：`C:\Program Files (x86)\Java\jdk1.7.0_45`）。

<div style="text-align:center"><img src="https://lh3.googleusercontent.com/-246QHP22ceE/UsFK7-2ZyeI/AAAAAAAAMAs/Mh08PB_rOdA/w914-h996-no/groovy2.png" alt="" style="max-width:100%" /></div>

取得 Groovy 的 Windows 安裝程式。

Groovy 2.2.1 Windows Installer：[groovy-2.2.1-installer.exe](http://dist.codehaus.org/groovy/distributions/installers/windows/nsis/groovy-2.2.1-installer.exe) ([原始下載頁面](http://groovy.codehaus.org/Download)）

<div style="text-align:center"><img src="https://lh3.googleusercontent.com/-4amH_9ugVo8/UsFJ2C7b98I/AAAAAAAAL_4/ScSJRglrMUY/w1026-h800-no/groovy1.png" alt="" style="max-width:100%" /></div>

安裝完成後，可以在應用程式集找到「Start GroovyConsole」的啟動捷徑。

## For Mac OS X 安裝指南 ##

Mac OS X 系統專用的安裝方式有兩種。

使用 [HomeBrew](http://brew.sh/) 安裝：

    brew install groovy

使用 [MacPorts](http://www.macports.org/) 安裝：

    sudo port install groovy

## For Linux 安裝指南 ##

Ubuntu Linux 與 Debian 可以使用 `apt-get` 指令安裝：

    sudo apt-get install groovy

RedHat 或 CentOS 等 RPM-based Linux 系統，可以使用 `yum` 指令安裝：

    sudo yum install groovy

也可以使用手動方式下載安裝最新版本：

    curl -O http://dist.groovy.codehaus.org/distributions/groovy-binary-2.2.1.zip
    unzip groovy-binary-2.2.1.zip /usr/local

自行安裝 Groovy 必須確認 JAVA_HOME 與 GROOVY_HOME 環境變數已正確設定，建議加到 `.bashrc` 或 `.profile` 設定檔。

    export JAVA_HOME=/usr/local/jdk-1.7.0
    export GROOVY_HOME=/usr/local/groovy-2.2.1

## 推薦使用 GVM 安裝 Groovy ##

在 Mac OS X 或 Linux 系統上，使用 GVM 工具安裝 Groovy 是筆者最推薦的方式。

受到 RVM（Ruby Version Manager）的啟發，Groovy 也有類似的安裝管理工具 GVM（Groovy enVironment Manager），GVM 不僅可以自動下載安裝最新版本的 Groovy，也可以輕鬆在不同 Groovy 版本之間自由切換。

安裝 GVM。

    curl -s get.gvmtool.net | bash

重新打開終端機，再使用 GVM 安裝 Groovy 的最新版本。

    gvm install groovy

使用 GVM 可以查詢並指定安裝哪個版本的 Groovy。

    gvm ls groovy
    gvm install groovy 2.1.9
    gvm use groovy 2.1.9

## 執行 GroovyConsole ##

使用 Sublime Text、Vim 或任何一種文字編輯器，都可以撰寫 Groovy 程式。但筆者建議先從 GroovyConsole 開始，這是在 Groovy 安裝後就內建的簡易編輯器，可以撰寫並直接執行 Groovy 程式碼。

在 Windows 的應用程式集可以找到「Start GroovyConsole」啟動捷徑；Linux 及 Mac OS X 系統，則必須使用終端機輸入「`groovyConsole&`」指令執行。

在 GroovyConsole 的程式碼編輯區，輸入一段 Groovy 程式碼，再點選上方工具列的「Execute Groovy Script」或選單的「Script / Run」，就可以在下方的訊息輸出畫面看到程式執行結果。

<div style="text-align:center"><img src="https://lh4.googleusercontent.com/ZS7zT4__0mqRrFsS-S3o76STDJACncTj_-7KVcSbW0s=w1640-h1336-no" alt="" style="max-width:100%" /></div>

使用鍵盤組合鍵「Ctrl + R」可以方便執行寫好的程式碼。

需要特別提醒讀者的一點，如果 Groovy 程式執行過程有問題，造成 JVM 當機，會導致 GroovyConsole 程式也一併被強制關閉，因此隨時存檔再執行是個必要的好習慣。

## Groovy Shell ##

Groovy Shell 提供「交談式」程式執行功能，讓開發者可以用來測試一些程式片段。在終端機輸入「`groovysh`」就可以進入 Groovy Shell 的畫面，這個工具類似 Python 的 IDLE 或 Ruby 的 IRB，提供 REPL（Read–eval–print loop）的操作介面。

<div style="text-align:center"><img src="https://lh4.googleusercontent.com/MDAQv9o8HSyOmAt2UO9HFfDqfZ-12oIJ_1VgUmK6O0M=w1916-h1206-no" alt="" style="max-width:100%" /></div>

Groovy Shell 用於測試 API 時相當管用，舉例來說，如果你想用某個 String 類別的物件方法，但是想不起方法的名稱，除了找 JavaDoc 查詢 API 用法外，還可以利用 TAB 按鍵查詢方法列表。

例如輸入「`"abc".t`」或「`"abc".s`」再按下 TAB 按鍵，就會列出以 t 或 s 為首的相關方法。 

<div style="text-align:center"><img src="https://lh5.googleusercontent.com/u5zBjED-T_bTw0sicMd8rRMpaL28wyuAdXNKSlgSlzk=w1916-h762-no" alt="" style="max-width:100%" /></div>

## Groovy Web Console ##

[Groovy Web Console](http://groovyconsole.appspot.com/) 是架設在 Google AppEngine 的網站服務，只要用瀏覽器打開「groovyconsole.appspot.com」，就可以在線上執行一段 Groovy Script，並且可以將 Code Snippets 透過網頁連結分享給其他人。

<div style="text-align:center"><img src="https://lh6.googleusercontent.com/AXGKbI8Pk4o5UEbgdjNp_-hhEckyS-2X2v94OvK4aGw=w1568-h1336-no" alt="" style="max-width:100%" /></div>

## 學習資源 ##

從 Groovy 的官方網站取得最新發佈消息、已發佈的軟體版本及說明文件。
從 Groovy 專案核心開發者 Guillaume Laforge 的部落格。

Java Scripting Programmer's Guide
JSR 223（Scripting for the Java Platform）
JSR 223 Scripting with Groovy

專案

Groovy
Gradle - 專案自動化建置工具
Grails - MVC網站開發框架，Rails-like Web Application Framework
Griffon - Rich Application Framework inspired by Grails
雜誌

GroovyMag 提供 Groovy & Grails 開發情報的數位雜誌（每期 $4.99 USD）
書籍

Programming Groovy, 2/e, Dynamic Productivity for the Java Developer
Making Java Groovy by Kenneth A. Kousen
Groovy in Action, 2/e
DSL, Domain-specific Language

Book: Domain Specific Languages by Martin Fowler