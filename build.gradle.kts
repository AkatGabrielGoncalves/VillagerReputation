import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.6.20"
  application
}

group = "me.akats"
version = "1.0.1"

repositories {
  mavenCentral()
  maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
  testImplementation(kotlin("test"))
  compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
  implementation(kotlin("stdlib-jdk8"))
}

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "17"
}

application {
  mainClass.set("Main")
  println(application.mainClass.get())
}

tasks.withType<Jar> {
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
  manifest {
    attributes["Main-Class"] = application.mainClass.get()
  }
  from(sourceSets.main.get().output)

  dependsOn(configurations.runtimeClasspath)

  from({
    configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
  })
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
  jvmTarget = "1.8"
}