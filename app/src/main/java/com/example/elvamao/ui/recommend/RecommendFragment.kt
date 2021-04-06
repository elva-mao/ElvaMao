package com.example.elvamao.ui.recommend

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.elvamao.databinding.FragmentRecommendBinding
import com.example.elvamao.ui.widget.PullRefreshRecyclerView
import com.example.elvamao.ui.widget.RecipeAdapter
import com.example.elvamao.viewmodel.RecipeListViewModel

class RecommendFragment : Fragment() {

    private lateinit var recipeListViewModel: RecipeListViewModel
    private lateinit var databinding: FragmentRecommendBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recyclerView : PullRefreshRecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentRecommendBinding.inflate(LayoutInflater.from(context), container, false)
        setUpRecyclerView()
        setupViewModel()
        return databinding.root
    }

    private fun setUpRecyclerView() {
        recipeAdapter = activity?.let { RecipeAdapter(it) }!!
        recyclerView = databinding.pullRefreshRecyclerView
        recyclerView.setEnableLoadMore(true)
        recyclerView.setEnablePullRefresh(true)
        recyclerView.setGridLayout(2)
        recyclerView.setPullLoadMoreRefreshListener(object : PullRefreshRecyclerView.PullRefreshLoadMoreListener{
            override fun onRefresh() {
                recipeListViewModel.refreshData()
            }

            override fun onLoadMore() {
//                recipeViewModel.loadMoreRecommendData()
            }
        })

        recyclerView.setAdapter(recipeAdapter)

    }

    private fun setupViewModel() {
        recipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        recipeListViewModel.getRecipeListLiveData().observe(viewLifecycleOwner, Observer {
            recipeAdapter.initAdapterData(it)
            databinding.circularProgressIndicator.visibility = View.GONE
            Log.d(RecommendFragment::class.java.simpleName, "setupViewModel |  data size ${it.size}")
            updateRefreshView(it.size)
        })
    }

    private fun updateRefreshView(dataSize : Int) {
        if(recyclerView.isRefreshing()) {
            recyclerView.setRefreshing(false)
            var toast = Toast.makeText(activity, "Recommend $dataSize recipes for you", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 100, 100)
            toast.show()
        }

    }}