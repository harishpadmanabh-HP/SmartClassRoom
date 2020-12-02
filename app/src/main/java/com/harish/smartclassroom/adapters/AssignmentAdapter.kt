package com.harish.smartclassroom.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harish.smartclassroom.R
import com.harish.smartclassroom.data.models.AssignmentListResponse
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
        }

    }

    fun setAssignments(assignments : List<AssignmentListResponse.AssignmentDetail>){

        assignmentsList = assignments
        notifyDataSetChanged()
    }

}