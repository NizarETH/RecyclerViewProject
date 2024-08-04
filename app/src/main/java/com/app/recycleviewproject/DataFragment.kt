package com.app.recycleviewproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapter
    private val dataList = mutableListOf<DataModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_data, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = DataAdapter(dataList)
        recyclerView.adapter = adapter

        fetchData()

        return view
    }

    private fun fetchData() {

        val apiInterface = RetrofitInstance.getInstance().create(ApiService::class.java)

        val call = apiInterface.getData()

        call?.enqueue(object : Callback<List<DataModel>> {
            override fun onResponse(
                call: Call<List<DataModel>>,
                response: Response<List<DataModel>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body() as List<DataModel>
                    dataList.addAll(data)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("MainActivity ",t.message.toString())
            }

        })

    }
}
