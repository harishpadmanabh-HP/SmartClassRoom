package com.harish.smartclassroom.ui.fragments

import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.futured.donut.DonutSection
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.harish.smartclassroom.MainActivity
import com.harish.smartclassroom.R
import com.harish.smartclassroom.data.AppData
import com.harish.smartclassroom.data.models.StudentDetails
import com.harish.smartclassroom.viewmodels.HomeViewModel
import com.harish.smartclassroom.viewmodels.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_more.*
import kotlinx.android.synthetic.main.fragment_more.donut_view_exams
import kotlinx.android.synthetic.main.fragment_more.view.*

class More : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var root: View
    private lateinit var appData : AppData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        root= inflater.inflate(R.layout.fragment_more, container, false)
        initViewModel(requireActivity().application)
        appData = AppData.init(requireContext())

        //viewModel.getStudentDetails("1")
        setupObservers()
       // appData.getStudentDict()?.stud_id?.let { viewModel.getStudentDetails(it) }

        viewModel.getStudentDetails("1",onSuccess = {
            response ->
            response?.let { renderUI(it) }
        },onFailure = {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })



        return root
    }
    override fun onResume() {
        super.onResume()
       setupObservers()

    }

    private fun setupObservers() {

        viewModel.apply{
            events.observe(requireActivity(), Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })

        }
    }

    private fun initViewModel(application: Application) {
        val factory = HomeViewModelFactory(application)
        viewModel = ViewModelProvider(requireActivity(),factory).get(HomeViewModel::class.java)
    }

    private fun renderUI(data : StudentDetails){
        tv_name.text = data.studentDetails[0].name
        tv_sem .text = "Semester : ${data.studentDetails[0].semester}"
        tv_batch .text = "Batch : ${data.studentDetails[0].batch}"

        tv_total_assignment . text = "Total Assignments : ${data.studentDetails[0].totalAssignment}"
        tv_pending_assignments .text = "Pending : ${(data.studentDetails[0].pendingAssignment)+1}"

        tv_total_exams . text = "Total Exams : ${data.studentDetails[0].totalExam}"
        tv_pending_exam .text = "Pending : ${(data.studentDetails[0].pendingExam)+1}"

        renderAssignmentDonut(data.studentDetails[0].totalAssignment.toInt(),data.studentDetails[0].pendingAssignment)
        renderExamDonut(data.studentDetails[0].totalExam.toInt(),data.studentDetails[0].pendingExam)

        cl_main.visibility = View.VISIBLE

        cl_load.visibility = View.GONE
    }

    private fun renderExamDonut(totalExam: Int, pendingExam: Int) {
        val section1 = DonutSection(
            name = "Total",
            color = Color.parseColor("#ffcc4d"),
            amount = totalExam.toFloat()
        )

        val section2 = DonutSection(
            name = "Pending",
            color = Color.parseColor("#000000"),
            amount = (pendingExam+1).toFloat()
        )

        donut_view_exams.cap = totalExam.toFloat()
        donut_view_exams.animationDurationMs = 3000

        donut_view_exams.submitData(listOf(section1, section2))


    }

    private fun renderAssignmentDonut(totalAssignment: Int, pendingAssignment: Int) {
        val section1 = DonutSection(
            name = "Total",
            color = Color.parseColor("#6f57eb"),
            amount = totalAssignment.toFloat()
        )

        val section2 = DonutSection(
            name = "Pending",
            color = Color.parseColor("#000000"),
            amount = (pendingAssignment+1).toFloat()
        )

        donut_view_assignment.cap = totalAssignment.toFloat()
        donut_view_assignment.animationDurationMs = 3000

        donut_view_assignment.submitData(listOf(section1, section2))

    }


}