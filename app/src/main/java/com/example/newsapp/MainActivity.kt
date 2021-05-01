package com.example.newsapp

//import javax.swing.JColorChooser.showDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), newsItemCliked {
    private lateinit var mAdapter:newsListAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle.syncState()
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.topHeadlines -> {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.health -> {
                    val intent = Intent(this,Health::class.java)
                    startActivity(intent)
                    true
                }
                R.id.technology -> {
                    val intent = Intent(this,Technology::class.java)
                    startActivity(intent)
                    true
                }
                R.id.business -> {
                    val intent = Intent(this,Bussinus::class.java)
                    startActivity(intent)
                true
                }
                R.id.entertainment -> {
                    val intent = Intent(this,Entertainment::class.java)
                    startActivity(intent)
                true
                }
                R.id.sports -> {
                    val intent = Intent(this,Sports::class.java)
                    startActivity(intent)
                true
                }
                R.id.science -> {
                    val intent = Intent(this,Science::class.java)
                    startActivity(intent)
                true
                }
                else -> {
                    false
                }
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        fatchData()
        mAdapter = newsListAdapter(this)
        recyclerView.adapter = mAdapter

    }
    fun isInternetConnection(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    fun fatchData() {
        progressBar.visibility = View.VISIBLE
        val url = "https://mysterious-badlands-08561.herokuapp.com/"
        val jsonObjectRequest =  JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {response ->
                val newsJsonArray = response.getJSONArray("articles")
                val newsArray = ArrayList<news>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val newNews = news(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(newNews)
                }
                mAdapter.updateNews(newsArray)
                progressBar.visibility = View.GONE
            },
            Response.ErrorListener {
                progressBar.visibility = View.GONE
            }
        )

       MySingletonClass.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onClicked(item: news) {
        val url = item.url
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}