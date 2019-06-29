package com.levkovskiy.ukadtest

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.net.ParseException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }

    private fun setupChart(dataList: List<GraphData>): ArrayList<LineDataSet>? {

        val dataSets: ArrayList<LineDataSet> = ArrayList()

        val valueSet1 = ArrayList<Entry>()
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
            xAxis.add(formatDate(it.StartDate)!!)
        }
        return xAxis
    }

    private fun formatDate(sourceDate: String): String? {
        val fromUser = SimpleDateFormat("yyyy-MM-dd")
        val myFormat = SimpleDateFormat("dd MMM")

        try {
            return myFormat.format(fromUser.parse(sourceDate))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getQuartetChart() {
        viewModel.getChartData(map).observe(this, Observer {
            setupChart((it as ApiSuccessResponse).body.GraphData)
        })
    }

    private fun getMonthChart() {
        viewModel.getChartData(map).observe(this, Observer {
            println(it.toString())
        })
    }

    private fun getWeekChart() {
        viewModel.getChartData(map).observe(this, Observer {
            println(it.toString())
        })
    }
}
