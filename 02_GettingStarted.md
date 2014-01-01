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
