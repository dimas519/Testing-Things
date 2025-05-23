// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.dynatrace.tools.android:gradle-plugin:8.315.1.1005")

    }
}

apply(plugin = "com.dynatrace.instrumentation")
configure<com.dynatrace.tools.android.dsl.DynatraceExtension> {
    configurations {
        create("Main") {
//            locationMonitoring(true) //Try 1:trying to put it here, because maybe need need top hierarchy ->Not work
            autoStart {
                applicationId("e6d719af-d971-4f57-beda-afdb15098a6a")
                beaconUrl("https://bf68249ooj.bf.dynatrace.com/mbeacon")

            }
//            locationMonitoring(true) //Try 2:trying to put it here, because value got replaced by autostart ->Not work

            userActions {
                sensors {
                    // fine-tune the sensors if necessary
//                    pageChange(true)
                    refresh(false)
                }
//                namePrivacy(true)
            }

            locationMonitoring(true) //Try 3:trying to put it here, because value got replaced by userAction option ->Not work
            sessionReplay.enabled(false)
            userOptIn(true)



        }
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false


}

