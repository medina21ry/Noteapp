
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.noteapp.ui.fragment.onboard.OnBoardFragment
import com.example.noteapp.ui.fragment.onboard.OnBoardViewPagerFragment
import com.example.noteapp.ui.fragment.onboard.OnBoardViewPagerFragment.Companion.ARG_ONBOARD_POSITION


class OnBoardViewPagerAdapter(
    fragment: OnBoardFragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int) = OnBoardViewPagerFragment().apply {
        arguments = Bundle().apply {
            putInt(ARG_ONBOARD_POSITION, position)
        }
    }
}