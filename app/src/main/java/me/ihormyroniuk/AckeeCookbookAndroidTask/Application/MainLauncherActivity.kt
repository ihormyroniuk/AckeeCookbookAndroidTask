package me.ihormyroniuk.AckeeCookbookAndroidTask.Application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.ihormyroniuk.AckeeCookbookAndroidTask.Presentation.PresentationDelegate
import java.lang.ref.WeakReference

class MainLauncherActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Application).onCreateMainLauncherActivity(this)
    }

}
