<img src="https://lh5.googleusercontent.com/4YM_JnVm8NLgBxNF6WTRGnJbKnjWcXRFqpv7iS_iNKU=w2000-h998-no" alt="" style="width:100%" />

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

但是相同的程式若改用 Java 撰寫，事情就變得複雜許多。

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

有趣的是上面這段標準的 Java 程式碼，其實也是一段符合 Groovy 語法規則的程式，可以直接使用 Groovy 以直譯方式執行它。

Groovy 提供函數型程式設計（Functional Programming）的必要功能，例如 Lambda 及 Closure 的支援。函數型程式設計在 Java SE 8 才開始支援，但其實只要改用 Groovy 寫程式，使用 Java SE 6 或 7 + Groovy，就能開始撰寫函數型風格的程式。

```
def func = { a, b -> 2 * a + 3 * b }

func(1, 2)  // return 8
```

Groovy 適用於多種類型的應用程式設計，包括桌面視窗程式、網路服務程式、網站或資料庫應用程式等。



Groovy 是動態語言（Dynamic Language），必須在 Java 虛擬機器平台上執行，它的語法和 Java 十分相似，可以視為 Java 的方言，大部份的 Java 程式碼可被當成 Groovy 使用。但是 Groovy 能讓 Java 程式寫得更加簡潔，既可以不必宣告
