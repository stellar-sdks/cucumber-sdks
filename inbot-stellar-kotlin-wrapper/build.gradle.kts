plugins {
    kotlin("jvm") version "1.2.71"
}

repositories {
    jcenter()
    maven(uri("https://jitpack.io"))
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("io.cucumber:cucumber-java:4.3.1")
    testImplementation("io.cucumber:cucumber-junit:4.3.1")

    testCompile("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testImplementation("com.github.Inbot:inbot-stellar-kotlin-wrapper:0.10.0")
}
