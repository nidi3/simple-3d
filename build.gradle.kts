import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("multiplatform") version "1.3.61"
    id("org.jetbrains.dokka") version "0.10.0"
    id("maven-publish")
    id("signing")
    id("io.codearte.nexus-staging") version "0.21.2"
}

group = "guru.nidi.simple-3d"
version = "0.0.2"

fun isSnapshot() = version.toString().endsWith("SNAPSHOT")

val dokka by tasks.getting(DokkaTask::class) {
    outputDirectory = "$buildDir/dokka"
    outputFormat = "html"

    multiplatform {
        val jvm by creating {}
    }
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    archiveClassifier.set("javadoc")
    from(dokka)
}

kotlin {
    jvm {
        mavenPublication {
            artifact(dokkaJar)
            pom {
                name.set("simple-3d")
                description.set("Create simple 3D models.")
                url.set("https://github.com/nidi3/simple-3d")
                developers {
                    developer {
                        name.set("Stefan Niederhauser")
                        email.set("ich@nidi.guru")
                        organization.set("github")
                        organizationUrl.set("https://github.com/nidi3")
                    }
                }
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                scm {
                    url.set("https://github.com/nidi3/simple-3d")
                }
            }
        }
    }
    js {}

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        jvm().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        jvm().compilations["test"].defaultSourceSet {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        js().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        js().compilations["test"].defaultSourceSet {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
}

signing {
    if (!isSnapshot()) {
        useGpgCmd()
        sign(publishing.publications)
    }
}

val ossrhUser: String by project
val ossrhPassword: String by project

publishing {
    repositories {
        maven {
            name = "sonatype"
            url = uri(
                if (isSnapshot()) "https://oss.sonatype.org/content/repositories/snapshots/"
                else "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
            )
            credentials {
                username = ossrhUser
                password = ossrhPassword
            }
        }
    }
}

nexusStaging {
    packageGroup = "guru.nidi"
    stagingProfileId = "4fe23be45bb350"
    username = ossrhUser
    password = ossrhPassword
}
