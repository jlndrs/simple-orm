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