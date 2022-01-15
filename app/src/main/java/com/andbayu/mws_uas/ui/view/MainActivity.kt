 package com.andbayu.mws_uas.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andbayu.mws_uas.R
import com.andbayu.mws_uas.data.api.ApiNewsInterface
import com.andbayu.mws_uas.data.model.NewsModel
import com.andbayu.mws_uas.ui.adapter.NewsAdapter
import com.andbayu.mws_uas.utils.ApiService
import com.andbayu.mws_uas.utils.Global.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

 class MainActivity : AppCompatActivity() {

     private lateinit var rvNews: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = ""

        rvNews = findViewById(R.id.rv_news)

        showRecycler()
    }

     override fun onCreateOptionsMenu(menu: Menu): Boolean {
         menuInflater.inflate(R.menu.search_bar_menu, menu)

         val myActionMenu: MenuItem = menu.findItem(R.id.action_search)
         val searchView: SearchView = myActionMenu.actionView as SearchView

         searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
             override fun onQueryTextSubmit(query: String?): Boolean {
                 if (TextUtils.isEmpty(query)) {
                     showToast(this@MainActivity, "Kosong")
                     return false
                 } else {
                     if (query != null) {
                         search(query)
                         return true
                     }
                     showToast(this@MainActivity, "Kosong")
                     return false

                 }
             }

             override fun onQueryTextChange(newText: String?): Boolean {
                 return false
             }

         })

         return super.onCreateOptionsMenu(menu)
     }

     private fun showRecycler() {
         showProgressBar(true)

         val apiService = ApiService.getService()?.create(ApiNewsInterface::class.java)
         val getNews: Call<ArrayList<NewsModel>> = apiService?.getGamesNews(1)!!

         getNews.enqueue(object : Callback<ArrayList<NewsModel>>{
             override fun onResponse(
                 call: Call<ArrayList<NewsModel>>,
                 response: Response<ArrayList<NewsModel>>
             ) {
                 val responseBody = response.body()
                 val responseCode = response.code()

                 showProgressBar(false)

                 if (responseCode != 200 && !response.isSuccessful) {
                     // failed
                     showToast(this@MainActivity,"Error: Status Code $responseCode")
                     return
                 }

                 if (responseBody == null) return showToast(this@MainActivity,"Message: data empty")

                 val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                 rvNews.apply {
                     layoutManager = linearLayoutManager
                     adapter = NewsAdapter(responseBody)
                     setHasFixedSize(true)
                 }
             }

             override fun onFailure(call: Call<ArrayList<NewsModel>>, t: Throwable) {
                 Log.e("ERROR", "Message: $t")
             }

         })
     }

     private fun search(query: String) {
         showProgressBar(true)

         val apiService = ApiService.getService()?.create(ApiNewsInterface::class.java)
         val getNews: Call<ArrayList<NewsModel>> = apiService?.searchNews(query)!!

         getNews.enqueue(object : Callback<ArrayList<NewsModel>>{
             override fun onResponse(
                 call: Call<ArrayList<NewsModel>>,
                 response: Response<ArrayList<NewsModel>>
             ) {
                 val responseBody = response.body()
                 val responseCode = response.code()

                 showProgressBar(false)

                 if (responseCode != 200 && !response.isSuccessful) {
                     // failed
                     showToast(this@MainActivity,"Error: Status Code $responseCode")
                     return
                 }

                 if (responseBody == null) return showToast(this@MainActivity,"Message: data empty")

                 val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                 rvNews.apply {
                     layoutManager = linearLayoutManager
                     adapter = NewsAdapter(responseBody)
                     setHasFixedSize(true)
                 }
             }

             override fun onFailure(call: Call<ArrayList<NewsModel>>, t: Throwable) {
                 Log.e("ERROR", "Message: $t")
             }

         })
     }

     fun showProgressBar(bool: Boolean) {
         val progressBar: ProgressBar = findViewById(R.id.pb_news)
         if (bool) {
             progressBar.visibility = View.VISIBLE
             return
         }
         progressBar.visibility = View.GONE
     }
}