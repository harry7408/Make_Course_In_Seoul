package com.example.whattodo.SecondFeature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.*
import androidx.core.widget.addTextChangedListener
import com.example.whattodo.R
import com.example.whattodo.UID
import com.example.whattodo.USER_INFO
import com.example.whattodo.databinding.ActivityMakeCourseBinding
import com.example.whattodo.databinding.DialogEndTimeSettingBinding
import com.example.whattodo.databinding.DialogStartTimeSettingBinding
import com.example.whattodo.datas.Course
import com.example.whattodo.datas.Friend

import com.example.whattodo.datas.Store
import com.example.whattodo.datas.courseResponse


import com.example.whattodo.network.RetrofitAPI
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MakeCourseActivity"

class MakeCourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakeCourseBinding
    private val userGoalList = mutableListOf<Int>(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    private val suggestList = listOf<String>("놀기", "체험", "관계", "관람")
    private val memberList = mutableListOf<String>()
    private var startTimeValue: Int = 0
    private var endTimeValue: Int = 0
    private lateinit var courseInput: Course

    private val getFriendResult=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        result->
        val friendList=result.data?.getStringArrayListExtra("friendList") ?: emptyArray<Friend>()

        memberList.addAll(listOf(friendList.toString()))
    }



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
        val sharedPreferences = getSharedPreferences(USER_INFO, MODE_PRIVATE)
        val userCode = sharedPreferences.getString(UID, null)

        binding.chipGroup1.setOnCheckedStateChangeListener { _, _ ->
            when (findViewById<Chip>(binding.chipGroup1.checkedChipId).text.toString()) {
                "놀기" -> spinnerOutPut(R.array.playing)
                "체험" -> spinnerOutPut(R.array.experience)
                "관계" -> spinnerOutPut(R.array.relationship)
                "관람" -> spinnerOutPut(R.array.watching)
            }
        }

        binding.numPeopleTextView.addTextChangedListener { text ->
            if (text != null) {
                binding.numPeopleTextView.isCursorVisible = false
            }
        }

        binding.startTimeTextView.setOnClickListener {
            initStartTime()
        }
        binding.endTimeTextView.setOnClickListener {
            initEndTime()
        }



        /* 친구목록 불러오기 부분 */
        binding.friendFindActionButton.setOnClickListener {
            val friendRequestCall=RetrofitAPI.requestFriendService
                .getFriendList(userCode.toString()).enqueue(object:Callback<List<Friend>>{
                    override fun onResponse(
                        call: Call<List<Friend>>,
                        response: Response<List<Friend>>
                    ) {
                        if (response.isSuccessful) {
                            val intent=Intent(applicationContext,RequestFriendActivity::class.java)
                            intent.putExtra("friendList", response.body()?.toTypedArray())
                            getFriendResult.launch(intent)


                        } else {
                            Log.e(TAG,"null return")
                        }
                    }

                    override fun onFailure(call: Call<List<Friend>>, t: Throwable) {
                        Log.e(TAG,"통신 실패")
                        t.printStackTrace()
                    }

                })



        }

        memberList.add(userCode.toString())



        /* 로그 찍어보기 */
        Log.e(TAG,"$memberList")

        /* 서버 통신 부분 */
        binding.courseMakeBtn.setOnClickListener {


            val categoryC=if (!placeFlag) null else initCategoryC(binding.categoryListSpinner.selectedItem.toString())
            initUserGoalList()
            courseInput = Course(
                binding.numPeopleTextView.text.toString().toInt(),
                userCode,
                memberList,
                binding.startTimeTextView.text.toString().toInt(),
                binding.endTimeTextView.text.toString().toInt(),
                mealFlag,
                if (placeFlag) binding.categoryListSpinner.selectedItem.toString() else null,
                categoryC,
                userGoalList
            )
            /* 로그 찍기 */
            Log.e(TAG, "$courseInput")
            Thread {
                val makeCourseCall =
                    RetrofitAPI.courseService.requestCourse(courseInput)
                        .enqueue(object : Callback<courseResponse> {
                            override fun onResponse(
                                call: Call<courseResponse>,
                                response: Response<courseResponse>
                            ) {
                                if (response.isSuccessful) {
                                    Log.e(TAG, "SUCCESS")
                                    Log.d(TAG, "${response.body()}")
                                    val responseData = ArrayList<Store>()
                                    Log.e(TAG, "${response.body()}")

                                    response.body()?.let { it ->
                                        it.placeDto?.let { it1 ->
                                            responseData.addAll(
                                                it1
                                            )
                                        }
                                    }

                                    val intent =
                                        Intent(
                                            this@MakeCourseActivity,
                                            CourseListShowActivity::class.java
                                        )
                                    intent.putExtra("response", responseData)
                                    startActivity(intent)
                                } else {
                                    Log.e(TAG, "Null returned")
                                    Log.e(TAG, "${response.body()}")
                                }
                            }

                            override fun onFailure(call: Call<courseResponse>, t: Throwable) {
                                Log.e(TAG, "Error Occur")
                                t.printStackTrace()

                            }

                        })
            }.start()
            /* 여기서 서버랑 통신하고 받은 response data를 다음 페이지에 넘겨줘야함 */
            /*val makeCourseCall =
                RetrofitAPI.storeService.requestStore().enqueue(object : Callback<List<Store>> {
                    override fun onResponse(
                        call: Call<List<Store>>,
                        response: Response<List<Store>>
                    ) {
                        if (response.isSuccessful) {
                            Log.e(TAG, "SUCCESS")
                            Log.d(TAG, "${response.body()}")
                            val responseData = ArrayList<Store>()
                            response.body()?.let { it1 -> responseData.addAll(it1) }
                            val intent =
                                Intent(this@MakeCourseActivity, CourseListShowActivity::class.java)
                            intent.putExtra("response", responseData)
                            startActivity(intent)
                        } else {
                            Log.e(TAG, "CONNECTED BUT NOT SUCCESS")
                            Log.e(TAG, "${response.body()}")
                        }
                    }

                    override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                        Log.e(TAG, "Error Occur")
                        Log.e(TAG, "${call.toString()}")
                    }

                })*/
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

    /* 코스 스피너 아웃풋 */
    private fun spinnerOutPut(resourceId: Int) {
        binding.categoryListSpinner.adapter = ArrayAdapter.createFromResource(
            this, resourceId,
            android.R.layout.simple_list_item_1
        )
    }

    private fun initStartTime() {
        AlertDialog.Builder(this).apply {
            val dialogBinding = DialogStartTimeSettingBinding.inflate(layoutInflater)
            dialogBinding.startTimePicker.wrapSelectorWheel = false
            with(dialogBinding.startTimePicker) {
                displayedValues = resources.getStringArray(R.array.StartTimeSpinner)
                minValue = 6
                maxValue = 27
            }

            setView(dialogBinding.root)
            setTitle("시작시간 설정")
            setPositiveButton("Ok") { _, _ ->
                startTimeValue = dialogBinding.startTimePicker.value
                if (startTimeValue >= 24)
                    binding.startTimeTextView.text = String.format("%02d", startTimeValue - 24)
                else binding.startTimeTextView.text = String.format("%02d", startTimeValue)
            }
            setNegativeButton("Cancel", null)
        }.show()
    }


    private fun initEndTime() {
        AlertDialog.Builder(this).apply {
            val dialogBinding = DialogEndTimeSettingBinding.inflate(layoutInflater)
            dialogBinding.endTimePicker.wrapSelectorWheel = false
            with(dialogBinding.endTimePicker) {
                when (binding.startTimeTextView.text) {
                    "06" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(1, 13)
                        minValue = 7
                        maxValue = 18
                    }
                    "07" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(2, 14)
                        minValue = 8
                        maxValue = 19
                    }
                    "08" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(3, 15)
                        minValue = 9
                        maxValue = 20
                    }
                    "09" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(4, 16)
                        minValue = 10
                        maxValue = 21
                    }
                    "10" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(5, 17)
                        minValue = 11
                        maxValue = 22
                    }
                    "11" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(6, 18)
                        minValue = 12
                        maxValue = 23
                    }
                    "12" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(7, 19)
                        minValue = 13
                        maxValue = 24
                    }
                    "13" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(8, 20)
                        minValue = 14
                        maxValue = 25
                    }
                    "14" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(9, 21)
                        minValue = 15
                        maxValue = 26
                    }
                    "15" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(10, 22)
                        minValue = 16
                        maxValue = 27
                    }
                    "16" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(11, 23)
                        minValue = 17
                        maxValue = 28
                    }
                    "17" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(12, 23)
                        minValue = 18
                        maxValue = 28
                    }
                    "18" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(13, 23)
                        minValue = 19
                        maxValue = 28
                    }
                    "19" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(14, 23)
                        minValue = 20
                        maxValue = 28
                    }
                    "20" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(15, 23)
                        minValue = 21
                        maxValue = 28
                    }
                    "21" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(16, 23)
                        minValue = 22
                        maxValue = 28
                    }
                    "22" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(17, 23)
                        minValue = 23
                        maxValue = 28
                    }
                    "23" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(18, 23)
                        minValue = 24
                        maxValue = 28
                    }
                    "00" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(19, 23)
                        minValue = 25
                        maxValue = 28
                    }
                    "01" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(20, 23)
                        minValue = 26
                        maxValue = 28
                    }
                    "02" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(21, 23)
                        minValue = 27
                        maxValue = 28
                    }
                    "03" -> {
                        displayedValues =
                            resources.getStringArray(R.array.StartTimeSpinner).copyOfRange(22, 23)
                        minValue = 28
                        maxValue = 28
                    }
                }
            }
            setView(dialogBinding.root)
            setTitle("종료시간 설정")
            setPositiveButton("Ok") { _, _ ->
                endTimeValue = dialogBinding.endTimePicker.value
                if (endTimeValue >= 24)
                    binding.endTimeTextView.text = String.format("%02d", endTimeValue - 24)
                else binding.endTimeTextView.text = String.format("%02d", endTimeValue)
            }
            setNegativeButton("Cancel", null)
        }.show()
    }

    /* 목적 갯수 3개로 제한*/
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

    private fun initUserGoalList() {
        if (binding.checkbox1.isChecked) userGoalList[0] = 1 else userGoalList[0] = 0
        if (binding.checkbox2.isChecked) userGoalList[1] = 1 else userGoalList[1] = 0
        if (binding.checkbox3.isChecked) userGoalList[2] = 1 else userGoalList[2] = 0
        if (binding.checkbox4.isChecked) userGoalList[3] = 1 else userGoalList[3] = 0
        if (binding.checkbox5.isChecked) userGoalList[4] = 1 else userGoalList[4] = 0
        if (binding.checkbox6.isChecked) userGoalList[5] = 1 else userGoalList[5] = 0
        if (binding.checkbox7.isChecked) userGoalList[6] = 1 else userGoalList[6] = 0
        if (binding.checkbox8.isChecked) userGoalList[7] = 1 else userGoalList[7] = 0
        if (binding.checkbox9.isChecked) userGoalList[8] = 1 else userGoalList[8] = 0
        if (binding.checkbox10.isChecked) userGoalList[9] = 1 else userGoalList[9] = 0
    }

    private fun initCategoryC(categoryD: String?): String {
        return when (categoryD) {
            "수상스포츠", "클라이밍", "수영장", "스킨스쿠버", "스케이트장" -> "PM1"
            "산", "계곡" -> "PM2"
            "테마파크", "워터테마파크", "눈썰매장" -> "PM3"
            "탁구", "볼링", "사격&궁도", "스크린야구", "스크린골프" -> "PS1"
            "오락실", "실내낚시", "만화카페" -> "PS2"
            "VR", "방탈출카페", "보드게임카페" -> "PS3"
            "터프팅", "캔들&향수&비누", "식물", "켈리그라피", "포장",
            "미니어처", "뜨개질" -> "EM1"
            "미술", "금속&유리", "라탄", "가죽" -> "EM2"
            "요리", "목공", "도자기" -> "EM3"
            "동물원", "식물원" -> "RM1"
            "궁", "전망대", "관광&명소", "고개", "광장", "촬영지", "케이블카" -> "RM2"
            "폭포", "하천", "공원", "숲", "호수" -> "RM3"
            "테마거리", "카페거리" -> "RM4"
            "룸카페&멀티방", "파티룸", "스파" -> "RM5"
            "백화점" -> "RM6"
            "갤러리카페", "고양이카페", "디저트카페", "뮤직카페", "북카페",
            "타로&사주&상담카페", "플라워카페", "한옥카페", "슬라임카페",
            "피로회복카페", "드로잉카페" -> "RS1"
            "실내포장마차", "와인바", "일본식주점", "칵테일바",
            "호프&요리주점" -> "RS2"
            "아쿠아리움", "미술관", "전시관" -> "WM1"
            "공연장&연극극장", "영화관" -> "WM2"
            else -> ({ null }).toString()
        }
    }
}







