package com.example.rishabhtoys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    private val HOME_FRAGMENT = "home_fragment"
    private val REPORT_FRAGMENT = "report_fragment"
    private val PROFILE_FRAGMENT = "profile_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        openFragment(HomeFragment(), HOME_FRAGMENT)
    }

    private fun openFragment(fragment:Fragment, tag:String){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container,fragment,tag)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }

    private val navigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.navigation_home -> {
                openFragment(HomeFragment(),HOME_FRAGMENT)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_report -> {
                openFragment(ReportFragment(),REPORT_FRAGMENT)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                openFragment(ProfileFragment(),PROFILE_FRAGMENT)
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false
        }
    }
}
