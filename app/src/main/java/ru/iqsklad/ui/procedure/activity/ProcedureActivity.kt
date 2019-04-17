package ru.iqsklad.ui.procedure.activity

import android.os.Bundle
import ru.iqsklad.R
import ru.iqsklad.domain.App
import ru.iqsklad.ui.base.activity.BaseActivity

class ProcedureActivity : BaseActivity() {

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
