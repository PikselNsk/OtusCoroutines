package info.javaway.otuscoroutines.presenter

import info.javaway.otuscoroutines.model.Character
import info.javaway.otuscoroutines.model.CharacterRepository
import info.javaway.otuscoroutines.view.CharacterScreenInterface
import kotlinx.coroutines.*

class CharacterScreenPresenter :CharacterRepository.OnLoadCharacterListener{

    var view: CharacterScreenInterface? = null
    var loader:CharacterRepository = CharacterRepository.instance
    var character: Character? = null
    var jobDescribeMovie = Job()
    val scopeDescribe = CoroutineScope(Dispatchers.Main + jobDescribeMovie)
    var idCharacter: kotlin.String? = null

    fun takeView(
        characterScreenInterface: CharacterScreenInterface,
        idMovie: kotlin.String
    ) {
        this.idCharacter = idMovie
        view = characterScreenInterface
        if(character == null){
            getData()
        } else {
            characterScreenInterface.showMovie(character!!)
        }
    }

    fun getData() {
        view?.showLoad()
        scopeDescribe.launch{
            loader.getCharacter(instance, idCharacter!!)
        }
    }

    fun onDestroy() {
        view = null
    }

    fun onLoadError(errorMessage: kotlin.String) {
        view?.hideLoad()
        view?.showLoadError()
    }

    override fun onLoadChar(character: Character?) {
        view?.showMovie(character)
    }

    companion object {
        val instance = CharacterScreenPresenter()
    }
}