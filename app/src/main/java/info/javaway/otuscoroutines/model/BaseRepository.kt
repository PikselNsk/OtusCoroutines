package info.javaway.otuscoroutines.model

abstract class BaseRepository<Params, Result> {
    abstract suspend fun getCharacters(params: Params, page:String): CharacterRepository.Result?
}