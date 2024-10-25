package com.example.noteapp.ui.fragment

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
    ): View  {
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
                lottieAnimation.setAnimation(R.raw.anim1)
                txtNotes.text = "Notes"
                txtTitle.text = "Удобство"
                txtDescription.text = "Создавайте заметки в два клика! Записывайте мысли, идеи и важные задачи мгновенно."
            }
            1 -> {
                lottieAnimation.setAnimation(R.raw.anim2)
                txtNotes.text = "Notes"
                txtTitle.text = "Организация"
                txtDescription.text = "Организуйте заметки по папкам и тегам. Легко находите нужную информацию в любое время."
            }
            2 -> {
                lottieAnimation.setAnimation(R.raw.anim3)
                txtNotes.text = "Notes"
                txtTitle.text = "Синхронизация"
                txtDescription.text = "Синхронизация на всех устройствах. Доступ к записям в любое время и в любом месте."
            }
        }

    }

    companion object{
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}