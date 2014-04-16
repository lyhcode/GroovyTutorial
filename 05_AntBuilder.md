# Groovy Tutorial（5）使用 AntBuilder 撰寫 Script #

[Groovy Tutorial（4）使用 Grape 與 Grab << 前情](http://www.codedata.com.tw/java/groovy-tutorial-4-grape-grab/)

Groovy 的語法提供 DSL（Domain-Specific Languages）的支援，讓程式碼更容易閱讀與撰寫，其中一種在 Groovy 很常見的 DSL 實作方式為 Builder。產生 XML、JSON 格式的資料，或是建立一個 Swing GUI 視窗畫面，都有專用的 Builder 可以利用。這篇要介紹很實用的 AntBuilder，這個 Builder 可以輕鬆完成許多任務，卻只要非常簡單的語法，是撰寫 Groovy Script 經常會用到的 Builder 功能。

## 什麼是 Ant？ ##

Apache Ant 是 Java 的專案建置工具（build tool），它使用 XML 的設定檔，幫一個專案定義任務，例如建立一個新資料夾、複製檔案或是建立壓縮檔封裝資料等。

範例 build.xml 是一個簡單的 Ant 使用範例，它的功能僅是利用 `<echo />` 任務指令輸出文字訊息。

```
<?xml version="1.0"?>
<project>
    <target name="info">
        <echo>Hello World</echo>
    </target>
</project>
```

Ant 提供許多方便的[任務指令](http://ant.apache.org/manual/tasksoverview.html)（Task），使用指令和參數就能完成一些常見工作，例如檔案系統操作與 Zip 及 Unzip 等。

使用 `<zip />` 任務壓縮檔案。

```
<zip destfile="${dist}/manual.zip"
     basedir="htdocs/manual"
     excludes="mydocs/**, **/todo.html"
/>
```

使用 `<unzip />` 任務解壓縮檔案。

```
<unzip src="${tomcat_src}/tools-src.zip"
     dest="${tools.home}">
  <patternset>
      <include name="**/*.java"/>
      <exclude name="**/Test*.java"/>
  </patternset>
</unzip>
```

刪除一個資料夾的 Ant 任務範例。

```
<delete includeemptydirs="true">
  <fileset dir="build" includes="**/*"/>
</delete>
```

## Groovy 與 Ant ##

作為自動化建置工具，Ant 已經被 Maven 與 Gradle 等新一代開發工具取代，除了接手古老的專案，大概不太會有 Java 開發者想在新的專案搭配 Ant 設定建置任務。

但是當 Ant 遇上 Groovy，卻可以讓我們瞥見 DSL 風格的 Ant Scripting 妙用。利用 Groovy 的 Builder 讓 Ant Library 以另一種形式融入 Script 語言中，然後重複利用那些 Ant 已經有的功能。

`AntBuilder` 是 GDK 內建的類別，不必 `import` 就可以直接建立一個新物件。

```
ant = new AntBuilder()
```

透過 AntBuilder 的 DSL 語法，我們就可以在 Groovy Script 中直接調用已經定義的 Ant Tasks，例如最簡單的 `<echo />` 指令。

```
// equal to <echo>Hello World</echo>
ant.echo("Hello World!")
```

在 Ant XML 中使用的 Tag Attributes 也可以用相等的「`name: value`」參數。

```
// equal to <echo message="Hello World!"/>
ant.echo(message: "Hello World!")
```

因此所有基本的 Ant 任務指令，都可以在 Groovy Script 中透過 AntBuilder 存取。

利用 Ant 已經定義好的任務，剛好彌補 Groovy Script 的一些缺憾，因為傳統的 Java API 總是需要多寫幾行程式碼，才能完成那些很簡單的任務。

舉例來說，如果要在 Groovy Script 刪除一個內含其他檔案與目錄的資料夾，可以有幾種選擇：

1. 自己寫一個 Method 實現刪除資料夾功能，但是重造輪子很浪費時間。
2. 搭配 Apache Commons IO 函式庫，使用 `FileUtils.deleteDirectory(dir);` 只要一行解決。
3. 使用 AntBuilder 也只要一行就能完成任務：`ant.delete(dir:'someDir')`。

上面這個例子，使用 Groovy 內建的 AntBuilder 不必再添加其他 Library（Ant 已包含在 Groovy SDK 裏面），是值得考慮的做法。

若我們把 Groovy 當作 Shell Script 的方式使用，檔案系統的操作，也可以執行系統的指令，例如：

```
"rm -rf someDir".execute()
```

但是使用 AntBuilder 更容易維持 Java 跨平台的優點，讓 Groovy Script 不管在什麼作業系統都能被執行。

## 使用 AntBuilder 寄送電子郵件 ##

這是一個我們將 Groovy Script 應用在系統管理常見的實際案例，利用 Groovy 本身可以直接使用 Java API 與 Library 的優勢，我們可以執行一些 Java 程式，例如透過 JDBC 取得資料庫的某些數據，加以處理然後彙整成電子郵件寄出。

在以下的程式範例，我們先用 AntBuilder 呼叫 Ant 的 Zip Task 將一個資料夾底下的 *.log 記錄檔，全部壓縮成一個 .zip 檔案。

```
def ant = new AntBuilder()
ant.zip(destFile: "tmp/apache2.zip") {
  fileset(dir: "/var/log/apache2") {
    include(name: "*.log")
  }
}
```

發送電子郵件是 AntBuilder 常見的應用，這個範例透過 Gmail 提供的 SMTP 伺服器，將文字訊息與壓縮檔附件寄出。

```
@GrabConfig(systemClassLoader = true)
@Grapes([
    @Grab('javax.mail:mail:1.4.7'),
    @Grab('javax.activation:activation'),
    @Grab('org.apache.ant:ant-javamail:1.9.2')
])

def opts = [
  mailhost: "smtp.gmail.com",
  mailport: 465,
  ssl: true,
  enableStartTLS: "true",
  user: "user",
  password: "password",
  messagemimetype: "text/html",
  subject: "A letter from Groovy Ant Script"
]

def content = new StringWriter()
def builder = new groovy.xml.MarkupBuilder(content)
builder.html {
    body {
        p('Hello World')
    }
}

def ant = new AntBuilder()

ant.zip(destFile: "tmp/apache2.zip") {
  fileset(dir: "/var/log/apache2") {
    include(name: "*.log")
  }
}

ant.mail(opts) {
  from(name: "Administrator", address: "admin@mail.server")
  to(address: "john@mail.server")
  to(address: "kyle@mail.server")
  cc(address: "lisa@mail.server")
  message(content)
  attachments {
    fileset(dir: "tmp") {
      include(name: "*.zip")
    }
  }
}
```

這個範例程式使用的各項設定，如 Gmail 的帳號密碼、寄件者與收件人清單，必須先改為正確的設定值。如果執行成功，就可以看到這個 AntBuilder 輸出的訊息。

```
  [mail] Sending email: A letter from Groovy Ant Script
  [mail] Sent email with 1 attachment
```

由於 Groovy 的 DSL 特性使得程式碼易讀易寫，實際上不需要太多的註解，就能輕易理解這個範例程式的每一行在做什麼事情，如果交給不懂 Groovy 程式設計的使用者修改部分設定，也許也不會太困難。

實際上這個程式還使用這篇未曾提及的 MarkupBuilder 來產生 HTML 代碼，這也是一個常用的 Groovy Builder，對於產生 XML 或 (X)HTML 代碼相當管用，相信也是不需要太多的解釋就能理解。

Groovy Script 提供很多的便利，雖然用傳統的 Java 程式也同樣能做到，但是有很多 Script 小程式，並不需要很嚴謹地當作一個專案來對待，此時 Groovy 提供開發者其他種選擇，可以用類似 Shell Script 或其他 Scripting Language 的方式，輕鬆完成一些任務，卻不改變 Java 程式的本質。