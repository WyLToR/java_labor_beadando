# Telepítési útmutató – java_labor_beadando

## 1. Rendszerkövetelmények

- Java JDK 25 szükséges.
- Spring Boot 4.1.1.
- Apache Tomcat 11.
- MySQL adatbázis.

## 2. WAR fájl előállítása

A projekt Maven eszközével kell előállítani a telepíthető WAR csomagot. Az IntelliJ Maven ablakában a Lifecycle műveletek között a clean, majd a package feladatot kell lefuttatni.

## 3. WAR fájl neve

A létrejövő telepítési csomag neve: nlyg87.war.

## 4. Rivendell szerver

A telepítés célja a Rivendell szerver.

Rivendell szerver: <RIVENDELL SZERVER CÍME>

## 5. Feltöltés helye

A WAR fájlt a szerveren az alábbi könyvtárba kell feltölteni:

/opt/tomcat/webapps

## 6. Alkalmazás elérése

A telepítés után az alkalmazás a szerver címén, az nlyg87 alkalmazásútvonal alatt érhető el.

https://<RIVENDELL SZERVER CÍME>/nlyg87/

## 7. MySQL adatbázis

A MySQL adatbázis eléréséhez szükséges beállításokat az alkalmazás konfigurációjában kell megadni.

A konfiguráció helye:

src/main/resources/application.properties

A kapcsolat beállításainak példája:

spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

## 8. Hibakeresés

A Tomcat naplófájljainak helye:

/opt/tomcat/logs

Hiba esetén itt ellenőrizhetők az alkalmazás indulásával és működésével kapcsolatos naplóbejegyzések.

## 9. Telepítés ellenőrzése

- Az alkalmazás elindul.
- A főoldal betöltődik.
- A bejelentkezés működik.
- A MySQL kapcsolat működik.
- A CSS és JavaScript fájlok betöltődnek.
- A REST API megfelelően válaszol.
- A hibakezelő oldalak megjelennek.