package com.example.guru_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.guru_2.databinding.FragmentNewTaskSheetBinding
import com.example.guru_2.databinding.FragmentTodoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTodoBinding.inflate(inflater, container, false)
        taskViewModel=ViewModelProvider(this).get(TaskViewModel::class.java)
        binding.newTaskButton.setOnClickListener{
            val newTaskSheet = NewTaskSheet(null) // Pass null as taskItem to indicate it's a new task
            newTaskSheet.show(parentFragmentManager, NewTaskSheet.TAG)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }
}

