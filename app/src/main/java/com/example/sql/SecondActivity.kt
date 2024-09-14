package com.example.sql

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {

    private val db = DBHelper(this, null)

    private var textSpinner: String? = null
    private lateinit var spinner: Spinner
    private lateinit var nameEditTextET: EditText
    private lateinit var surnameEditTextET: EditText
    private lateinit var phoneEditTextET: EditText
    private lateinit var saveButtonBT: Button
    private lateinit var getButtonBT: Button
    private lateinit var deleteButtonBT: Button
    private lateinit var nameTextViewTV: TextView
    private lateinit var surnameTextViewTV: TextView
    private lateinit var phoneTextViewTV: TextView
    private lateinit var postTextViewTV: TextView


    @SuppressLint("Range", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        init()

        saveButtonBT.setOnClickListener{
            val name = nameEditTextET.text.toString()
            val surname = surnameEditTextET.text.toString()
            val phone = phoneEditTextET.text.toString()
            val post = textSpinner.toString()

            db.addPerson(name, surname, phone, post)

            Toast.makeText(this,"Данные добавлены в базу данных", Toast.LENGTH_LONG).show()

            nameEditTextET.text.clear()
            surnameEditTextET.text.clear()
            phoneEditTextET.text.clear()
        }

        getButtonBT.setOnClickListener{
            val cursor = db.getInfo()
            if(cursor != null && cursor.moveToFirst()){
                cursor.moveToFirst()

                nameTextViewTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)) + "\n")
                surnameTextViewTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SURNAME)) + "\n")
                phoneTextViewTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)) + "\n")
                postTextViewTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_POST)) + "\n")
            }
            while (cursor!!.moveToNext()){
                nameTextViewTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)) + "\n")
                surnameTextViewTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SURNAME)) + "\n")
                phoneTextViewTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)) + "\n")
                postTextViewTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_POST)) + "\n")
            }
            cursor.close()
        }

        deleteButtonBT.setOnClickListener {
            db.removeAll()
            nameTextViewTV.text = ""
            surnameTextViewTV.text = ""
            phoneTextViewTV.text = ""
            postTextViewTV.text = ""
        }
    }

    private fun init(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        title = "База данных"
        setSupportActionBar(toolbar)

        spinner = findViewById(R.id.spinner)
        nameEditTextET = findViewById(R.id.nameEditTextET)
        surnameEditTextET = findViewById(R.id.surnameEditTextET)
        phoneEditTextET = findViewById(R.id.phoneEditTextET)
        saveButtonBT = findViewById(R.id.saveButtonBT)
        getButtonBT = findViewById(R.id.getButtonBT)
        deleteButtonBT = findViewById(R.id.deleteButtonBT)
        nameTextViewTV = findViewById(R.id.nameTextViewTV)
        surnameTextViewTV = findViewById(R.id.surnameTextViewTV)
        phoneTextViewTV = findViewById(R.id.phoneTextViewTV)
        postTextViewTV = findViewById(R.id.postTextViewTV)

        val adapter = ArrayAdapter(this@SecondActivity,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.post))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val itemSelectedListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = parent?.getItemAtPosition(position) as String
                    textSpinner = item
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        spinner.onItemSelectedListener = itemSelectedListener

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.exit_menu -> {
                Toast.makeText(
                    applicationContext,
                    "Программа завершена",
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}