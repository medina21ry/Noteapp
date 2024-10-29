
package com.example.noteapp.ui.fragment.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardViewPagerBinding

class OnBoardViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardViewPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() = with(binding) {
        when (requireArguments().getInt(ARG_ONBOARD_POSITION)) {
            0 -> {
                textView.text = "Очень удобный функционал"
                binding.lottie.setAnimation(R.raw.anim1)
            }
            1 -> {
                textView.text = "Быстрый, качественный продукт"
                binding.lottie.setAnimation(R.raw.anim2)
            }
            2 -> {
                textView.text = "Куча функций и интересных фишек"
                binding.lottie.setAnimation(R.raw.anim3)
            }
        }
    }
    companion object {
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}
