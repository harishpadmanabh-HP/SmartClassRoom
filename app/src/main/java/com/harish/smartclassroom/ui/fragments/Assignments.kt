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
import com.harish.smartclassroom.data.AppData
import com.harish.smartclassroom.data.models.AssignmentListResponse
import com.harish.smartclassroom.viewmodels.HomeViewModel
import com.harish.smartclassroom.viewmodels.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_assignments.*
import kotlinx.android.synthetic.main.fragment_assignments.view.*

class Assignments : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private val mAdapter by lazy { AssignmentAdapter() }
    private lateinit var root: View
    private lateinit var appData : AppData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_assignments, container, false)

        initViewModel(requireActivity().application)
        appData = AppData.init(requireContext())

        viewModel.getAssignments(appData.getSemester(),appData.getBatchId())

        showShimmerEffect()
     //   setupObservers()

        return root
    }

    override fun onResume() {
        super.onResume()
        setupObservers()

    }

    private fun setupObservers() {
        viewModel.apply {
            assignments.observe(requireActivity(), Observer {assignmentList->
                if(assignmentList.isNullOrEmpty()){
                    root.ll_nodata.visibility = View.VISIBLE
                    hideShimmerEffect()
                }else{
                    setupRecyclerView(assignmentList)
                }
            })

            events.observe(requireActivity(), Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun setupRecyclerView(assignmentList: List<AssignmentListResponse.AssignmentDetail>?) {

            assignmentList?.let { mAdapter.setAssignments(it) }
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