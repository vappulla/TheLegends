package com.example.thelegends

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ChampionAdapter(
    private var championList: List<MutableList<String>>,
    private var championImageMap: Map<String, String>,
    private val listener: OnChampionClickListener
) :
    RecyclerView.Adapter<ChampionAdapter.ViewHolder>() {

    private var originalChampionList: List<MutableList<String>> = ArrayList(championList)

    interface OnChampionClickListener {
        fun onChampionClick(champion: MutableList<String>)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val championImg: ImageView = view.findViewById(R.id.champion_image)
        val name: TextView = view.findViewById(R.id.name)
        val title: TextView = view.findViewById(R.id.title)
        val HPAndMP: TextView = view.findViewById(R.id.HPAndMP)
        val AttackAndDef: TextView = view.findViewById(R.id.AttackAndDef)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_lol, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = championList[position][0]
        holder.title.text = championList[position][1]
        holder.HPAndMP.text =
            "HP: ${championList[position][8]} MP: ${championList[position][9]}"
        holder.AttackAndDef.text =
            "Attack: ${championList[position][3]} Def: ${championList[position][4]}"

        // Load champion image using Glide library
        val championKey = championList[position][10] // Assuming the champion key is stored at index 10
        val imageUrl = championImageMap[championKey]
        imageUrl?.let {
            Log.d("ChampionAdapter", "Champion Key: $championKey, Image URL: $it")
            Glide.with(holder.itemView.context)
                .load(it)
                .placeholder(R.drawable.mon) // Placeholder image while loading
                .into(holder.championImg)
        } ?: Log.e(
            "ChampionAdapter",
            "Image URL is null for champion key: $championKey"
        ) // Log error if image URL is null

        holder.itemView.setOnClickListener {
            listener.onChampionClick(championList[position])
        }
    }

    override fun getItemCount() = championList.size

    fun updateChampionImageMap(map: Map<String, String>) {
        championImageMap = map
        notifyDataSetChanged() // Notify RecyclerView to refresh
    }

    fun filterChampions(query: String, originalList: List<MutableList<String>>) {
        championList = originalList.filter { it[0].toLowerCase().contains(query.toLowerCase()) }
        notifyDataSetChanged()
    }
}



//package com.example.thelegends
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//
//class ChampionAdapter(private val championList: List<MutableList<String>>, private var championImageMap: Map<String, String>) :
//    RecyclerView.Adapter<ChampionAdapter.ViewHolder>() {
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val championImg: ImageView
//        val name: TextView
//        val title: TextView
//        val HPAndMP: TextView
//        val AttackAndDef: TextView
//
//        init {
//            // Find our RecyclerView item's views
//            championImg = view.findViewById(R.id.champion_image)
//            name = view.findViewById(R.id.name)
//            title = view.findViewById(R.id.title)
//            HPAndMP = view.findViewById(R.id.HPAndMP)
//            AttackAndDef = view.findViewById(R.id.AttackAndDef)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        // Inflate the layout for each list item
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.rv_lol, parent, false)
//
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        // Bind data to views for each item in the list
//        holder.name.text = championList[position][0]
//        holder.title.text = championList[position][1]
//        holder.HPAndMP.text =
//            "HP: ${championList[position][8]} MP: ${championList[position][9]}"
//        holder.AttackAndDef.text =
//            "Attack: ${championList[position][8]} Def: ${championList[position][9]}"
//
//        // Load champion image using Glide library
//        val championKey = championList[position][10] // Assuming the champion key is stored at index 2
//        val imageUrl = championImageMap[championKey]
//        imageUrl?.let {
//            Log.d("ChampionAdapter", "Champion Key: $championKey, Image URL: $it")
//            Glide.with(holder.itemView.context)
//                .load(it)
//                .placeholder(R.drawable.mon) // Placeholder image while loading
//                .into(holder.championImg)
//        }?: Log.e("ChampionAdapter", "Image URL is null for champion key: $championKey") // Log error if image URL is null
//    }
//
//    override fun getItemCount() = championList.size
//
//    // Function to update the championImageMap
//    fun updateChampionImageMap(map: Map<String, String>) {
//        championImageMap = map
//        notifyDataSetChanged() // Notify RecyclerView to refresh
//    }
//}
//
//
//
