package com.example.swoosh

import android.content.Intent
import android.os.Bundle
import android.view.View

class LeagueActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)

//        leagueNextBtn.setOnClickListener {
//
//            val skillIntent = Intent(this, SkillActivity::class.java)
//            startActivity(skillIntent)
//        }
    }

    fun leagueNextClicked(view: View) {
        val skillActivity = Intent(this, SkillActivity::class.java)
        startActivity(skillActivity)
    }
}
