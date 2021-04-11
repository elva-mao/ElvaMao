package com.example.elvamao.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
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
    private lateinit var mRootView : View
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
    private var mIsRefresh = false
    private var mIsLoadingMore = false

    /**
     * listeners
     */
    private lateinit var mPullLoadMoreListener: PullRefreshLoadMoreListener

    private fun initViews(context: Context) {
        mRootView = inflate(context, R.layout.pull_refresh_recycler_view, this)
        setUpRecyclerView()
        setUpSwipeRefreshView()
        setUpLoadMoreView()
    }

    private fun setUpLoadMoreView() {
        mFooterLayout = mRootView.findViewById(R.id.footerView)
        mLoadMoreLayout = mRootView.findViewById(R.id.loadMoreLayout)
        mLoadMoreTextView = mRootView.findViewById(R.id.loadMoreText)
    }

    private fun setUpSwipeRefreshView(){

            var mSwipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
                if(!mIsRefresh){
                    mIsRefresh = true
                    if(this::mPullLoadMoreListener.isInitialized){
                        mPullLoadMoreListener.onRefresh()
                    }
                }
            }
            mSwipeRefreshLayout = mRootView.findViewById(R.id.swipe_refresh_layout)
            mSwipeRefreshLayout.setOnRefreshListener(mSwipeRefreshListener)
            mSwipeRefreshLayout.visibility = VISIBLE

    }

    private fun setUpRecyclerView(){
        mRecyclerView = mRootView.findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.itemAnimator = DefaultItemAnimator()

        val onScrollListener = object : RecyclerView.OnScrollListener() {
            var lastVisibleItemPos = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPos + 1 == recyclerView.adapter?.itemCount) {
                    mPullLoadMoreListener.onLoadMore()
                    setIsLoadingMore(true)
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
    }


    fun isRefreshing() = mIsRefresh

    fun setRefreshing(isRefreshing : Boolean) {
        mIsRefresh = isRefreshing
        mSwipeRefreshLayout.isRefreshing = isRefreshing
    }

    fun setIsLoadingMore(isLoadingMore : Boolean){
        mIsLoadingMore = isLoadingMore
        mFooterLayout.visibility = if(mIsLoadingMore) VISIBLE else GONE
        mLoadMoreTextView.text = if(mIsLoadingMore) "is loading data" else "load data finished"
    }

    fun isLoadingMore() = mIsLoadingMore

    fun setEnablePullRefresh(enable : Boolean) {
        enablePullRefresh = enable
        if(this::mSwipeRefreshLayout.isInitialized) {
            mSwipeRefreshLayout.visibility = if (enablePullRefresh) VISIBLE else GONE
        }
    }

    fun setEnableLoadMore(enable: Boolean) {
        enableLoadMore = enable
        if(this::mFooterLayout.isInitialized){
            mFooterLayout.visibility = if(enableLoadMore) VISIBLE else GONE
        }
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
     * recyclerview scroll to top
     * trigger swipeRefreshLayout to refresh
     */
    fun scrollToTopAndRefresh() {
        mRecyclerView.scrollToPosition(0)
        mIsRefresh = true
        mSwipeRefreshLayout.post(Runnable {
            mSwipeRefreshLayout.isRefreshing = true
        })
    }

    /**
     * interface (pull refresh/load more)
     */
    interface PullRefreshLoadMoreListener{
        fun onRefresh()
        fun onLoadMore()
    }
}