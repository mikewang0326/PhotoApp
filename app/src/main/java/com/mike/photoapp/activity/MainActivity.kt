package com.mike.photoapp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.mike.photoapp.R
import com.mike.photoapp.adapter.MainAdapter
import com.mike.photoapp.api.PhotoRetriever
import com.mike.photoapp.models.Photo
import com.mike.photoapp.models.PhotoList
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var photos: List<Photo> ? = null;
    var mainAdapter: MainAdapter? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recycleView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        var retriver = PhotoRetriever()

        var callback = object: Callback<PhotoList> {
            override fun onResponse(call: Call<PhotoList>?, response: Response<PhotoList>?) {
                response?.isSuccessful.let {
                    this@MainActivity.photos = response?.body()?.hits
                    mainAdapter = MainAdapter(this@MainActivity.photos!!, this@MainActivity)
                    recyclerView.adapter = mainAdapter
                }
            }

            override fun onFailure(call: Call<PhotoList>?, t: Throwable?) {
                Log.e("MainActivity", "Problem PhotoAPI", t)
            }
        }

        retriver.getPhotos(callback)
    }

    override fun onClick(view: View?) {
        Toast.makeText(this@MainActivity, "Image was clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)
        val holder = view?.tag as MainAdapter.PhotoViewHolder
        intent.putExtra(DetailActivity.PHOTO, mainAdapter?.getPhoto(holder.adapterPosition))
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
