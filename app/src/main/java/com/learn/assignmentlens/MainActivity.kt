package com.learn.assignmentlens

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.learn.assignmentlens.adapters.BodyRecyclerAdapter
import com.learn.assignmentlens.adapters.HeaderAdapter
import com.learn.assignmentlens.adapters.ImageSliderAdapter
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HeaderAdapter

    private lateinit var bodyRecyclerView: RecyclerView
    private lateinit var bodyAdapter: BodyRecyclerAdapter

    private lateinit var viewPager: ViewPager2
    private lateinit var timer: Timer
    private lateinit var runningText: TextView

    private lateinit var toolbar: Toolbar

    private lateinit var contentLayout: FrameLayout

    private val imageList = listOf(
        R.drawable.homeimage1,
        R.drawable.homeimage2,
        R.drawable.homeimage3,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerView)
        bodyRecyclerView = findViewById(R.id.verticalRecyclerView)
        runningText = findViewById(R.id.runningText)
        contentLayout = findViewById(R.id.contentLayout)

        /*// Set custom ActionBar
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false) // Hide default title
        actionBar?.setDisplayHomeAsUpEnabled(true)*/

        val inflater = LayoutInflater.from(this)
        val defaultLayout = inflater.inflate(R.layout.irpof_layout, contentLayout, false)
        contentLayout.addView(defaultLayout)

        val headerText = listOf("Home", "Organization", "DITS/Panel", "IRMS", "Events", "Seniority", "Circulars", "News/Article", "IRPOBF", "Links")
        val bodyTexts = listOf("Who are we: IRPOF", "Mission & Vision", "Recent Events", "Images", "Videos")

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
        adapter = HeaderAdapter(headerText)
        recyclerView.adapter = adapter

        viewPager = findViewById(R.id.viewPager)

        val adapter = ImageSliderAdapter(imageList)
        viewPager.adapter = adapter

        // Auto-scroll
        timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(), 1000, 3000)

        startRunningTextAnimation()

        bodyRecyclerView.layoutManager = LinearLayoutManager(this)
        bodyRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        bodyAdapter = BodyRecyclerAdapter(bodyTexts) {position->
            val inflater = LayoutInflater.from(this)
            val layout = when (position) {
                0 -> inflater.inflate(R.layout.irpof_layout, null, false)
                1 -> inflater.inflate(R.layout.mission_vision_layout, null, false)
                2 -> inflater.inflate(R.layout.recent_events_layout, null, false)
                3 -> inflater.inflate(R.layout.image_layout, null, false)
                4 -> inflater.inflate(R.layout.videos_layout, null, false)
                else -> inflater.inflate(R.layout.irpof_layout, null, false)
            }
            contentLayout.removeAllViews()
            contentLayout.addView(layout)
        }
        bodyRecyclerView.adapter = bodyAdapter
    }

    private fun startRunningTextAnimation() {
        runningText.postDelayed({
            val maxWidth = runningText.width
            val textWidth = runningText.paint.measureText(runningText.text.toString())
            val duration = (textWidth / maxWidth * 10000).toLong() // Adjust scrolling speed here

            runningText.animate()
                .translationXBy(-textWidth)
                .setDuration(duration)
                .withEndAction {
                    runningText.translationX = maxWidth.toFloat()
                    runningText.postDelayed({
                        runningText.translationX = 0f
                        startRunningTextAnimation() // Restart animation
                    }, 300)
                }
                .start()
        }, 300)
    }

    override fun onDestroy() {
        super.onDestroy()

        timer.cancel()
    }

    inner class SliderTimer : TimerTask() {
        override fun run() {
            viewPager.post {
                if (viewPager.currentItem < imageList.size - 1) {
                    viewPager.currentItem += 1
                } else {
                    viewPager.currentItem = 0
                }
            }
        }
    }
}