package ru.iqsklad.ui.procedure.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.iqsklad.R
import ru.iqsklad.domain.App

class ProcedureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).buildProcedureComponent()

        setContentView(R.layout.activity_procedure)
    }

    override fun onDestroy() {
        super.onDestroy()

        App.procedureComponent = null
    }
}
