package com.example.noteapp.ui.fragment

import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardViewPagerAdapter(fragment: androidx.fragment.app.Fragment):FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int) = OnBoardViewPagerFragment().apply {
        arguments = Bundle().apply{
            putInt(OnBoardViewPagerFragment.ARG_ONBOARD_POSITION, position)
        }
    }

}