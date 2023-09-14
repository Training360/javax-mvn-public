# JAVAX-MVN - Szoftver életciklus menedzsment Maven használatával (2 nap)

Maven használata, életciklus, pluginek, függőségek, pom.xml optimalizálás

## Ismertető

A Maven egy régóta létező, népszerű build eszköz, mellyel szinte már minden Java fejlesztő találkozott. 
A gyakorlatorientált képzésünk célja, hogy megtanítsa a Maven alapjait, és segítsen megérteni, 
hogyan használható hatékonyan ez az eszközt a fejlesztési folyamat során.

A képzés során gyakorlati példákon keresztül lépésről lépésre fedezzük fel a Maven funkcionalitásait. 
Megtanuljuk, hogyan hozhatunk létre Maven projektet, hogyan kezelhetjük a függőségeket, 
és hogyan buildelhetjük le és tesztelhetjük a projektünket. Emellett megismerjük a Maven konfigurációját és 
testreszabását, hogy maximálisan kihasználhassuk az eszköz nyújtotta lehetőségeket.

## Szükséges előképzettség

A következő témakörök ismerete elvárt: biztonságos fejlesztőeszköz kezelés, Java csomagok, osztályok,
interfészek, kivételkezelés.

Előfeltétele a _Java SE alapok (JAVA-BSC)_ és a _Java SE haladó (JAVA-ADV)_ képzések elvégzése, 
vagy azon képzések anyagának gyakorlati ismerete.

Mivel a tananyagok egy
része angol nyelvű, ezért alapfokú, dokumentumolvasás-szintű angol
nyelvtudás szükséges. A képzés magyar nyelvű, magyar slide-ok alapján.

## Tematika

* Maven: alapfogalmak, convention over configuration
* Maven telepítése, környezeti beállítások
* Repository
* Projekt felépítése, könyvtárstruktúra
* Életciklusok, fázisok, pluginek
* Maven Wrapper
* Projekt létrehozása archetype segítségével
* Függőségek
* Függőség intervallumok
* Tranzitív függőségek, függőségi fa
* Scope-ok
* Property-k használata
* Lombok és MapStruct használata
* Tesztesetek futtatása
* Párhuzamos futtatás
* Tesztlefedettség
* Integrációs tesztek
* SonarScanner, SonarQube integráció
* OWASP dependency check
* Csomagolás
* Uber JAR
* Profile, profile activation
* Install lokális repository-ba
* Nexus és Artifactory, mint mirror
* Telepítés Nexus és Artifactory repo-kba
* Site generálás, riportok
* CI/CD támogatás, paraméterezhető verziószám
* Docker konténer futtatása
* Docker build futtatása
* Build Docker konténerben
* Library készítése és felhasználása
* SNAPSHOT verziók
* Projekt öröklődés, szülő - gyermek projekt
* Dependency management
* Multi-module projekt
* Java Platform Module System
* Java webes alkalmazás
* Spring Boot Mavennel
* POM optimalizálás
* Maven Plugin implementálása