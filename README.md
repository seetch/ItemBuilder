## Maven

```xml
<repository>
  <id>daycube-repo</id>
  <url>https://repo.daycube.su/releases</url>
</repository>

<dependency>
  <groupId>su.daycube</groupId>
  <artifactId>ItemBuilder</artifactId>
  <version>1.0.1</version>
</dependency>
```

## Gradle 

```xml
maven {
    url = uri("https://repo.daycube.su/releases")
}

implementation("su.daycube:ItemBuilder:1.0.1")
```
