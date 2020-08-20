package me.ihormyroniuk.AckeeCookbookAndroidTask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.RecipesListScreenActivity


class ApplicationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, RecipesListScreenActivity::class.java)
        startActivity(intent);
        finish();
    }

}
