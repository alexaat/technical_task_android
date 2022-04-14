package com.alexaat.technical_task_android

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import com.alexaat.technical_task_android.adapters.UsersAdapter
import com.alexaat.technical_task_android.databinding.ActivityMainBinding
import com.alexaat.technical_task_android.model.User
import com.alexaat.technical_task_android.network.Status
import com.alexaat.technical_task_android.network.page
import com.alexaat.technical_task_android.ui.UserLongClickEvent
import com.alexaat.technical_task_android.ui.UsersViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UserLongClickEvent {


    private val viewModel: UsersViewModel by viewModels()

    @Inject
    lateinit var adapter: UsersAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.addUserFloatingActionButton.setOnClickListener {
            showAddUserDialog()
        }

        adapter.longClickListener = this

        binding.usersRecyclerView.addItemDecoration(DividerItemDecoration( binding.usersRecyclerView.context, DividerItemDecoration.VERTICAL))
        binding.usersRecyclerView.adapter = adapter

        viewModel.getUsers(page)

        viewModel.users.observe(this){users->
            users?.let{
                adapter.submitList(it)
            }
        }

        viewModel.status.observe(this){
            when(it){
                Status.LOADING -> {
                    binding.apply {
                        usersRecyclerView.visibility = View.GONE
                        progressImageView.visibility = View.VISIBLE
                        errorImageView.visibility = View. GONE
                    }
                }
                Status.ERROR -> {
                    binding.apply {
                        usersRecyclerView.visibility = View.GONE
                        progressImageView.visibility = View.GONE
                        errorImageView.visibility = View. VISIBLE
                    }
                }
                else -> {
                    binding.apply {
                        usersRecyclerView.visibility = View.VISIBLE
                        progressImageView.visibility = View.GONE
                        errorImageView.visibility = View. GONE
                    }

                }
            }
        }

        viewModel.response.observe(this){
            if(it.isSuccessful){
                val message = when(it.code()){
                    201 -> getString(R.string.user_added)
                    204 -> getString(R.string.user_deleted)
                    else -> getString(R.string.request_is_successful)
                }
                viewModel.getUsers(page)
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }else{
                Snackbar.make(binding.root, getString(R.string.error_message), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDeleteDialog(user: User) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.delete_user))
            .setMessage(getString(R.string.delete_user_message, user.name))

            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
                dialog.cancel()
                viewModel.deleteUser(user)
            }
            .show()
    }

    private fun showAddUserDialog() {
        val builder: AlertDialog.Builder = MaterialAlertDialogBuilder(this)
        builder.setTitle(getString(R.string.new_user))
        val view = LayoutInflater.from(this).inflate(R.layout.new_user_dialog_layout, null)
        builder.setView(view)

        builder.setPositiveButton(getString(R.string.ok), DialogInterface.OnClickListener { dialog, _ ->
            val name = view.findViewById<EditText>(R.id.new_user_name_text_input)?.text.toString()
            val email = view.findViewById<EditText>(R.id.new_user_email_text_input)?.text.toString()
            val user = User(
                name = name,
                email = email)
            viewModel.postUser(user)
            dialog.cancel()
        })
        builder.setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
        builder.show()
    }

    override fun onItemLongClick(user: User) {
        showDeleteDialog(user)
    }
}