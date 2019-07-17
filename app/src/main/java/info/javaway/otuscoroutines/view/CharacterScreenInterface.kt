package info.javaway.otuscoroutines.view

import info.javaway.otuscoroutines.model.Character

interface CharacterScreenInterface {
    fun showLoad()
    fun hideLoad()
    fun showLoadError()
    fun showMovie(character: Character?)
}