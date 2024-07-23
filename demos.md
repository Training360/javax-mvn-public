## Telepítendő szoftverek

* JDK 17
* Git
* IntelliJ IDEA Community

## Maven telepítése

* `JAVA_HOME` beállítása

```shell
java -version
```

https://maven.apache.org/download.cgi

* `M2_HOME` beállítása

```shell
mvn --version
```

## Proxy server használata

```shell
mvn help:help
```

* Central repository: https://repo.maven.apache.org/maven2

`~/.m2/settings.xml`

```shell
mkdir .m2
notepad .m2\settings.xml
```

```xml
<settings>
  .
  .
  <proxies>
   <proxy>
      <id>example-proxy</id>
      <active>true</active>
      <protocol>http</protocol>
      <host>proxy.example.com</host>
      <port>8080</port>
      <username>proxyuser</username>
      <password>somepassword</password>
      <nonProxyHosts>www.google.com|*.example.com</nonProxyHosts>
    </proxy>
  </proxies>
  .
  .
</settings>
```

```shell
mvn help:effective-settings
```

## Lokális repository, helyének beállítása

```shell
mvn help:help
```

`~/.m2/settings.xml`

```xml
<settings>
  <localRepository>${user.home}/.m2/repository</localRepository>
</settings>
```

```shell
mvn help:effective-settings
```

* Lokális repo törlése kézzel

## Egyszerű Maven projekt

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>training</groupId>
	<artifactId>helloworld</artifactId>
	<version>1.0.0</version>
</project>
```

`src/main/java/training/HelloWorld.java`

```java
package training;

public class HelloWorld {

  public static void main(String[] args) {
    System.out.println("Hello World!");
  }

}
```

```shell
mvn package
mvn clean
mvn clean package
jar -xvf helloworld
```

# Maven karakterkódolás, Java verzió

```
[WARNING] bootstrap class path not set in conjunction with -source 8
```

```xml
<properties>
		<maven.compiler.source>17</maven.compiler.source>
		<maven.compiler.target>17</maven.compiler.target>
</properties>
```

```
[WARNING] Using platform encoding (Cp1250 actually) to copy filtered resources, i.e. build is platform dependent! 
```

```xml
<properties>
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

## Egyszerű Maven projekt IDEA fejlesztőeszközből

* Name: `employees-simple`
* GroupId: `training`

```xml
<version>1.0.0</version>
```

`src/main/java/training/EmployeesMain.java`

```java
package training;

public class EmployeesMain {

    public static void main(String[] args) {
        System.out.println("Employees");
    }
}
```

## Feltöltés Git repository-ba

* Create repository
* `git init`
* `.gitignore`
* `git remote add origin`
* Push

```xml
<scm>
    <connection>scm:git:https://github.com/vicziani/employees-simple.git</connection>
    <developerConnection>scm:git:https://github.com/vicziani/employees-simple.git</developerConnection>
    <url>https://github.com/vicziani/employees-simple</url>
</scm>
```

## Életciklusok és fázisok

* https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html
* https://maven.apache.org/plugins/maven-compiler-plugin/index.html
* https://maven.apache.org/plugins/maven-compiler-plugin/plugin-info.html (goals)

```shell
mvn buildplan:list
```

## Információ a pluginokról

```shell
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-compiler-plugin
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-compiler-plugin -Ddetail
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-compiler-plugin -Ddetail -Dgoal=compile
mvn help:effective-pom
```

## IDEA Maven Tool Window 

* IDEA Maven Tool Window
* Felépítése
    * Lifecycle
    * Plugins
* Execute Maven Goal
* Maven Settings
    * Home path
    * User settings
    * Local repository

## Alkalmazás futtatása

```shell
mvn exec:java -Dexec.mainClass="employees.EmployeesMain"
```

## Memória és debug

```
set MAVEN_OPTS=-Xmx1024m -Xms512m
```

```
mvn -X package
mvn -X package > console.log
```

## Maven wrapper

```shell
mvn wrapper:wrapper
```

```
.mvn/
├─ wrapper/
│  ├─ maven-wrapper.jar
│  ├─ maven-wrapper.properties
│  ├─ MavenWrapperDownloader.java
mvnw
mvnw.cmd
```

## Környezet biztosítása, enforcer

* Maven Enforcer Plugin - biztosítani a megfelelő környezetet, pl. operációs rendszer, JDK verzió, Maven verzió
* Nagyon sok további beépített szabály:
    * Fájlok megléte
    * Környezeti változó megléte
    * Stb.
* Saját szabály is fejleszhető

Melyik fázishoz van kötve?

```shell
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-enforcer-plugin -Dgoal=enforce -Ddetail
```

```xml
<build>
    <plugins>
        <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-enforcer-plugin</artifactId>
        <version>3.4.0</version>
        <executions>
            <execution>
            <id>enforce-versions</id>
            <goals>
                <goal>enforce</goal>
            </goals>
            <configuration>
                <rules>
                <requireMavenVersion>
                    <version>3.8.0</version>
                </requireMavenVersion>
                <requireJavaVersion>
                    <version>17</version>
                </requireJavaVersion>
                </rules>
            </configuration>
            </execution>
        </executions>
        </plugin>
    </plugins>
    </build>
```

* `requireJavaVersion` megemelése 18-asra, majd `mvn package`

## Fordító paraméterek

```java
new ArrayList().add(10);
```

```plain
[INFO] /C:/training/maven-training/employees-simple/src/main/java/training/EmployeesMain.java: C:\training\maven-training\employees-simple\src\main\java\training\EmployeesMain.java uses unchecked or unsafe operations.
[INFO] /C:/training/maven-training/employees-simple/src/main/java/training/EmployeesMain.java: Recompile with -Xlint:unchecked for details.
```

```xml
<compilerArgument>-Xlint:unchecked</compilerArgument>
```

```plain
[WARNING] /C:/training/maven-training/employees-simple/src/main/java/training/EmployeesMain.java:[23,28] unchecked call to add(E) as a member of the raw type java.util.ArrayList
```

## Projekt létrehozása archetype segítségével parancssorból

* Saját is létrehozható

```shell
mvn archetype:generate -DgroupId=training -DartifactId=hello-archetype -DarchetypeArtifactId=maven-archetype-quickstart -Dpackage=hello -DinteractiveMode=false
```

## Projekt létrehozása archetype segítségével IDEA-ban

* `hello-archetype-ide`

## Resource-ok használata

```java
var properties = new Properties();
try (var reader = new InputStreamReader(EmployeesMain.class.getResourceAsStream("/application.properties"), StandardCharsets.UTF_8)) {
    properties.load(reader);
} catch (IOException ioe) {
    throw new IllegalStateException("Can not load properties file from classpath: application.properties", ioe);
}
System.out.println(properties.get("welcome-message"));
```

`application.properties`

```properties
welcome-message = Hello!
```

## Resource filtering

```xml
<properties>
    <info>Sample employees project</info>
</properties>

<build>
	<resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>
        </resource>
    </resources>

    <plugins>
        <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>3.3.1</version>
                <configuration>
                    <propertiesEncoding>UTF-8</propertiesEncoding>
                </configuration>
            </plugin>
    </plugins>
</build>
```

```properties
project-version = ${project.version}
project-info = ${info}
```

`target/classes/application.properties`

## Property-k

Implicit property-k:

* `project.*`
* `settings.*`
* `env.*`
* System Properties

User-defined property-k

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-antrun-plugin</artifactId>
    <version>3.1.0</version>
    <executions>
        <execution>
            <phase>validate</phase>
            <goals>
                <goal>run</goal>
            </goals>
            <configuration>
                <target>
                    <echoproperties />
                </target>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## Függőségek kezelése

* Local repository
* Central repo: https://repo1.maven.org/maven2
    * https://central.sonatype.com/
    * https://mvnrepository.com/

Keresés a `slf4j-simple`-re

```xml
<dependencies>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>2.0.7</version>
    </dependency>
</dependencies>
```

```java
private static final Logger log = LoggerFactory.getLogger(EmployeesMain.class);

log.info("Hello SLF4J");
```

## Függőség intervallumok

* https://maven.apache.org/pom.html#dependency-version-requirement-specification
* https://maven.apache.org/pom.html#version-order-specification
* https://www.mojohaus.org/versions/versions-maven-plugin/version-rules.html
* https://cwiki.apache.org/confluence/display/MAVENOLD/Versioning

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>[2.0.0,)</version>
</dependency>
```

`mvn dependency:list`

`mvn versions:resolve-ranges`

## Tranzitív függőségek, függőségi fa

```shell
mvn dependency:tree
```

## Függőségek elemzése, scope-ok

```shell
mvn dependency:analyze
```

```plain
[WARNING] Used undeclared dependencies found:
[WARNING]    org.slf4j:slf4j-api:jar:2.0.7:compile
[WARNING] Unused declared dependencies found:
[WARNING]    org.slf4j:slf4j-simple:jar:2.0.7:compile
```

* Csak az `slf4j-api` függőséget benn hagyva:

```plain
SLF4J: No SLF4J providers were found.
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See https://www.slf4j.org/codes.html#noProviders for further details.
```

https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#dependency-scope

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>2.0.7</version>
    <scope>runtime</scope>
</dependency>
```

Még mindig warning.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>3.6.0</version>
    <configuration>
        <usedDependencies>
            <dependency>org.slf4j:slf4j-simple</dependency>
        </usedDependencies>
    </configuration>
</plugin>
```

## Property-k használata ismétlődés megszüntetésére

```xml
<properties>
    <slf4j.version>2.0.7</slf4j.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf4j.version}</version>
    </dependency>

    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>${slf4j.version}</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

## Függőségek verziójának felülírása

```xml
<properties>
    <zxing.version>3.3.0</zxing.version>
</properties>

<dependencies>
    <dependency>
        <groupId>com.google.zxing</groupId>
        <artifactId>core</artifactId>
        <version>3.3.0</version>
    </dependency>
    <dependency>
        <groupId>com.google.zxing</groupId>
        <artifactId>javase</artifactId>
        <version>3.3.0</version>
    </dependency>
</dependencies>
```

```java
try {
    var barcodeWriter = new QRCodeWriter();
    var bitMatrix =
            barcodeWriter.encode("Hello World!", BarcodeFormat.QR_CODE, 200, 200);
    
    MatrixToImageWriter.writeToPath(bitMatrix, "png", Path.of("./hello.png"));
} catch (WriterException | IOException we) {
    throw new IllegalStateException("Can not write barcode", we);
}
```

```plain
[INFO] +- com.google.zxing:core:jar:3.3.0:compile
[INFO] \- com.google.zxing:javase:jar:3.3.0:compile
[INFO]    +- com.beust:jcommander:jar:1.48:compile
[INFO]    \- com.github.jai-imageio:jai-imageio-core:jar:1.3.1:compile

```

```xml
<dependency>
    <groupId>com.github.jai-imageio</groupId>
    <artifactId>jai-imageio-core</artifactId>
    <version>1.4.0</version>
</dependency>
```

## Függőségek kizárása

```xml
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>javase</artifactId>
    <version>3.3.0</version>
    <exclusions>
        <exclusion>
            <groupId>com.beust</groupId>
            <artifactId>jcommander</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

## Függőségek frissítése

```xml
<slf4j.version>2.0.6</slf4j.version>
```

```shell
mvn versions:display-dependency-updates
```

## Lombok

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.28</version>
    <scope>provided</scope>
</dependency>
```

```java
@Data
@AllArgsConstructor
public class Employee {

    private String name;

    private int salary;
}
```

```java
var employee = new Employee("John Doe", 100);
log.info(employee.getName());
```

* Mivel a classpath-on van, a javac észreveszi

## Lombok és MapStruct

* Refactor / Property

```xml
<dependencies>
    <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${mapstruct.version}</version>
        </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                        <version>${lombok.version}</version>
                    </path>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>${mapstruct.version}</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

```java
@Data
public class EmployeePresentationModel {

    private String name;

    private int salary;
}
```

```java
@Mapper
public interface EmployeeMapper {

    EmployeePresentationModel toPresentationModel(Employee employee);
}
```

`EmployeeMapperImpl`

```java
var mapper = new EmployeeMapperImpl();
var presentationModel = mapper.toPresentationModel(employee);
log.info(presentationModel.getName());
```

```shell
mvn package
```

## Unit tesztek

```java
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

```java
class EmployeeTest {

    @Test
    void create() {
        var employee = new Employee("John Doe", 100);
        assertEquals("John Doe", employee.getName());
    }

    @Test
    void increaseSalary() {
        var employee = new Employee("John Doe", 100);
        employee.increaseSalary(10);
        assertEquals(110, employee.getSalary());
    }

}
```

```shell
mvn package
```

* Legalább 2.22.2 Surefire plugin

```shell
mvn help:effective-pom > q
```

* `target/surefire-reports`

## Unit tesztek futtatásának paraméterezése

Egy teszteset futtatása:

```shell
mvn package -Dtest="EmployeeTest#increaseSalary"
```

Tesztek átugrása

```shell
mvn package -DskipTests
```

Tesztek futtatása tag alapján

* Tag expression adható meg
    * vagy (`|`)
    * és (`&`)
    * nem (`!`) operátorok
    * zárójelek használhatóak <br /> (pl. `unit & feature-329 & !long-running`)


```java
@Test
@Tag("getter")
void create() {
    // ...
}

@Test
@Tag("method")
void increaseSalary() {
    // ...
}
```

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.1.2</version>
    <configuration>
        <groups>method</groups>
    </configuration>
</plugin>
```

## Unit tesztek részletesebb riport

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.1.2</version>
    <dependencies>
        <dependency>
            <groupId>me.fabriciorby</groupId>
            <artifactId>maven-surefire-junit5-tree-reporter</artifactId>
            <version>1.2.1</version>
        </dependency>
    </dependencies>
    <configuration>
        <reportFormat>plain</reportFormat>
        <consoleOutputReporter>
            <disable>true</disable>
        </consoleOutputReporter>
        <statelessTestsetInfoReporter implementation="org.apache.maven.plugin.surefire.extensions.junit5.JUnit5StatelessTestsetInfoTreeReporter"/>
    </configuration>
</plugin>
```

## Unit tesztek párhuzamos futtatása

* From JUnit Platform does not support running tests in parallel.

```java
@BeforeEach
void init() {
    System.out.println(Thread.currentThread().getName());
}
```

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.1.2</version>
    <configuration>
        <properties>
            <configurationParameters>
                junit.jupiter.execution.parallel.enabled=true
                junit.jupiter.execution.parallel.mode.default = concurrent
            </configurationParameters>
        </properties>
    </configuration>
</plugin>
```

## Tesztlefedettség

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <executions>
        <execution>
            <id>jacoco-initialize</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>jacoco-site</id>
            <phase>package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

* `target/jacoco.exec`
* `target/site/jacoco/index.html`

## Adatbáziskezelés

```shell
docker run -d -e POSTGRES_DB=employees -e POSTGRES_USER=employees -e POSTGRES_PASSWORD=employees -p 5432:5432  --name employees-postgres postgres
```

```shell
docker exec -it employees-postgres psql -U employees
```

```sql
create table employees (id int8 generated by default as identity, emp_name varchar(255), salary int4, primary key (id));
```

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>6.0.11</version>
</dependency>

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.5.1</version>
</dependency>
```

* Nem jó `runtime` scope-pal, mert a DataSource-ot én példányosítom

```java
public class EmployeesRepository {

    private JdbcTemplate jdbcTemplate;

    public EmployeesRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createEmployee(Employee employee) {
        jdbcTemplate.update("insert into employees(emp_name, salary) values (?, ?)", employee.getName(), employee.getSalary());
    }

    public List<String> listEmployeeNames() {
        return jdbcTemplate.query("select emp_name from employees order by emp_name",
                (rs, rowNum) -> rs.getString("emp_name"));
    }

}
```

```java
public class EmployeesApplication {

    public static void main(String[] args) {
        var properties = new Properties();
        try (var reader = new InputStreamReader(EmployeesMain.class.getResourceAsStream("/application.properties"), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not load properties file from classpath: application.properties", ioe);
        }

        var dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("jdbc.url"));
        dataSource.setUser(properties.getProperty("jdbc.username"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));

        var employeesRepository = new EmployeesRepository(dataSource);

        employeesRepository.createEmployee(new Employee("John Doe", 100));
        log.info("Employees: {}", employeesRepository.listEmployeeNames());
    }

}
```

```properties
jdbc.url = jdbc:postgresql:employees
jdbc.username = employees
jdbc.password = employees
```

```sql
select * from employees;
exit
```

## Integrációs tesztek

```shell
docker run -d -e POSTGRES_DB=employees -e POSTGRES_USER=employees -e POSTGRES_PASSWORD=employees -p 5433:5432  --name employees-it-postgres postgres
```

```shell
docker exec -it employees-it-postgres psql -U employees
```

```sql
create table employees (id int8 generated by default as identity, emp_name varchar(255), salary int4, primary key (id));
```


```java
class EmployeesRepositoryIT {

    EmployeesRepository employeesRepository;

    @BeforeEach
    void init() {
        Properties properties = new Properties();
        try (var reader = new InputStreamReader(EmployeesMain.class.getResourceAsStream("/application-test.properties"), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not load properties file from classpath: application-test.properties", ioe);
        }

        var dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("jdbc.url"));
        dataSource.setUser(properties.getProperty("jdbc.username"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));

        employeesRepository = new EmployeesRepository(dataSource);
        var jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("delete from employees");
    }

    @Test
    void create_then_list()  {
        employeesRepository.createEmployee(new Employee("John Doe", 100));
        var names = employeesRepository.listEmployeeNames();
        assertEquals(List.of("John Doe"), names);
    }
}
```

```properties
jdbc.url = jdbc:postgresql://localhost:5433/employees
jdbc.username = employees
jdbc.password = employees
```

```sql
select * from employees;
```

Kilépés: `exit`

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>3.1.2</version>
    <executions>
        <execution>
            <goals>
                <goal>integration-test</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

```shell
mvn clean verify
```

## Integrációs tesztek adatbázis kapcsolat paraméterek

```java
var url = Optional.ofNullable(System.getProperty("jdbc.url"))
        .orElse(properties.getProperty("jdbc.url"));
var user = Optional.ofNullable(System.getProperty("jdbc.user"))
        .orElse(properties.getProperty("jdbc.user"));
var password = Optional.ofNullable(System.getProperty("jdbc.password"))
        .orElse(properties.getProperty("jdbc.password"));

var dataSource = new PGSimpleDataSource();
dataSource.setUrl(url);
dataSource.setUser(user);
dataSource.setPassword(password);
```

```xml
<properties>
    <jdbc.url>jdbc:postgresql://localhost:5433/employees</jdbc.url>
    <jdbc.user>employees</jdbc.user>
    <jdbc.password>employees2</jdbc.password>
</properties>
```

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>3.1.2</version>
    <executions>
        <execution>
            <goals>
                <goal>integration-test</goal>
            </goals>
            <configuration>
                <systemPropertyVariables>
                    <jdbc.url>${jdbc.url}</jdbc.url>
                    <jdbc.user>${jdbc.user}</jdbc.user>
                    <jdbc.password>${jdbc.password}</jdbc.password>
                </systemPropertyVariables>
            </configuration>
        </execution>
    </executions>
</plugin>
```

```shell
mvn verify -Djdbc.password=employees
```

## Integrációs tesztek részletesebb riport

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>3.1.2</version>
    <executions>
        <execution>
            <goals>
                <goal>integration-test</goal>
            </goals>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>me.fabriciorby</groupId>
            <artifactId>maven-surefire-junit5-tree-reporter</artifactId>
            <version>1.2.1</version>
        </dependency>
    </dependencies>
    <configuration>
        <reportFormat>plain</reportFormat>
        <consoleOutputReporter>
            <disable>true</disable>
        </consoleOutputReporter>
        <statelessTestsetInfoReporter implementation="org.apache.maven.plugin.surefire.extensions.junit5.JUnit5StatelessTestsetInfoTreeReporter"/>
    </configuration>
</plugin>
```

```xml
<statelessTestsetInfoReporter implementation="org.apache.maven.plugin.surefire.extensions.junit5.JUnit5StatelessTestsetInfoTreeReporter">
    <printStacktraceOnError>true</printStacktraceOnError>
</statelessTestsetInfoReporter>
```

## Liquibase adatbázis migráció futtatása Maven pluginnal

```shell
docker exec -it employees-postgres psql -U employees
```

```sql
drop table employees;
```

Kilépés: `exit`

`src/test/resources/db.changelog-master.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
        xmlns:pro="http://www.liquibase.org/xml/ns/pro"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
        http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
        http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd
        http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd">
    <changeSet id="1" author="trainer">
        <sql>
            create table employees (id int8 generated by default as identity, emp_name varchar(255), salary int4, primary key (id));
        </sql>
    </changeSet>
</databaseChangeLog>
```

```xml
<pluginManagement>
    <plugins>
        <plugin>
            <groupId>org.liquibase</groupId>
            <artifactId>liquibase-maven-plugin</artifactId>
            <version>4.23.1</version>
            <configuration>
                <changeLogFile>src/test/resources/db.changelog-master.xml</changeLogFile>
                <url>jdbc:postgresql:employees</url>
                <username>employees</username>
                <password>employees</password>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>org.postgresql</groupId>
                    <artifactId>postgresql</artifactId>
                    <version>${postgresql.version}</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</pluginManagement>
```

```shell
mvn liquibase:update
```

## Docker konténer indítása integrációs tesztek előtt, leállítása utána

```xml
<plugin>
    <groupId>io.fabric8</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>0.43.4</version>
    <executions>
        <execution>
            <id>prepare-it-database</id>
            <goals>
                <goal>start</goal>
            </goals>
            <configuration>
                <images>
                    <image>
                        <name>postgres</name>
                        <alias>it-database</alias>
                        <run>
                            <ports>
                                <port>5432:5432</port>
                            </ports>
                            <env>
                                <POSTGRES_DB>employees</POSTGRES_DB>
                                <POSTGRES_USER>employees</POSTGRES_USER>
                                <POSTGRES_PASSWORD>employees</POSTGRES_PASSWORD>
                            </env>
                            <wait>
                                <log>(?s)database system is ready to accept connections.*database system is ready to accept connections</log>
                                <time>30000</time>
                            </wait>
                        </run>
                    </image>
                </images>
            </configuration>
        </execution>
        <execution>
            <id>remove-it-database</id>
            <goals>
                <goal>stop</goal>
            </goals>
        </execution>
    </executions>
</plugin>

<plugin>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-maven-plugin</artifactId>
    <version>4.23.1</version>
    <configuration>
        <changeLogFile>src/test/resources/db.changelog-master.xml</changeLogFile>
        <url>jdbc:postgresql:employees</url>
        <username>employees</username>
        <password>employees</password>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>${postgresql.version}</version>
        </dependency>
    </dependencies>
    <executions>
    <execution>
        <id>update-it-database</id>
        <phase>pre-integration-test</phase>
        <goals>
            <goal>update</goal>
        </goals>
    </execution>
    </executions>
</plugin>
```

```shell
mvn verify
```

## JDepend

* TC: Total classes
* CC: Concrete classes
* AC: Abstract classes (interfészek is)
* Ca: Afferent Couplings - bejövő függőségek
* Ce: Efferent Couplings - kimenő függőségek
* A: Abstractness (0 - 1): TC / AC (0: konkrét, 1: absztrakt csomag)
* I: Instability (0 - 1): Ce / (Ce + Ca) (0: stabil, 1: instabil), instabil, ha a kimenő függőségek vannak túlsúlyban
* D: Distance (0 - 1): ideális esetben a csomagok vagy absztraktak és stabilak (A=1, I=0), vagy konkrétak és instabilak (A=0, I=1). Azaz a kettő összege 1. Ez a szám mutatja az ettől való távolságot. Minél kisebb, annál jobb.
    * absztraktak és stabilak (A=1, I=0) - frameworkök, bejövő függőségekkel, legyen absztrakt, hogy lehessen az implementációt módosítani    
    * konkrétak és instabilak (A=0, I=1) - legfelső réteg, gyakran változhat, ezért ne tegyünk rá függőségeket
* V: Volatility (0-1): ez nem számolt, hanem beállítható. Alapesetben az értéke 1, és konfigurációban lehet 0-ra állítani. Ezzel jelezzük, hogy 
az adott csomagot nem akarjuk változtatni (Pl. ha a `java.lang` benne van az elemzésben, akkor arra érdemes beállítani.)

```xml
<reporting>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>jdepend-maven-plugin</artifactId>
            <version>2.0</version>
        </plugin>
    </plugins>
</reporting>
```

```shell
mvn site
```

`target/site/jdepend-report.html`

# Projekt elemzése SonarScanner Maven pluginnal

```shell
docker run --name sonarqube -d -p 9000:9000 sonarqube:lts
```

* `admin`/`admin` -> `admin` / `admin12AA`
* My Account / Security / Generate Tokens

```shell
mvn sonar:sonar -Dsonar.login=token
```

* Elemzés eredménye
* Tesztlefedettség eredménye

## Integrációs tesztek SonarScanner Maven pluginnal

```xml
<sonar.junit.reportPaths>
  ${project.basedir}/target/surefire-reports/,${project.basedir}/target/failsafe-reports/
</sonar.junit.reportPaths>
```

## SonarQube Quality Gate

Bevárás:

```shell
mvnw sonar:sonar -Dsonar.login=token -Dsonar.qualitygate.wait=true
```

## OWASP dependency check

```xml
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <version>8.2.1</version>
    <executions>
        <execution>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

```shell
mvn verify
```

Lásd `target/dependency-check-report.html`

## Csomagolás JAR állományba

```xml
<packaging>jar</packaging>
```

```xml
<finalName>employees-simple</finalName>
```

```shell
java -classpath target\employees-simple.jar training.EmployeesApplication
```

```plain
Exception in thread "main" java.lang.NoClassDefFoundError: org/slf4j/LoggerFactory
```

```shell
mvn exec:java -Dexec.mainClass=employees.EmployeesMain
```

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <mainClass>training.EmployeesApplication</mainClass>
    </configuration>
</plugin>
```

```shell
mvn exec:java
```

## Egyszerű JAR futtatása

```shell
mvn dependency:copy-dependencies -DoutputDirectory=target/libs
```

```shell
cd target
java -classpath target\employees-simple.jar;target\libs\slf4j-api-2.0.9.jar;target\libs\slf4j-simple-2.0.9.jar;target\libs\core-3.3.0.jar;target\libs\javase-3.3.0.jar;target\libs\jai-imageio-core-1.4.0.jar employees.EmployeesMain  
```

## Futtatható JAR állomány készítése

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>3.3.0</version>
    <configuration>
        <archive>
            <manifest>
                <mainClass>employees.EmployeesMain</mainClass>
                <addClasspath>true</addClasspath>
                <classpathPrefix>libs</classpathPrefix>
            </manifest>
        </archive>
    </configuration>
</plugin>
```

```shell
java -jar employees-simple.jar
```

## Uber JAR Assembly pluginnal

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>single</goal>
            </goals>
            <configuration>
                <archive>
                    <manifest>
                        <mainClass>
                            employees.EmployeesMain
                        </mainClass>
                    </manifest>
                </archive>
                <descriptorRefs>
                    <descriptorRef>jar-with-dependencies</descriptorRef>
                </descriptorRefs>
            </configuration>
        </execution>
    </executions>
</plugin>
```

* Ún. secondary artifact, `jar-with-dependencies` classifier

```shell
mvn package
java -jar target\employees-simple-jar-with-dependencies.jar
```

## Uber JAR Shade pluginnal

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.4</version>
    <executions>
        <execution>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <shadedArtifactAttached>true</shadedArtifactAttached>
                <transformers>
                    <transformer implementation=
                                            "org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>employees.EmployeesMain</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```

```shell
mvn package
java -jar target\employees-simple-1.0-SNAPSHOT-shaded.jar
```

## Source jar előállítása

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-source-plugin</artifactId>
    <version>3.3.0</version>
    <executions>
        <execution>
            <id>attach-sources</id>
            <phase>package</phase>
            <goals>
                <goal>jar-no-fork</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

* Ún. secondary artifact, `sources` classifier

```shell
mvn package
```

## JavaDoc jar előállítása

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>3.5.0</version>
    <executions>
        <execution>
            <id>attach-javadocs</id>
            <goals>
                <goal>jar</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

* Ún. secondary artifact, `javadoc` classifier

```shell
mvn package
```

`target/apidocs/index.html`

## JavaDoc JAR előállítása Lombokkal és MapStructtal

```xml
<plugin>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok-maven-plugin</artifactId>
    <version>1.18.20.0</version>
    <configuration>
        <sourceDirectory>${project.basedir}/src/main/java</sourceDirectory>
        <outputDirectory>${delombok.output}</outputDirectory>
        <addOutputDirectory>false</addOutputDirectory>
    </configuration>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>delombok</goal>
            </goals>
        </execution>
    </executions>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>3.5.0</version>
    <executions>
        <execution>
            <id>attach-javadocs</id>
            <phase>package</phase>
            <goals>
                <goal>jar</goal>
            </goals>
            <configuration>
                <sourcepath>
                    ${project.build.directory}/generated-sources/delombok:${project.build.directory}/generated-sources/annotations
                </sourcepath>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## Secondary artifactok előállítása profile-lal

```xml
<profiles>
        <profile>
            <id>release</id>
            <build>
                <plugins>
    <!-- 
        maven-assembly-plugin
        maven-shade-plugin
        maven-source-plugin
        lombok-maven-plugin
        maven-javadoc-plugin
    -->
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>
```

```shell
mvn package
mvn package -Prelease
```

## Install local repository-ba

```shell
mvn install
```

* Secondary artifactokat is

## Fájl install local repository-ba

```shell
mkdir hello-no-maven
cd hello-no-maven
notepad HelloWorld.java 
```

```java
public class HelloWorld {

  public static void main(String[] args) {
    System.out.println("Hello World!");
  }

}
```

```shell
javac HelloWorld.java 
jar -cf hello-no-maven.jar *.class
mvn install:install-file -Dfile=hello-no-maven.jar -DgroupId=training -DartifactId=hello-no-maven	-Dversion=1.0.0	-Dpackaging=jar -DgeneratePom=true
```

## Installed pom.xml, Flatten Maven Plugin

* Feltölti az eredeti pom.xml fájlt is
* Flatten Maven Plugin
    * Fejlesztéshez, buildhez szükséges részeket eltávolítja
    * Csak azok maradnak meg, melyek a felhasználásához kellenek
    * Property-ket feloldja

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>flatten-maven-plugin</artifactId>
    <version>1.2.5</version>
    <configuration>
    </configuration>
    <executions>
        <execution>
            <id>flatten</id>
            <phase>process-resources</phase>
            <goals>
                <goal>flatten</goal>
            </goals>
        </execution>
        <execution>
            <id>flatten.clean</id>
            <phase>clean</phase>
            <goals>
                <goal>clean</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```    

## Nexus repo manager

```shell
docker run --name nexus -d -p 8081:8081 sonatype/nexus3
```

```shell
docker exec -it nexus cat /nexus-data/admin.password
```

## Nexus repo manager Maven proxyként

`~/.m2/settings.xml` fájlban

```xml
<settings>
    <mirrors>
        <mirror>
            <id>nexus</id>
            <mirrorOf>*</mirrorOf>
            <url>http://localhost:8081/repository/maven-public/</url>
        </mirror>
    </mirrors>
</settings>
```


## Deploy Nexus repoba

```xml
<distributionManagement>
    <repository>
        <id>nexus-releases</id>
        <url>http://localhost:8081/repository/maven-releases/</url>
    </repository>
</distributionManagement>
```

C:\Users\iviczian\.m2

`~/.m2/settings.xml` fájlban a `settings` tagen belül

```xml
<servers>
   <server>
      <id>nexus-releases</id>
      <username>admin</username>
      <password>admin</password>
   </server>
</servers>
```

```shell
mvn deploy
```

## Artifactory repo manager

> Változás a videóhoz képest!

```shell
docker run --name artifactory -d -p 8091:8081 -p 8092:8082 releases-docker.jfrog.io/jfrog/artifactory-oss:7.59.9
```

http://localhost:8092/

`admin/password` -> `admin/admin11AA`

* User management/Settings/Allow Anonymous Access

## Artifactory repo manager proxyként

`~/.m2/settings.xml` fájlban

```xml
<settings>
    <mirrors>
        <mirror>
            <id>artifactory</id>
            <mirrorOf>*</mirrorOf>
            <url>http://localhost:8092/artifactory/libs-release</url>
        </mirror>
    </mirrors>
</settings>
```

## Deploy Artifactory repoba

```xml
<distributionManagement>
    <repository>
        <id>artifactory-releases</id>
        <url>http://localhost:8092/artifactory/libs-release-local/</url>
    </repository>
</distributionManagement>
```

C:\Users\iviczian\.m2

`~/.m2/settings.xml` fájlban a `settings` tagen belül

```xml
<servers>
    <server>
        <id>artifactory-releases</id>
        <username>admin</username>
        <password>admin12AA</password>
    </server>
</servers>
```

```shell
mvn deploy
```

## Site elkészítése

```shell
mvn site
```

`target/site/index.html`

https://maven.apache.org/pom.html

```xml
<description>Employees application - sample Maven project</description>
<developers>
    <developer>
        <id>trainer</id>
        <name>Trainer</name>
        <roles>
            <role>developer</role>
        </roles>
    </developer>
</developers>
```

* About
* Dependencies
* Dependency Information
* Plugin Management
* Plugins
* Summary
* Team

Reports:

* JDepend

## Jacoco report

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <reportSets>
    <reportSet>
        <id>report</id>
        <reports>
            <report>report</report>
        </reports>
    </reportSet>
    </reportSets>
</plugin>
```

## JavaDoc report

```xml
<reporting>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-javadoc-plugin</artifactId>
            <version>3.5.0</version>
            <reportSets>
                <reportSet>
                    <id>html</id>
                    <configuration>
                        <doctitle>My API for ${project.name} ${project.version}</doctitle>
                        <windowtitle>My API for ${project.name} ${project.version}</windowtitle>
                        <sourcepath>
                            ${project.build.directory}/generated-sources/delombok:${project.build.directory}/generated-sources/annotations
                        </sourcepath>
                    </configuration>
                    <reports>
                        <report>javadoc</report>
                    </reports>
                </reportSet>
                <reportSet>
                    <id>test-html</id>
                    <configuration>
                        <testDoctitle>My Test API for ${project.name} ${project.version}</testDoctitle>
                        <testWindowtitle>My Test API for ${project.name} ${project.version}</testWindowtitle>
                        <show>package</show>
                    </configuration>
                    <reports>
                        <report>test-javadoc</report>
                    </reports>
                </reportSet>
            </reportSets>
        </plugin>
    </plugins>
</reporting>
```

## JXR plugin

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jxr-plugin</artifactId>
    <version>3.3.0</version>
</plugin>
```

## Changelog report

https://maven.apache.org/plugins/maven-changelog-plugin/usage.html

* Utolsó egy hónap, de változtatható

```xml
<reporting>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-changelog-plugin</artifactId>
            <version>2.3</version>
        </plugin>
    </plugins>
</reporting>
```

## Saját oldalak létrehozása a site-on

Támogatott formátumok: `apt`, `markdown`, `fml`, `xdoc`

`src/site/markdown/index.md`

További állományok: `src/site/resources` könyvtárba

```markdown
# Employees application

## Intro

This is a sample application.

![Maven logo](images/logo.png)
```

* Site descriptor: https://maven.apache.org/guides/mini/guide-site.html#creating-a-site-descriptor

## Site publikálás

* New branch: `gh-pages`


```xml
<distributionManagement>
        <site>
            <id>web</id>
            <url>scm:git:https://github.com/Training360/maven-training</url>
        </site>
</distributionManagement>
```

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-scm-publish-plugin</artifactId>
    <version>3.2.1</version>
    <configuration>
        <scmBranch>gh-pages</scmBranch>
    </configuration>
</plugin>
```

* `mvn site:stage scm-publish:publish-scm`
* Weben
* Settings / Pages

## CI/CD támogatás, paraméterezhető verziószám

Ha változót használunk a verziószámban, warning
Ha neve pl. `revision`, akkor nincs warning

```xml
<version>${revision}</version>

<properties>
    <build.number>unknown</build.number>
    <revision>1.0.0-${build.number}</revision>
</properties>
```

```shell
mvn clean package -Dbuild.number=12
```

## Docker build futtatása

```shell
mvn dependency:copy-dependencies -DoutputDirectory=target/libs
```

```dockerfile
FROM eclipse-temurin:17
WORKDIR /app
COPY target/libs ./libs
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```xml
<plugin>
    <groupId>io.fabric8</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>0.43.4</version>
</plugin>
```

```dockerfile
COPY target/${project.build.finalName}.jar app.jar
```

## Maven futtatása Docker konténerben

`Dockerfile.build`

```dockerfile
FROM eclipse-temurin:17 as builder
WORKDIR /app
COPY . .
RUN ./mvnw package
RUN ./mvnw dependency:copy-dependencies -DoutputDirectory=target/libs

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
COPY --from=builder /app/target/libs ./libs
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```shell
docker build -f Dockerfile.build -t employees-simple .
```

## Repository cache

```dockerfile
FROM eclipse-temurin:17 as builder
WORKDIR /app
COPY pom.xml mvnw .
COPY .mvn .mvn
RUN ./mvnw  package

COPY . .
RUN ./mvnw package
RUN ./mvnw dependency:copy-dependencies -DoutputDirectory=target/libs

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=builder /app/target/*.jar .
COPY --from=builder /app/target/libs ./libs
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## Opentelemetry

> Változott a videóhoz képest!

`otel/docker-compose.yaml`

```yaml
services:
  zipkin:
    image: openzipkin/zipkin:3.4.0
    ports:
      - "9411:9411"
  collector:
    image: otel/opentelemetry-collector-contrib:0.105.0
    volumes:
      - ./collector-config.yaml:/etc/otelcol-contrib/config.yaml
    ports:
      - "4317:4317"
      - "4318:4318"
    depends_on:
      - zipkin
```

`otel/collector-config.yaml`

```yaml
receivers:
  otlp:
    protocols:
      grpc:
        endpoint: 0.0.0.0:4317
      http:
        endpoint: 0.0.0.0:4318

exporters:
  zipkin:
    endpoint: "http://zipkin:9411/api/v2/spans"

service:
  pipelines:
    traces:
      receivers: [otlp]
      exporters: [zipkin]
```

```shell
docker compose up -d
```

```xml
<extensions>
  <extension>
    <groupId>io.opentelemetry.contrib</groupId>
    <artifactId>opentelemetry-maven-extension</artifactId>
    <version>1.35.0-alpha</version>
  </extension>
</extensions>
```

```shell
set OTEL_TRACES_EXPORTER=otlp
set OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4317

mvn package
```

## Library készítése és felhasználása

*  `hello-lib`

`hello.Hello`

```java
package hello;

public class Hello {
    
    public String sayHello(String name) {
        return "Welcome %s!".formatted(name);
    }
}
```

```shell
mvn install
```

`hello-client`

```xml
<dependencies>
    <dependency>
        <groupId>training</groupId>
        <artifactId>hello-lib</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

```java
package hello;

public class HelloMain {

    public static void main(String[] args) {
        System.out.println(new Hello().sayHello("Trainer"));
    }
}
```

## Opcionális függőségek

```xml
<zxing.version>3.3.0</zxing.version>

<dependencies>
    <dependency>
        <groupId>com.google.zxing</groupId>
        <artifactId>core</artifactId>
        <version>${zxing.version}</version>
    </dependency>
    <dependency>
        <groupId>com.google.zxing</groupId>
        <artifactId>javase</artifactId>
        <version>${zxing.version}</version>
    </dependency>
</dependencies>
```

```java
package hello;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.Path;

public class QrCodeHello {

    public void writeHelloImage(String name) {
        var message = "Welcome %s!".formatted(name);

        try {
            var barcodeWriter = new QRCodeWriter();
            var bitMatrix =
                    barcodeWriter.encode("Hello World!", BarcodeFormat.QR_CODE, 200, 200);

            MatrixToImageWriter.writeToPath(bitMatrix, "png", Path.of("./hello.png"));
        } catch (WriterException | IOException we) {
            throw new IllegalStateException("Can not write barcode", we);
        }
    }
}
```

```xml
<optional>true</optional>
```

## SNAPSHOT verziók

```xml
<distributionManagement>
    <repository>
        <id>nexus-releases</id>
        <url>http://localhost:8081/repository/maven-releases/</url>
    </repository>
</distributionManagement>
```

```shell
mvn deploy
```

```shell
mvn deploy

status code: 400, reason phrase: Repository does not allow updating assets: maven-releases (400)
```

```xml
<snapshotRepository>
    <id>nexus-snapshots</id>
    <url>http://localhost:8081/repository/maven-snapshots/</url>
</snapshotRepository>

<version>1.0.1-SNAPSHOT</version>
```

`settings.xml`

```xml
<server>
    <id>nexus-snapshots</id>
    <username>admin</username>
    <password>admin</password>
</server>
```

```shell
mvn deploy
mvn deploy
```

## SNAPSHOT verziók használata

* Törlés a lokális repo-ból

```xml
<version>1.0.1-SNAPSHOT</version>
```

```shell
notepad %USERPROFILE%\.m2\settings.xml
```

```xml
<mirrors>
    <mirror>
        <id>nexus</id>
        <mirrorOf>*</mirrorOf>
        <url>http://localhost:8081/repository/maven-public/</url>
    </mirror>
</mirrors>
```

```shell
mvn package
```

```
[ERROR] Failed to execute goal on project hello-client: Could not resolve dependencies for project training:hello-client:jar:1.0-SNAPSHOT: The following artifacts could not be resolved: training:hello-lib:j
ar:1.0.1-SNAPSHOT (absent): Could not find artifact training:hello-lib:jar:1.0.1-SNAPSHOT -> [Help 1]
```

```shell
notepad %USERPROFILE%\.m2\settings.xml
```


```xml
<mirrors>
    <mirror>
        <id>nexus</id>
        <mirrorOf>*</mirrorOf>
        <url>http://localhost:8081/repository/maven-public/</url>
    </mirror>
</mirrors>

<activeProfiles>
    <!--make the profile active all the time -->
    <activeProfile>nexus</activeProfile>
</activeProfiles>
<profiles>
    <profile>
        <id>nexus</id>
        <!--Override the repository (and pluginRepository) "central" from the Maven Super POM
            to activate snapshots for both! -->
        <repositories>
        <repository>
            <id>central</id>
            <url>http://central</url>
            <releases>
            <enabled>true</enabled>
            </releases>
            <snapshots>
            <enabled>true</enabled>
            </snapshots>
        </repository>
        </repositories>
        <pluginRepositories>
        <pluginRepository>
            <id>central</id>
            <url>http://central</url>
            <releases>
            <enabled>true</enabled>
            </releases>
            <snapshots>
            <enabled>true</enabled>
            </snapshots>
        </pluginRepository>
        </pluginRepositories>
    </profile>
</profiles>
```

* `hello-lib` változtatás, `mvn deploy -Dmaven.repo.local=%USERPROFILE%\.m2\repository2`
* `hello-client` build, `mvn package`
* Build `mvn package -U`

## Projekt leszármazás

* `hello-parent`

```xml
<packaging>pom</packaging>

<version>1.0.0</version>
```

```xml
<slf4j.version>2.0.9</slf4j.version>
```

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>${slf4j.version}</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>${slf4j.version}</version>
    <scope>runtime</scope>
</dependency>
```

* `mvn install`

* `hello-child`

```xml
<parent>
    <groupId>training</groupId>
    <artifactId>hello-parent</artifactId>
    <version>1.0.0</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

* Függőségek Maven ablakban

```shell
mvn help:effective-pom > q
```

## Dependency management

* `hello-bom`

```xml
<packaging>pom</packaging>

<version>1.0.0</version>
```

```xml
<slf4j.version>2.0.9</slf4j.version>
```

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>${slf4j.version}</version>
        <scope>runtime</scope>
    </dependency>
 </dependencies>
</dependencyManagement>
```

* `mvn install`

* `hello-bom-client`

```xml
<parent>
    <groupId>training</groupId>
    <artifactId>hello-bom</artifactId>
    <version>1.0.0</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

```xml
<dependencies>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>training</groupId>
            <artifactId>hello-bom</artifactId>
            <version>1.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## Multi-module projektek

`hello-multimodule`

`hello-backend`, `hello-frontend`

```java
package hello;

public class Hello {

    public String sayHello(String name) {
        return "Hello %s!".formatted(name);
    }
}
```

```java
package hello;

public class HelloMain {

    public static void main(String[] args) {
        var hello = new Hello();
        System.out.println(hello.sayHello("Trainer"));
    }
}

```

```shell
mvn package -pl hello-backend
mvn package -pl hello-frontend
mvn package -pl hello-backend, hello-frontend
```

* `-am`, `-amd`

## Felkészülés a Java Platform Module Systemre

```shell
xcopy /e /i hello-multimodule hello-jpms
```

## Java Platform Module System

```java
import hellobackend.impl.SimpleHello;
import hellobackend.Hello;

module hello.backend {

    exports hellobackend;

    provides Hello with SimpleHello;
}
```

```java
import hellobackend.Hello;

module hello.frontend {

    requires hello.backend;

    uses Hello;
}
```

```java
public static void main(String[] args) {
    var hello = ServiceLoader.load(Hello.class)
            .findFirst().orElseThrow();

    System.out.println(hello.sayHello("Trainer"));
}
```

## Csak integrációs teszteket tartalmazó projekt

```xml
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <executions>
            <execution>
                <id>default-jar</id>
                <phase>never</phase>
            </execution>                    
            <execution>
                <goals>
                    <goal>test-jar</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
</plugins>
```

## Párhuzamos futtatás

## Webes alkalmazás fejlesztése Jetty-vel

`hello-web`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>training</groupId>
    <artifactId>hello-web</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>5.0.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.eclipse.jetty</groupId>
                <artifactId>jetty-maven-plugin</artifactId>
                <version>11.0.16</version>
            </plugin>
        </plugins>
    </build>

</project>
```

* `provided` scope

```java
package hello;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var out = resp.getWriter()) {
            out.println("Hello!");
        }
    }
}
```

## Spring Boot fejlesztés Maven használatával

```java
package training.hellospringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class HelloController {

    @GetMapping
    @ResponseBody
    public String hello() {
        return "hello " + LocalDateTime.now();
    }
}
```

## Groovy script futtatása

```xml
<plugin>
    <groupId>org.codehaus.gmavenplus</groupId>
    <artifactId>gmavenplus-plugin</artifactId>
    <version>3.0.0</version>
    <executions>
        <execution>
            <phase>validate</phase>
            <goals>
                <goal>execute</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <properties>
            <property>
                <name>name</name>
                <value>Trainer</value>
            </property>
        </properties>
        <scripts>
            <script>file:///${project.basedir}/src/main/groovy/Hello.groovy</script>
        </scripts>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>org.apache.groovy</groupId>
            <artifactId>groovy</artifactId>
            <!-- any version of Groovy \>= 1.5.0 should work here -->
            <version>4.0.15</version>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
</plugin>
```

* `/src/main/groovy/Hello.groovy`

```groovy
log.info("Hello ${name}!")
```

## Plugin fejlesztés

* `hello-plugin`

```xml
<version>1.0.0</version>
<packaging>maven-plugin</packaging>

<dependencies>
    <dependency>
        <groupId>org.apache.maven</groupId>
        <artifactId>maven-plugin-api</artifactId>
        <version>3.9.4</version>
    </dependency>

    <dependency>
        <groupId>org.apache.maven.plugin-tools</groupId>
        <artifactId>maven-plugin-annotations</artifactId>
        <version>3.9.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>

<build>
    <pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-plugin-plugin</artifactId>
                <version>3.9.0</version>
                <executions>
                    <execution>
                        <id>help-mojo</id>
                        <goals>
                            <!-- good practice is to generate help mojo for plugin -->
                            <goal>helpmojo</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </pluginManagement>
</build>
```

```java
package hello;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "hello")
public class HelloMojo extends AbstractMojo {

    @Parameter( property = "hello.name", defaultValue = "anonymous" )
    private String name;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Hello %s!".formatted(name));
    }
}
```

```shell
mvn install
mvn training:hello-plugin:1.0.0:hello
mvn training:hello-plugin:1.0.0:hello -Dhello.name=Trainer
```

* `hello-plugin-client`

```xml
<build>
    <plugins>
        <plugin>
            <groupId>training</groupId>
            <artifactId>hello-plugin</artifactId>
            <version>1.0.0</version>
            <configuration>
                <name>Trainer</name>
            </configuration>
            <executions>
                <execution>
                    <phase>validate</phase>
                    <goals>
                        <goal>hello</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

```shell
mvn validate
```

## pom.xml bejegyzések sorrendje

```xml
<plugin>
    <groupId>com.github.ekryd.sortpom</groupId>
    <artifactId>sortpom-maven-plugin</artifactId>
    <version>3.0.0</version>
    <configuration>
        <nrOfIndentSpace>4</nrOfIndentSpace>
    </configuration>
</plugin>
```

```shell
mvn sortpom:sort
```