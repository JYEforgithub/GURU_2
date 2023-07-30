package com.example.guru_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guru_2.databinding.FragmentTodoBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TodoFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: FragmentTodoBinding
    private lateinit var taskAdapter:TaskItemAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_todo, container, false)

        val fabOpenDialog =rootView.rootView.findViewById<FloatingActionButton>(R.id.newTaskButton)
        taskViewModel=ViewModelProvider(this).get(TaskViewModel::class.java)
        fabOpenDialog.setOnClickListener{
            val dialogFragment = NewTaskSheet(null)
            dialogFragment.show(childFragmentManager, NewTaskSheet.TAG)

            taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
            val recyclerView = rootView.findViewById<RecyclerView>(R.id.todoListRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            // Create and set the adapter for the RecyclerView
            taskAdapter = TaskItemAdapter(taskViewModel.getAllTasks(), this)
            recyclerView.adapter = taskAdapter
        }

        return rootView

    }


    fun editTaskItem(taskItem: TaskItem)
    {
        NewTaskSheet(taskItem).show(childFragmentManager,"newTaskTag")
    }

    fun completeTaskItem(taskItem: TaskItem)
    {
        taskViewModel.setCompleted(taskItem)
    }



    }




