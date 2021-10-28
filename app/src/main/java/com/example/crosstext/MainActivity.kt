package com.example.crosstext

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.crosstext.adapters.ViewPagerAdapter
import com.example.crosstext.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var toggle:ActionBarDrawerToggle
    private lateinit var auth:FirebaseAuth
    private var user:FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.Open,R.string.Close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser

        Handler().postDelayed({
            if (user == null){
                val intent = Intent(this,SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        },200)


        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> {
                        binding.bottomNavigationView.menu.findItem(R.id.dataFragment).isChecked = true
                    }
                    1 -> {
                        binding.bottomNavigationView.menu.findItem(R.id.bookMarkFragment).isChecked = true
                    }
                    2 -> {
                        binding.bottomNavigationView.menu.findItem(R.id.linkFragment).isChecked = true
                    }
                    3 -> {
                        binding.bottomNavigationView.menu.findItem(R.id.settingFragment).isChecked = true
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        val adapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = adapter

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.dataFragment -> {
                    binding.viewPager.currentItem = 0
                }
                R.id.bookMarkFragment -> {
                    binding.viewPager.currentItem = 1
                }
                R.id.linkFragment -> {
                    binding.viewPager.currentItem = 2
                }
                R.id.settingFragment -> {
                    binding.viewPager.currentItem = 3
                }
            }
            true
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.dataFragment -> {
                    binding.drawerLayout.closeDrawers()
                    binding.viewPager.currentItem = 0
                }
                R.id.bookMarkFragment -> {
                    binding.drawerLayout.closeDrawers()
                    binding.viewPager.currentItem = 1
                }
                R.id.linkFragment -> {
                    binding.drawerLayout.closeDrawers()
                    binding.viewPager.currentItem = 2
                }
                R.id.settingFragment -> {
                    binding.drawerLayout.closeDrawers()
                    binding.viewPager.currentItem = 3
                }
            }
            true
        }
        setDrawerHeaderLayout()
    }

    private fun setDrawerHeaderLayout() {
        val headerView = binding.navView.inflateHeaderView(R.layout.header_layout)
        val circleImg:CircleImageView = headerView.findViewById(R.id.profileImg)
        val profileTv: TextView = headerView.findViewById(R.id.userNameTV)
        profileTv.text = user?.displayName
        Glide.with(this).load(user?.photoUrl).into(circleImg)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}