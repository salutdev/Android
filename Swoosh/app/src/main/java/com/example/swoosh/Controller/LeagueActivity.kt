package com.example.swoosh.Controller

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.swoosh.Model.Player
import com.example.swoosh.R
import com.example.swoosh.Utilities.EXTRA_LEAGUE
import kotlinx.android.synthetic.main.activity_league.*
import com.example.swoosh.Model.SelectedLeague
import com.example.swoosh.Model.Skill
import com.example.swoosh.Utilities.EXTRA_PLAYER

class LeagueActivity : BaseActivity() {

    var selectedLeague = SelectedLeague.None
    var player = Player(SelectedLeague.None.name, Skill.None.name)

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(EXTRA_PLAYER, player)
        Log.d(TAG,"Leageue: --- onSaveInstanceState -----")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null) {
            player = savedInstanceState.getParcelable(EXTRA_PLAYER)
        }
        Log.d(TAG,"Leageue: --- onRestoreInstanceState -----")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)

        coedLeagueBtn.setOnClickListener {
            selectedLeague = SelectedLeague.CoEd
            player.league = SelectedLeague.CoEd.name
            womensLeagueBtn.isChecked = false
            mensLeagueBtn.isChecked = false
        }
    }

    fun leagueNextClicked(view: View) {
        if (player.league != SelectedLeague.None.name) {
            val skillActivity = Intent(this, SkillActivity::class.java)
            skillActivity.putExtra(EXTRA_LEAGUE, selectedLeague)
            skillActivity.putExtra(EXTRA_PLAYER, player)
            startActivity(skillActivity)
        } else {
            val toast = Toast.makeText(this, "Please select a league", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun onMensLeagueBtnClicked(view: View) {
        selectedLeague = SelectedLeague.Mens
        player.league = SelectedLeague.Mens.name
        womensLeagueBtn.isChecked = false
        coedLeagueBtn.isChecked = false
    }

    fun onWomensLeagueBtnClicked(view: View) {
        selectedLeague = SelectedLeague.Womens
        player.league = SelectedLeague.Womens.name
        mensLeagueBtn.isChecked = false
        coedLeagueBtn.isChecked = false
    }
}
