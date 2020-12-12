package com.app.smartclassroom.ui.fragments

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
import com.app.smartclassroom.R
import com.app.smartclassroom.adapters.AssignmentAdapter
import com.app.smartclassroom.data.AppData
import com.app.smartclassroom.data.models.AssignmentListResponse
import com.app.smartclassroom.data.models.StudentDetails
import com.app.smartclassroom.viewmodels.HomeViewModel
import com.app.smartclassroom.viewmodels.HomeViewModelFactory
import kotlinx.android.synthetic.main.error_layout.view.*
import kotlinx.android.synthetic.main.fragment_assignments.view.*


class Assignments : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private val mAdapter by lazy { AssignmentAdapter() }
    private lateinit var root: View
    private lateinit var appData : AppData
    var assignmentList:List<StudentDetails.Assignment>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_assignments, container, false)

        initViewModel(requireActivity().application)
        appData = AppData.init(requireContext())
        showShimmerEffect()

     //   setupObservers()

        return root
    }

    override fun onResume() {
        super.onResume()
        if(!appData.getStudentDict()?.stud_id.isNullOrEmpty()) {
            //viewModel.getAssignments(appData.getStudentDict()!!.semester, appData.getStudentDict()!!.batch_id)

            appData.getStudentDict()?.stud_id?.let {
                viewModel.getStudentDetails(it,onSuccess = { response ->
                    assignmentList = response?.assignment
                    setupRecyclerView(assignmentList)
                },onFailure = { error ->

                    if(error == context?.getString(R.string.offline)) {
                        root.ll_nodata.visibility = View.VISIBLE
                        root.ll_nodata.tv_nointernt.visibility =View.VISIBLE
                        root.ll_nodata.nointernet_animationView.visibility =View.VISIBLE
                    }else{
                        root.ll_nodata.visibility = View.VISIBLE
                        root.ll_nodata.tv_server.visibility =View.VISIBLE
                        root.ll_nodata.server_error_animationView. visibility = View.VISIBLE
                    }
                })
            }

        }else{
            hideShimmerEffect()
            root.ll_nodata.visibility = View.VISIBLE

        }
        setupObservers()

    }

    private fun setupObservers() {
        viewModel.apply {


            events.observe(requireActivity(), Observer {
              //  Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                hideShimmerEffect()


            })
        }
    }

    private fun setupRecyclerView(assignmentList: List<StudentDetails.Assignment>?) {

        if(assignmentList.isNullOrEmpty()){
            hideShimmerEffect()
            root.ll_nodata.visibility = View.VISIBLE
            root.ll_nodata.tv_error.visibility =View.VISIBLE
            root.ll_nodata.loading_animationView. visibility = View.VISIBLE

        }else {
            assignmentList?.let { mAdapter.setAssignments(it) }
            root.recyclerview.adapter = mAdapter
            root.recyclerview.layoutManager = LinearLayoutManager(context)


        }
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