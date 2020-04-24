# Simple-Orm
Simple-Orm ist ein einfaches object-relational-mapping Modul, mit welchem sich
Java-Datenobjekte (POJO, "Plain old Java object") auf Datensätze mappen lassen.

## Dependencies
- [https://projectlombok.org](https://projectlombok.org) - 1.18.2
- [https://junit.org/junit5/](https://junit.org/junit5/) - 4.13

## Annotationen
Simple-Orm basiert auf der Nutzung von Java-Annotationen,
die im Quellcode Typen (Klassen) und Methoden (**nur** Getter) zugewiesen werden können.

Um Simple-Orm einfach zu halten, müssen jedoch einige Richtlinien beachtet werden, damit das Modul
optimal genutzt werden kann.

Die entsprechenden Richtlinien / Hinweise werden bei den jeweiligen Annotationen erläutert.

### EntityMapping

``` java
@EntityMapping(value = "testEntity")
public class TestEntity {
```

Ein `@EntityMapping` ist die Annotation für eine Tabelle. Ein Typ (eine Klasse) wird
auf eine Tabelle gemapped.

Der Wert von `value` kann entweder
- ausgelassen werden (es wird der Name des Typs verwendet)
- spezifisch angegeben werden

### SuperclassMapping
``` java
@EntityMapping(value = "testEntity")
@SuperclassMapping
public class TestEntity {
```

Ein `@SuperclassMapping` ist der Hinweis für das Modul, dass diese Klasse
auch als Superklasse für eine Entität verwendet werden kann.

**Der Vorteil**: Entitäten, die von anderen Typen ableiten, besitzen zusätzlich
zu den eigenen definierten Feldern die Felder der Superklasse.
Was wie einfache Java-Vererbung klingt, ist bei ORM nicht selbstverständlich.

**Hinweis**: Ein Typ kann sowohl eine Entität (`@EntityMapping`) als auch eine Superklasse für Entitäten (`@SuperclassMapping`)
sein.

### PrimaryKeyColumn
``` java
@PrimaryKeyColumn
@ColumnMapping
private Long id;
```
Ein `@PrimaryKeyColumn` ist der Hinweis für das Modul, dass dieses
Attribut die Werte des Primärschlüssels enthält. (Wichtig für Persistence).

**Jede Entität benötigt eine Primärschlüsselspalte**.

## Weitere Klassen
Zusätzlich bietet Simple-Orm einige Klassen, die beim Verwalten und Persistieren von Entitäten 
nützlich sein können.

### EntityManager
Der EntityManager ist eine Klasse, mit der sich die Entitäten im allgemeinen Verwalten lassen,
die im Projekt vorhanden und gescannt wurden.

#### Erstellen
Da der EntityManager Entitäten (hier: Typen, Klassen) verwaltet, müssen diese zuerst geladen werden.

``` java
EntityManager#scanPackage(String package, boolean recursive)
```
>Auswahl der Entitäten mit Angabe eines Packages per String (z. B. `scanPackage("de.juliandrees.simpleorm.model", true`).

``` java
EntityManager#scanPackage(Class<?> classInPackage, boolean recursive)
```
> Auswahl der Entitäten anhand einer beliebigen Klasse, die sich im Package befindet.

So kann die Initialisierung eines EntityManagers beispielsweise wie folgt aussehen:

``` java
EntityManager entityManager = EntityManager.scanPackage("de.juliandrees.model", true);
```

#### Option: recursive
Die Option "recursive" bedeutet, dass rekursiv die Subpackages des "Startpackages" behandelt werden können,
um alle unterliegenden Entitäten auch zu finden. 

Ist die Option auf false, so wird nur das Package beachtet, welches konkret angegeben wurde (bzw. welches durch die Klasse bestimmt wird).