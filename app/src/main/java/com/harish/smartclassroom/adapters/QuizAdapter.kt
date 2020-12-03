package com.harish.smartclassroom.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harish.smartclassroom.R
import com.harish.smartclassroom.data.models.QuizResponse
import kotlinx.android.synthetic.main.quiz_card.view.*

class QuizAdapter (val listener : QuizListener): RecyclerView.Adapter<QuizAdapter.PagerVH>() {

    var quiz = emptyList<QuizResponse.Question>()

    class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quiz_card, parent, false)
        return PagerVH(view)
    }

    override fun getItemCount(): Int {
        return quiz.size
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        val currentQuiz = quiz[position]

        holder.itemView.apply {
            tv_question . text = currentQuiz.question
            tv_a . text = currentQuiz.a
            tv_b . text = currentQuiz.b
            tv_c . text = currentQuiz.c
            tv_d . text = currentQuiz.d
            listener.onQuizSwipe(position)


        }
    }

    fun setQuizList(quizList:List<QuizResponse.Question>){
        quiz = quizList
        notifyDataSetChanged()
    }
}

interface QuizListener{
    fun onQuizSwipe(position: Int)
}