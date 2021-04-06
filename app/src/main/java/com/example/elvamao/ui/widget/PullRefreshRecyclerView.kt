package com.example.elvamao.ui.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.elvamao.R
import com.example.elvamao.databinding.PullRefreshRecyclerViewBinding

class PullRefreshRecyclerView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
)  : FrameLayout(context, attrs, defStyle)  {
    init {
        initViews(context)
    }

    companion object{
        val TAG = PullRefreshRecyclerView::class.simpleName
    }

    /**
     * views
     */
    private lateinit var mDataBinding: PullRefreshRecyclerViewBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mFooterLayout: View
    private lateinit var mLoadMoreLayout: LinearLayout
    private lateinit var mLoadMoreTextView: TextView

    /**
     * pull refresh and load more switch
     */
    private var enablePullRefresh: Boolean = false
    private var enableLoadMore: Boolean = false

    /**
     * recyclerview state flags
     */
    private var hasMore = false
    private var isRefresh = false
    private var isLoadMore = false

    /**
     * listeners
     */
    private lateinit var mPullLoadMoreListener: PullRefreshLoadMoreListener

    private fun initViews(context: Context) {
        val root = inflate(context, R.layout.pull_refresh_recycler_view, this)
        mRecyclerView = root.findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.itemAnimator = DefaultItemAnimator()

        val onScrollListener = object : RecyclerView.OnScrollListener() {
            var lastVisibleItemPos = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPos + 1 == recyclerView.adapter?.itemCount) {
                    mPullLoadMoreListener.onLoadMore()
                    Log.d(TAG, "onScrollStateChanged | show footer or load more data if have ")
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItemPos = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                Log.d(TAG, "onScrolled | lastVisibleItemPos $lastVisibleItemPos ")
            }
        }
        mRecyclerView.addOnScrollListener(onScrollListener)

        var mSwipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
            if(!isRefresh){
                isRefresh = true
                if(this::mPullLoadMoreListener.isInitialized){
                    mPullLoadMoreListener.onRefresh()
                }
            }
        }
        mSwipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout)
        mSwipeRefreshLayout.setOnRefreshListener(mSwipeRefreshListener)

        mFooterLayout = root.findViewById(R.id.footerView)
        mLoadMoreLayout = root.findViewById(R.id.loadMoreLayout)
        mLoadMoreTextView = root.findViewById(R.id.loadMoreText)
//        mDataBinding = PullRefreshRecyclerViewBinding.inflate(LayoutInflater.from(context), this, false)
//        mRecyclerView = mDataBinding.recyclerView
//        mRecyclerView.setHasFixedSize(true)
//        mRecyclerView.itemAnimator = DefaultItemAnimator()
//
//        mSwipeRefreshLayout = mDataBinding.swipeRefreshLayout
//        mSwipeRefreshLayout.setOnRefreshListener(mSwipeRefreshListener)
//
//        mFooterLayout = mDataBinding.footerView.root as View
//        mLoadMoreLayout = mDataBinding.footerView.loadMoreLayout
//        mLoadMoreTextView = mDataBinding.footerView.loadMoreText
    }

    fun isRefreshing() = isRefresh

    fun setRefreshing(isRefreshing : Boolean) {
        isRefresh = isRefreshing
        mSwipeRefreshLayout.isRefreshing = isRefreshing
    }

    fun setEnablePullRefresh(enable : Boolean) {
        enablePullRefresh = enable
    }

    fun setEnableLoadMore(enable: Boolean) {
        enableLoadMore = enable
    }

    fun setAdapter(adapter: RecipeAdapter) {
        mRecyclerView.adapter = adapter
    }

    fun setPullLoadMoreRefreshListener(listener: PullRefreshLoadMoreListener) {
        mPullLoadMoreListener = listener
    }

    /**
     * LinearLayoutManager
     */
    fun setLinearLayout() {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = linearLayoutManager
    }

    /**
     * GridLayoutManager
     */
    fun setGridLayout(spanCount: Int) {
        val gridLayoutManager = GridLayoutManager(context, spanCount)
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        mRecyclerView.layoutManager = gridLayoutManager
    }


    /**
     * StaggeredGridLayoutManager
     */
    fun setStaggeredGridLayout(spanCount: Int) {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL)
        mRecyclerView.layoutManager = staggeredGridLayoutManager
    }

    /**
     * interface (pull refresh/load more)
     */
    interface PullRefreshLoadMoreListener{
        fun onRefresh()
        fun onLoadMore()
    }
}