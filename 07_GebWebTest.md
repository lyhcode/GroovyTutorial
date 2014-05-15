# Groovy Tutorial（7）使用 Geb 開發 Web Test 網站自動化測試 #

[Groovy Tutorial（6）HTTP 資料存取基礎 << 前情](http://www.codedata.com.tw/java/groovy-tutorial-6-url-httpbuilder/)

Geb 是瀏覽器自動化（browser automation）的解決方案 ，它是 Groovy 用於 Web Test 自動化測試的 DSL（Domain-Specific Language）。Geb 的內部是 Selenium WebDriver 瀏覽器自動化測試引擎，提供類似 jQuery Selector 的 DOM 操作方法，可以與多種 Java 測試框架整合搭配。使用易讀易寫的 Groovy DSL 語法撰寫 Script 可以簡化工作，Geb 是不能錯過的 Web Test 自動化測試框架。

## Web Test ##

現代 Web Application 專案愈來愈複雜，只做基本的 Unit Test 並不夠驗證軟體品質。在一個導入持續整合（Continuous Integration）開發流程的專案中，我們期望專案除了通過程式碼的單元測試，實際部署到伺服器的測試站台，也要能夠以使用者的角度進行更完整的測試。

使用瀏覽器實際進行網站功能的操作，這是最接近 End User 行為的方式，即使有很高的 Unit Test 覆蓋率，最終仍不免要實際以瀏覽器操作各項功能，才能確保呈現給使用者的網站能夠正常使用。

由於瀏覽器版本相當多，在不同的作業系統上也可能存在些許差異，因此傳統的 Web QA Test 總是相當耗費人力，並且不斷重複耗時且枯燥乏味的工作。如何讓測試的工作自動化，是許多開發者努力的方向。

撰寫程式自動化測試網站各項功能，並無法取代所有測試工作的人工作業，因為許多人腦可以很容易處理的資訊，並無法輕易用程式取代。自動化測試無法完全取代網站測試所需的人工作業，但我們仍期望 80/20 法則能實踐，至少八成枯燥乏味的重複作業，能夠撰寫自動化測試程式取代，讓測試人員專注完成更重要的工作。

## Sellenium WebDriver ##

[Sellenium](http://docs.seleniumhq.org/) 是為瀏覽器自動化（Browser Automation）需求所設計的一組工具集，它讓我們直接用真實的瀏覽器來自動操作一個網站，將 Sellenium 應用在自動化測試時，除了檢驗網頁內容、填寫表單等基本操作，也能驗證 JavaScript 的功能是否正常執行等，因為 Sellenium 操作的網站畫面就是瀏覽器呈現給使用者的最終結果。

如果是第一次接觸 Sellenium 的讀者，可以從 Sellenium IDE 這個工具開始認識。Sellenium IDE 需要搭配 Firefox 瀏覽器使用，在安裝 [Sellenium IDE Plugins](http://docs.seleniumhq.org/projects/ide/) 之後，我們就能在 Firefox 瀏覽器打開 Sellenium IDE 視窗，進行網站操作的「錄製」，然後再「重播」一次所有被錄下來的動作。這就是 Sellenium 進行網站自動化測試的基礎，我們需要先建立一組測試案例（Test Case），定義那些需要被執行的步驟，例如填寫表單、按下送出按鈕等，然後再重複執行這些動作，檢查其結果是否符合預期。

![Sellenium IDE](images/sellenium-ide-google-search.png)

許多 Web Test Framework，都是以 Sellenium API 作為基礎，功能強大且穩固已經讓 Sellenium 成為瀏覽器自動化的基石。Sellenium 2.0 帶來 WebDriver 的實作，跨越不同瀏覽器的自動化操作，有更清楚定義的標準可循，目前 [WebDriver API](http://www.w3.org/TR/webdriver/) 規範已提交 W3C，若能夠被標準化且在各大瀏覽器實作，執行跨瀏覽器的自動化測試工作將會被簡化許多。

在 Sellenium 及開放源碼社群的努力下，已有許多 WebDriver 可供使用，包含目前佔有率最高的 Google Chrome、Firefox 與 InternetExplorer，已能滿足大多數網站自動化測試的需求。

Sellenium WebDriver 支援 Java、C#、Ruby、Python 及 Perl 等多種語言，使用 WebDriver API 可以實現。

    WebDriver driver = new FirefoxDriver();
    driver.get("http://www.google.com");
    WebElement element = driver.findElement(By.id("coolestWidgetEvah"));


## Geb ##


