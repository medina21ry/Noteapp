
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentDetailNoteBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailNoteFragment : Fragment() {

    private lateinit var binding: FragmentDetailNoteBinding
    private var colorResource: Int = R.drawable.style

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        displayCurrentDateTime()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpListeners() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnAdd.visibility =
                    if (binding.etTitle.text.isNotEmpty() || binding.etDescription.text.isNotEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etTitle.addTextChangedListener(textWatcher)
        binding.etDescription.addTextChangedListener(textWatcher)

        binding.btnAdd.setOnClickListener {
            val etTitle = binding.etTitle.text.toString()
            val etDescription = binding.etDescription.text.toString()
            val itemDate = binding.date.text.toString()
            val itemTime = binding.time.text.toString()
            App().getInstance()?.noteDao()
                ?.insertNote(
                    NoteModel(
                        title = etTitle,
                        description = etDescription,
                        date = itemDate,
                        time = itemTime,
                        color = colorResource.toString()
                    )
                )
            findNavController().navigateUp()
        }

        binding.returnBtn.setOnClickListener {
            findNavController().navigate(R.id.action_detailNoteFragment_to_noteFragment)
        }
        binding.btnAdd.visibility = View.GONE

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            colorResource = when (checkedId) {
                binding.rb1.id -> R.drawable.style
                binding.rb2.id -> R.drawable.style_white
                binding.rb3.id -> R.drawable.style_red
                else -> R.drawable.style
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayCurrentDateTime() {
        val currentDateTime = LocalDateTime.now()
        val formatterDate = DateTimeFormatter.ofPattern("dd MMMM")
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val formattedDate = currentDateTime.format(formatterDate)
        val formattedTime = currentDateTime.format(formatterTime)
        binding.date.text = formattedDate
        binding.time.text = formattedTime
    }
}
