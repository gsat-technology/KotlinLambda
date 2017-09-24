## KotlinLambda


Instructions to create AWS Lambda using Kotlin

#### Create New Project
1. Open IntelliJ, click 'Create New Project'
2. Choose 'Maven' in left panel
3. Select 'create from archetype'
4. Choose 'org.jetbrains.kotlin:kotlin-archetype-jvm'
5. Next
6. GroupId: type something like 'com.gsat.mylambdafunction'
7. ArtifactId: type something like 'MyLambdaFunction'
8. Version: (leave as default)
9. Next, Next
10. Update project location if needed. Note that you will probably want to actually create a new folder on disk e.g 'MyLambdaFunction'

#### Update POM

Update kotlin version to `1.1.2-2`

```
<properties>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <kotlin.version>1.1.2-2</kotlin.version>
    <junit.version>4.12</junit.version>
</properties>
```

Add Shade Plugin

```
<plugins>
  ..
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>2.3</version>
    <configuration>
      <createDependencyReducedPom>false</createDependencyReducedPom>
    </configuration>
    <executions>
      <execution>
        <phase>package</phase>
        <goals>
          <goal>shade</goal>
        </goals>
      </execution>
    </executions>
  </plugin>
</plugins>
```

### Update Maven

1. Open the Maven Projects panel in IntelliJ
2. Click 'Re-import all maven projects' button (refresh button)
3. In Lifecycles, execute 'install' 

### Create Handler

Rename `Hello.kt` to `Hander.kt` and replace code with

```
package com.gsat.mylambdafunction

class Handler {
    fun handler() {
        println("it works")
    }
}
```

### Create Package

1. Run maven package

A new jar should appear in target/ `MyLambdaFunction-1.0-SNAPSHOT.jar`

### Create New Lambda in AWS

1. Upload `MyLambdaFunction-1.0-SNAPSHOT.jar`
2. Hander for this would be `com.gsat.mylambdafunction.Handler::handler` 
3. Do a test execution. You should see the output 'it works'

