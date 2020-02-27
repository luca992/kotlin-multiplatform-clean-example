package co.lucaspinazzola.example.ui.giphy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.co.lucaspinazzola.example.ui.gifs.GiphyViewModel
import co.lucaspinazzola.example.databinding.FragmentGiphyBinding
import co.lucaspinazzola.example.ui.base.BaseFragment
import dev.icerock.moko.mvvm.livedata.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GiphyFragment constructor(private val factory: ViewModelProvider.Factory) : BaseFragment(){

    private lateinit var viewModel: GiphyViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentGiphyBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_giphy, container, false)
        viewModel = ViewModelProvider(this, factory).get(GiphyViewModel::class.java)
        val view: View = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner = this
        //binding.recyclerView.adapter = ConversationsAdapter()

        lifecycleScope.launch {
            viewModel.error.asFlow().collect {
                showError(it)
            }
        }

        return view
    }

}
