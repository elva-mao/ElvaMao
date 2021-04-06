package com.example.elvamao.ui.collect

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elvamao.R
import com.example.elvamao.databinding.FragmentCollectBinding
import com.example.elvamao.ui.BaseFragment
import com.example.elvamao.ui.widget.RecipeAdapter
import com.example.elvamao.viewmodel.RecipeListViewModel

class CollectFragment : BaseFragment() {

    companion object{
        val TAG = CollectFragment::class.simpleName
    }
    private lateinit var recipeListViewModel: RecipeListViewModel
    private lateinit var databinding : FragmentCollectBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var recipeAdapter : RecipeAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_collect

    override fun initViewModel() {
        recipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        recipeListViewModel.getRecipeListLiveData().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "initViewModel | recipeData : $it")
            recipeAdapter.initAdapterData(it)
        })
    }

    override fun initViews(inflater: LayoutInflater, container: ViewGroup?) {
        databinding = FragmentCollectBinding.inflate(inflater, container, false)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recipeAdapter = context?.let { RecipeAdapter(it) }!!

        val onScrollListener = object : RecyclerView.OnScrollListener() {
            var lastVisibleItemPos = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPos == recipeAdapter.itemCount - 1) {
                    // homeViewModel.loadMoreRecommendData()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItemPos = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
        }

        recyclerView = databinding.pullRefreshRecyclerView
//        recyclerView.setEnableLoadMore(true)
//        recyclerView.setEnablePullRefresh(true)
//        recyclerView.setLinearLayout()
//        recyclerView.setPullLoadMoreRefreshListener(object : PullRefreshRecyclerView.PullRefreshLoadMoreListener{
//            override fun onRefresh() {
//                homeViewModel.refreshRecommendData()
//            }
//
//            override fun onLoadMore() {
//                homeViewModel.loadMoreRecommendData()
//            }
//        })
        val linearLayoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = recipeAdapter
        recyclerView.addOnScrollListener(onScrollListener)
    }

}