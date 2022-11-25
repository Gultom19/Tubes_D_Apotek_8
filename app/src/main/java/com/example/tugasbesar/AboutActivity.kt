package com.example.tugasbesar

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage


class AboutActivity : AppCompatActivity() {
    private var fragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        fragmentManager = supportFragmentManager

        // new instance is created and data is took from an
        // array list known as getDataonborading
        val paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataforOnboarding())
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()

        // fragmentTransaction method is used
        // do all the transactions or changes
        // between different fragments
        fragmentTransaction.add(R.id.frame_layout, paperOnboardingFragment)

        // all the changes are committed
        fragmentTransaction.commit()
    }

    private fun getDataforOnboarding(): ArrayList<PaperOnboardingPage>? {

        // the first string is to show the main title ,
        // second is to show the message below the
        // title, then color of background is passed ,
        // then the image to show on the screen is passed
        // and at last icon to navigate from one screen to other
        val source = PaperOnboardingPage(
            "Aplikasi Hans Apotik",
            "Aplikasi beli obat secara online ya... Hans Apotik. Salah satu aplikasi beli obat secara online adalah Hans Apotik. Hans Apotik yang memiliki slogan #obataslikapanpun hadir sebagai Platform Beli Obat Online pertama di Indonesia yang benar-benar buka 24 jam nonstop dan terlengkap di Indonesia. Aplikasi beli obat online",
            Color.parseColor("#ffb174"),
            R.drawable.logoapk,
            org.osmdroid.library.R.drawable.person
        )
        val source1 = PaperOnboardingPage(
            "Komitmen kami",
            "AMAN DAN TERPERCAYA, KAPANPUN DIMANAPUN, KONSULTASI GRATIS, Hans Apotik Paling KOMPLIT",
            Color.parseColor("#22eaaa"),
            R.drawable.shakehand,
            R.drawable.bg_circle
        )
        val source2 = PaperOnboardingPage(
            "Kenapa harus beli obat secara Online?",
            "Saat ini sudah banyak dan menjamur pembelian produk secara online. Dengan teknologi yang semakin maju, banyak industri yang menghadirkan aplikasi beli barang yang diinginkan dan dibutuhkan secara online. Masyarakat dimudahkan dengan cara pembelian secara online. Customer hanya perlu smartphone, selanjutnya pilih produk yang diinginkan dan barang pilihannya akan dikirimkan ke alamat sesuai yang diinginkan.",
            Color.parseColor("#ee5a5a"),
            R.drawable.thinking,
            R.drawable.bg_triangle
        )

        // array list is used to store
        // data of onbaording screen
        val elements: ArrayList<PaperOnboardingPage> = ArrayList()

        // all the sources(data to show on screens)
        // are added to array list
        elements.add(source)
        elements.add(source1)
        elements.add(source2)
        return elements
    }
}