package com.levkovskiy.ukadtest

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.net.ParseException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.levkovskiy.ukadtest.network.factory.ApiSuccessResponse
import kotlinx.android.synthetic.main.activity_main.*
import com.levkovskiy.ukadtest.network.GraphData

import kotlin.collections.HashMap
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import android.widget.ArrayAdapter
import com.levkovskiy.ukadtest.utils.DateUtils
import android.widget.Spinner


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val map = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.login().observe(this, Observer {
            map["AuthToken"] = (it as ApiSuccessResponse).body.Tokens.AuthToken
            map["SessionToken"] = it.body.Tokens.SessionToken
            viewModel.tokenLiveData.value = it.body.Tokens.AuthToken
            println(it.toString())

        })
        viewModel.tokenLiveData.observe(this, Observer {
            if (it.isNotEmpty())
                getQuartetChart()
        })
        val list = ArrayList<String>()
        list.add("Quartet")
        list.add("Month")
        list.add("Week")
        val dataAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> getQuartetChart()
                    1 -> getMonthChart()
                    2 -> getWeekChart()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun setupChart(dataList: List<GraphData>): ArrayList<LineDataSet>? {

        val dataSets: ArrayList<LineDataSet> = ArrayList()
        val valueSet1 = ArrayList<Entry>()
        if (dataList.isEmpty())
            return dataSets
        dataList.forEachIndexed { index, graphData ->
            valueSet1.add(Entry(graphData.Steps.toFloat(), index))
        }
        val barDataSet1 = LineDataSet(valueSet1, "")
        barDataSet1.color = Color.rgb(0, 155, 0)
        dataSets.add(barDataSet1)
        val data = LineData(getXAxisValues(dataList), dataSets)
        chart.data = data
        chart.setDescription("My Chart")
        chart.animateXY(2000, 2000)
        chart.invalidate()
        return dataSets
    }

    private fun getXAxisValues(dataList: List<GraphData>): ArrayList<String> {
        val xAxis = ArrayList<String>()
        dataList.forEach {
            xAxis.add(DateUtils.formatDate(it.StartDate)!!)
        }
        return xAxis
    }

    private fun getQuartetChart() {
        viewModel.getDataPerQuartet(map).observe(this, Observer {
            if (it is ApiSuccessResponse)
                setupChart(it.body.GraphData)
        })
    }

    private fun getMonthChart() {
        viewModel.getDataPerMonth(map).observe(this, Observer {
            if (it is ApiSuccessResponse)
                setupChart(it.body.GraphData)
        })
    }

    private fun getWeekChart() {
        viewModel.getDataPerWeek(map).observe(this, Observer {
            if (it is ApiSuccessResponse)
                setupChart(it.body.GraphData)
        })
    }
}
