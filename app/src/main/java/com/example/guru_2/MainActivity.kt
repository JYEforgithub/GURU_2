package com.example.guru_2

/* 데이터(명언) 연동을 위해 Firebase remote config 및 JSONObject 사용 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var homeFragment : HomeFragment
    lateinit var todoFragment: TodoFragment
    private val quotePager: ViewPager2 by lazy {
        findViewById(R.id.viewPager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFragment = HomeFragment()
        todoFragment = TodoFragment()

        supportFragmentManager.beginTransaction().replace(R.id.containers, homeFragment).commit()

        val navigationBarView = findViewById<NavigationBarView>(R.id.bottom_navigation_view)
        navigationBarView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> supportFragmentManager.beginTransaction().replace(R.id.containers, homeFragment).commit()
                R.id.todo -> supportFragmentManager.beginTransaction().replace(R.id.containers, todoFragment).commit()
            }
            true
        }

        // initViews()
        initData()
    }

    private fun initData() {
        val remoteConfig = Firebase.remoteConfig

        // 비동기로 데이터를 가져옴
        remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0 // 앱을 열 때마다 패치
            }
        )
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if(it.isSuccessful) { // 패치 작업이 완료되면 JSON을 파싱
                val quotes = parseQuotesJson(remoteConfig.getString("quotes"))
                val isNameRevealed = remoteConfig.getBoolean("is_name_revealed")

                displayQuotesPager(quotes, isNameRevealed)
            }
        }
    }

    private fun parseQuotesJson(json: String): List<Quote> {
        val jsonArray = JSONArray(json)
        var jsonList = emptyList<JSONObject>()
        for(index in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index)
            jsonObject?.let {
                jsonList = jsonList + it
            }
        }

        // List<Quote>로 변환
        return jsonList.map {
            Quote(
                quote = it.getString("quote"),
                name = it.getString("name")
            )
        }
    }

    private fun displayQuotesPager(quotes:List<Quote>, isNameRevealed: Boolean) {
        // 데이터 리스트 받아 어댑터 생성
        val adapter = QuotePagerAdapter(
            quotes,
            isNameRevealed
        )
        quotePager.adapter = adapter
        // 앱을 실행시켰을 때 시작 위치가 매번 다르도록 난수 생성
        val range = (0..quotes.size)
        quotePager.setCurrentItem(range.random(), false)
    }
}