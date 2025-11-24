import com.example.calculator.foundation.CustomEffect
import com.example.calculator.foundation.CustomEvent
import com.example.calculator.foundation.CustomState

sealed interface LinearRegressionContract {

    data class State(
        val placeholder: String = ""
    ) : CustomState

    sealed interface Event : CustomEvent {
        
    }

    sealed interface Effect : CustomEffect {
        
    }
}