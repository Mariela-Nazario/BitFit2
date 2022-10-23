package com.mnazari2.bitfit2

import android.content.Intent
import android.content.Intent.getIntent
import android.media.tv.TvContract.RecordedPrograms
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class FoodFragment : Fragment() {
    private  val food = mutableListOf<DisplayFoodCalories>()

    private lateinit var Rv: RecyclerView
    private lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food, container, false)
        val layoutManager = LinearLayoutManager(context)
         Rv =view.findViewById<RecyclerView>(R.id.Rv)
        Rv.layoutManager = layoutManager
        Rv.setHasFixedSize(true)
        adapter = Adapter(food)
        Rv.adapter = adapter










        return view
    }





    private fun fetchArticles(){
        val btn = view?.findViewById(R.id.btn_addFood) as Button

        lifecycleScope.launch {
            (activity?.application as FoodApplication).db.articleDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFoodCalories(
                        entity.food,
                        entity.calories

                    )
                }.also { mappedList ->
                    food.clear()
                    food.addAll(mappedList)
                    adapter.notifyDataSetChanged()
                }
            }

        }




        btn.setOnClickListener() {

            val intent = Intent(activity, RecordFood::class.java)

            activity?.startActivity(intent)
        }
        var username = activity?.getIntent()?.getStringExtra("name").toString()
        var cal = activity?.getIntent()?.getStringExtra("calories").toString()

        if (cal != "null" && username != "null") {


            val l = foodCaloriesEntity(food = username, calories = cal)

            lifecycleScope.launch(IO) {
                //  (application as FoodAplication).db.articleDao().deleteAll()

                (activity?.application as FoodApplication).db.articleDao().insertAll(l)
            }
            adapter.notifyDataSetChanged();


        }



    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  val  Rv = view.findViewById<RecyclerView>(R.id.Rv)
       // val adapter = Adapter(food)

        fetchArticles()
    }
    companion object {
        fun newInstance(): FoodFragment {
            return FoodFragment()
        }
    }


}