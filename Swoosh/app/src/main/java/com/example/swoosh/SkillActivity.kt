package com.example.swoosh

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SkillActivity : AppCompatActivity() {

    var selectedLeague = SelectedLeague.None

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill)
//        selectedLeague = intent.getSerializableExtra(EXTRA_LEAGUE) as SelectedLeague
        selectedLeague = intent.extras.get(EXTRA_LEAGUE) as SelectedLeague

        println("Hello $selectedLeague")
    }
}
