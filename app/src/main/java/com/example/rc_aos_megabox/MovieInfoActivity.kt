package com.example.rc_aos_megabox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.rc_aos_megabox.databinding.ActivityMovieInfoBinding
import com.example.rc_aos_megabox.databinding.FragmentMovieInfoBinding
import com.example.rc_aos_megabox.movieInfo.*
import com.example.rc_aos_megabox.tablayout.BoxOfficeFragment
import com.example.rc_aos_megabox.tablayout.DollCinemaFragment
import com.example.rc_aos_megabox.tablayout.PlannedScreeningFragment
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MovieInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieInfoBinding
    private var API_KEY = "b759981996e94214280b6ef4dc3fe99b"

    lateinit var tab1: MovieInfoFragment
    lateinit var tab2: MovieCustomerFragment
    lateinit var tab3: MoviePostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieInfoBinding.inflate(layoutInflater)



        var id = Integer.parseInt(intent.getStringExtra("id"))
        binding.ivPoster.setImageResource(BoxOfficeFragment.imgArray[id!!])
        binding.tvRank.text = intent.getStringExtra("rank") + "위 (" +
            intent.getStringExtra("buy") + ")"
        binding.tvStar.text = "평점 " + intent.getStringExtra("star")


        var movieCd =  intent.getStringExtra("movieCd")
        //Toast.makeText(this, intent.getStringExtra("movieCd"), Toast.LENGTH_SHORT).show()
        if (movieCd != null) {
            getMovieInfoData(API_KEY, movieCd)
            //Toast.makeText(this, intent.getStringExtra("movieCd"), Toast.LENGTH_SHORT).show()
        }
        setContentView(binding.root)

        binding.ivLeftArrow.setOnClickListener {
            finish()
        }

        tab1 = MovieInfoFragment()
        tab2 = MovieCustomerFragment()
        tab3 = MoviePostFragment()

        supportFragmentManager.beginTransaction().add(R.id.tab_framelayout, tab1).commit()

        binding.infoTablayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> replaceView(tab1)
                    1 -> replaceView(tab2)
                    2 -> replaceView(tab3)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun replaceView(tab: Fragment){

        val bundle = Bundle()
        bundle.putString("movieCd",intent.getStringExtra("movieCd"))
        bundle.putString("id", intent.getStringExtra("id"))
        tab.arguments = bundle

        var selectedFragment: Fragment?= null
        selectedFragment = tab
        selectedFragment?.let{
            supportFragmentManager.beginTransaction()
                .replace(R.id.tab_framelayout, it).commit()
        }
    }

    private fun getMovieInfoData(key: String,movieCd: String){
        val MovieInfoInterface = MovieInfoRetrofitClient.sRetrofit.create(MovieInfoInterface::class.java)

        MovieInfoInterface.getMovieInfo(key, movieCd).enqueue(object: retrofit2.Callback<MovieInfoResponse>{
            override fun onResponse(
                call: Call<MovieInfoResponse>,
                response: Response<MovieInfoResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body() as MovieInfoResponse

                    binding.tvMovieNm.text = result.movieInfoResult.movieInfo.movieNm
                    binding.tvEngMoiveNm.text = result.movieInfoResult.movieInfo.movieNmEn
                    //Log.d("watchgrade", result.movieInfoResult.movieInfo.audits.get(0).watchGradeNm)
                    binding.tvWatchGrade.text = result.movieInfoResult.movieInfo.audits.get(0).watchGradeNm

                }else{
                    Log.d("fail", "error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieInfoResponse>, t: Throwable) {
                Log.d("onFailure", t.message ?: "통신오류")
            }

        })
    }
}