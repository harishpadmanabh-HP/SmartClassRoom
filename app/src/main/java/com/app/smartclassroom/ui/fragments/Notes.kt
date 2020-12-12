package com.app.smartclassroom.ui.fragments

import android.app.Application
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.smartclassroom.R
import com.app.smartclassroom.adapters.NotesAdapter
import com.app.smartclassroom.data.AppData
import com.app.smartclassroom.data.models.NotesResponse
import com.app.smartclassroom.data.models.StudentDetails
import com.app.smartclassroom.viewmodels.HomeViewModel
import com.app.smartclassroom.viewmodels.HomeViewModelFactory
import kotlinx.android.synthetic.main.error_layout.view.*
import kotlinx.android.synthetic.main.fragment_assignments.view.*

class Notes : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private val mAdapter by lazy { NotesAdapter() }
    private lateinit var root: View
    private lateinit var appData: AppData
    var notes: List<StudentDetails.Note>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_notes, container, false)

        initViewModel(requireActivity().application)
        appData = AppData.init(requireContext())

        //  viewModel.getNotes("S1","1","Data Structures")

        showShimmerEffect()


        return root
    }

    override fun onResume() {
        super.onResume()
        if (!appData.getStudentDict()?.stud_id.isNullOrEmpty()) {
            //viewModel.getAssignments(appData.getStudentDict()!!.semester, appData.getStudentDict()!!.batch_id)

            appData.getStudentDict()?.stud_id?.let {
                viewModel.getStudentDetails(it, onSuccess = { response ->
                    notes = response?.note
                    setupRecyclerView(notes)
                }, onFailure = { error ->

                    if (error == context?.getString(R.string.offline)) {
                        root.ll_nodata.visibility = View.VISIBLE
                        root.ll_nodata.tv_nointernt.visibility = View.VISIBLE
                        root.ll_nodata.nointernet_animationView.visibility = View.VISIBLE
                    } else {
                        root.ll_nodata.visibility = View.VISIBLE
                        root.ll_nodata.tv_server.visibility = View.VISIBLE
                        root.ll_nodata.server_error_animationView.visibility = View.VISIBLE
                    }
                })
            }

        } else {
            hideShimmerEffect()
            root.ll_nodata.visibility = View.VISIBLE

        }
    }


    private fun setupRecyclerView(notes: List<StudentDetails.Note>?) {
        if (notes.isNullOrEmpty()) {
            hideShimmerEffect()
            root.ll_nodata.visibility = View.VISIBLE
            root.ll_nodata.tv_error.visibility =View.VISIBLE
            root.ll_nodata.loading_animationView. visibility = View.VISIBLE

        } else {

            notes?.let { mAdapter.setNotesList(it) }
            root.recyclerview.adapter = mAdapter
            root.recyclerview.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initViewModel(application: Application) {
        val factory = HomeViewModelFactory(application)
        viewModel = ViewModelProvider(requireActivity(), factory).get(HomeViewModel::class.java)
    }

    private fun showShimmerEffect() {
        root.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
        root.recyclerview.hideShimmer()
    }

}