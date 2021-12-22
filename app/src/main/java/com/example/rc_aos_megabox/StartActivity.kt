package com.example.rc_aos_megabox

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        binding.btnCoupon.setOnClickListener {
            // alert dialog
            showLoginPop()
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }

    private fun showLoginPop(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.alert_pop, null)
        val textView: TextView = view.findViewById(R.id.textView)
        textView.text = "AlertDialog"

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Dialog test")
            .setPositiveButton("확인"){ dialog, which ->
                Toast.makeText(applicationContext, "ok", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("취소", null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }
}