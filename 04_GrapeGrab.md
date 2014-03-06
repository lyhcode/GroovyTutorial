# Groovy Tutorial（4）使用 Grape 與 Grab #

Java 世界有大量豐富的 Library，可以被重複利用的函式庫，封裝成 JAR 檔案發佈，大部份開放原始碼的專案，都可以在 Maven Repository 找到最新版本的 JAR 檔案。Groovy 提供 Grape 與 Grab 的功能，讓開發者可以方便使用相關套件資源，很輕鬆地利用 Groovy Script 完成許多任務。

## 認識 Maven Repository ##

Maven 是 Java 的自動化建置工具，可以編譯 Java 原始程式、把專案封裝成 JAR 檔案，更重要的是，它提供相依性管理（Dependency Management），並且讓 Java 社群開始建置 Open Source 函式庫的 Repository。Maven Repository 是保存函式庫 JAR 檔案的倉庫，大多常見的 Java 開放源碼函式庫，都可以從網路上公開的 Maven Repository 取得已經打包的 JAR 檔案。

有不少開源的 Java 函式庫透過 Maven Repository 發佈，例如下圖是 Spring Framework 專案的網頁內容，它沒有提供檔案下載的連結，而是提供一段 Maven 或 Gradle 的 Dependency 設定範例。

![image](images/spring-maven-repository.png)

只要有以下三項參數資訊，就可以從 Maven Repository 取得所需要的 JAR 檔案。

1. `groupId` （例如：commons-io）
2. `artifactId` （例如：commons-io）
3. `version` （例如：2.4）

目前有不少 Open Source 組織架設 Maven Repository 的伺服器，例如：

* http://repo.maven.apache.org/maven2/

Repository 伺服器使用 HTTP(S) 協定提供檔案，標準的路徑結構格式為：

`[groupId]/[artifactId]/[artifactId]-[version].jar`

因此取得 JAR 檔案的 URL 來源是：

`http://repo.maven.apache.org/maven2/commons-io/commons-io/2.4/commons-io-2.4.jar`

實際上 Dependency Management 的運作比上述更複雜，Maven 使用「POM（Project Object Model）」設定專案組態，解析 Dependency 需要先從 Repository 伺服器取得 pom.xml 的組態檔，才能獲取正確的 JAR 檔案下載位址。

對於使用 Groovy 的開發者，不必瞭解太多關於 Maven 的細節；未來如果有 Groovy 的專案自動化建置需求，更多開發者偏好使用 Gradle 工具。到目前為止，我們只需要知道，許多公開的 Maven Repository 提供大量 Java Library，撰寫 Groovy 程式時，可以方便地取用這些 JAR 資源。

## 使用 Grape 指令工具 ##

Groovy 提供「`grape`」的 command-line 指令工具，可以用於 Dependency Libraries（亦稱為 grapes）的管理。

執行 `grape --help` 可以看到 `grape` 提供的指令用法。

* install      Installs a particular grape
* uninstall    Uninstalls a particular grape (non-transitively removes the respective jar file from the grape cache)
* list         Lists all installed grapes
* resolve      Enumerates the jars used by a grape

使用 Grape 安裝套件時，需要以下三個參數，基本上與 Maven 的用法一致，但是在 Groovy 我們慣用 module 而非 artifact 來稱呼套件名稱。

* group （Maven 的 groupId）
* module （Maven 的 artifactId）
* version

安裝新套件至少需要兩個參數，依序是 group 與 module，如此才能完整辨識一個套件；若不指定 version 參數，就會取得最新版本。以下是安裝新套件的指令範例：

* `grape install commons-io commons-io`
* `grape install commons-io commons-io 1.0`

使用 `grape list` 可以查詢哪些套件已被安裝，以及有哪些已安裝的版本。

* `commons-codec commons-codec  [1.6]`
* `commons-collections commons-collections  [3.2.1]`
* `commons-io commons-io  [1.0, 2.2]`
* `commons-logging commons-logging  [1.1.1, 1.1.3]`

如果需要移除，使用 `uninstall` 指令，需要明確指定移除的版本代碼。

* `gradle uninstall commons-io commons-io 1.0`

## 搜尋 Maven Repository ##



* http://mvnrepository.com/
* http://search.maven.org/


## 使用 Grab 設定相依性 ##


## 參考資料 ##

