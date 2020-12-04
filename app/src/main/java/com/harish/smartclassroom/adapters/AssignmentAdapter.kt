package com.harish.smartclassroom.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harish.smartclassroom.R
import com.harish.smartclassroom.data.models.AssignmentListResponse
import com.harish.smartclassroom.ui.AddAssignment
import com.harish.smartclassroom.ui.Viewnotes
import kotlinx.android.synthetic.main.assignment_row_layout.view.*

class AssignmentAdapter : RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>(){

    var assignmentsList = emptyList<AssignmentListResponse.AssignmentDetail>()

    class AssignmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.assignment_row_layout, parent, false)
        return  AssignmentViewHolder(view)
    }

    override fun getItemCount(): Int {

        return assignmentsList.size
    }

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {

        holder.itemView.apply {

            tv_topic . text = assignmentsList[position].topic
            tv_subject . text = assignmentsList[position].subject
            tv_dueDate . text = assignmentsList[position].submittionDate
            setOnClickListener {
                val intent = Intent(context,AddAssignment::class.java)
                intent.putExtra("SUBJECT",assignmentsList[position].subject)
                intent.putExtra("ASSGN_ID",assignmentsList[position].assignId)
                intent.putExtra("BATCH_ID",assignmentsList[position].batchId)
                intent.putExtra("SEM",assignmentsList[position].semester)
                intent.putExtra("FACULTY_ID",assignmentsList[position].facultyId)
                context.startActivity(intent)
            }
        }

    }

    fun setAssignments(assignments : List<AssignmentListResponse.AssignmentDetail>){

        assignmentsList = assignments
        notifyDataSetChanged()
    }

}