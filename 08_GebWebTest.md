# Groovy Tutorial（8）使用 Geb 開發 Web Test 網站自動化測試（中） #

[Groovy Tutorial（7）使用 Geb 開發 Web Test 網站自動化測試（上）](http://www.codedata.com.tw/java/groovy-tutorial-7-greb-web-test-part1/)

在上篇教學中，我們已經初步認識 Geb 工具。以強大的 Selenium WebDriver 為核心，結合類似 jQuery 的 DOM 選取與操作方式，使用 Groovy DSL 易讀易寫的語法開發，使得 Geb 成為受矚目的新一代瀏覽器自動化測試工具。

## Geb 程式範例回顧 ##

Geb 測試程式本身就是 Groovy 原始碼，使用 Groovy Console 或使用個人喜愛的文字編輯器（如 Sublime Text 或 Vim），即可輕鬆撰寫、執行 Geb 程式，完全不需要依賴特定的編輯工具。

以下是一個最簡易的 Geb 程式，包含 Grapes 的相依套件宣告。在裝有 Groovy 與 Firefox 瀏覽器的電腦上即可執行。

範例：ex01.groovy

```
@Grapes([
    @Grab('org.gebish:geb-core:0.9.2'),
    @Grab('org.seleniumhq.selenium:selenium-firefox-driver:2.42.0'),
    @Grab('org.seleniumhq.selenium:selenium-support:2.42.0')
])
import geb.Browser

Browser.drive {
    go 'http://www.codedata.com.tw/'
    
    quit()
}
```

執行方式：終端機執行指令「`groovy ex01.groovy`」。

## 瀏覽器操作指令 ##

`Browser.drive` 開啟一個 DSL 程式區塊，在這個區塊中可以使用 Geb 提供的指令操作瀏覽器。在本節中介紹的瀏覽器操作指令，都必須撰寫在 `drive` 的區塊中。

```
Browser.drive {
    // Geb DSL here!!!
}
```

使用「`go`」命令瀏覽器開啟一個網址，這個範例以 CodeData 網站為例。

```
    go 'http://www.codedata.com.tw/'
```

如果希望 Geb 的任務完成後，就關閉瀏覽器視窗結束程式，可以使用「`close()`」指令。但是在 Groovy Console 下執行 Geb 程式時，並不建議將瀏覽器視窗關閉，因為這麼做會使得 Selenium WebDriver 無法再次執行任務，需要重新打開 Groovy Console 才能繼續執行 Geb 程式。

```
    close()
```

另一個結束指令為「`quit()`」，它不僅關閉瀏覽器視窗，也會終止 WebDriver 的執行。

```
    quit()
```


println title


## 使用 WebDriver API ##

Geb 讓測試程式更容易撰寫，

println driver.pageSource


## 截取測試結果 ##

report



## 使用不同的瀏覽器 ##

* Firefox
* Chrome

