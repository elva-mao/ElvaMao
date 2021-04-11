package com.example.elvamao.ui.collect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elvamao.MainActivity
import com.example.elvamao.R
import com.example.elvamao.data.RecipeData
import com.example.elvamao.databinding.FragmentCollectBinding
import com.example.elvamao.listeners.IUserActionListener
import com.example.elvamao.ui.widget.PullRefreshRecyclerView
import com.example.elvamao.ui.widget.RecipeAdapter
import com.example.elvamao.viewmodel.RecipeViewModel

/**
 * The recipe datas in collect feeds are loaded from database
 */
class CollectFragment : Fragment(), IUserActionListener{

    companion object{
        val TAG = CollectFragment::class.simpleName
    }
    private lateinit var mViewModel: RecipeViewModel
    private lateinit var mDatabinding : FragmentCollectBinding
    private lateinit var mRecyclerView : PullRefreshRecyclerView
    private lateinit var mRecipeAdapter : RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDatabinding = FragmentCollectBinding.inflate(LayoutInflater.from(context), container, false)
        setupRecyclerView()
        initViewModel()
        return mDatabinding.root
    }

    private fun initViewModel() {
        mViewModel = activity?.let { ViewModelProvider(it).get(RecipeViewModel::class.java) }!!
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
        mDatabinding.btnJumpToRecommendFeeds.visibility = View.VISIBLE
        mDatabinding.btnJumpToRecommendFeeds.setOnClickListener(View.OnClickListener {
            if(activity is MainActivity){
                (activity as MainActivity).switchTab(R.id.navigation_recommend)
            }
        })
        mDatabinding.pullRefreshRecyclerView.visibility = View.GONE
        mDatabinding.circularProgressIndicator.visibility = View.GONE
    }

    private fun updateRecyclerView(list : MutableList<RecipeData>) {
        mRecipeAdapter.initAdapterData(list)
        mDatabinding.btnJumpToRecommendFeeds.visibility = View.GONE
        mDatabinding.pullRefreshRecyclerView.visibility = View.VISIBLE
        mDatabinding.circularProgressIndicator.visibility = View.GONE
    }

    private fun updateRefreshView() {
        if(mRecyclerView.isRefreshing()) {
            mRecyclerView.setRefreshing(false)
        }
        mDatabinding.circularProgressIndicator.visibility = View.GONE
    }


    private fun setupRecyclerView() {
        mRecipeAdapter = context?.let {
            RecipeAdapter(it).apply { setUserInteractionListener(this@CollectFragment) }
        }!!

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
        mRecyclerView.setGridLayout(2)
        mRecyclerView.setPullLoadMoreRefreshListener(object : PullRefreshRecyclerView.PullRefreshLoadMoreListener{
            override fun onRefresh() {
                mViewModel.getCollectedRecipesLiveData()
            }

            override fun onLoadMore() {

            }
        })
        mRecyclerView.setAdapter(mRecipeAdapter)

    }

    override fun onClickLike() {
        //async network request to update data on server
    }

    override fun onClickCollect(recipeData: RecipeData) {
        //save it to local db
        mViewModel.saveCollectedRecipeToDB(recipeData)
    }

    override fun onClickShare() {
        Toast.makeText(activity, activity?.resources?.getString(R.string.share_recipe_to_social_platforms), Toast.LENGTH_LONG).show()
        //todo later integrate the share sdk
    }

    override fun onClickTabToRefresh() {
        mRecyclerView.scrollToTopAndRefresh()
        mViewModel.refreshRecommendRecipes()
    }

}