package uz.abduvali.football.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.abduvali.football.R
import uz.abduvali.football.adapters.TeamAdapter
import uz.abduvali.football.database.AppDatabase
import uz.abduvali.football.databinding.FragmentLeagueBinding
import uz.abduvali.football.utils.makeToast
import uz.abduvali.football.utils.setImage

class LeagueFragment : Fragment(R.layout.fragment_league) {
    //  val app ID= "ca-app-pub-6956226656976749~9480306211"
    //  val banner = "ca-app-pub-6956226656976749/6223587484"
    private var mInterstitialAd: InterstitialAd? = null
    private val binding by viewBinding(FragmentLeagueBinding::bind)
    private lateinit var leagueId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            leagueId = it.getString("league").toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MobileAds.initialize(requireContext()) {}
        val build = AdRequest.Builder().build()
        val league = AppDatabase.getInstance(requireContext())
            .leagueDao().getLeagueById(leagueId)
        binding.apply {
            back.setOnClickListener {
                findNavController().popBackStack()
            }
            adView.loadAd(build)
            val list = AppDatabase.getInstance(requireContext())
                .teamDao().getTeamsByLeagueId(leagueId)
            image.setImage(league.logo)
            val adapter = TeamAdapter(true)
            adapter.submitList(list)
            rv.adapter = adapter
        }
        bannerAd(requireContext())
    }

    private fun bannerAd(context: Context) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    context.makeToast(adError.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
        }
    }
}