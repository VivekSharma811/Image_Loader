package com.hypheno.imageloader.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.hypheno.imageloader.R
import com.hypheno.imageloader.view.adapter.ImageAdapter
import com.hypheno.imageloader.viewmodel.MainViewModel
import com.hypheno.imageloader.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory by instance<MainViewModelFactory>()
    private lateinit var viewModel : MainViewModel
    private val imageAdapter = ImageAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.options -> {
                Toast.makeText(context, "Working", Toast.LENGTH_LONG).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)

        imageList.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = imageAdapter
        }

        bindUi()
    }

    fun bindUi() = launch {
        viewModel.getPhotos("dogs")
        viewModel.photosList.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer

            imageAdapter.updateImages(it)
            imageList.visibility = View.VISIBLE
        })
    }
}