package com.example.swoosh

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_league.*

enum class SelectedLeague {
    None,
    Mens,
    Womens,
    CoEd
}

class LeagueActivity : BaseActivity() {

    var selectedLeague = SelectedLeague.None

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)

        coedLeagueBtn.setOnClickListener {
            selectedLeague = SelectedLeague.CoEd
            womensLeagueBtn.isChecked = false
            mensLeagueBtn.isChecked = false
        }
    }

    fun leagueNextClicked(view: View) {
        if (selectedLeague != SelectedLeague.None) {
            val skillActivity = Intent(this, SkillActivity::class.java)
            skillActivity.putExtra(EXTRA_LEAGUE, selectedLeague)
            startActivity(skillActivity)
        } else {
            val toast = Toast.makeText(this, "Please select a league", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun onMensLeagueBtnClicked(view: View) {
        selectedLeague = SelectedLeague.Mens
        womensLeagueBtn.isChecked = false
        coedLeagueBtn.isChecked = false
    }

    fun onWomensLeagueBtnClicked(view: View) {
        selectedLeague = SelectedLeague.Womens
        mensLeagueBtn.isChecked = false
        coedLeagueBtn.isChecked = false
    }
}
