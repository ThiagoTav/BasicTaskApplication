package com.example.basictaskapplication


import android.view.View
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.basictaskapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            showTitlePopup(view)
        }
    }

    private fun showTitlePopup(view: View) {
        val popupTitulo = AlertDialog.Builder(this)
        val textTitulo = EditText(this)

        popupTitulo.setTitle("Título")
        popupTitulo.setView(textTitulo)
        popupTitulo.setPositiveButton("Ok") { dialog, _ ->
            val titulo = textTitulo.text.toString()
            showDescriptionPopup(view, titulo)
        }
        popupTitulo.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        popupTitulo.show()
    }

    private fun showDescriptionPopup(view: View, titulo: String) {
        val popupDescricao = AlertDialog.Builder(this)
        val textDescricao = EditText(this)

        popupDescricao.setTitle("Descrição")
        popupDescricao.setView(textDescricao)
        popupDescricao.setPositiveButton("Ok") { dialog, _ ->
            val descricao = textDescricao.text.toString()
            showStatusPopup(view, titulo, descricao)
        }
        popupDescricao.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        popupDescricao.show()
    }

    private fun showStatusPopup(view: View, titulo: String, descricao: String) {
        val popupStatus = AlertDialog.Builder(this)
        val spinnerStatus = Spinner(this)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.task_status_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = adapter

        popupStatus.setTitle("Status")
        popupStatus.setView(spinnerStatus)
        popupStatus.setPositiveButton("Ok") { dialog, _ ->
            val status = spinnerStatus.selectedItem.toString()
            if (status.isNotEmpty()) {
                val task = Task(titulo, descricao, status)
                tasks.add(task)
                Snackbar.make(view, "Task criada: $task", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                Snackbar.make(view, "Por favor, escolha um status.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
        popupStatus.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        popupStatus.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}