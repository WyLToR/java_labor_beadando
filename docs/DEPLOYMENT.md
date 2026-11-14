# MoziDB – Rivendell/Tomcat telepítés

## Követelmények

A projekt Java 25-re fordul, Spring Boot 4.1.1-et használ. Külső Servlet 6.1+
konténer szükséges, például Tomcat 11. A szolgáltatás JVM-je legalább Java 25
legyen; a Tomcat saját alacsonyabb Java-minimuma ehhez a WAR-hoz nem elegendő.
A projekt verzióit telepítési hiba miatt ne cseréljük le.

Források: [Spring Boot követelmények](https://docs.spring.io/spring-boot/system-requirements.html),
[Tomcat verziók](https://tomcat.apache.org/whichversion.html).

**A Rivendell tényleges Java- és Tomcat-verziója NEM ELLENŐRZÖTT.**
Az üzemeltetőtől kell megtudni, vagy jogosultság birtokában ellenőrizni az
`/opt/tomcat/bin/version.sh` kimenetét. A szolgáltatás JVM-jét vizsgáljuk,
mert eltérhet az SSH terminál `java -version` eredményétől.

## WAR előállítása

Java 25 és Maven 3.6.3+ mellett a projekt gyökerében:

```powershell
java -version
mvn -version
mvn clean test
mvn clean package
```

Eredmény: `target/nlyg87.war`. A `target/` generált tartalom, nem commitolandó.
## Telepítési cél

- Host: `rivendell.nje.hu`
- Tomcat port: `9443`
- Feltöltési könyvtár: `/opt/tomcat/webapps`
- WAR neve: `nlyg87.war`
- Elvárt URL: `http://rivendell.nje.hu:9443/nlyg87/`

Külső Tomcatnél a WAR neve határozza meg a context path-t. Ne írjunk még egy
`/nlyg87` előtagot a sablonokba. A tényleges feltöltés és újraindítás külön,
az üzemeltetővel egyeztetett lépés; ez a dokumentum nem igazolja a deploymentet.

## MySQL konfiguráció

A production `application.properties` változatlanul ezt használja:

```properties
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/${MYSQL_DATABASE:mozi_beadando}
spring.datasource.username=${MYSQL_USERNAME:root}
spring.datasource.password=${MYSQL_PASSWORD:}
```

Rivendellen a **Tomcat folyamat környezetében** legyen:

```text
MYSQL_HOST=localhost
MYSQL_DATABASE=nlyg87
MYSQL_USERNAME=nlyg87
MYSQL_PASSWORD=<csak a szerveren megadott jelszó>
```

A port `3306`. Valódi jelszó ne kerüljön Gitbe, dokumentumba vagy parancssori
argumentumba. A helyi alapértékek a fejlesztéshez megmaradtak.

Egy SSH terminálban kiadott `export` vagy PowerShell-változó nem jut el
automatikusan a már futó szolgáltatáshoz. Először meg kell tudni, hogy a Tomcat
systemd szolgáltatásként vagy indítószkripttel fut-e. Systemd esetén az üzemeltető
korlátozott jogosultságú, Git-en kívüli `EnvironmentFile` fájlból adhatja át
a változókat, majd újraindítja a szolgáltatást. Ez a teljes Tomcat folyamatot
érinti; megosztott szerveren alkalmazásonkénti beállítás ajánlott.

Szolgáltatásszintű jogosultság hiányában, ha alkalmazásonkénti Tomcat context
descriptor létrehozható, például az
`/opt/tomcat/conf/Catalina/localhost/nlyg87.xml` fájlban megadható:

```xml
<Context>
    <Parameter name="spring.config.additional-location"
               value="file:/home/nlyg87/private/mozidb.properties"
               override="false" />
</Context>
```

A descriptor helyét az üzemeltetőnek a tényleges `CATALINA_BASE`, Engine és Host
alapján kell jóváhagynia. A külső fájl ne legyen a webapps alatt vagy a repóban;
csak a tulajdonos és a Tomcat szolgáltatás felhasználója olvashassa. Tartalma:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nlyg87
spring.datasource.username=nlyg87
spring.datasource.password=<csak a szerveren megadott valódi jelszó>
```

Ez alkalmazásonkénti külső konfiguráció, a WAR átírása nélkül. Ha a descriptor
sem írható, az üzemeltető segítsége szükséges. A WAR-ba égetett jelszó nem
elfogadható kerülőút. A szerver konfigurációs módja jelenleg nincs ellenőrizve.
Forrás: [Spring Boot külső konfiguráció](https://docs.spring.io/spring-boot/reference/features/external-config.html).

## SQL import

A `database/mozi.sql` a `film`, `mozi`, `eloadas` táblák szerkezetét és adatait
tartalmazza. **A script a három táblát törli és újralétrehozza, ezért meglévő
éles adatokra nem szabad ellenőrizetlenül újrafuttatni.** Előtte mentés és a
kiválasztott adatbázis ellenőrzése szükséges. A mozis SQL nem teljes alkalmazásmentés.

A szerveren, az SQL-fájl megfelelő elérési útjával:

```sh
mysql -h localhost -P 3306 -u nlyg87 -p nlyg87
```

A jelszót az interaktív kérdésnél adjuk meg. A MySQL kliensben:

```sql
SELECT DATABASE();
SOURCE /a/feltoltott/database/mozi.sql;
SELECT COUNT(*) AS filmek FROM film;
SELECT COUNT(*) AS mozik FROM mozi;
SELECT COUNT(*) AS eloadasok FROM eloadas;
```

A darabszámokat az importált forrással kell összevetni.

## Helyi context-path ellenőrzés

Elérhető helyi MySQL és a hozzá tartozó `MYSQL_*` konfiguráció mellett:

```powershell
java -jar target/nlyg87.war --server.servlet.context-path=/nlyg87
```

Egyenértékű az adott indítási folyamatnak átadott
`SERVER_SERVLET_CONTEXT_PATH=/nlyg87` változóval. A kapcsoló csak erre az
indításra érvényes; normál helyi indításkor hagyjuk el.

Ellenőrizzük a `http://localhost:8080/nlyg87` előtaggal a `/`, `/adatbazis`,
`/kapcsolat`, `/diagram`, `/rest`, `/login`, `/register`, `/api/filmek` útvonalakat.
USER és ADMIN szerepkörrel az `/uzenetek`, ADMIN-ként a `/crud` is működjön.
A login és a POST logout az alkalmazáson belül irányítson át. Nézzük meg mobilon
a menüt, a kijelentkezést, a Chart.js diagramot és a táblázatokat is.
REST- és CRUD-próbához csak ütközésellenőrzött ideiglenes adatot használjunk,
és csak az általunk létrehozott tesztadatokat töröljük.

## Hibakeresés és éles ellenőrzés

### Tomcat naplók és ellenőrzések

Tomcat logok: `/opt/tomcat/logs`; systemd esetén a szolgáltatás journalja is
szükséges lehet. Indulási hiba esetén először a Java/Tomcat verziókat, az
adatbázis-kapcsolatot, a külső konfiguráció elérhetőségét és jogosultságait nézzük.

A tényleges szerveren HTTP-vel is ellenőrizni kell a funkciókat, a context path-t,
az asseteket, a jogosultságokat és a 404/500 státuszokat. A hiányzó film
JSON-kérésre 404-et adjon, loginra irányítás nélkül. A helyi ellenőrzések
nem helyettesítik a külső Tomcat + MySQL ellenőrzést.
A Rivendell deployment addig **NEM ELLENŐRZÖTT**.
