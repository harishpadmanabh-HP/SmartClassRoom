package com.apps.smartclassroom.ui.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.smartclassroom.R
import com.apps.smartclassroom.adapters.ExamsAdapter
import com.apps.smartclassroom.data.AppData
import com.apps.smartclassroom.data.models.StudentDetails
import com.apps.smartclassroom.viewmodels.HomeViewModel
import com.apps.smartclassroom.viewmodels.HomeViewModelFactory
import kotlinx.android.synthetic.main.error_layout.view.*
import kotlinx.android.synthetic.main.fragment_assignments.view.*

class Exam : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private val mAdapter by lazy { ExamsAdapter() }
    private lateinit var root: View
    private lateinit var appData : AppData
    var examList : List<StudentDetails.Exam>?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_exam, container, false)
        initViewModel(requireActivity().application)
        appData = AppData.init(requireContext())
//        if(!appData.getStudentDict()?.semester.isNullOrEmpty()&& !appData.getStudentDict()?.batch_id.isNullOrEmpty()) {
//            viewModel.getAssignments(appData.getStudentDict()!!.semester, appData.getStudentDict()!!.batch_id)
//        }else{
//            hideShimmerEffect()
//        }

     //   viewModel.getQuizList("S1","1","Data structures")

        showShimmerEffect()
        //
        return root
    }
    override fun onResume() {
        super.onResume()
        if(!appData.getStudentDict()?.stud_id.isNullOrEmpty()) {
            //viewModel.getAssignments(appData.getStudentDict()!!.semester, appData.getStudentDict()!!.batch_id)

            appData.getStudentDict()?.stud_id?.let {
                viewModel.getStudentDetails(it,onSuccess = { response ->
                    examList = response?.exam
                    setupRecyclerView(examList)
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


    private fun setupRecyclerView(exams: List<StudentDetails.Exam>?) {

        if(exams.isNullOrEmpty()){
            hideShimmerEffect()
            root.ll_nodata.visibility = View.VISIBLE
            root.ll_nodata.tv_error.visibility =View.VISIBLE
            root.ll_nodata.loading_animationView. visibility = View.VISIBLE
        }else {
            exams?.let { mAdapter.setExamList(it) }
            root.recyclerview.adapter = mAdapter
            root.recyclerview.layoutManager = LinearLayoutManager(context)
        }

    }



}