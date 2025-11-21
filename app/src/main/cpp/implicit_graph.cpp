#include <jni.h>
#include <cmath>
#include <vector>

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_example_calculator_native_NativePlot_computeImplicit(
        JNIEnv* env,
        jobject /* this */,
        jint width,
        jint height,
        jfloat originX,
        jfloat originY,
        jfloat step,
        jfloat scale,
        jfloat threshold,
        jobject evaluator   // Kotlin callback to evaluate f(x,y)
) {

    jclass evaluatorClass = env->GetObjectClass(evaluator);
    jmethodID evalMethod = env->GetMethodID(
            evaluatorClass,
            "evaluate",
            "(DD)F"
    );

    // Output pixel bitmap (1 = draw, 0 = empty)
    std::vector<jint> bitmap(width * height, 0);

    const float pixelStep = std::max(1.0f / scale, 0.25f);

    for (int px = 0; px < width; px += pixelStep) {
        for (int py = 0; py < height; py += pixelStep) {

            // Convert to world coordinates
            float worldX = (px - originX) / (step * scale);
            float worldY = (originY - py) / (step * scale);

            // Evaluate f(x,y)
            jfloat f = env->CallFloatMethod(
                    evaluator,
                    evalMethod,
                    (double)worldX,
                    (double)worldY
            );

            if (std::fabs(f) < threshold) {
                bitmap[py * width + px] = 1;
            }
        }
    }

    // Convert to jintArray to send back to Kotlin
    jintArray result = env->NewIntArray(width * height);
    env->SetIntArrayRegion(result, 0, width * height, bitmap.data());
    return result;
}
