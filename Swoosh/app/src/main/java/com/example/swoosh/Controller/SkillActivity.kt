package com.example.swoosh.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.swoosh.R
import com.example.swoosh.Utilities.EXTRA_LEAGUE
import com.example.swoosh.Utilities.EXTRA_SKILL
import kotlinx.android.synthetic.main.activity_skill.*

enum class Skill {
    None,
    Beginner,
    Baller
}

class SkillActivity : BaseActivity() {

    var selectedLeague: SelectedLeague = SelectedLeague.None
    var skillLevel = Skill.None

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill)
//        selectedLeague = intent.getSerializableExtra(EXTRA_LEAGUE) as? SelectedLeague
        val league = intent?.extras?.get(EXTRA_LEAGUE)
        if (league is SelectedLeague) {
            selectedLeague =  league
        }

        beginnerBtn.setOnClickListener {
            ballerBtn.isChecked = false
            skillLevel = Skill.Beginner
        }

        finishBtn.setOnClickListener {
            if (skillLevel != Skill.None) {
                val finishActivity = Intent(this, FinishActivity::class.java)
                finishActivity.putExtra(EXTRA_SKILL, skillLevel)
                finishActivity.putExtra(EXTRA_LEAGUE, selectedLeague)
                startActivity(finishActivity)
            } else {
                Toast.makeText(this, "Select skill level", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun ballerBtnClicked(view: View) {
        beginnerBtn.isChecked = false
        skillLevel = Skill.Baller
    }
}
