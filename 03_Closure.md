# Groovy Tutorial（3）淺談 Closure 程式設計 #

認識 Closure 是設計 Groovy 程式的重要基礎，本篇提供淺顯易懂的範例說明，希望幫助讀者更快掌握 Closure 的觀念，並活用在 Groovy 程式的撰寫。開始使用 Closure 設計 Groovy 程式，可以發現 Groovy 不只簡化 Java 的語法，它也許還能啟發 Java 開發者不同的設計思維。

## Groovy JDK 與 Closure 參數 ##

Groovy 擴充 Java SE API，提供一組 Groovy JDK（簡稱為 GDK），它讓 Java 類別具有更豐富的功能。

被擴充的類別，具有與 Java 類別相同的命名，包括套件名稱（package name）與類別名稱（class name），例如「`java.io.File`」。在 Groovy 程式中使用 `new File()` 建立的物件，就是 GDK 提供的版本，功能比原始的 Java 類別更多。

GDK 提供的同名類別，稱為 Helper Class（擴充自 Java 原有的類別），查閱 Groovy SDK 的 [API 文件](http://groovy.codehaus.org/groovy-jdk/)，可以了解有哪些類別及方法可供使用。

Helper Class 讓程式撰寫風格可以 Groovier（更有 Groovy 程式的風味），明顯的例子就是使用那些包含 Closure 參數的方法。

例如 `File` 類別就有數個方法包含 Closure 參數，以下列舉其中四個方法：

* `eachLine(Closure closure)`
* `eachLine(String charset, Closure closure)`
* `withWriter(Closure closure)`
* `withWriter(String charset, Closure closure)`

許多 GDK 類別的方法都使用 Closure 型別的參數，所以認識 Closure 才能活用 GDK API 設計程式。

那麼這些 Closure 參數到底如何使用呢？

初學者容易產生誤會，看到文件可能誤以為需要 ``new Closure()`` 產生新的 Closure 物件（Instance），然後當成參數來傳遞。

確實可以這樣做，但這並不是正常的 Groovy 程式撰寫方式。

## 認識 Closure ##

在 Groovy 的世界中，Closure 是一種 first class object，類似 JavaScript 的匿名函式（anonymous function）。Groovy 使用「`{ ... }`」區塊的語法，來建立一個 Closure 物件。

例如，計算平方的函式，可以使用 Closure 改寫。

```
def square = { x ->
  x * x
}

square(3)
```

Groovy 的 Closure 有「Code as data」的特性，實際上它會建立 `Closure` 型別的物件，但內容是可被執行的 Groovy 程式。Closure 是程式也是資料，它不僅可以儲存成變數，也可以當作參數被傳遞到其它函式或方法。

參考以下範例，三個 `calc` 方法都包含一個 Closure 型別的參數。

```
class MyClass {
  def num
  def calc(Closure closure) {
    closure(num)
  }
  def calc(n, Closure closure) {
    closure(n)
  }
  def calc(n1, n2, Closure closure) {
    closure(n1, n2)
  }
}
```

`MyClass` 類別提供三個方法，都把 Closure 放在最後一個參數，如此就能在呼叫方法時，執行 Closure 提供的程式功能。

先產生一個 `MyClass` 物件，同時指派 5 作為 `num` 屬性的值。

```
mc1 = new MyClass(num: 5)
```

在存取 `calc` 方法時，接在後方的 Closure，會被當作參數資料傳入 `calc` 方法。當這個 Closure 的程式被執行時，變數 `x` 的來源是 `MyClass` 物件的 `num` 屬性值，所以執行結果是 25（計算 5 的平方）。

```
mc1.calc { x ->
  x * x
}
```

增加一個參數，根據多載函式的規則，會執行 `calc(n, Closure cls)` 這個方法。同樣利用 Closure 計算數字的平方，但數字的來源是自定的參數。

```
mc1.calc(3) { x ->
  x * x
}
```

計算 x 的 y 次方，執行結果 25 是 5 的 3 次方。

```
mc1.calc(5, 3) { x, y ->
  (int)Math.pow(x, y)
}
```

## 使用 Closure 的檔案處理範例 ##

有了 GDK 提供 Helper Class 的幫助，使用 `java.io.File` 類別進行檔案處理變得更容易。例如將一段文字儲存至「output.log」檔案，可以使用 `setText(String text)` 這個方法輕鬆完成。 

```
file1 = new File('output.log')
file1.setText('hello\n')
```

只帶一個參數的 `setText` 可以換個方式改寫，Groovy 簡化 Setter 方法的使用方式，省略「set」文字，看起來更像把值指派給一個物件的屬性。

```
file1.text = 'hello\n'
```

使用 `getText()` 方法，可以讀取檔案的文字內容。

```
content = file1.getText()
```

同樣也能把 Getter 方法的「get」省略。

```
content = file1.text
```

撰寫 Groovy 程式時，讓程式碼看起來更簡潔是件愉快的事。

但是如果遇到一邊讀檔一邊處理的需求時，怎麼做才是 Groovy 程式的風格呢？

又輪到 Closure 上場囉！

許多 GDK 的 Helper Class 利用 Closure 參數擴充原有的類別，在 `File` 類別中就有相當多這樣的例子。

如果需要逐行處理文字檔的內容，使用 `eachLine(Closure closure)` 能夠輕鬆完成任務。

```
new File('abc.txt').eachLine { line ->                                                                                                                                                            
  println line
}
```

在 eachLine 後面加上 Closure 就能搞定，這是 Groovy 程式的風格！

使用 `withWriter(Closure closure)` 可以在 Closure 取得 `BufferedWriter` 物件，利用它把文字訊息寫入檔案。

如果省略 Closure 中的參數命名，會使用「it」當作預設參數名稱。以下利用 `getClass().getName()` 顯示「it」的類別名稱，並驗證 `BufferedWriter` 繼承自 `Writer` 類別。

```
def stats = new File('stats.log')

stats.withWriter {
    println it.class.name
    println it instanceof BufferedWriter
    println it instanceof Writer
}
```

由於 `BufferedWriter` 繼承 [`Writer`](http://groovy.codehaus.org/groovy-jdk/java/io/Writer.html) 的 `leftShift(Object value)` 方法，這會允許 Groovy 程式利用「`<<`」運算子輸出資料。

以下範例程式將數字 1 至 10 依序寫入檔案。

```
stats.withWriter { writer ->
  (1..10).each {
    writer << "${it}\n"
  }
}
```

利用 Closure 撰寫 Groovy 程式，不只是一種風格，也帶來更靈活的程式設計思考方式。

舉例來說，我們可以設計一個程式，讓使用者動態選擇、增加不同的資料處理程序（processors）；這些程序可以用 Closure 方式設計，不僅是可以被執行的函數，也能當作資料保存在 List 中即當作參數傳遞。

以下分別定義不同的 Closure 程序，計算平方根與平方。Closure 被當作資料放入 `processors` 的 List 容器中，這些 Closure 在 List 中可以新增、移除或改變順序，最後再依序（在 List 中的順序）被執行。

```
def processors = []

processors << { line ->
    println Math.sqrt(line.toInteger())
}

processors << { line ->
    println Math.pow(line.toInteger(), 2).toLong()
}

processors.each { stats.eachLine it }
```

## 更多 Closure 的程式設計範例 ##

使用 `File` 的 `withWriter` 或 `withReader` 方法，在 Closure 程式中只要專心處理資料，而不必理會 Java 檔案存取的其他細節，例如 open 與 close 的動作。

存取串流或資料庫等資源時，經常會有類似的操作，通常我們需要小心翼翼地使用 try-catch-finally 敘述來處理。

為了說明如何用 Closure 解決問題，我們先設計一個 `MyStream` 類別。

`MyStream` 包含常見的資料存取功能，開始使用 read / write 存取資料之前，必須先執行 open 的動作；如果有 Exception 發生就停止後續動作，但無論如何最後都要確保 close 動作正常被執行，才能避免資源被非預期的佔用。

```
class MyStream {
  def open() { println 'open stream' }
  def close() { println 'close stream' }
  def read() { println 'read action' }
  def write(obj) { println "write action with ${obj}" }
  def exception() { throw new Exception('bad news') }
}
```

爲免於煩人的 try-catch-finally 不斷出現，我們定義一個 `use` 標準處理程序，確保 Exception 發生時能夠顯示錯誤訊息，且 close 動作一定會被執行。

```
def use = { MyStream stream, Closure closure ->
  try {
    stream.open()
    closure(stream)
  }
  catch (e) {
    println e.message
  }
  finally {
    stream.close()
  }
}
```

使用 `use` 來操作 `MyStream` 的資料存取，可以發現程式碼不再冗長，能夠更專注於處理真正要解決的問題。

```
def stream1 = new MyStream()

use(stream1) {
  it.read()
  it.write(new Object())
  it.exception()
}
```

再來看一個簡單的範例，我們讓 List 增加新的功能，讓新增加到 List 的資料先經過篩選，只有符合條件的資料才被允許加入，否則直接忽略丟棄。

```
class ListWithFilter extends ArrayList {
  Closure filter
  def leftShift(obj) {
    if (filter && filter(obj)) add(obj)
  }
}
```

`ListWithFilter` 的 `filter` 屬性用來指派一個資料篩選器，根據不同的資料篩選條件，可以分別用數個 Closure 定義篩選器。

```
filters = [:]
filters.isOdd = { it % 2 != 0 }
filters.isEven = { it % 2 == 0 }
```

產生 `ListWithFilter` 物件時，同時指派一個篩選器（isOdd 用來判斷資料是否為奇數）。試著將 1 至 100 的數字增加到 List，如果篩選器發揮作用，最後只有符合奇數條件的資料成功被加入。

```
def list1 = new ListWithFilter(filter: filters.isOdd)

(1..100).each { list1 << it }
println list1
```

## 參考資料 ##

* [Groovy User Guide - Closures](http://groovy.codehaus.org/Closures)
* [Martin Fowler's closure examples in Groovy](http://groovy.codehaus.org/Martin+Fowler's+closure+examples+in+Groovy)
* [Code as data, or closures](http://groovy.codehaus.org/Tutorial+2+-+Code+as+data,+or+closures)
* [Programming Groovy 2 - Using Closures](http://media.pragprog.com/titles/vslg2/closures.pdf)
