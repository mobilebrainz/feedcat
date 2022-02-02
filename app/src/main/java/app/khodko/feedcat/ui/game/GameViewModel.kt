package app.khodko.feedcat.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.khodko.feedcat.database.entity.GameResult
import app.khodko.feedcat.database.entity.User
import app.khodko.feedcat.database.repository.GameResultRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class GameViewModel(
    private val gameResultRepository: GameResultRepository,
    val user: User?
) : ViewModel() {

    companion object {
        const val MAX_COUNT = 15
    }

    private val _satiety = MutableLiveData(0)
    val satiety: LiveData<Int> = _satiety

    fun feed(): Boolean {
        _satiety.value = _satiety.value!! + 1
        return _satiety.value!!.mod(MAX_COUNT) == 0
    }

    fun save() {
        user?.let { u ->
            _satiety.value?.let {
                val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.US)
                val currentDate = sdf.format(Calendar.getInstance().time)
                val gameResult = GameResult(it, u.id, currentDate)
                viewModelScope.launch {
                    gameResultRepository.insert(gameResult)
                }
            }
        }
    }
}