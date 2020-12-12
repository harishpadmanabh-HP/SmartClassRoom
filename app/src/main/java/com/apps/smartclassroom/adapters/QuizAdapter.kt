package com.apps.smartclassroom.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apps.smartclassroom.R
import com.apps.smartclassroom.data.models.QuizResponse
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

        var answered = QuizAnswers.Nothing
        var selected = false
        var reselected = false

        holder.itemView.apply {
            listener.onQuizShown(position,currentQuiz)
            tv_qno . text = (position+1).toString()
            tv_question . text = currentQuiz.question
            tv_a . text = currentQuiz.a
            tv_b . text = currentQuiz.b
            tv_c . text = currentQuiz.c
            tv_d . text = currentQuiz.d

            tv_a.setOnClickListener {


                aSelected(holder)
                answered = QuizAnswers.A
                currentQuiz.isAnswered = true
                listener.onQuizAnswered(currentQuiz,position,answered)

            }

            tv_b.setOnClickListener {
                bSelected(holder)
                answered = QuizAnswers.B
                currentQuiz.isAnswered = true

                listener.onQuizAnswered(currentQuiz,position,answered)


            }

            tv_c.setOnClickListener {
                cSelected(holder)
                answered = QuizAnswers.C
                currentQuiz.isAnswered = true

                listener.onQuizAnswered(currentQuiz,position,answered)


            }

            tv_d.setOnClickListener {
                dSelected(holder)
                answered = QuizAnswers.D
                currentQuiz.isAnswered = true

                listener.onQuizAnswered(currentQuiz,position,answered)

            }


        }
    }

    fun setQuizList(quizList:List<QuizResponse.Question>){
        quiz = quizList
        notifyDataSetChanged()
    }

    fun aSelected(holder: PagerVH){
        holder.itemView.apply {

            tv_a.setBackgroundResource(R.drawable.answer_selected_shape)
            tv_a.setTextColor(Color.parseColor("#ffffff"))

            tv_b.setBackgroundResource(R.drawable.answer_shape)
            tv_b.setTextColor(Color.parseColor("#000000"))

            tv_c.setBackgroundResource(R.drawable.answer_shape)
            tv_c.setTextColor(Color.parseColor("#000000"))

            tv_d.setBackgroundResource(R.drawable.answer_shape)
            tv_d.setTextColor(Color.parseColor("#000000"))

        }
    }

    fun bSelected(holder : PagerVH){
        holder.itemView.apply {

            tv_a.setBackgroundResource(R.drawable.answer_shape)
            tv_a.setTextColor(Color.parseColor("#000000"))

            tv_b.setBackgroundResource(R.drawable.answer_selected_shape)
            tv_b.setTextColor(Color.parseColor("#ffffff"))

            tv_c.setBackgroundResource(R.drawable.answer_shape)
            tv_c.setTextColor(Color.parseColor("#000000"))

            tv_d.setBackgroundResource(R.drawable.answer_shape)
            tv_d.setTextColor(Color.parseColor("#000000"))

        }
    }

    fun cSelected(holder: PagerVH){
        holder.itemView.apply {

            tv_a.setBackgroundResource(R.drawable.answer_shape)
            tv_a.setTextColor(Color.parseColor("#000000"))

            tv_b.setBackgroundResource(R.drawable.answer_shape)
            tv_b.setTextColor(Color.parseColor("#000000"))

            tv_c.setBackgroundResource(R.drawable.answer_selected_shape)
            tv_c.setTextColor(Color.parseColor("#ffffff"))

            tv_d.setBackgroundResource(R.drawable.answer_shape)
            tv_d.setTextColor(Color.parseColor("#000000"))

        }
    }

    fun dSelected(holder : PagerVH){
        holder.itemView.apply {

            tv_a.setBackgroundResource(R.drawable.answer_shape)
            tv_a.setTextColor(Color.parseColor("#000000"))

            tv_b.setBackgroundResource(R.drawable.answer_shape)
            tv_b.setTextColor(Color.parseColor("#000000"))

            tv_c.setBackgroundResource(R.drawable.answer_shape)
            tv_c.setTextColor(Color.parseColor("#000000"))

            tv_d.setBackgroundResource(R.drawable.answer_selected_shape)
            tv_d.setTextColor(Color.parseColor("#ffffff"))

        }
    }
}

interface QuizListener {
        fun onQuizSwipe(position: Int)
        fun onQuizAnswered(currentQuiz : QuizResponse.Question , position: Int , answered : QuizAnswers)
        fun onQuizShown(position: Int,currentQuiz: QuizResponse.Question)

}

enum class QuizAnswers{A,B,C,D,Nothing}