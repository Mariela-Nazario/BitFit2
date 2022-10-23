package com.mnazari2.bitfit2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import java.lang.Byte.MAX_VALUE
import java.lang.Byte.MIN_VALUE


class StatFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_stat, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val max = activity?.findViewById(R.id.MaxCal) as TextView
        val min = activity?.findViewById(R.id.minCal) as TextView
        val avg = activity?.findViewById(R.id.AVGCal) as TextView
        var s : List<String> = listOf()
        var smallestInt: Int = MAX_VALUE.toInt()
        var max_val: Int = MIN_VALUE.toInt()
        var numOfCal: Int = 0
        var sumOfCal: Int = 0


        lifecycleScope.launch(Dispatchers.IO) {
            for (item in (activity?.application as FoodApplication).db.articleDao().getCall()){
                numOfCal = numOfCal + 1
                sumOfCal = sumOfCal + Integer.parseInt(item)
                avg.text = (sumOfCal/numOfCal).toString()
                if(Integer.parseInt(item)> max_val){
                    max_val = Integer.parseInt(item)
                }
                if(Integer.parseInt(item)< smallestInt){
                    smallestInt = Integer.parseInt(item)
                }
                max.text = max_val.toString()
                min.text = smallestInt.toString()
            }


        }





    }



}