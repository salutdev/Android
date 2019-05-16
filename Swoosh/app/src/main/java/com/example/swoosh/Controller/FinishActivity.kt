package com.example.swoosh.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.swoosh.Model.Player
import com.example.swoosh.R
import com.example.swoosh.Utilities.EXTRA_LEAGUE
import com.example.swoosh.Utilities.EXTRA_SKILL
import kotlinx.android.synthetic.main.activity_finish.*
import com.example.swoosh.Model.SelectedLeague
import com.example.swoosh.Model.Skill
import com.example.swoosh.Utilities.EXTRA_PLAYER

class FinishActivity : AppCompatActivity() {

    var skillLevel = Skill.None
    var selectedLeague: SelectedLeague? = SelectedLeague.None
    lateinit var player: Player

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

        player = intent.getParcelableExtra<Player>(EXTRA_PLAYER)
        searchLeaguesText.text = "Looking for ${player.league} ${player.skill} league near you"
    }
}
