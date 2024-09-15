package code.madlife.foodfirstver

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import code.madlife.foodfirstver.databinding.ActivityMainBinding
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), FragmentManager.OnBackStackChangedListener {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.addOnBackStackChangedListener(this)
        NavigationManager.getInstance().init(this, supportFragmentManager, R.id.fragment_container)
    }

    override fun onBackStackChanged() {

    }
}