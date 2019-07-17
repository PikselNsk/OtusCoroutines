package info.javaway.otuscoroutines.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import info.javaway.otuscoroutines.R
import info.javaway.otuscoroutines.model.Character
import info.javaway.otuscoroutines.presenter.CharacterScreenPresenter
import kotlinx.android.synthetic.main.activity_movie_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.text.SimpleDateFormat

class CharacterScreenActivity : AppCompatActivity(), CharacterScreenInterface {

    lateinit var presenter: CharacterScreenPresenter
    lateinit var titleTv: TextView
    lateinit var descriptionTv: TextView
    lateinit var directorTv: TextView
    lateinit var producerTv: TextView
    lateinit var release_dateTv: TextView
    lateinit var rt_scoreTv: TextView
    lateinit var imagesRv: RecyclerView
    lateinit var charIv: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_screen)
        val movieId = intent.getStringExtra(MOVIE_ID)

        titleTv = name_char_tv
        descriptionTv = description_movie_tv
        directorTv = director_movie_tv
        producerTv = producer_movie_tv
        release_dateTv = release_date_movie_tv
        rt_scoreTv = rt_score_movie_tv
        imagesRv = images_rv
        charIv = char_iv
        presenter = CharacterScreenPresenter.instance
        presenter.takeView(this, movieId)

        imagesRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showLoad() {

    }

    override fun hideLoad() {

    }

    override fun showLoadError() {

    }

    @SuppressLint("SimpleDateFormat")
    override fun showMovie(character: Character?) {
        if (character != null) {
            titleTv.text = character.name
            descriptionTv.text = character.species
            directorTv.text = character.created
            producerTv.text = character.gender
            release_dateTv.text = character.status
            rt_scoreTv.text = character.type
            val dateCreate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").parse(character.created)
            directorTv.text = SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateCreate)

//            Log.d("M_CharacterScreenActivity", " Date(character.created) ${ Date(character.created)}")

            val job = SupervisorJob()                               // (1)
            val scope = CoroutineScope(Dispatchers.Default + job)
            Picasso
                .get()
                .load(character?.image)
                .placeholder(R.drawable.placeholder)
                .into(charIv)
        }
    }

}
