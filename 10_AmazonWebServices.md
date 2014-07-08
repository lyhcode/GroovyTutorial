# Groovy Tutorial（10）實作篇：製作 Amazon S3 檔案上傳工具 #

[Groovy Tutorial（9）使用 Geb 開發 Web Test 網站自動化測試（下） << 前情](http://www.codedata.com.tw/java/groovy-tutorial-8-greb-web-test-part3/)

使用 Groovy 可以讓 Java 開發人員更方便撰寫工具程式，使用熟悉的語法與函式庫，許多我們利用 Shell Script 處理的事情，也能利用 Groovy Script 簡單完成任務。本篇介紹如何用 Groovy 製作檔案上傳工具程式，將指定的資料夾壓縮成 ZIP 檔案，然後備份到 Amazon S3 雲端儲存服務。

學習目標：

* 使用 Groovy Script 存取 Amazon S3 服務
* AntBuilder：使用 Zip Task 壓縮檔案
* CliBuilder：開發 command-line 工具程式
* ConfigSlurper：從外部設定檔讀取程式執行所需的參數

## Amazon S3 儲存服務介紹 ##

S3（[Simple Storage Service](http://aws.amazon.com/s3/)）是 AWS（Amazon Web Services）提供的雲端儲存服務，價格便宜且服務穩定度高，應用範圍也十分廣泛。使用 Amazon S3 服務的好處有：

* 不受限制的儲存容量
* 單一檔案大小可達 5TB
* 依照實際儲存及傳輸量計費，用多少付多少
* 方便產生加密、具時效性的檔案存取網址
* 高可靠度的服務品質，可承受大量同時存取
* 提供東京與新加坡亞太地區機房可供選擇，頻寬及傳輸速度
* 可以搭配 CloudFront CDN 服務加速傳輸

放在 Dropbox 雲端硬碟的檔案，實際上就是儲存在 Amazon S3 服務。

對於 Java 應用程式的開發，可以使用 [JetS3t](http://www.jets3t.org/) 與官方的 [AWS SDK for Java](http://aws.amazon.com/cn/sdkforjava/) 兩種函式庫存取 S3 服務，本文中我們將以 AWS SDK 為例，它可以提供較完整的功能，除了可以存取 S3 服務外，日後也能用來存取其他 AWS 服務。

## 開始撰寫 Groovy Script 程式 ##

我們打算以 Groovy Script 的方式撰寫，這種方法更適合開發此類小工具；傳統 Java 程式需要大費周章建立專案，然後配置 Maven / Gradle 的專案建置設定，最後打包成 .jar 檔案才能方便執行；如此會增加太多不必要的成本，Groovy 讓我們可以用更簡單的方式完成任務。

我們只需要一個命名為 `s3upload` 的檔案。省略 `.groovy` 副檔名，是為了讓它更像一般 command-line 工具程式。

建立 `s3upload` 檔案內容：

```
#!/usr/bin/env groovy

@Grab('com.amazonaws:aws-java-sdk:1.8.2')
import com.amazonaws.auth.*
import com.amazonaws.services.s3.*

// 以下兩行需要替換成正確的 AWS  Access Key ID 與 Secret Access Key
String accessKey = "AWS_ACCESS_KEY_ID"
String secretKey = "AWS_SECRET_ACCESS_KEY"

def credentials = new BasicAWSCredentials(accessKey, secretKey)
def s3client = new AmazonS3Client(credentials)
```

第一行的 `#!/usr/bin/env groovy` 是撰寫 Shell Script 使用的語法，加上這一行讓程式更方便在 Shell 環境中執行。

在 Unix-like（如 Linux 或 Mac OS X）環境中，Shell Script 程式的第一行會使用 `#!...` 特殊註解語法，用來指定 Script 的執行方式（interpreter 的路徑），例如：

* `#!/bin/bash`
* `#!/bin/csh`
* `#!/usr/bin/perl`

如果我們要知道 Groovy 安裝在哪裡，可以用 `which groovy` 找到正確位置。通常 Groovy 不像一般 Unix 常見系統工具會有慣用路徑，為了讓 Groovy Script 更方便執行，我們可以不必使用絕對路徑，利用 `/usr/bin/env groovy` 轉換成正確路徑再執行，安裝於任意位置的 `groovy` 指令都能夠被執行（前提是該路徑必須已包含在 PATH 環境變數）。

利用 `chmod` 系統指令給予 `s3upload` 程式執行權限，我們就能將這個 Groovy 程式當作一般 Shell Script 程式執行。

```
chmod a+x s3upload
./s3upload
```

如果想讓寫好的 Groovy Script 能夠像一般系統指令同樣方便執行，可以將程式放在 `PATH` 環境變數所包含的路徑，例如：

* `/usr/local/bin/s3upload` 

如此一來不管工作目錄（current working directory）在哪個路徑，都能直接使用 `s3upload` 指令來執行這個 Groovy 程式，這樣也更方便與其他 Shell 指令搭配使用。

註解：Windows 的開發者需要先安裝 Cygwin 軟體，才能模擬 Unix-like 的執行環境。

在這個程式中，我們利用 `@Grab` 來配置所需的 dependency library，也就是 AWS SDK for Java 的 [aws-java-sdk](http://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk) 套件，在 MVNRepository 網站可以查詢目前可用的最新版本（1.8.2）。

我們只要在已安裝 JDK 與 Groovy 軟體的電腦，就能夠直接執行此 Groovy Script 程式，程式內容也能夠隨時做修改，比起傳統用 Java 專案方式開發方便許多，而且所需要的函式庫檔案，也會在第一次執行時自動下載，未來如果需要使用更新版的函式庫，只要直接修改程式中的定義即可。

Groovy 讓開發者在 Shell Script 與 Java 程式之間獲得一個兩者優點兼顧的利益，可以讓工具程式像 Shell Script 一樣方便撰寫，又同時能直接存取 Java 豐富的資源，且可以使用已經熟悉的語法。

## 使用 Zip Task 壓縮檔案 ##

我們也可以呼叫系統的 `zip` 指令來壓縮檔案，但是在 Groovy Script 程式中，我們更偏好使用 100% Pure Java 的做法。使用 `ZipOutputStream` 又太過麻煩，在不添加其他函式庫的前提下，使用 Groovy 已經內建的 AntBuilder 似乎是最輕鬆的方法。

使用 Groovy 的 AntBuilder 提供 ZipTask，只需要非常簡短的程式碼，就可以完成資料夾壓縮打包的任務。

```
new AntBuilder().zip(
	destFile: 'file1.zip',
	basedir: 'dir1'
)
```

由於這個工具產生的壓縮檔，只是先暫存在磁碟中，上傳完成後就會刪除，暫存檔要放在哪裡也是需要考慮的問題。一般 Shell Script 會將暫存檔放在 `/tmp` 路徑，這是相當常見的做法，但是在 Groovy Script 程式中，有更簡單的選擇，我們可以使用 Java File API 提供的 `createTempFile` 方法，產生一個不會有檔名重複問題的暫存檔。

```
def zipFile = File.createTempFile("temp", ".zip")
zipFile.delete()
```

自動產生的暫存檔位於哪個資料夾，會因不同作業系統而異，以下是在 Mac OS X 上產生的暫存檔路徑，路徑中包含隨機產生的字串，所以不會有命名重複衝突問題。

* /var/folders/pm/gfrpc2zs291d95fbmcr9shj40000gn/T/temp4800969213160532647.zip

我們只需要暫存檔的路徑，但是 `createTempFile` 會實際產生一個空白檔案，這會造成 Zip Task 執行發生錯誤（因為空白檔案並不是合法的 Zip 檔案格式），因此在第二行使用 `delete()` 方法先將檔案刪除。

將以上程式碼組合在一起，就可以將資料夾壓縮保存至一個暫存檔。

```
def zipFile = File.createTempFile("temp", ".zip")
zipFile.delete()

new AntBuilder().zip(
	destFile: zipFile,
	basedir: 'dir1'
)
```

## 將檔案上傳到 S3 儲存 ##

Groovy Script 僅需要在檔案起始處設定 `@Grab` 的宣告，就能輕鬆使用 AWS SDK for Java 存取雲端服務，帶給 Java 開發者很多便利性，不必下載 `.jar` 套件檔案，也不必配置 Maven 的 `pom.xml` 或 Gradle 的 `build.gradle` 設定檔，可以非常簡易地 Getting started with Amazon Web Services，就像其他 Scripting 語言一樣。

首先回顧本篇第一個範例程式，已經建立 `crendentials` 與 `s3client` 兩個物件。

```
@Grab('com.amazonaws:aws-java-sdk:1.8.2')
import com.amazonaws.auth.*
import com.amazonaws.services.s3.*

// Skip...

def credentials = new BasicAWSCredentials(accessKey, secretKey)
def s3client = new AmazonS3Client(credentials)
```

AWS SDK 的 API 操作，都是從建立 Crendentials 開始，註冊 AWS 服務後，即可從 [AWS Management Console](https://aws.amazon.com/console/) 取得授權的 Access Key ID 與 Secret Access Key，用這一組 Key 來產生 `BasicAWSCredentials` 物件，就能利用它存取各種 AWS 服務 API，在產生 `AmazonS3Client` 物件時，我們就需要將 `credentials` 參數傳給建構子。

Groovy Script 程式很容易撰寫，新增或修改一個段落後，就可以立即執行、測試結果，即使只搭配 Vim 或 Sublime Text 等純文字編輯器，也可以很容易完成上百行的程式碼，不像傳統 Java 總是很依賴 IDE 開發工具；如果習慣有 IDE 豐富功能，也可以選擇支援 Groovy 的 IDE 工具如 IntelliJ IDEA。可以自由選擇用哪種方式撰寫程式，簡單易寫的 Groovy 讓開發者有更多選擇。

開始使用 S3 之前，需要先知道一些專用詞彙：

1. Bucket：S3 的儲存區，就像硬碟的分割區（partition）
2. S3 Object：被儲存在 Bucket 的檔案，每一個都是 Object
3. Object Key：Object 在 Bucket 中存放的路徑，就像磁碟上的資料夾與檔名組成的檔案路徑
4. Object Metadata：用於設定或取得 Object 的屬性如 Content Length 或 Content Type 等

我們可以在 S3 建立多組 Bucket，分別給予不同的命名，對應不同檔案儲存的需求。查閱 [AmazonS3Client](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3Client.html) 的 API 說明，可以開始在 Groovy Script 使用 API 提供的方法存取 S3 服務。

例如，列出所有的 Bucket 名稱。

```
s3client.listBuckets().each {
    println it.name
}
```

也可以檢查一個 Bucket 是否存在，如果不存在就新建一個。

```
def bucketName = 's3.groovy-tutorial.com'

if (!s3client.doesBucketExist(bucketName)) {
    s3client.createBucket(bucketName)
}
```

需要特別注意的地方，就是所有 S3 使用者共用相同的命名空間，所以 Bucket 的命名需要避免與他人重複，一般來說使用網址是個常見的做法，例如：「s3.hello-groovy.com」。利用網址當作 Bucket 命名的做法，也可以在日後搭配網域名稱 DNS 的 CNAME 設定，使用自訂的 S3 存取網址。

使用 `putObject` 將檔案上傳至 S3。

```
// putObject(String bucketName, String key, File file)
s3client.putObject(bucketName, zipFile.name, zipFile)
```

## 使用 ConfigSlurper 讀取設定檔 ##

在程式中我們用到許多參數設定，例如 accessKey、secretKey 及 bucketName 等，為了方便使用者自行調整參數設定，我們需要讓這些參數可以在程式外部被設定。

使用系統環境變數（environment variable）的做法很常見，例如在 Shell 環境中設定變數「AWS_ACCESS_KEY_ID」。

```
export AWS_ACCESS_KEY_ID=...
```

Groovy Script 中可以使用 `System.getenv()` 取得系統環境變數的設定值。

```
System.getenv("AWS_ACCESS_KEY_ID")
```

但是有些情況，我們希望提供一個實際存在的設定檔，方便使用者檢視與修改參數內容。Groovy 提供的 `ConfigSlurper` 可以滿足這個需求，我們可以用 Groovy 程式碼的撰寫方式定義一個設定檔（例如：config.groovy），設定內容很有 DSL 的風格，不僅容易閱讀及修改，也可以加上註解或程式運算處理。

設定檔範例：`~/.s3upload/config.groovy`

```
aws {
	 // comments here
    credentials {
        accessKey = "AWS_ACCESS_KEY_ID"
        secretKey = "AWS_SECRET_ACCESS_KEY"
    }
    s3 {
        bucket = 's3.groovy-tutorial.com'
    }
}
```

利用 `ConfigSlurper` 解析設定檔，轉換成一個容易取得設定內容的 `config` 物件，然後透過它存取程式所需的各項設定參數內容。

```
def configPath = "${System.properties.'user.home'}/.s3upload/config.groovy"
def config = new ConfigSlurper().parse(new File(configPath).toURL())

String accessKey = config.aws.credentials.accessKey                             
String secretKey = config.aws.credentials.secretKey                             
String bucketName = config.aws.s3.bucket                  
```

如此一來，我們就不必將敏感的資料直接放在程式碼，作業系統的不同使用者，也可以分別擁有各自不同的設定內容。使用獨立的外部設定檔更加便利且提升安全性，Groovy 搭配 `ConfigSlurper` 的使用，也可以完全取代傳統 Java 的 `.properties` 設定檔。

## 使用 CliBuilder 設計指令參數 ##

Groovy 提供 `CliBuilder` 類別，它可以幫忙解析 `args` 的參數內容，用於簡化 CLI（Command-Line Interface）工具程式的設計。例如，我們想要讓 `s3upload` 可以支援這些指令參數：

* 執行程式的參數格式為 `s3upload [options] dir` 
* `s3upload -help` 或 `s3upload -h` 顯示使用說明
* `s3upload -bucket [BucketName]` 允許使用者自訂不同的 Bucket 名稱

使用 `CliBuilder` 可以輕鬆處理 `args` 參數的內容，我們只需要定義需要提供哪些參數的設定及說明，然後就可以放心交給 `CliBuilder` 幫忙完成解析參數的任務。

```
def cli = new CliBuilder(usage: 's3upload [options] dir')
cli.h args: 0, longOpt: 'help', 'print usage information'
cli.bucket args: 1, argName: 'bucketName', 'use given bucket name'

def options = cli.parse(args)
```

`options` 變數是解析後的參數物件，我們利用它來取得參數的內容，並且分別加以處理。

```
if (options.h) {
    cli.usage()
    System.exit(0)
}

if (options.bucket) {
    bucketName = options.bucket
}

if (options.arguments().size() == 0) {
    System.err.println 'No dir specified. Use -h to show usage messages.'
    System.exit(-1)
}

def targetDir = options.arguments().first()

println "Processing target dir: ${targetDir}"

if (!new File(targetDir).isDirectory()) {
    System.err.println "Target dir ${targetDir} is not valid."
    System.exit(-1)
}
```

## 完整程式實作範例 ##

將前面的程式碼片段整合，就能完成一個具有壓縮及上傳檔案到 S3 功能的小工具，例如執行「s3upload dir1」就可以將資料夾「dir1」壓縮後，上傳到 S3 儲存為「dir1.zip」檔案保存。

使用 Groovy 製作以 Java 函式庫為基礎發展的工具程式，就像撰寫 Shell Script 一樣容易，而且完成的程式也可以與其他 Shell 工具指令搭配使用。學會利用 Groovy Script 解決問題，能夠讓 Java 開發者如虎添翼。

程式碼範例：`s3upload`

```
#!/usr/bin/env groovy

@Grab('com.amazonaws:aws-java-sdk:1.8.2')
import com.amazonaws.auth.*
import com.amazonaws.services.s3.*

def configPath = "${System.properties.'user.home'}/.s3upload/config.groovy"

if (!new File(configPath).exists()) {
    System.err.println("Configuration file ${configPath} not exists.")
    System.exit(-1)
}

def config = new ConfigSlurper().parse(new File(configPath).toURL())

String accessKey = config.aws.credentials.accessKey
String secretKey = config.aws.credentials.secretKey
String bucketName = config.aws.s3.bucket

def cli = new CliBuilder(usage: 's3upload [options] dir')
cli.h args: 0, longOpt: 'help', 'print usage information'
cli.bucket args: 1, argName: 'bucketName', 'use given bucket name'

def options = cli.parse(args)

if (options.h) {
    cli.usage()
    System.exit(0)
}

if (options.bucket) {
    bucketName = options.bucket
}

if (options.arguments().size() == 0) {
    System.err.println 'No dir specified. Use -h to show usage messages.'
    System.exit(-1)
}

def targetDir = options.arguments().first()

println "Processing target dir: ${targetDir}"

if (!new File(targetDir).isDirectory()) {
    System.err.println "Target dir ${targetDir} is not valid."
    System.exit(-1)
}

def credentials = new BasicAWSCredentials(accessKey, secretKey)
def s3client = new AmazonS3Client(credentials)

def zipFile = File.createTempFile("temp", ".zip")
zipFile.delete()

new AntBuilder().zip(destFile: zipFile, basedir: targetDir)

s3client.listBuckets().each {
    println it.name
}

if (!s3client.doesBucketExist(bucketName)) {
    s3client.createBucket(bucketName)
}

s3client.putObject(bucketName, "${new File(targetDir).name}.zip", zipFile)
```

設定檔範例：`~/.s3upload/config.groovy`

```
aws {
    credentials {
        accessKey = "AWS_ACCESS_KEY_ID"
        secretKey = "AWS_SECRET_ACCESS_KEY"
    }
    s3 {
        bucket = 's3.groovy-tutorial.com'
    }
}
```