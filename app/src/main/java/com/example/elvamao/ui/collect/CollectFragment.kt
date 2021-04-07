package com.example.elvamao.ui.collect

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elvamao.R
import com.example.elvamao.data.RecipeData
import com.example.elvamao.databinding.FragmentCollectBinding
import com.example.elvamao.ui.BaseFragment
import com.example.elvamao.ui.widget.PullRefreshRecyclerView
import com.example.elvamao.ui.widget.RecipeAdapter
import com.example.elvamao.viewmodel.CollectViewModel

class CollectFragment : BaseFragment() {

    companion object{
        val TAG = CollectFragment::class.simpleName
    }
    private lateinit var mViewModel: CollectViewModel
    private lateinit var mDatabinding : FragmentCollectBinding
    private lateinit var mRecyclerView : PullRefreshRecyclerView
    private lateinit var mRecipeAdapter : RecipeAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_collect

    override fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(CollectViewModel::class.java)
        mViewModel.setContext(activity)
        mViewModel.getCollectedRecipesLiveData().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "initViewModel | recipeDatas : $it")
            if (it.isNullOrEmpty()) {
                showNoDataView()
            }else{
                updateRecyclerView(it)
            }
            updateRefreshView()
        })
    }

    private fun showNoDataView(){
        mDatabinding.tvNoData.visibility = View.VISIBLE
        mDatabinding.pullRefreshRecyclerView.visibility = View.GONE
    }

    private fun updateRecyclerView(list : MutableList<RecipeData>) {
        mRecipeAdapter.initAdapterData(list)
        mDatabinding.tvNoData.visibility = View.GONE
        mDatabinding.pullRefreshRecyclerView.visibility = View.VISIBLE
        mDatabinding.circularProgressIndicator.visibility = View.GONE
    }

    private fun updateRefreshView() {
        if(mRecyclerView.isRefreshing()) {
            mRecyclerView.setRefreshing(false)
        }
        mDatabinding.circularProgressIndicator.visibility = View.GONE
    }

    override fun initViews(inflater: LayoutInflater, container: ViewGroup?) {
        mDatabinding = FragmentCollectBinding.inflate(inflater, container, false)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        mRecipeAdapter = context?.let { RecipeAdapter(it) }!!

        val onScrollListener = object : RecyclerView.OnScrollListener() {
            var lastVisibleItemPos = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPos == mRecipeAdapter.itemCount - 1) {

                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItemPos = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
        }

        mRecyclerView = mDatabinding.pullRefreshRecyclerView
        mRecyclerView.setEnableLoadMore(true)
        mRecyclerView.setEnablePullRefresh(true)
        mRecyclerView.setLinearLayout()
        mRecyclerView.setPullLoadMoreRefreshListener(object : PullRefreshRecyclerView.PullRefreshLoadMoreListener{
            override fun onRefresh() {

            }

            override fun onLoadMore() {

            }
        })
        mRecyclerView.setAdapter(mRecipeAdapter)

    }

}