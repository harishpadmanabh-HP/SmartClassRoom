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
import com.harish.smartclassroom.adapters.ExamsAdapter
import com.harish.smartclassroom.data.AppData
import com.harish.smartclassroom.data.models.QuizList
import com.harish.smartclassroom.viewmodels.HomeViewModel
import com.harish.smartclassroom.viewmodels.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_assignments.view.*

class Exam : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private val mAdapter by lazy { ExamsAdapter() }
    private lateinit var root: View
    private lateinit var appData : AppData


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

        viewModel.getQuizList("S1","1","Data structures")

        showShimmerEffect()
        //
        return root
    }
    override fun onResume() {
        super.onResume()
        setupObservers()

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

    private fun setupObservers() {
        viewModel.apply {
            exams.observe(requireActivity(), Observer {exam->
                if(exam.exam.isNullOrEmpty()){
                    root.ll_nodata.visibility = View.VISIBLE
                    hideShimmerEffect()
                }else{
                    setupRecyclerView(exam.exam)
                }
            })

            events.observe(requireActivity(), Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun setupRecyclerView(assignmentList: List<QuizList.Exam>) {

        assignmentList?.let { mAdapter.setExamList(it) }
        root.recyclerview.adapter = mAdapter
        root.recyclerview.layoutManager = LinearLayoutManager(context)


    }



}