@GrabConfig(systemClassLoader = true)
@Grapes([
    @Grab('javax.mail:mail:1.4.7'),
    @Grab('javax.activation:activation'),
    @Grab('org.apache.ant:ant-javamail:1.9.2')
])

def ant = new AntBuilder()
new AntBuilder().zip(destFile: "tmp/apache2.zip") {
  fileset(dir: "/var/log/apache2") {
      include(name: "*.log")
    }
}

def opts = [
  mailhost: "smtp.gmail.com",
  mailport: 465,
  ssl: true,
  enableStartTLS: "true",
  user: "lyhcode",
  password: "u04cj/61234",
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

new AntBuilder().mail(opts) {
  from(name: "Administrator", address: "lyhcode@gmail.com")
  to(address: "lyhcode@gmail.com")
  message(content)
  attachments {
    fileset(dir: "tmp"){
      include(name: "*.zip")
    }
  }
}
