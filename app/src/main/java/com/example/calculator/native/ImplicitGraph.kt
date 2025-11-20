import com.example.calculator.utlis.ImplicitEvaluator

object NativePlot {
    init {
        System.loadLibrary("calculator_native")
    }

    external fun computeImplicit(
        width: Int,
        height: Int,
        originX: Float,
        originY: Float,
        step: Float,
        scale: Float,
        threshold: Float,
        evaluator: ImplicitEvaluator
    ): IntArray
}
