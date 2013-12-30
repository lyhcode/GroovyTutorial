<img src="https://lh5.googleusercontent.com/4YM_JnVm8NLgBxNF6WTRGnJbKnjWcXRFqpv7iS_iNKU=w2000-h998-no" alt="" style="width:100%" />

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

上面的程式碼看起來已經相當精簡，但是可別以為 Groovy 只是簡化版的 Java 語言。還記得大師 Martin Fowler 曾寫一本「[Domain Specific Languages](http://martinfowler.com/books/dsl.html)」（領域描述語言，簡稱ＤＳＬ）的經典著作嗎？也許你早已知道 DSL 的美好，但是卻不知道怎麼實際應用它。

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

Groovy 已經對一些常用的 Java API 類別做了擴充，例如 String 和 URL 這些相當基本的類別，在 Groovy 的版本具有更強大的功能。

```
// Execute external command and get results
execResult = "echo hello".execute().text

// Fetch html response from a url
htmlText = new URL("http://google.com/").text
```





Groovy 是動態語言（Dynamic Language），必須在 Java 虛擬機器平台上執行，它的語法和 Java 十分相似，可以視為 Java 的方言，大部份的 Java 程式碼可被當成 Groovy 使用。但是 Groovy 能讓 Java 程式寫得更加簡潔，既可以不必宣告變數型別，語法規則也比 Java 寬鬆，例如每行敘述結尾可以省略「;」符號結尾、只使用「print」就能輸出訊息。Groovy 讓程式設計師有不同的選擇，既可寫得像標準 Java 的風格，但是也可以變成類似 Ruby 或 Python 的風格。

以下兩種敘述語句，同樣是 Hello World 的 Groovy 程式碼，第一種與 Java 的寫法無異，但是第二種寫法看起來更加簡潔。

// Java-like
System.out.println(“Hello World");

// Groovier
print “Hello World”

想進一步認識 Groovy 嗎？

從 Groovy 的官方網站取得最新發佈消息、已發佈的軟體版本及說明文件。
從 Groovy 專案核心開發者 Guillaume Laforge 的部落格。
Java 的美麗與哀愁

TIOBE 提供程式語言熱門程度的排行榜（Programming Community Index），觀察歷史排名的變化，不難發現 Java 歷久不衰的熱門程度，幾年來始終保持在前三名之列。

Java 不僅有大量 Open Source 社群開發者的支持，也有多家跨國大型軟體公司相挺，形成的經濟體難以被撼動；我們可以預期 Java 在未來幾年仍會有舉足輕重的地位，在 Web-based 及 Cloud 應用成為主流的今日，Java 在 Server-side 扮演的角色已不易被取代。

從 1995 年誕生迄今，Java 已經不是年輕的程式語言，也一直被大量應用著。有著豐富的 Java API，還有大量的函式庫與框架，能夠滿足企業級的開發需求；例如 Apache Commons 與 Spring Framework 等，Web Server 也有輕量的 Jetty、老字號的 Tomcat 及商業付費的其他多種選擇。

傳統的 Java 語言已經變得過度複雜，常令人失望和卻步。但是 Java 世界龐大的建設，都是由傳統的 Java 程式語言開發；儘管許多開發者渴望更時髦的 Java 語言，但它的規格並不可能輕易修改擴充，歷史的包袱使得 Java 語言不可能快速地進化。

Groovy 的誕生，開啓 Java 世界新的一扇窗。

Groovy 結合許多其他語言的優點，像是 Smalltalk、Ruby 或 Python 等。例如那些你在寫 Ruby 能得到的快樂，也有機會透過 Groovy 在  Java 世界實現；對於一個有經驗的 Java Web Developer，只要學會 Groovy 語法，就不必再羨慕 Ruby on Rails 的敏捷快活，因為 Groovy on Grails 也能做到同樣的事。

Polyglot Programming in the JVM

Java 的世界不只有 Java 程式語言！

先談談什麼是 Polyglot Programming 呢？

以微軟的 .NET 開發平台為例，MSDN 文件提供 C# / C++ / F# / JScript / VB 等五種不同語言的使用範例。雖然使用不同的程式語言撰寫，但它們都使用相同的 .NET Framework API。



Java 虛擬機器（JVM, Java Virtual Machine）的設計，亦支援 Polyglot Programming。不論用什麼語言寫程式，只要最後能夠編譯成 Java Bytecode，就可以在 JVM 平台上面運行。

儘管在官方的 Java API 文件中，看不到可以使用其他程式語言寫程式的範例，但事實上在 Java 世界用其他語言寫程式，早已盛行多年而且可用於實際產品開發。

常見的 JVM Languages 包括：

Groovy
Clojure
Scala
JRuby
Jython
Rhino
⋯還有更多。
Polyglot Programming 給 Java 程式設計師更多的語言選擇。

舉例來說，你可以：

用簡潔的 Scripting Language 方式寫 Java 程式。
不必等待 Java SE 8，就能使用函數型編程（Functional Programming）。
使用其他語言的 Framework，例如 JRuby on Rails。
移植其他語言寫的程式碼或函示庫（libraries）。
混搭不同程式語言，各取其長處。
不同程式語言開發的函示庫，在 JVM 可以交互使用。以大家熟悉的 JavaScript 為例，在程式中直接使用 Java SE API 的 Thread 撰寫多執行緒程式，可以看到 JavaScript 的 Anonymous Function 與 Java 的 Runnable / Thread 類別有趣的共存：

var obj = { run: function () { print(“Hello Thread\n"); } }
var r = new java.lang.Runnable(obj);
var t = new java.lang.Thread(r);
t.start();

（註：這段 JavaScript 程式碼必須使用 Rhino 才能執行。）

JVM Scripting Language

Ousterhout's dichotomy（Oursterhout, 1998）將程式語言類型歸納成以下兩種：

System Programming Language（系統程式語言）

靜態型別
可支援建立複雜資料結構
程式被編譯成機器碼
Scripting Language（腳本語言）

動態型別
較不適合複雜資料結構
程式以直譯方式執行
（資料來源：參考自 Wikipedia）

Groovy 兼顧上述兩種不同特性，可以當作 Scripting Language 以直譯方式執行，也可以將 Groovy 的類別編譯為 Java Byte Code，執行方式與一般 Java Class 無異。

Java SE 6 版本開始內建 Java Scripting API，提供 ScriptEngine 介面類別，讓不同的 JVM Scripting Language 有統一的管理方式。

// Scripting with JavaScript
ScriptEngineManager factory = new ScriptEngineManager();
ScriptEngine engine = factory.getEngineByName("JavaScript");
engine.eval("print('Hello, World')");

Java SE 6 並無內建 Groovy 或其他 JVM Scripting Language，但是只要將符合 JSR-223 規範的 Library 加到 CLASSPATH，就能使用相同的 ScriptEngine Interface  執行。

// Scripting with Groovy
ScriptEngineManager factory = new ScriptEngineManager();
ScriptEngine engine = factory.getEngineByName("JavaScript");
engine.eval(“print ‘hello'");

Java Scripting API 參考資料：

Java Scripting Programmer's Guide
JSR 223（Scripting for the Java Platform）
JSR 223 Scripting with Groovy
Why Groovy?

Scripting Language 帶給 Java 應用程式的設計更多彈性，以下是一些實際應用案例：

Java 強大的報表工具 iReport / JasperReport，利用 Groovy 簡化報表製作時的程式撰寫；在處理報表資料顯示時，可以利用 Groovy Scripting 進行程式運算。

iReport Designer - Groovy

Gradle 是新一代的專案自動化建置工具，提供 Groovy DSL 的方式設定專案、定義任務，可程式（programmable）的彈性遠高於過去 Ant / Maven 採用 XML 格式的設定檔。

Gradle The Enterprise Build Automation Tool

受歡迎的 Jenkins CI 持續整合工具，提供 Script Console 可利用 Groovy 進行程式化的維護管理。

Jenkins Script Console

Spock Framework 提供以更簡潔的 Groovy 語法撰寫測試案例，可以用於 Java 或 Groovy 程式碼的單元測試。用更簡單乾淨易懂的語法撰寫單元測試程式，相信是很多 Java 開發者共同的渴望。

Spock Enterprise-ready Testing and Specification Framework

DSL Support

提供 Metaprogramming、AST Transformations 與 Builders，讓 Groovy 成為設計 DSL（領域語言）的不二選擇。

Groovy 的出現，不會取代原有 Java 語言的位置。在設計應用程式時，主要的基礎功能、函式庫，仍然可以繼續使用 Java 語言開發程式；但是在部分功能需要 Scripting Language 的彈性時，Groovy 通常是理想的搭配組合。

舉例來說，如果使用 Java 開發一套文字編輯工具，希望其他開發者能夠自己寫 Plugins 外掛擴充新功能，利用 Groovy Scripts 開發 Plugins 就能帶來很多便利性；例如近期廣受歡迎的 Sublime Text 工具，就能以 Python 輕鬆撰寫外掛，擁有社群貢獻大量的擴充套件；反觀在 Java 世界歷史悠久的 jEdit 工具，雖然也提供 Plugins 擴充功能，但無法用簡單的 Script 方式開發，一般入門者大概都會卻步。

近期我們利用 Groovy 開發 Java Web Start 應用，也得到很好的成果，先利用 JNLP 下載執行一個傳統 Java 開發的啟動程式，接下來就交給 ScriptEngine 開啓 Groovy Scripting 的世界，開發效率獲得不少提升。

學習 Groovy 也可帶來許多附加價值，它讓你可以繼續專注在 Java 技術領域，但同時享受 Scripting Language 的便利性。

學習資源

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