package app.khodko.feedcat.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        const val MAX_COUNT = 15
    }

    private val _satiety = MutableLiveData<Int>().apply {
        value = 0
    }
    val satiety: LiveData<Int> = _satiety

    fun feed() {
        _satiety.value?.let {
            _satiety.value = if (it < MAX_COUNT) it.plus(1) else 0
        }
    }
}