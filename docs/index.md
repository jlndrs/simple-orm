Simple-Orm ist ein einfaches object-relational-mapping Modul, mit welchem sich 
Java-Datenobjekte (POJO, "Plain old Java object") in einer relationen Datenbank ablegen lassen.

## Inhalt
- [Download](#download)
- [Abhängigkeiten](#abhängigkeiten)
- [Erste Schritte](#erste-schritte)
  - [EntityManager](#entitymanager)
    - [Rekursiver Scan](#rekursiver-scan)
  - [PersistenceService](#persistenceservice)
    - [Eigene Implementierung](#eigene-implementierung)
    - [Methoden](#methoden)
  - [Datenbankanbindung](#datenbankanbindung)
- [Annotationen](#annotationen)
  - [EntityMapping](#entitymapping)
  - [SuperclassMapping](#superclassmapping)
  - [ColumnMapping](#columnmapping)
  - [PrimaryKeyColumn](#primarykeycolumn)

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

## Erste Schritte
Für die Nutzung gibt es zwei Zentrale Klassen: den `EntityManager` und den `PersistenceService`:

``` java
EntityManager entityManager = EntityManagerFactory.scanPackage("de.juliandrees.simpleorm.model", false);
PersistenceService persistenceService = PersistenceServiceFactory.newInstance(entityManager);
```

### EntityManager
``` java
EntityManager entityManager = EntityManagerFactory.scanPackage("YOURPACKAGE", true);
```
Der EntityManager enthält die Schemata für die Entitäten, die einem Datensatz zugewiesen werden sollen.
Dazu werden Packages gescannt und Klassen ausgewählt, die die Annotation `@EntityManager` haben.

#### Rekursiver Scan
Mit der Option "recursive" kann festgelegt werden, ob auch Subpackages für den Scan einbezogen werden.
Nützlich für Programme, wo sich die Entitäten in unterschiedlichen Packages befinden.

### PersistenceService
```java 
PersistenceService persistenceService = PersistenceServiceFactory.newInstance(entityManager);
```
Der PersistenceService ist die Schnittstelle zwischen Anwendung und Datenbank.

#### Eigene Implementierung
Wenn gewünscht, kann auch eine eigene Implementierung des PersistenceService genutzt werden.
Dazu muss jedoch der PersistenceService von folgender Klasse ableiten:

``` java
public class CustomPersistenceService extends AbstractPersistenceService {
```

Die Klasse kann bei der Initialisierung übergeben werden:
`PersistenceServiceFactory.newInstance(entityManager, CustomPersistenceService.class)`

#### Methoden
``` java
<T> void persist(T entity)

<T> T find(Long id, Class<T> entityClass)

<T> T find(String column, Object value, Class<T> entityClass)

<T> List<T> loadAll(Class<T> entityClass)

<T> List<T> loadAll(String column, Object value, Class<T> entityClass)
```

### Datenbankanbindung
Eine JSON-Datei mit dem Namen "persistence.json" muss sich im Basisverzeichnis des Classpath befinden:

``` json
{
  "credentials": {
    "userName": "USERNAME",
    "password": "PASSWORD"
  },
  "host": "localhost",
  "database": "simple-orm",
  "port": 5432,
  "jdbcType": "postgresql"
}
```

## Annotationen
Simple-Orm basiert auf Java-Annotationen, die im Quellcode Typen (Klassen) und Methoden (**nur** Getter) zugewiesen werden.
Auf Basis der genutzten Annotationen wird ein Schema generiert. So werden alle Typen und Methoden mit Annotationen "markiert",
die für Simple-Orm relevant sein sollen.

Um das Modul so einfach wie möglich zu halten, müssen einige Richtlinien beachtet werden, damit das Modul optimal genutzt werden kann.
Diese "Richtlinien" beziehen sich auf die Annotationen.

### EntityMapping
Markiert eine Klasse als Entity-Schema.

``` java
@EntityMapping("testEntity")
public class TestEntity {
```

Hinweise:
- wenn kein String übergeben wird, wird der Klassenname als Entity-Name gewählt

### SuperclassMapping
Markiert eine Klasse als Superklasse einer Entität. So werden Methoden und Felder, die für
Simple-Orm markiert wurden, ebenfalls beachtet.
``` java
@SuperclassMapping
public class BaseEntity {
```

Hinweise:
- kann auch an eine Klasse angehängt werden, die mit `@EntityMapping` markiert wurde
- eine Klasse ohne `@EntityMapping` gilt nicht als Entity-Schema

### ColumnMapping
Markiert eine Methode als Getter für ein definiertes Feld.
``` java
private String name;

@ColumnMapping("NAME_")
public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}
```

Hinweise:
- kann nur bei Gettern definiert werden
- Feld und Setter müssen in der Klasse definiert sein (keine Vererbung!)

### PrimaryKeyColumn
Markiert eine Methode als Getter für den PRIMARY KEY.
``` java
private Long id;

@PrimaryKeyColumn
@ColumnMapping
public Long getId() {
    return id;
}
````

Hinweise:
- gilt nur für Felder vom Typ long/Long