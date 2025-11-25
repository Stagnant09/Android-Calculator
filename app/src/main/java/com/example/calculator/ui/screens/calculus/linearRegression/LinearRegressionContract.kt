import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface LinearRegressionContract {

    data class State(
        val points: List<Point> = emptyList(),
        val selectedPointIndex: Int? = null,
        val regression: RegressionResult? = null
    ) : CustomState

    data class Point(val x: Float, val y: Float)

    data class RegressionResult(
        val a: Float,
        val b: Float,
        val correlation: Float,
        val covariance: Float
    )

    sealed interface Event : CustomEvent {
        data class AddPoint(val x: Float, val y: Float) : Event
        data class SelectPoint(val index: Int?) : Event
        data class MovePoint(val index: Int, val x: Float, val y: Float) : Event
        data object RemoveSelectedPoint : Event
    }

    sealed interface Effect : CustomEffect
}
