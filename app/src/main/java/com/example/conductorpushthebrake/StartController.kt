package com.example.conductorpushthebrake

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction

class StartController: Controller(), Conductor.ConductorListener {
    private lateinit var tvTicketsCount: TextView
    private lateinit var btnFindConductor: Button
    private lateinit var btnStepOff: Button
    private var ticketsCount = 0
    private var isRunning = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_start, container, false)
        tvTicketsCount = view.findViewById(R.id.tv_tickets_count)
        btnFindConductor = view.findViewById(R.id.btn_find_cond)
        btnStepOff = view.findViewById(R.id.btn_step_out)

        tvTicketsCount.text = tvTicketsCount.context.resources.getString(R.string.tickets_count, ticketsCount)
        btnFindConductor.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Conductor.KEY_TIC_COUNT, ticketsCount)
            bundle.putBoolean(Conductor.KEY_IS_RUNNING, isRunning)
            router.pushController(RouterTransaction.with(Conductor(this, bundle)))
        }

        btnStepOff.setOnClickListener {
            if (ticketsCount > 0 && !isRunning)
                //TODO NOT WORKING
                handleBack()
            else
                Toast.makeText(view.context, "Купи билет или попроси об остановке!", Toast.LENGTH_LONG).show()
        }

        return view
    }

    override fun ticketsCount(count: Int) {
        ticketsCount = count
        tvTicketsCount.text = tvTicketsCount.context.resources.getString(R.string.tickets_count, ticketsCount)
        Log.d("STARTC", count.toString())
    }

    override fun isRunning(isRunning: Boolean) {
        Log.d("STARTC", isRunning.toString())
        this.isRunning = isRunning
    }


}