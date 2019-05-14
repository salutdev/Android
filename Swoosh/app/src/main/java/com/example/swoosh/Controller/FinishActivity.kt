package com.example.swoosh.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.swoosh.R
import com.example.swoosh.Utilities.EXTRA_LEAGUE
import com.example.swoosh.Utilities.EXTRA_SKILL
import kotlinx.android.synthetic.main.activity_finish.*

class FinishActivity : AppCompatActivity() {

    var skillLevel = Skill.None
    var selectedLeague: SelectedLeague? = SelectedLeague.None

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        val skill = intent?.extras?.get(EXTRA_SKILL)
        if (skill is Skill) {
            skillLevel = skill
        }

        val league = intent?.extras?.get(EXTRA_LEAGUE)
        if (league is SelectedLeague) {
            selectedLeague =  league
        }

        searchLeaguesText.text = "Looking for league is $selectedLeague; Skill level is $skill league near you"
    }
}
