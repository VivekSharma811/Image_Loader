package com.hypheno.imageloader.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.hypheno.imageloader.R
import com.hypheno.imageloader.model.provider.PreferenceProvider
import com.hypheno.imageloader.view.adapter.ImageAdapter
import com.hypheno.imageloader.viewmodel.MainViewModel
import com.hypheno.imageloader.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainFragment() : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory by instance<MainViewModelFactory>()
    private lateinit var viewModel : MainViewModel
    private val imageAdapter = ImageAdapter(arrayListOf())
    private lateinit var searchView : SearchView

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)

        textview.visibility = View.VISIBLE
        imageList.visibility = View.GONE
        progressBar.visibility = View.GONE

        imageList.apply {
            layoutManager = getSpanCount()?.let { GridLayoutManager(context, it) }
            adapter = imageAdapter
        }
        populateUi()
    }

    fun populateUi() = launch {
        viewModel.photosList.observe(viewLifecycleOwner, Observer {
            if(it == null) {
                return@Observer
            }

            if(it.size == 0) {
                progressBar.visibility = View.GONE
                imageList.visibility = View.GONE
                textview.visibility = View.VISIBLE
            } else {
                imageAdapter.updateImages(it)
                progressBar.visibility = View.GONE
                textview.visibility = View.GONE
                imageList.visibility = View.VISIBLE
            }
        })
    }

    fun bindUi(tag : String) = launch {
        viewModel.getPhotos(tag)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search)
        if(searchItem != null) {
            searchView = MenuItemCompat.getActionView(searchItem) as SearchView
            searchView.setOnCloseListener(object : SearchView.OnCloseListener {
                override fun onClose(): Boolean {
                    return true
                }
            })

            val searchPlate = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
            searchPlate.hint = "Search"
            val searchPlateView : View = searchView.findViewById(androidx.appcompat.R.id.search_plate)
            context?.let {
                searchPlateView.setBackgroundColor(
                    ContextCompat.getColor(
                        it,
                        android.R.color.transparent
                    )
                )
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        progressBar.visibility = View.VISIBLE
                        imageList.visibility = View.GONE
                        textview.visibility = View.GONE
                        bindUi(it) }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.options -> {
                Navigation.findNavController(textview).navigate(MainFragmentDirections.actionSettings())
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getSpanCount() : Int? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString("SPAN_COUNT", "2")?.toInt()
    }
}