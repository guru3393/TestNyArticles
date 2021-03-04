package com.guru.testnyarticles

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.guru.testnyarticles.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

import java.lang.Boolean.FALSE

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val viewModel = makeApiCall()

        setupBinding(viewModel)

    }

    fun setupBinding(viewModel: MainActivityViewModel) {

        val activityMainBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.setVariable(BR.viewModel,  viewModel)
        activityMainBinding.executePendingBindings()

        recyclerView.apply {

            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration  = DividerItemDecoration(this@MainActivity, VERTICAL)
            addItemDecoration(decoration)
        }
        horizontalrecyclerView.apply {

            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL,FALSE)
        }

    }

    fun makeApiCall(): MainActivityViewModel {
       val viewModel =  ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getRecyclerListDataObserver().observe(this, Observer<ArticleResponse>{
            progressbar.visibility = GONE
            if(it != null) {
                //update the adapter
                viewModel.setAdapterData(it.response.docs)
            } else {
                Toast.makeText(this@MainActivity, "Error in fetching data", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.makeAPICall("")
        viewModel.getHorizontalRecyclerListDataObserver().observe(this, Observer<HorizontalArticleResponse>{
            progressbar.visibility = GONE
            if(it != null) {
                //update the adapter
                viewModel.setHorizontalAdapterData(it.results)
                horizontalrecyclerView.post { // Call smooth scroll
                    horizontalrecyclerView.smoothScrollToPosition(horizontalrecyclerView.adapter!!.itemCount - 1)
                }
            } else {
                Toast.makeText(this@MainActivity, "Error in fetching data", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.makeHorizontalAPICall()
        return viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val viewModel =  ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView!!.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                viewModel.makeAPICall(query)
                if (!searchView!!.isIconified) {
                    searchView!!.isIconified = true
                }
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                viewModel.makeAPICall(s)
                return false
            }
        })


        return super.onCreateOptionsMenu(menu)
    }

}