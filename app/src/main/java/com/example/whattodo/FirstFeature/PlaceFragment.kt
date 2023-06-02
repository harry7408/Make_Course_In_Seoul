package com.example.whattodo.FirstFeature

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.whattodo.R
import com.example.whattodo.databinding.FragmentPlaceBinding
import com.example.whattodo.datas.Category
import com.example.whattodo.datas.Store
import com.example.whattodo.network.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "PlaceFragment"

/* 첫번째 장소 보여주기 기능이 있는 페이지 */
class PlaceFragment : Fragment(), PlaceFragmentAdapter.ItemClickListener {
    private lateinit var binding: FragmentPlaceBinding
    private lateinit var categoryAdapter: PlaceFragmentAdapter


    private val categoryList = mutableListOf<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoryList()
        initRecyclerView()
    }


    private fun initRecyclerView() {
        categoryAdapter = PlaceFragmentAdapter(categoryList, this)
        binding.categoryRecyclerView.apply {
            adapter = categoryAdapter
            categoryAdapter.notifyDataSetChanged()
            layoutManager = GridLayoutManager(context, 4)
        }
    }

    override fun onClick(category: Category) {
        val serverRequest = category.title
        val requestCall = RetrofitAPI.requestCategoryService.requestCategoryList(serverRequest)
            .enqueue(object : Callback<List<Store>> {
                override fun onResponse(call: Call<List<Store>>, response: Response<List<Store>>) {
                    if (response.isSuccessful) {
                        val responseData = ArrayList<Store>()
                        response.body()?.let { it1 -> responseData.addAll(it1) }
                        val intent = Intent(activity, StoreListShowActivity::class.java)
                        intent.putExtra("selected", responseData)
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "NULL RETURNED")
                    }
                }

                override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                    t.printStackTrace()
                }

            })

    }

    private fun initCategoryList() {
        categoryList.add(0, Category(R.drawable.d_1, "수상스포츠"))
        categoryList.add(1, Category(R.drawable.d_2, "클라이밍"))
        categoryList.add(2, Category(R.drawable.d_3, "수영장"))
        categoryList.add(3, Category(R.drawable.d_4, "스킨스쿠버"))
        categoryList.add(4, Category(R.drawable.d_5, "스케이트장"))
        categoryList.add(5, Category(R.drawable.d_6, "산"))
        categoryList.add(6, Category(R.drawable.d_7, "계곡"))
        categoryList.add(7, Category(R.drawable.d_8, "테마파크"))
        categoryList.add(8, Category(R.drawable.d_9, "워터테마파크"))
        categoryList.add(9, Category(R.drawable.d_10, "눈썰매장"))
        categoryList.add(10, Category(R.drawable.d_11, "탁구"))
        categoryList.add(11, Category(R.drawable.d_12, "볼링"))
        categoryList.add(12, Category(R.drawable.d_13, "사격&궁도"))
        categoryList.add(13, Category(R.drawable.d_14, "스크린야구"))
        categoryList.add(14, Category(R.drawable.d_15, "스크린골프"))
        categoryList.add(15, Category(R.drawable.d_16, "오락실"))
        categoryList.add(16, Category(R.drawable.d_17, "실내낚시"))
        categoryList.add(17, Category(R.drawable.d_18, "만화카페"))
        categoryList.add(18, Category(R.drawable.d_19, "VR"))
        categoryList.add(19, Category(R.drawable.d_20, "방탈출카페"))
        categoryList.add(20, Category(R.drawable.d_21, "보드게임카페"))
        categoryList.add(21, Category(R.drawable.d_22, "터프팅"))
        categoryList.add(22, Category(R.drawable.d_23, "캔들,향수,비누"))
        categoryList.add(23, Category(R.drawable.d_24, "식물"))
        categoryList.add(24, Category(R.drawable.d_25, "켈리그라피"))
        categoryList.add(25, Category(R.drawable.d_26, "포장"))
        categoryList.add(26, Category(R.drawable.d_27, "미니어처"))
        categoryList.add(27, Category(R.drawable.d_28, "뜨개질"))
        categoryList.add(28, Category(R.drawable.d_29, "미술"))
        categoryList.add(29, Category(R.drawable.d_30, "금속&유리"))
        categoryList.add(30, Category(R.drawable.d_31, "라탄"))
        categoryList.add(31, Category(R.drawable.d_32, "가죽"))
        categoryList.add(32, Category(R.drawable.d_33, "요리"))
        categoryList.add(33, Category(R.drawable.d_34, "목공"))
        categoryList.add(34, Category(R.drawable.d_35, "도자기"))
        categoryList.add(35, Category(R.drawable.d_36, "동물원"))
        categoryList.add(36, Category(R.drawable.d_37, "식물원"))
        categoryList.add(37, Category(R.drawable.d_38, "궁"))
        categoryList.add(38, Category(R.drawable.d_39, "전망대"))
        categoryList.add(39, Category(R.drawable.d_40, "관광명소"))
        categoryList.add(40, Category(R.drawable.d_41, "고개"))
        categoryList.add(41, Category(R.drawable.d_42, "광장"))
        categoryList.add(42, Category(R.drawable.d_43, "촬영지"))
        categoryList.add(43, Category(R.drawable.d_44, "케이블카"))
        categoryList.add(44, Category(R.drawable.d_45, "폭포"))
        categoryList.add(45, Category(R.drawable.d_46, "하천"))
        categoryList.add(46, Category(R.drawable.d_47, "공원"))
        categoryList.add(47, Category(R.drawable.d_48, "숲"))
        categoryList.add(48, Category(R.drawable.d_49, "호수"))
        categoryList.add(49, Category(R.drawable.d_50, "테마거리"))
        categoryList.add(50, Category(R.drawable.d_51, "카페거리"))
        categoryList.add(51, Category(R.drawable.d_52, "룸카페,멀티방"))
        categoryList.add(52, Category(R.drawable.d_53, "파티룸"))
        categoryList.add(53, Category(R.drawable.d_54, "스파"))
        categoryList.add(54, Category(R.drawable.d_55, "백화점"))
        categoryList.add(55, Category(R.drawable.d_56, "갤러리카페"))
        categoryList.add(56, Category(R.drawable.d_57, "고양이카페"))
        categoryList.add(57, Category(R.drawable.d_58, "디저트카페"))
        categoryList.add(58, Category(R.drawable.d_59, "뮤직카페"))
        categoryList.add(59, Category(R.drawable.d_60, "북카페"))
        categoryList.add(60, Category(R.drawable.d_61, "타로,사주,상담카페"))
        categoryList.add(61, Category(R.drawable.d_62, "플라워카페"))
        categoryList.add(62, Category(R.drawable.d_63, "한옥카페"))
        categoryList.add(63, Category(R.drawable.d_64, "슬라임카페"))
        categoryList.add(64, Category(R.drawable.d_65, "피로회복카페"))
        categoryList.add(65, Category(R.drawable.d_66, "드로잉카페"))
        categoryList.add(66, Category(R.drawable.d_67, "실내포장마차"))
        categoryList.add(67, Category(R.drawable.d_68, "와인바"))
        categoryList.add(68, Category(R.drawable.d_69, "일본식주점"))
        categoryList.add(69, Category(R.drawable.d_70, "칵테일바"))
        categoryList.add(70, Category(R.drawable.d_71, "호프,요리주점"))
        categoryList.add(71, Category(R.drawable.d_72, "아쿠아리움"))
        categoryList.add(72, Category(R.drawable.d_73, "미술관"))
        categoryList.add(73, Category(R.drawable.d_74, "전시관"))
        categoryList.add(74, Category(R.drawable.d_75, "공연,연극극징"))
        categoryList.add(75, Category(R.drawable.d_76, "영화관"))
    }

}


