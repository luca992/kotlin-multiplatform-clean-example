package co.lucaspinazzola.example.ui.rickandmorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.databinding.FragmentRickandmortyBinding
import co.lucaspinazzola.example.ui.base.BaseFragment
import co.lucaspinazzola.example.ui.utils.EndlessRecyclerGridLayoutManager
import co.lucaspinazzola.example.ui.utils.ImgAdapter
import co.lucaspinazzola.example.ui.utils.OnLastItemVisible
import co.lucaspinazzola.example.ui.utils.Visibility
import dev.icerock.moko.mvvm.livedata.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RickAndMortyCharactersFragment constructor(private val factory: ViewModelProvider.Factory) : BaseFragment(), OnLastItemVisible{

    private lateinit var viewModel: RickAndMortyCharactersViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentRickandmortyBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_rickandmorty, container, false)
        viewModel = ViewModelProvider(this, factory).get(RickAndMortyCharactersViewModel::class.java)
        mainLoadingIndicatorVisibilityLd = viewModel.loadingIndicatorVisibility.ld()
        val view: View = binding.root
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = ImgAdapter()
        val layoutManager = (binding.recyclerView.layoutManager as EndlessRecyclerGridLayoutManager)
        layoutManager.setOnLastItemVisible(this)
        lifecycleScope.launch {
            viewModel.error.asFlow().collect {
                showError(it)
            }
        }
        lifecycleScope.launch {
            viewModel.loadingIndicatorVisibility.asFlow().collect{
                if (it == Visibility.GONE){
                    layoutManager.resetLoadMoreState()
                }
            }
        }
        return view
    }

    override fun onLastItemVisible() {
        viewModel.loadNextPage()
    }

    override fun onLoadMoreComplete() {
    }

}
