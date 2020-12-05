package com.harish.smartclassroom.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harish.smartclassroom.R
import com.harish.smartclassroom.data.models.QuizList
import com.harish.smartclassroom.ui.StartQuiz
import kotlinx.android.synthetic.main.assignment_row_layout.view.*
import kotlinx.android.synthetic.main.assignment_row_layout.view.tv_dueDate
import kotlinx.android.synthetic.main.assignment_row_layout.view.tv_subject
import kotlinx.android.synthetic.main.assignment_row_layout.view.tv_topic
import kotlinx.android.synthetic.main.exam_row_layout.view.*

class ExamsAdapter : RecyclerView.Adapter<ExamsAdapter.ExamVH>() {

    var exams = emptyList<QuizList.Exam>()

    class ExamVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exam_row_layout, parent, false)
        return ExamVH(view)
    }

    override fun getItemCount(): Int {
        return exams.size
    }

    override fun onBindViewHolder(holder: ExamVH, position: Int) {

        holder.itemView.apply {
            tv_topic . text = exams[position].title
            tv_subject . text = exams[position].subject
            tv_dueDate . text = exams[position].date
            tv_examDuration . text = "${exams[position].duration} minutes"

            setOnClickListener {
                val intent = Intent(context,StartQuiz::class.java)
                intent.putExtra("examId",exams[position].examId)
                intent.putExtra("duration",exams[position].duration)
                context.startActivity(intent)
            }
        }
    }

    fun setExamList(examList : List<QuizList.Exam>){
        exams = examList
        notifyDataSetChanged()
    }
}