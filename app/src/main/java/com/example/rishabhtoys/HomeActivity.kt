package com.example.rishabhtoys

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity() {

    private val HOME_FRAGMENT = "home_fragment"
    private val REPORT_FRAGMENT = "report_fragment"
    private val PROFILE_FRAGMENT = "profile_fragment"

    private val homeFragmentObject: HomeFragment = HomeFragment()
    private val reportFragmentObject: ReportFragment = ReportFragment()
    private val profileFragmentObject: ProfileFragment = ProfileFragment()
    private lateinit var menuItem: MenuItem


    private lateinit var currentlyOpenFragment: Fragment
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        // openFragment(HomeFragment(), HOME_FRAGMENT)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        openFragments()
    }

    /*private fun openFragment(fragment: Fragment, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment, tag)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }*/


    private fun openFragments() {

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, profileFragmentObject, PROFILE_FRAGMENT)
            .hide(profileFragmentObject)
            .addToBackStack(PROFILE_FRAGMENT)
            .commit()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, reportFragmentObject, REPORT_FRAGMENT)
            .hide(reportFragmentObject)
            .addToBackStack(REPORT_FRAGMENT)
            .commit()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, homeFragmentObject, HOME_FRAGMENT)
            .show(homeFragmentObject)
            .addToBackStack(HOME_FRAGMENT)
            .commit()

        currentlyOpenFragment = homeFragmentObject
    }


    private val navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {

            menuItem = it

            when (it.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(currentlyOpenFragment)
                        .show(homeFragmentObject)
                        .commit()
                    currentlyOpenFragment = homeFragmentObject
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_report -> {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(currentlyOpenFragment)
                        .show(reportFragmentObject)
                        .commit()
                    currentlyOpenFragment = reportFragmentObject
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(currentlyOpenFragment)
                        .show(profileFragmentObject)
                        .commit()
                    currentlyOpenFragment = profileFragmentObject
                    return@OnNavigationItemSelectedListener true
                }
                else -> return@OnNavigationItemSelectedListener false
            }
        }

    override fun onBackPressed() {

        when (currentlyOpenFragment) {
            homeFragmentObject -> {
                finish()
            }

            reportFragmentObject -> {
                supportFragmentManager
                    .beginTransaction()
                    .hide(reportFragmentObject)
                    .show(homeFragmentObject)
                    .commit()

                currentlyOpenFragment = homeFragmentObject
                bottomNavigationView.selectedItemId = R.id.navigation_home
            }

            profileFragmentObject -> {
                supportFragmentManager
                    .beginTransaction()
                    .hide(profileFragmentObject)
                    .show(homeFragmentObject)
                    .commit()

                currentlyOpenFragment = homeFragmentObject
                bottomNavigationView.selectedItemId = R.id.navigation_home
            }
        }

    }
}
