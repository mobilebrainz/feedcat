package app.khodko.feedcat.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.khodko.feedcat.database.entity.GameResult
import app.khodko.feedcat.database.repository.GameResultRepository
import kotlinx.coroutines.launch

class GameViewModel(private val gameResultRepository: GameResultRepository) : ViewModel() {

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

    fun save() {
        _satiety.value?.let {
            val gameResult = GameResult(it)
            viewModelScope.launch {
                gameResultRepository.insert(gameResult)
            }

        }
    }
}