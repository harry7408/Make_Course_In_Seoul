package com.example.whattodo

import android.annotation.SuppressLint
import android.graphics.Color.red
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.whattodo.UserInfo.UserInfo1
import org.w3c.dom.Text
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.io.path.fileVisitor


class JoinActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var cpass: EditText
    private lateinit var nickname: EditText
    private lateinit var name: EditText
    private lateinit var birth: EditText
    private lateinit var genderGroup: RadioGroup
    private lateinit var maleBox: RadioButton
    private lateinit var femaleBox: RadioButton
    private lateinit var joinBtn: Button
    private lateinit var checkBtn: Button
    private lateinit var emailError: TextView
    private lateinit var passError: TextView
    private lateinit var cpassError: TextView
    var idFlag = false
    var passFlag = false
    var cpassFlag = false
    var nicknameFlag = false
    var nameFlag = false
    var birthFlag = false
    var genderFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        email = findViewById(R.id.emailArea)
        pass = findViewById(R.id.passArea)
        cpass = findViewById(R.id.passCheckArea)
        nickname = findViewById(R.id.nickNameArea)
        name = findViewById(R.id.nameArea)
        birth = findViewById(R.id.birthArea)
        genderGroup = findViewById(R.id.radioGroup)
        maleBox = findViewById(R.id.male)
        femaleBox = findViewById(R.id.female)
        joinBtn = findViewById(R.id.joinButton)
        checkBtn = findViewById(R.id.doubleCheck)
        emailError = findViewById(R.id.wrong_message1)
        passError = findViewById(R.id.wrong_message2)
        cpassError = findViewById(R.id.wrong_message3)

        checkInput()
        joinBtn.setOnClickListener {
//            입력된 데이터를 가지고 회원정보에 넣고 회원가입이 성공한다면 데이터가 서버에 저장 되도록
            Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkInput() {

//         아이디 유효성 검사 파트
        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    when {
                        s.isEmpty() -> {
                            email.setBackgroundResource(R.drawable.background_rectangle_red)
                            emailError.setText(R.string.no_id)
                            idFlag = false
                        }
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches() -> {
                            email.setBackgroundResource(R.drawable.background_rectangle_red)
                            emailError.setText(R.string.wrong_email)
                            idFlag = false
                        }
                        else -> {
                            email.setBackgroundResource(R.drawable.background_rectangle)
                            emailError.text = ""
                            idFlag = true
                        }

                    }
                }
            }
        })

//       패스워드 유효성 체크 파트
        pass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s!=null) {
                    when {
                        s.isEmpty() -> {
                            pass.setBackgroundResource(R.drawable.background_rectangle_red)
                            passError.setText(R.string.no_pass)
                            passFlag=false
                        }
                        !checkPass(s.toString()).matches() -> {
                            pass.setBackgroundResource(R.drawable.background_rectangle_red)
                            passError.setText(R.string.wrong_pass)
                            passFlag=false
                        }
                        else -> {
                            pass.setBackgroundResource(R.drawable.background_rectangle)
                            passError.text = ""
                            passFlag=true
                        }
                    }
                }
            }
        })

//      비밀번호 확인파트
        cpass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    when {
                        s.isEmpty() -> {
                            cpass.setBackgroundResource(R.drawable.background_rectangle_red)
                            cpassError.setText(R.string.no_cpass)
                            cpassFlag = false
                        }
                        s.toString() != pass.text.toString() -> {
                            cpass.setBackgroundResource(R.drawable.background_rectangle_red)
                            cpassError.setText(R.string.wrong_pass_check)
                            cpassFlag = false
                        }
                        else -> {
                            cpass.setBackgroundResource(R.drawable.background_rectangle)
                            cpassError.text = ""
                            cpassFlag = true

                        }
                    }
//                    flagCheck()
                }
            }
        })
//        닉네임 입력여부
        nickname.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s!=null) {
                    if (s.isEmpty()) { nicknameFlag=false }
                    checkBtn.setOnClickListener {
//                        서버와 통신
                    }
                }
            }
        })

//        이름 입력 여부
         name.addTextChangedListener(object:TextWatcher{
             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
             override fun afterTextChanged(s: Editable?) {
                 if (s!=null) {
                     nameFlag = when {
                         s.isEmpty()-> false
                         else -> true
                     }
                 }
             }
         })
//        생년월일 입력여부
        birth.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s!=null) {
                    birthFlag =when {
                        s.isEmpty() ->false
                        else-> true
                    }
                }
            }
        })
//      성별 입력여부
        genderGroup.setOnCheckedChangeListener { _,checkId ->
            when(checkId) {
                R.id.male -> genderFlag=true
                R.id.female -> genderFlag=true
                else -> genderFlag=false
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

//    모든 입력이 있으면 회원가입 버튼 활성화
    private fun flagCheck() {
        joinBtn.isEnabled=idFlag&&passFlag&&cpassFlag && nameFlag && birthFlag && genderFlag
    }
}
















