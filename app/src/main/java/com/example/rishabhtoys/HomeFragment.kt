package com.example.rishabhtoys

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    var fragmentSelected = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val homePagerAdapter = HomePagerAdapter(childFragmentManager)
        view_pager.adapter = homePagerAdapter
        tabs.setupWithViewPager(view_pager)

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            @SuppressLint("MissingSuperCall")
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                fragmentSelected = position
            }

        })

        floating_btn.setOnClickListener {
            val intent = Intent(activity, AddEntityActivity::class.java)
            if(fragmentSelected == 0){
                intent.putExtra(Entity_Type, Purchase)
            }else if(fragmentSelected == 1 ){
                intent.putExtra(Entity_Type, Sale)
            }
            startActivity(intent)
        }
    }
}
