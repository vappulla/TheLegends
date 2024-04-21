package com.example.thelegends

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.firebase.auth.FirebaseAuth
import okhttp3.Headers
import org.json.JSONArray

class MainActivity : AppCompatActivity(), ChampionAdapter.OnChampionClickListener {

    private lateinit var championList: MutableList<MutableList<String>>
    private lateinit var originalChampionList: MutableList<MutableList<String>>
    private lateinit var rvChampion: RecyclerView
    private lateinit var adapter: ChampionAdapter
    private lateinit var signOutButton: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        rvChampion = findViewById(R.id.champion_list)
        championList = mutableListOf()
        originalChampionList = mutableListOf()
        adapter = ChampionAdapter(championList, emptyMap(), this) // Initialize with an empty map
        rvChampion.adapter = adapter
        rvChampion.layoutManager = LinearLayoutManager(this)
        rvChampion.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        signOutButton = findViewById(R.id.btn_sign_out)
        signOutButton.setOnClickListener {
            signOut()
        }

        getChampionData()
    }

    private fun signOut() {
        mAuth.signOut()
        Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        // You can add an intent to move to a sign-in activity or handle the sign-in as per your requirement
    }

    private fun getChampionData() {
        val client = AsyncHttpClient()
        client["https://ddragon.leagueoflegends.com/cdn/14.8.1/data/en_US/champion.json", object :
            JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                val championInfo = json.jsonObject.getJSONObject("data")
                val info = JSONArray()

                // Iterate through all keys to get their values
                val keysIterator = championInfo.keys()
                while (keysIterator.hasNext()) {
                    val key = keysIterator.next()
                    info.put(championInfo.get(key))
                }

                val championImageMap = mutableMapOf<String, String>() // Initialize map to hold champion image URLs

                for (i in 0 until info.length()) {
                    val champ = info.getJSONObject(i)
                    val arr = mutableListOf<String>()
                    // Get name, title, blurb, attack, defense, magic, difficulty, partype, hp, and mp
                    arr.add(champ.getString("name"))
                    arr.add(champ.getString("title"))
                    arr.add(champ.getString("blurb"))
                    arr.add(champ.getJSONObject("info").getInt("attack").toString())
                    arr.add(champ.getJSONObject("info").getInt("defense").toString())
                    arr.add(champ.getJSONObject("info").getInt("magic").toString())
                    arr.add(champ.getJSONObject("info").getInt("difficulty").toString())
                    arr.add(champ.getString("partype"))
                    arr.add(champ.getJSONObject("stats").getInt("hp").toString())
                    arr.add(champ.getJSONObject("stats").getInt("mp").toString())
                    arr.add(champ.getString("key"))

                    championImageMap[champ.getString("key")] =
                        "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/${champ.getString(
                            "id"
                        )}_0.jpg"
                    championList.add(arr)
                }

                adapter.updateChampionImageMap(championImageMap)
                originalChampionList.addAll(championList)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers,
                response: String,
                throwable: Throwable
            ) {
                Log.e("MainActivity", "Fail JSON: $response")
            }
        }]
    }

    override fun onChampionClick(champion: MutableList<String>) {
        val intent = ChampionDetailsActivity.newIntent(this, champion)
        startActivity(intent)
    }
}











//package com.example.thelegends
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.Menu
//import android.widget.SearchView
//import androidx.recyclerview.widget.DividerItemDecoration
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.codepath.asynchttpclient.AsyncHttpClient
//import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
//import okhttp3.Headers
//import org.json.JSONArray
//
//class MainActivity : AppCompatActivity(), ChampionAdapter.OnChampionClickListener {
//
//    private lateinit var championList: MutableList<MutableList<String>>
//    private lateinit var originalChampionList: MutableList<MutableList<String>>
//    private lateinit var rvChampion: RecyclerView
//    private lateinit var adapter: ChampionAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        rvChampion = findViewById(R.id.champion_list)
//        championList = mutableListOf()
//        originalChampionList = mutableListOf()
//        adapter = ChampionAdapter(championList, emptyMap(), this) // Initialize with an empty map
//        rvChampion.adapter = adapter
//        rvChampion.layoutManager = LinearLayoutManager(this)
//        rvChampion.addItemDecoration(
//            DividerItemDecoration(
//                this,
//                LinearLayoutManager.VERTICAL
//            )
//        )
//
//        getChampionData()
//    }
//
//    private fun getChampionData() {
//        val client = AsyncHttpClient()
//        client["https://ddragon.leagueoflegends.com/cdn/14.8.1/data/en_US/champion.json", object :
//            JsonHttpResponseHandler() {
//            override fun onSuccess(
//                statusCode: Int,
//                headers: Headers,
//                json: JsonHttpResponseHandler.JSON
//            ) {
//                val championInfo = json.jsonObject.getJSONObject("data")
//                val info = JSONArray()
//
//                // Iterate through all keys to get their values
//                val keysIterator = championInfo.keys()
//                while (keysIterator.hasNext()) {
//                    val key = keysIterator.next()
//                    info.put(championInfo.get(key))
//                }
//
//                val championImageMap = mutableMapOf<String, String>() // Initialize map to hold champion image URLs
//
//                for (i in 0 until info.length()) {
//                    val champ = info.getJSONObject(i)
//                    val arr = mutableListOf<String>()
//                    // Get name, title, blurb, attack, defense, magic, difficulty, partype, hp, and mp
//                    arr.add(champ.getString("name"))
//                    arr.add(champ.getString("title"))
//                    arr.add(champ.getString("blurb"))
//                    arr.add(champ.getJSONObject("info").getInt("attack").toString())
//                    arr.add(champ.getJSONObject("info").getInt("defense").toString())
//                    arr.add(champ.getJSONObject("info").getInt("magic").toString())
//                    arr.add(champ.getJSONObject("info").getInt("difficulty").toString())
//                    arr.add(champ.getString("partype"))
//                    arr.add(champ.getJSONObject("stats").getInt("hp").toString())
//                    arr.add(champ.getJSONObject("stats").getInt("mp").toString())
//                    arr.add(champ.getString("key"))
//
//                    championImageMap[champ.getString("key")] =
//                        "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/${champ.getString(
//                            "id"
//                        )}_0.jpg"
//                    championList.add(arr)
//                }
//
//                adapter.updateChampionImageMap(championImageMap)
//                originalChampionList.addAll(championList)
//            }
//
//            override fun onFailure(
//                statusCode: Int,
//                headers: Headers,
//                response: String,
//                throwable: Throwable
//            ) {
//                Log.e("MainActivity", "Fail JSON: $response")
//            }
//        }]
//    }
//
//
//    override fun onChampionClick(champion: MutableList<String>) {
//        val intent = ChampionDetailsActivity.newIntent(this, champion)
//        startActivity(intent)
//    }
//}
//
//
//
//
//
