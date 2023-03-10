pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://repo.spring.io/release") }
  }
  resolutionStrategy {
    eachPlugin {
      if (requested.id.id == "org.hibernate.orm") {
        useModule("org.hibernate:hibernate-gradle-plugin:5.6.12.Final")
      }
    }
  }
}
rootProject.name = "frolic-sns"
