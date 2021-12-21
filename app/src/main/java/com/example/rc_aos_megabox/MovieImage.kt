package com.example.rc_aos_megabox

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.rc_aos_megabox.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import retrofit2.http.GET
import java.io.*
import java.lang.RuntimeException
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import kotlin.coroutines.coroutineContext

