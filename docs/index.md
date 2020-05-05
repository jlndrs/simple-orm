Simple-Orm ist ein einfaches object-relational-mapping Modul, mit welchem sich 
Java-Datenobjekte (POJO, "Plain old Java object") in einer relationen Datenbank ablegen lassen.

## Download

maven
``` xml
<dependency>
    <groupId>com.github.jlndrs</groupId>
    <artifactId>simple-orm</artifactId>
    <version>1.0.3</version>
</dependency>

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://www.jitpack.io</url>
    </repository>
</repositories>
```

Die Bereitstellung des Moduls erfolgt nur über jitpack.io.

## Abhängigkeiten
- lombok: [https://projectlombok.org](https://projectlombok.org) - 1.18.2
- junit: [https://junit.org/junit5/](https://junit.org/junit5/) - 5.6.2
- apache-commons lang3: [http://commons.apache.org/proper/commons-lang/](http://commons.apache.org/proper/commons-lang/) - 3.10
- gson: [https://github.com/google/gson](https://github.com/google/gson) - 2.8.6
- diverse jdbc Driver (postgres)

## Annotationen
Simple-Orm basiert auf Java-Annotationen, die im Quellcode Typen (Klassen) und Methoden (**nur** Getter) zugewiesen werden.
Auf Basis der genutzten Annotationen wird ein Schema generiert. So werden alle Typen und Methoden mit Annotationen "markiert",
die für Simple-Orm relevant sein sollen.

Um das Modul so einfach wie möglich zu halten, müssen einige Richtlinien beachtet werden, damit das Modul optimal genutzt werden kann.
Diese "Richtlinien" beziehen sich auf die Annotationen.
