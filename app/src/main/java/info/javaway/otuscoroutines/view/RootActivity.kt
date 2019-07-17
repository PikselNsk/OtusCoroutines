package info.javaway.otuscoroutines.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ldoublem.loadingviewlib.LVCircularCD
import info.javaway.otuscoroutines.R
import info.javaway.otuscoroutines.adapter.CharactersRecyclerAdapter
import info.javaway.otuscoroutines.model.Character
import info.javaway.otuscoroutines.presenter.RootPresenter
import kotlinx.android.synthetic.main.activity_main.*

lateinit var presenter: RootPresenter
const val MOVIE_ID = "info.javaway.otuscoroutines.MOVIE_ID"

class RootActivity : AppCompatActivity(), RootView {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mPrevIv: ImageView
    lateinit var mNextIv: ImageView
    lateinit var mLoadView: LVCircularCD
    lateinit var mLoadLL: LinearLayout
    lateinit var mPageTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = RootPresenter.instance

        mRecyclerView = recycler_view
        mPrevIv = prev_iv
        mNextIv = next_iv
        mLoadView = load_view as LVCircularCD
        mLoadLL = load_ll
        mPageTv = page_number_tv

        mLoadView.setViewColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mPrevIv.setOnClickListener { presenter.clickPrev() }
        mNextIv.setOnClickListener { presenter.clickNext() }

        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showLoading() {
        mLoadLL.visibility = View.VISIBLE
        mLoadView.startAnim()
    }

    override fun hideLoading() {
        mLoadLL.visibility = View.GONE
        mLoadView.stopAnim()
    }

    override fun showListItem(list: List<Character>) {
        mRecyclerView.adapter = CharactersRecyclerAdapter(layoutInflater, list, presenter)
    }

    override fun refreshListItem() {
    }

    override fun showNetworkError(messageError: String) {
    }

    override fun hideNetworkError() {
    }

    override fun setPage(page: String) {
        mPageTv.text = page
    }

    override fun hideNext() {
        mNextIv.visibility = View.INVISIBLE
    }

    override fun hidePrev() {
        mPrevIv.visibility = View.INVISIBLE

    }

    override fun showNext() {
        mNextIv.visibility = View.VISIBLE
    }

    override fun showPrev() {
        mPrevIv.visibility = View.VISIBLE
    }

    override fun openMovieScreen(id: String) {
        val intent = Intent(this, CharacterScreenActivity::class.java)
        intent.putExtra(MOVIE_ID, id)
        startActivity(intent)
    }
}
