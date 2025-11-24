import com.example.calculator.foundation.CustomViewModel

class LinearRegressionViewModel :
    CustomViewModel<LinearRegressionContract.State, LinearRegressionContract.Event, LinearRegressionContract.Effect>(
        initialState = LinearRegressionContract.State()
    ) {

    override suspend fun handleEvent(event: LinearRegressionContract.Event) {
        when (event) {
            else -> {
            
            }
        }
    }
}