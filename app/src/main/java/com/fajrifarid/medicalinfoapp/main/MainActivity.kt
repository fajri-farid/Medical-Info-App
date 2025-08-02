package com.fajrifarid.medicalinfoapp.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fajrifarid.medicalinfoapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mengecek apakah activity ini baru pertama kali dibuat (bukan hasil rotasi/saved state)
        if (savedInstanceState == null) {
            // Menampilkan fragment utama (MainFragment) ke dalam container yang ada di layout
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container, // ID container (FrameLayout) tempat fragment akan dimasukkan
                    MainFragment.newInstance("", "") // Memanggil fragment utama, dengan parameter kosong
                )
                .commitNow() // Segera jalankan transaksinya
        }
    }
}