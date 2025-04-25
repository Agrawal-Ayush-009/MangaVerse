import de.undercouch.gradle.tasks.download.Download

val assetDir = "${project.buildDir}/downloaded_assets"

tasks.register<Download>("downloadModelFile") {
    src("https://storage.googleapis.com/mediapipe-models/face_detector/blaze_face_short_range/float16/1/blaze_face_short_range.tflite")
    dest(File(assetDir, "face_detection_short_range.tflite"))
    overwrite.set(false)
}

// Make sure the file is downloaded before build starts
tasks.named("preBuild").configure {
    dependsOn("downloadModelFile")
}