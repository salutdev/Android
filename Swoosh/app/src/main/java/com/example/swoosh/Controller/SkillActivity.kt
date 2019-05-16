package com.example.swoosh.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.swoosh.Model.Player
import com.example.swoosh.R
import com.example.swoosh.Utilities.EXTRA_LEAGUE
import com.example.swoosh.Utilities.EXTRA_SKILL
import kotlinx.android.synthetic.main.activity_skill.*
import com.example.swoosh.Model.SelectedLeague
import com.example.swoosh.Model.Skill
import com.example.swoosh.Utilities.EXTRA_PLAYER

class SkillActivity : BaseActivity() {

    var selectedLeague: SelectedLeague = SelectedLeague.None
    var skillLevel = Skill.None
    lateinit var player: Player

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(EXTRA_PLAYER, player)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            player = savedInstanceState.getParcelable(EXTRA_PLAYER)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill)
//        selectedLeague = intent.getSerializableExtra(EXTRA_LEAGUE) as? SelectedLeague
        player = intent.getParcelableExtra<Player>(EXTRA_PLAYER)
        val league = intent?.extras?.get(EXTRA_LEAGUE)

        if (league is SelectedLeague) {
            selectedLeague =  league
        }

        beginnerBtn.setOnClickListener {
            ballerBtn.isChecked = false
            skillLevel = Skill.Beginner
            player.skill = Skill.Beginner.name
        }

        finishBtn.setOnClickListener {
            if (player.skill != Skill.None.name) {
                val finishActivity = Intent(this, FinishActivity::class.java)
                finishActivity.putExtra(EXTRA_SKILL, skillLevel)
                finishActivity.putExtra(EXTRA_LEAGUE, selectedLeague)
                finishActivity.putExtra(EXTRA_PLAYER, player)
                startActivity(finishActivity)
            } else {
                Toast.makeText(this, "Select skill level", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun ballerBtnClicked(view: View) {
        beginnerBtn.isChecked = false
        skillLevel = Skill.Baller
        player.skill = Skill.Baller.name
    }
}
