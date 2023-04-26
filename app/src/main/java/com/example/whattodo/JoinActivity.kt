package com.example.whattodo


import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.whattodo.databinding.ActivityJoinBinding
import com.example.whattodo.dto.JoinData
import com.example.whattodo.network.RetrofitAPI

import retrofit2.Call
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val TAG = "JoinActivity"

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding
    var idFlag = false
    var passFlag = false
    var cpassFlag = false
    var emailFlag = false
    var nameFlag = false
    var birthFlag = false
    var genderFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkInput()
        /* ID 중복확인 검사 부분 */
        binding.idCheck.setOnClickListener {
            val check_id = binding.idArea.text.toString()
            var dupChecked: Boolean = false
            val userListCall = RetrofitAPI.idCheckService.checkNickname()
            userListCall.enqueue(object : retrofit2.Callback<List<JoinData>> {
                override fun onResponse(
                    call: Call<List<JoinData>>,
                    response: Response<List<JoinData>>
                ) {
                    if (response.isSuccessful() && check_id.isNotEmpty()) {
//                        Log.d(TAG, "SUCCESS")
                        val userList: List<JoinData>? = response.body()
                        for (users in userList!!) {
                            if (users.memberId == check_id) {
                                dupChecked = false
                                break
                            } else {
                                dupChecked = true
                            }
                        }
                        if (dupChecked) {
                            idFlag = true
                            binding.idArea.inputType = InputType.TYPE_NULL
                            binding.idCheck.isEnabled = false
                            AlertDialog.Builder(this@JoinActivity).run {
                                setTitle(R.string.check_id)
                                setMessage(R.string.isNot_duplicate)
                                setPositiveButton(R.string.ok, null)
                                show()
                            }
                        } else {
                            AlertDialog.Builder(this@JoinActivity).run {
                                setTitle(R.string.check_id)
                                setMessage(R.string.is_duplicate)
                                setPositiveButton(R.string.ok, null)
                                show()
                            }
                        }
                    } else {
                        AlertDialog.Builder(this@JoinActivity).run {
                            setTitle(R.string.check_id)
                            setMessage(R.string.no_id)
                            setPositiveButton(R.string.ok, null)
                            show()
                        }
                    }
                }

                override fun onFailure(call: Call<List<JoinData>>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                    call.cancel()
                }
            })
        }



        binding.joinBtn.setOnClickListener {
//            입력된 데이터를 가지고 회원정보에 넣고 회원가입이 성공한다면 데이터가 서버에 저장 되도록
//            아이디의 중복확인 버튼이 비활성화 되어있을때
            val genderText= when {
                binding.male.isChecked->binding.male.text.toString()
                else -> binding.female.text.toString()
            }
            val userData=JoinData(
                binding.idArea.text.toString(),
                binding.passArea.text.toString(),
                binding.emailArea.text.toString(),
                binding.nameArea.text.toString(),
                binding.birthArea.text.toString(),
                genderText
            )
            /* 여기서 현재 통신 안됨 */
            val joinCall=RetrofitAPI.joinService.Join(userData)
            joinCall.enqueue(object:retrofit2.Callback<JoinData>{
                override fun onResponse(call: Call<JoinData>, response: Response<JoinData>) {
                    if (response.isSuccessful) {
                        Log.d(TAG,"hahahaha")
                    }
                }

                override fun onFailure(call: Call<JoinData>, t: Throwable) {
                    Log.d(TAG,"so sad")
                }

            })
        }
    }

    private fun checkInput() {
//         아이디 유효성 검사 파트
        binding.idArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) when {
                    s.isEmpty() -> {
                        idFlag = false
                    }
                    else -> true
                }
            }
        })

//       패스워드 유효성 체크 파트
        binding.passArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    when {
                        s.isEmpty() -> {
                            binding.passArea.setBackgroundResource(R.drawable.background_rectangle_red)
                            binding.passMessage.setText(R.string.no_pass)
                            passFlag = false
                        }
                        !checkPass(s.toString()).matches() -> {
                            binding.passArea.setBackgroundResource(R.drawable.background_rectangle_red)
                            binding.passMessage.setText(R.string.wrong_pass)
                            passFlag = false
                        }
                        else -> {
                            binding.passArea.setBackgroundResource(R.drawable.background_rectangle)
                            binding.passMessage.text = ""
                            passFlag = true
                        }
                    }
                }
            }
        })

//      비밀번호 확인파트
        binding.passCheckArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    when {
                        s.isEmpty() -> {
                            binding.passCheckArea.setBackgroundResource(R.drawable.background_rectangle_red)
                            binding.cpassMessage.setText(R.string.no_pass)
                            cpassFlag = false
                        }
                        !s.toString().equals(binding.passArea.text.toString()) -> {
                            binding.passCheckArea.setBackgroundResource(R.drawable.background_rectangle_red)
                            binding.cpassMessage.setText(R.string.not_same_pass)
                            cpassFlag = false
                        }
                        else -> {
                            binding.passCheckArea.setBackgroundResource(R.drawable.background_rectangle)
                            binding.cpassMessage.setText("")
                            cpassFlag = true
                        }
                    }
                }
            }
        })
        //         아이디 유효성 검사 파트
        binding.emailArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    when {
                        s.isEmpty() -> {
                            binding.emailArea.setBackgroundResource(R.drawable.background_rectangle_red)
                            binding.emailMessage.setText(R.string.no_email)
                            emailFlag = false
                        }
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches() -> {
                            binding.emailArea.setBackgroundResource(R.drawable.background_rectangle_red)
                            binding.emailMessage.setText(R.string.wrong_email)
                            emailFlag = false
                        }
                        else -> {
                            binding.emailArea.setBackgroundResource(R.drawable.background_rectangle)
                            binding.emailMessage.text = ""
                            emailFlag = true
                        }

                    }
                }
            }
        })


//        이름 입력 여부
        binding.nameArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    nameFlag = when {
                        s.isEmpty() -> false
                        else -> true
                    }
                }
            }
        })
//        생년월일 입력여부
        binding.birthArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    birthFlag = when {
                        s.isEmpty() -> false
                        else -> true
                    }
                }
            }
        })
//      성별 입력여부
        binding.genderGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.male -> genderFlag = true
                R.id.female -> genderFlag = true
                else -> genderFlag = false
            }
            flagCheck()
        }
    }


    //    비밀번호 유효성 확인을 위한 함수 (비밀번호 길이 + 문자 조합)
    private fun checkPass(s: String): Matcher {
        val pwPattern: String = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,16}.\$"
        val matcher: Matcher = Pattern.compile(pwPattern).matcher(s)
        return matcher
    }

    //    모든 입력이 있으면 회원가입 버튼 활성화 (id 부분 추후 추가)
    private fun flagCheck() {
        binding.joinBtn.isEnabled =
            idFlag && passFlag && cpassFlag && emailFlag && nameFlag && birthFlag && genderFlag
    }
}



















