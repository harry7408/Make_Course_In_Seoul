package com.example.whattodo.SecondFeature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.view.*
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityMakeCourseBinding
import com.example.whattodo.datas.Store
import com.example.whattodo.datas.StoreList
import com.example.whattodo.network.RetrofitAPI
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MakeCourseActivity"

class MakeCourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakeCourseBinding
    private var keywords = mutableListOf<String>()
    private var userGoal = mutableListOf<String>()
    private val userKeywordList = mutableListOf<Int>(0, 0, 0, 0, 0, 0)
    private val userGoalList = mutableListOf<Int>(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    private val suggestList = listOf<String>("놀기", "체험", "관계", "관람")
    private val goalList =
        listOf<String>("산책", "음주", "체험", "힐링", "관람", "지적", "경치", "일반", "운동", "솔로")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* 칩을 적용하려면 material 테마 적용해야함 */
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light)


        /* 액션바 설정 */
        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = "정보 입력"

        /*식사 포함여부와 필수장소 포함 여부 정하기 위한 변수들*/
        val mealFlag = intent.getBooleanExtra("meal", false)
        val placeFlag = intent.getBooleanExtra("place", false)

        checkMenu(placeFlag)
        limitCheckCount()

        /* 키워드 int 값으로 전달 위해 체크하면 1 아니면 0 */
        initKeywords()

//        initGoals()

        binding.chipGroup1.setOnCheckedStateChangeListener { _, _ ->
            when (findViewById<Chip>(binding.chipGroup1.checkedChipId).text.toString()) {
                "놀기" -> spinnerOutPut(R.array.playing)
                "체험" -> spinnerOutPut(R.array.experience)
                "관계" -> spinnerOutPut(R.array.relationship)
                "관람" -> spinnerOutPut(R.array.watching)
            }
        }

        // 스피너 선택 부분에서 객체 초기화 해야할 듯 싶다
       /* val courseInput = CourseDto(
            binding.numPeople.text.toString().toInt(), null,
            binding.startTime.text.toString().toInt(),
            binding.endTime.text.toString().toInt(),
            mealFlag,
            if (placeFlag) binding.categoryListSpinner.selectedItem.toString() else null,
            userKeywordList,
            userGoalList,
        )*/

        /* 서버 통신 부분 */
        binding.courseMakeBtn.setOnClickListener {
            /* 여기서 서버랑 통신하고 받은 response data를 다음 페이지에 넘겨줘야함 */
            val makeCourseCall=RetrofitAPI.storeService.requestStore().enqueue(object:Callback<StoreList>{
                override fun onResponse(call: Call<StoreList>, response: Response<StoreList>) {
                    if  (response.isSuccessful) {
                        Log.e(TAG,"SUCCESS")
                        val responseData=response.body()
                        val intent = Intent(this@MakeCourseActivity, CourseListShowActivity::class.java)
                        intent.putExtra("response", responseData)
                        startActivity(intent)
                    } else {
                        Log.e(TAG,"CONNECTED BUT NOT SUCCESS")
                        Log.e(TAG,"${response.body()}")
                    }
                }

                override fun onFailure(call: Call<StoreList>, t: Throwable) {
                    Log.e(TAG,"Error Occur")
                    t.printStackTrace()
                }

            })
        }
    }

    /* 필수장소 선택 chip 내용 초기화 하는 부분 */
    private fun initSuggestChips() {
        binding.chipGroup1.apply {
            suggestList.forEach { text ->
                addView(createChip(text))
            }
        }
    }

    /* 칩 제작 부분 */
    private fun createChip(text: String): Chip {
        val chip = Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
        return chip
    }

    /* 필수 장소 있는 경우 다음 페이지의 장소선택 보이도록*/
    private fun checkMenu(flag: Boolean) {
        if (flag) {
            initSuggestChips()
            binding.categoryListSpinner.isVisible = true
            binding.requiredPlaceTextView.isVisible = true
        }
    }

    private fun spinnerOutPut(resourceId: Int) {
        binding.categoryListSpinner.adapter = ArrayAdapter.createFromResource(
            this, resourceId,
            android.R.layout.simple_list_item_1
        )
    }

    /* 사용자 키워드 값 조정 부분*/
    private fun initKeywords() {
        binding.radioGroup1.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.exoticHigh -> {
                    userKeywordList[0] = 1
                    userKeywordList[1] = 0
                }
                R.id.exoticLow -> {
                    userKeywordList[1] = 1
                    userKeywordList[0] = 0
                }
            }
            Log.d(TAG, "${userKeywordList[0]}, ${userKeywordList[1]}")
        }
        binding.radioGroup2.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.hpHigh -> {
                    userKeywordList[2] = 1
                    userKeywordList[3] = 0
                }
                R.id.hpLow -> {
                    userKeywordList[3] = 1
                    userKeywordList[2] = 0
                }
            }
            Log.d(TAG, "${userKeywordList[2]}, ${userKeywordList[3]}")

        }
        binding.radioGroup3.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.activeHigh -> {
                    userKeywordList[4] = 1
                    userKeywordList[5] = 0
                }
                R.id.activeLow -> {
                    userKeywordList[5] = 1
                    userKeywordList[4] = 0
                }
            }
            Log.d(TAG, "${userKeywordList[4]}, ${userKeywordList[5]}")
        }
    }

    private fun limitCheckCount() {
        val maxCheck = 3
        var checkedCount = 0
        binding.goalsGridLayout.children.forEachIndexed { index, view ->
            if (view is CheckBox) {
                view.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        userGoalList[index] = 1
                        checkedCount++
                        if (checkedCount > maxCheck) {
                            view.isChecked = false
                            userGoalList[index] = 0
                            checkedCount--
                        }
                    } else {
                        userGoalList[index] -= 1
                        checkedCount--
                    }
                }
            }
        }
    }
}





