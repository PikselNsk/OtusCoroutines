package info.javaway.otuscoroutines.view

import info.javaway.otuscoroutines.model.Character

interface RootView {
    fun showLoading()
    fun hideLoading()
    fun showListItem(list:List<Character>)
    fun refreshListItem()
    fun showNetworkError(messageError: String)
    fun hideNetworkError()
    fun openMovieScreen(id: String)
    fun showNext()
    fun showPrev()
    fun hidePrev()
    fun hideNext()
    fun setPage(page: String)
}