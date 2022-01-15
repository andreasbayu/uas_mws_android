package com.andbayu.mws_uas.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.andbayu.mws_uas.R
import com.andbayu.mws_uas.data.api.ApiNewsInterface
import com.andbayu.mws_uas.data.model.DetailNewsModel
import com.andbayu.mws_uas.utils.ApiService
import com.andbayu.mws_uas.utils.Global.showToast
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvCategories: TextView
    private lateinit var tvContent: TextView
    private lateinit var imgThumb: ImageView

    private var KEY= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        showProgressBar(true)

        tvTitle = findViewById(R.id.tv_title)
        tvDate = findViewById(R.id.tv_date)
        tvCategories = findViewById(R.id.tv_categories)
        tvContent = findViewById(R.id.tv_content)
        imgThumb = findViewById(R.id.img_thumb)

        KEY = intent.getStringExtra("KEY").toString()

         loadContent()
    }

    private fun loadContent() {


        val apiService = ApiService.getService()?.create(ApiNewsInterface::class.java)
        val getDetailNews: Call<DetailNewsModel> = apiService?.getDetailNews(
            KEY
        )!!

        getDetailNews.enqueue(object : Callback<DetailNewsModel> {
            override fun onResponse(
                call: Call<DetailNewsModel>,
                response: Response<DetailNewsModel>
            ) {


                val responseBody = response.body()
                val responseCode = response.code()


                showProgressBar(false)

                if (responseCode != 200 && !response.isSuccessful) {
                    // failed
                    showToast(this@DetailNewsActivity, "Error: Status Code $responseCode")
                    return
                }

                if (responseBody?.status == null) return showToast(
                    this@DetailNewsActivity,
                    "Message: data empty"
                )

                val results = responseBody.results

                Glide
                    .with(this@DetailNewsActivity)
                    .load(results.thumb)
                    .centerCrop()
                    .into(imgThumb)

                var content = String()

                results.content.forEach { it ->
                    content += "$it \n\n"
                }

                val patterns1 = "(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)".toRegex()
                val patterns2 = "([?]+)[a-z].*".toRegex()


                tvTitle.text = results.title
                tvDate.text = results.date
                tvCategories.text = results.categories.toString()
                tvContent.text = content
                    .replace(patterns1, "")
                    .replace(patterns2, "")
            }

            override fun onFailure(call: Call<DetailNewsModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun showProgressBar(bool: Boolean) {
        val progressBar: ProgressBar = findViewById(R.id.pb_detail_news)
        if (bool) {
            progressBar.visibility = View.VISIBLE
            return
        }
        progressBar.visibility = View.GONE
    }
}