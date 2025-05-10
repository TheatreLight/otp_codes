plugins {
    application
    java
}
application {
    mainClass.set("promo.it.Main")
}
group = "promo.it"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven ("https://jitpack.io" )
}

dependencies {
    implementation ("io.github.cdimascio:dotenv-java:2.3.0")
    implementation("org.postgresql:postgresql:42.7.2")

    implementation("com.sun.mail:javax.mail:1.6.2")

    implementation("org.jsmpp:jsmpp:2.3.7")

    implementation("org.telegram:telegrambots:6.9.7.1")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")

    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.slf4j:slf4j-simple:2.0.13")

    implementation("at.favre.lib:bcrypt:0.9.0")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("com.eatthepath:java-otp:0.4.0")

    implementation("com.sun.mail:javax.mail:1.6.2")

    implementation("org.opensmpp:opensmpp-core:3.0.0")

    implementation("org.apache.httpcomponents:httpclient:4.5.13")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

