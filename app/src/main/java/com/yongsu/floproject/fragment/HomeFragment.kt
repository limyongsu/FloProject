package com.yongsu.floproject.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.yongsu.floproject.R
import com.yongsu.floproject.adapter.AlbumRVAdapter
import com.yongsu.floproject.adapter.BannerVPAdapter
import com.yongsu.floproject.adapter.PannelVPAdapter
import com.yongsu.floproject.databinding.FragmentHomeBinding
import com.yongsu.floproject.datas.Album

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val sliderHandler = Handler(Looper.getMainLooper())
    private var sliderRunnable: Runnable? = null

    private val albumAdapter = AlbumRVAdapter(Dummy())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initBannerVP()
        initPannelVP()

        binding.homeTodayMusicAlbumRv.adapter = albumAdapter
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.homeTodayMusicAlbumRv.layoutManager = manager

        return binding.root
    }

    private fun Dummy() : ArrayList<Album>{
        val dummy1 = Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp)
        val dummy2 = Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2)
        val dummy3 = Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3)
        val dummy4 = Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4)
        val dummy5 = Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5)
        val dummy6 = Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6)

        val arr = ArrayList<Album>()
        arr.add(dummy1)
        arr.add(dummy2)
        arr.add(dummy3)
        arr.add(dummy4)
        arr.add(dummy5)
        arr.add(dummy6)

        return arr
    }

    private fun initAlbumFragment(titleTV : String, singerTV : String){
        with(binding){
            val albumFragment = AlbumFragment().apply {
                arguments = Bundle().apply {
                    putString("albumTitle", titleTV)
                    putString("albumSinger", singerTV)
                }
            }
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frm, albumFragment)
                .addToBackStack(null)
                .commit()
        }
    }



    private fun initBannerVP(){
        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun initPannelVP(){
        val pannelAdapter = PannelVPAdapter(this)
        pannelAdapter.addFragment(PannelFragment(R.drawable.img_first_album_default))
        pannelAdapter.addFragment(PannelFragment(R.drawable.img_album_exp4))
        pannelAdapter.addFragment(PannelFragment(R.drawable.img_album_exp5))
        pannelAdapter.addFragment(PannelFragment(R.drawable.img_album_exp6))
        binding.homePannelBackgroundVp.adapter = pannelAdapter
        binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator = binding.Indicator;
        indicator.setViewPager(binding.homePannelBackgroundVp);

        sliderRunnable = Runnable {
            val viewPager = binding.homePannelBackgroundVp
            viewPager.currentItem =
                if (viewPager.currentItem < pannelAdapter.itemCount - 1) {
                    viewPager.currentItem + 1
                } else {
                    0
                }
            sliderHandler.postDelayed(sliderRunnable!!, 3000L)
        }
        sliderHandler.post(sliderRunnable!!)
    }
}