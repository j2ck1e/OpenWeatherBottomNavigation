package com.jcdesign.openweatherbottomnavigation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jcdesign.openweatherbottomnavigation.R
import com.jcdesign.openweatherbottomnavigation.ui.WeatherViewModel
import com.jcdesign.openweatherbottomnavigation.util.Constants.Companion.API_KEY

//const val API_KEY = "b7e8027296720e35adc4416296829b71"

class FifthFragment : Fragment() {
    private lateinit var viewModel: WeatherViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fifth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        requestWeatherData("Vologda")
    }

//    private fun requestWeatherData(city: String) {
//        // api.openweathermap.org/data/2.5/forecast?q={city name}&appid={API key}
//        val url = "https://api.openweathermap.org/data/2.5/forecast" +
//                "?q=" +
//                city +
//                "&appid=" +
//                API_KEY +
//                "&units=" +
//                "metric"
//        Log.d("MyLog", "url: $url")
//        val queue = Volley.newRequestQueue(context)
//        val request = StringRequest(
//            Request.Method.GET, url,
//            { result ->
//                Log.d("MyLog", "res : $result")
////                parseWeatherData(result)
//            },
//            { error ->
//                Log.d("MyLog", "Error: $city")
//            })
//        queue.add(request)
//    }


}