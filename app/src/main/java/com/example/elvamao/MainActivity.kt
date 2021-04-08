package com.example.elvamao

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.elvamao.ui.collect.CollectFragment
import com.example.elvamao.ui.notifications.NotificationsFragment
import com.example.elvamao.ui.recommend.RecommendFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var mNavView : BottomNavigationView
    private var mRecommendFragment : RecommendFragment = RecommendFragment()
    private var mCollectFragment: CollectFragment = CollectFragment()
    private var mNotificationFragment: NotificationsFragment = NotificationsFragment()

    private var mActiveFragment : Fragment = mRecommendFragment
    private var mFragmentManager : FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setContentView(R.layout.activity_main)
        initViews()
    }


    private fun initViews(){
        mFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, mRecommendFragment , getString(R.string.title_Recommend)).show(mRecommendFragment)
            add(R.id.fragment_container, mCollectFragment, getString(R.string.title_collect)).hide(mCollectFragment)
            add(R.id.fragment_container, mNotificationFragment, getString(R.string.title_notifications)).hide(mNotificationFragment)
        }.commit()

        mNavView= findViewById(R.id.nav_view)
        mNavView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_recommend -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(mRecommendFragment).commit()
                    mActiveFragment = mRecommendFragment
                    true
                }
                R.id.navigation_collect -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(mCollectFragment).commit()
                    mActiveFragment = mCollectFragment
                    true
                }
                R.id.navigation_notifications -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(mNotificationFragment).commit()
                    mActiveFragment = mNotificationFragment
                    true
                }
                else -> false
            }
        }
    }

}