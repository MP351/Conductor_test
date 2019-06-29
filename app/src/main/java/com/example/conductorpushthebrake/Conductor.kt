package com.example.conductorpushthebrake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bluelinelabs.conductor.Controller

class Conductor<T>(val listener: T? = null, val bundle: Bundle? = null): Controller(bundle) where T: Controller, T: Conductor.ConductorListener {
    companion object {
        val KEY_IS_RUNNING = "RUNNING_KEY"
        val KEY_TIC_COUNT = "TICKETS_COUNT"
    }

    private lateinit var btnBuyTicket: Button
    private lateinit var btnAskToStop: Button
    private lateinit var btnAskToGo: Button
    private var ticketsCount = 0
    private var isRunning = true
    init {
        isRunning = args.getBoolean(KEY_IS_RUNNING)
        ticketsCount = args.getInt(KEY_TIC_COUNT)

        if (listener != null)
            targetController = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_controller, container, false)

        btnAskToGo = view.findViewById(R.id.btn_start_the_train)
        btnAskToStop = view.findViewById(R.id.btn_press_break)
        btnBuyTicket = view.findViewById(R.id.btn_buy_the_ticket)

        btnBuyTicket.setOnClickListener {
            ticketsCount++
            args.putInt(KEY_TIC_COUNT, ticketsCount)
        }

        btnAskToStop.setOnClickListener {
            pressTheBreak()
        }

        btnAskToGo.setOnClickListener {
            pressTheBreak()
        }

        updateUI()
        return view
    }

    fun pressTheBreak() {
        isRunning = !isRunning
        args.putBoolean(KEY_IS_RUNNING, isRunning)
        updateUI()
    }

    fun updateUI() {
        btnAskToGo.visibility = if (isRunning) View.INVISIBLE else View.VISIBLE
        btnAskToStop.visibility = if (!isRunning) View.INVISIBLE else View.VISIBLE
    }

    override fun handleBack(): Boolean {
        (targetController as ConductorListener).isRunning(isRunning)
        (targetController as ConductorListener).ticketsCount(ticketsCount)

        return super.handleBack()
    }

    interface ConductorListener {
        fun ticketsCount(count: Int)
        fun isRunning(isRunning: Boolean)
    }
}