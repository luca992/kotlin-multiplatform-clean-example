package co.lucaspinazzola.example.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import co.lucaspinazzola.example.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view =  binding.root
        binding.giphyBtn.setOnClickListener{
            Navigation.findNavController(view).navigate(
                    MainFragmentDirections.actionMainFragmentToGiphyFragment()
            )
        }
        binding.rickAndMortyBtn.setOnClickListener{
            Navigation.findNavController(view).navigate(
                    MainFragmentDirections.actionMainFragmentToRickandmortyFragment()
            )
        }
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
