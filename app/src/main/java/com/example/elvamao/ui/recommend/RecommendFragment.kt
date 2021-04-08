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
import com.example.elvamao.R
import com.example.elvamao.data.RecipeData
import com.example.elvamao.databinding.FragmentRecommendBinding
import com.example.elvamao.listeners.IUserActionListener
import com.example.elvamao.ui.widget.PullRefreshRecyclerView
import com.example.elvamao.ui.widget.RecipeAdapter
import com.example.elvamao.viewmodel.RecommendViewModel
/**
 * The recipe datas in recommend feeds are loaded from server
 */
class RecommendFragment : Fragment(), IUserActionListener {

    private lateinit var mRecommendViewModel: RecommendViewModel
    private lateinit var mDatabinding: FragmentRecommendBinding
    private lateinit var mRecipeAdapter: RecipeAdapter
    private lateinit var mRecyclerView : PullRefreshRecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mDatabinding = FragmentRecommendBinding.inflate(LayoutInflater.from(context), container, false)
        setUpRecyclerView()
        setupViewModel()
        return mDatabinding.root
    }

    private fun setUpRecyclerView() {
        mRecipeAdapter = activity?.let {
            RecipeAdapter(it).apply { setUserInteractionListener(this@RecommendFragment) }
        }!!
        mRecyclerView = mDatabinding.pullRefreshRecyclerView
        mRecyclerView.setEnableLoadMore(true)
        mRecyclerView.setEnablePullRefresh(true)
        mRecyclerView.setGridLayout(2)
        mRecyclerView.setPullLoadMoreRefreshListener(object : PullRefreshRecyclerView.PullRefreshLoadMoreListener{
            override fun onRefresh() {
                mRecommendViewModel.refreshData()
            }

            override fun onLoadMore() {
                mRecommendViewModel.loadMoreRecipes()
                mRecyclerView.setIsLoadingMore(true)
            }
        })
        mRecyclerView.setAdapter(mRecipeAdapter)
    }

    private fun setupViewModel() {
        mRecommendViewModel = ViewModelProvider(this).get(RecommendViewModel::class.java)
        mRecommendViewModel.setContext(activity)
        mRecommendViewModel.getRecipeListLiveData().observe(viewLifecycleOwner, Observer {
            mRecipeAdapter.initAdapterData(it)
            mDatabinding.circularProgressIndicator.visibility = View.GONE
            Log.d(RecommendFragment::class.java.simpleName, "setupViewModel |  data size ${it.size}")
            updateRefreshView(it.size)
            updateLoadMoreView()
        })
    }
    /**
     * handle load more callback
     * hide the loading view
     */
    private fun updateLoadMoreView(){
        if(mRecyclerView.isLoadingMore()) {
            mRecyclerView.setIsLoadingMore(false)
        }
    }

    /**
     * handle refresh callback
     * hide the refreshing view and show toast to user
     */
    private fun updateRefreshView(dataSize : Int) {
        if(mRecyclerView.isRefreshing()) {
            mRecyclerView.setRefreshing(false)
            var toast = Toast.makeText(activity, "Recommend $dataSize recipes for you", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 100, 100)
            toast.show()
        }
    }

    override fun onClickLike() {
        //async network request to update data on server
    }

    override fun onClickCollect(recipeData: RecipeData) {
        //save it to local db
       mRecommendViewModel.saveRecipeDataToDB(recipeData)
    }

    override fun onClickShare() {
        Toast.makeText(activity, activity?.resources?.getString(R.string.share_recipe_to_social_platforms), Toast.LENGTH_LONG).show()
        //todo later integrate the share sdk
    }
}