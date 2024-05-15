package com.example.nirogyamhealthathome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    private var exercises: List<Exercise> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun setExercises(exercises: List<Exercise>) {
        this.exercises = exercises
        notifyDataSetChanged()
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
        private val difficultyTextView: TextView = itemView.findViewById(R.id.difficultyTextView)
        private val muscleTextView: TextView = itemView.findViewById(R.id.muscleTextView)
        private val equipmentTextView: TextView = itemView.findViewById(R.id.equipmentTextView)
        private val instructionsTextView: TextView = itemView.findViewById(R.id.instructionsTextView)


        fun bind(exercise: Exercise) {
            nameTextView.text = exercise.name
            typeTextView.text = exercise.type
            muscleTextView.text= exercise.muscle
            equipmentTextView.text= exercise.equipment
            difficultyTextView.text = exercise.difficulty
            instructionsTextView.text= exercise.instructions

        }
    }
}

