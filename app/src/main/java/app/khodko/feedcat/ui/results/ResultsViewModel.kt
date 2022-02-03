package app.khodko.feedcat.ui.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.khodko.feedcat.data.entity.GameResult
import app.khodko.feedcat.data.entity.User
import app.khodko.feedcat.data.repository.GameResultRepository
import kotlinx.coroutines.launch

class ResultsViewModel(
    private val gameResultRepository: GameResultRepository,
    private val user: User?
) : ViewModel() {

    private val _gameResults = MutableLiveData<List<GameResult>>().apply {
        user?.let { u -> viewModelScope.launch {
                value = gameResultRepository.getGameResults(u.id)
            }
        }
    }
    val gameResults: LiveData<List<GameResult>> = _gameResults

    fun delete(gameResult: GameResult) {
            viewModelScope.launch {
                gameResultRepository.delete(gameResult)
                user?.let {
                    _gameResults.value = gameResultRepository.getGameResults(it.id)
                }
        }
    }

}