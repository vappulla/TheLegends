package com.example.thelegends

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChampionDetailsActivity : AppCompatActivity() {

    companion object {
        const val CHAMPION_DETAILS_KEY = "champion_details"

        fun newIntent(context: Context, champion: MutableList<String>): Intent {
            val intent = Intent(context, ChampionDetailsActivity::class.java)
            intent.putExtra(CHAMPION_DETAILS_KEY, ArrayList(champion))
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_champion_details)

        val championDetails = intent.getSerializableExtra(CHAMPION_DETAILS_KEY) as ArrayList<String>

        findViewById<TextView>(R.id.txtChampionName).text = championDetails[0]
        findViewById<TextView>(R.id.txtChampionTitle).text = championDetails[1]
        findViewById<TextView>(R.id.txtChampionHPAndMP).text = "HP: ${championDetails[8]} MP: ${championDetails[9]}"
        findViewById<TextView>(R.id.txtChampionAttackAndDef).text = "Attack: ${championDetails[3]} Def: ${championDetails[4]}"
        findViewById<TextView>(R.id.txtChampionBlurb).text = championDetails[2]
    }
}
