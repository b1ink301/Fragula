/*
 * Copyright 2023 Fragula contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.get
import org.gradle.plugins.signing.SigningExtension
import java.util.Properties

/**
 * ./gradlew publishReleasePublicationToSonatypeRepository
 */
class PublishModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("maven-publish")
                apply("signing")
            }

            val publishing = extensions["publishing"] as PublishingExtension
            val publishModule = extensions.create("publishModule", PublishModuleExtension::class.java)
            val properties = Properties().apply {
                load(rootProject.file("local.properties").inputStream())
            }
            extra["signing.keyId"] = properties.getProperty("signing.keyId")
            extra["signing.password"] = properties.getProperty("signing.password")
            extra["signing.secretKeyRingFile"] = properties.getProperty("signing.secretKeyRingFile")
            extra["ossrhUsername"] = properties.getProperty("ossrhUsername")
            extra["ossrhPassword"] = properties.getProperty("ossrhPassword")

            group = publishModule.libraryGroup
            version = publishModule.libraryVersion

            configure<PublishingExtension> {
                afterEvaluate {
                    repositories {
                        maven {
                            name = "sonatype"
                            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                            credentials {
                                username = properties.getProperty("ossrhUsername")
                                password = properties.getProperty("ossrhPassword")
                            }
                        }
                    }
                    publications {
                        create("release", MavenPublication::class.java) {
                            groupId = publishModule.libraryGroup
                            artifactId = publishModule.libraryArtifact
                            version = publishModule.libraryVersion

                            if (plugins.hasPlugin("com.android.library")) {
                                from(components["release"])
                            } else {
                                from(components["java"])
                            }

                            pom {
                                name.set(publishModule.libraryArtifact)
                                description.set("Fragula is a swipe-to-dismiss extension for navigation component library for Android.")
                                url.set("https://github.com/massivemadness/Fragula")
                                licenses {
                                    license {
                                        name.set("Apache 2.0 License")
                                        url.set("https://github.com/massivemadness/Fragula/blob/master/LICENSE")
                                    }
                                }
                                developers {
                                    developer {
                                        id.set("massivemadness")
                                        name.set("Dmitrii Rubtsov")
                                        email.set("dm.mironov01@gmail.com")
                                    }
                                }
                                scm {
                                    connection.set("scm:git:github.com/massivemadness/Fragula.git")
                                    developerConnection.set("scm:git:ssh://github.com/massivemadness/Fragula.git")
                                    url.set("https://github.com/massivemadness/Fragula/tree/master")
                                }
                            }
                        }
                    }
                }
            }
            configure<SigningExtension> {
                sign(publishing.publications)
            }
        }
    }
}