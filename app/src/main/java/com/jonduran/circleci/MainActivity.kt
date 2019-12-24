package com.jonduran.circleci

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jonduran.circleci.databinding.ActivityMainBinding
import com.jonduran.circleci.databinding.ActivityMainBinding.inflate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    internal lateinit var component: MainComponent

    init {
        lifecycleScope.launchWhenCreated {
            component = inject()
            binding = inflate(layoutInflater)
            setContentView(binding.root)
            supportFragmentManager.beginTransaction()
                .add(R.id.content, BuildListFragment())
                .commit()
        }
    }

    private fun MainActivity.inject(): MainComponent {
        return CircleCiApp.component.mainComponent()
            .create()
            .apply { inject(this@inject) }
    }
}
