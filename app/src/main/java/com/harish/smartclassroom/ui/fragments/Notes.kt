package com.harish.smartclassroom.ui.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.harish.smartclassroom.R
import com.harish.smartclassroom.adapters.AssignmentAdapter
import com.harish.smartclassroom.adapters.NotesAdapter
import com.harish.smartclassroom.data.AppData
import com.harish.smartclassroom.data.models.NotesResponse
import com.harish.smartclassroom.viewmodels.HomeViewModel
import com.harish.smartclassroom.viewmodels.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_assignments.view.*

class Notes : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private val mAdapter by lazy { NotesAdapter() }
    private lateinit var root: View
    private lateinit var appData : AppData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_notes, container, false)

        initViewModel(requireActivity().application)
        appData = AppData.init(requireContext())

        viewModel.getNotes("S1","1","Data Structures")

        showShimmerEffect()


        return root
    }

    override fun onResume() {
        super.onResume()

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.apply {
            events.observe(requireActivity(), Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })

            notes.observe(requireActivity(), Observer {
                if(it.isNullOrEmpty()){
                    root.ll_nodata.visibility = View.VISIBLE
                    hideShimmerEffect()
                }else{
                    setupRecyclerView(it)

                }
            })


        }
    }

    private fun setupRecyclerView(notes: List<NotesResponse.StudyMaterial>) {
        notes?.let { mAdapter.setNotesList(it) }
        root.recyclerview.adapter = mAdapter
        root.recyclerview.layoutManager = LinearLayoutManager(context)
    }

    private fun initViewModel(application: Application) {
        val factory = HomeViewModelFactory(application)
        viewModel = ViewModelProvider(requireActivity(),factory).get(HomeViewModel::class.java)
    }

    private fun showShimmerEffect() {
        root.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
        root.recyclerview.hideShimmer()
    }

}