package com.example.rc_aos_megabox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.rc_aos_megabox.databinding.ActivityStartBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class StartActivity : AppCompatActivity() {

    private val fl: FrameLayout by lazy {
        binding.framelayout
    }

    private val bn: BottomNavigationView by lazy {
        binding.navigationBar
    }

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(fl.id, HomeFragment()).commit()

        bn.setOnItemSelectedListener {
            replaceFragment(
                when(it.itemId){
                    R.id.home_tab -> HomeFragment()
                    R.id.store_tab -> StoreFragment()
                    R.id.ticket_tab -> TicketFragment()
                    R.id.mobile_order_tab -> MobileOrderFragment()
                    else -> MyFragment()
                }
            )
            true
        }
    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }
}