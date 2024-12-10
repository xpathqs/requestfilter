import kotlin.text.get

plugins {
	java
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
	`java-library`
	`maven-publish`
	signing
	id("io.codearte.nexus-staging") version "0.30.0"
}

group = "org.xpathqs.api.requestfilter"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.apache.commons:commons-lang3:3.17.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}



publishing {
	publications {
		beforeEvaluate {
			signing.sign(this@publications)
		}
		create<MavenPublication>("mavenJava") {
			pom {
				name.set("XpathQS API DelayController")
				description.set("A library for building Xpath queries in an OOP style")
				url.set("https://xpathqs.org/")
				licenses {
					license {
						name.set("MIT License")
						url.set("http://www.opensource.org/licenses/mit-license.php")
					}
				}
				developers {
					developer {
						id.set("nachg")
						name.set("Nikita A. Chegodaev")
						email.set("nikchgd@gmail.com")
					}
				}
				scm {
					connection.set("scm:git:git://github.com/xpathqs/requestfilter.git")
					developerConnection.set("scm:git:ssh://github.com/xpathqs/requestfilter.git")
					url.set("https://xpathqs.org/")
				}
			}
			groupId = "org.xpathqs.api"
			artifactId = "delaycontroller"

			from(components["java"])
		}
	}
	repositories {
		maven {
			val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
			val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
			url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
			credentials {
				username = project.property("ossrhUsername").toString()
				password = project.property("ossrhPassword").toString()
			}
		}
	}
}

signing {
	sign(publishing.publications["mavenJava"])
}

nexusStaging {
	serverUrl = "https://s01.oss.sonatype.org/service/local/"
}