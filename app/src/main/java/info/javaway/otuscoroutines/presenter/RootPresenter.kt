package info.javaway.otuscoroutines.presenter

import info.javaway.otuscoroutines.adapter.CharactersRecyclerAdapter
import info.javaway.otuscoroutines.model.Character
import info.javaway.otuscoroutines.model.CharacterContainer
import info.javaway.otuscoroutines.model.CharacterRepository
import info.javaway.otuscoroutines.view.RootView
import info.javaway.otuscoroutines.view.presenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RootPresenter private constructor() : CharacterRepository.OnLoadCharactersListener,
    CharactersRecyclerAdapter.OnCharacterClickListener{

    private lateinit var page: String
    private lateinit var next: String
    private lateinit var prev: String
    var view: RootView? = null
    var loader:CharacterRepository = CharacterRepository.instance
    var items: List<Character>? = null
    var job = Job()
    val scope = CoroutineScope(Dispatchers.Main + job)

    fun takeView(rootView: RootView) {
        view = rootView
        if(items == null){
            getData()
        } else {
            setDataToView()
        }
    }

    fun getData(page:String = "1") {
        this.page = page
        view?.showLoading()
        scope.launch {
            loader.getCharacters(instance,page)
        }
    }

    fun onDestroy() {
        view = null
    }

    companion object {
        val instance = RootPresenter()
    }
    override fun onLoadSuccess(container: CharacterContainer) {
        view?.hideLoading()
        this.items = container.results
        prev = container.info.prev.split("=".toRegex()).last()
        next = container.info.next.split("=".toRegex()).last()
        setDataToView()
    }

    private fun setDataToView() {
        view?.showListItem(items!!)
        when (next) {
            "" -> view?.hideNext()
            else -> view?.showNext()
        }
        when (prev) {
            "" -> view?.hidePrev()
            else -> view?.showPrev()
        }
        view?.setPage(page)
    }

    override fun onLoadError(errorMessage: String) {
        view?.hideLoading()
        view?.showNetworkError(errorMessage)
    }
    override fun clickOnMovie(id: String) {
        view?.openMovieScreen(id)
    }

    fun clickNext() {
        getData(next)
    }

    fun clickPrev() {
        getData(prev)
    }
}