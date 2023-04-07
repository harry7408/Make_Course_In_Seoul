package com.example.whattodo

import android.annotation.SuppressLint
import android.graphics.Color.red
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
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
    private lateinit var maleBox: RadioButton
    private lateinit var femaleBox: RadioButton
    private lateinit var joinBtn: Button
    private lateinit var checkBtn: Button
    private lateinit var emailError: TextView
    private lateinit var passError: TextView
    private lateinit var cpassError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        email = findViewById(R.id.emailArea)
        pass = findViewById(R.id.passArea)
        cpass = findViewById(R.id.passCheckArea)
        nickname = findViewById(R.id.nickNameArea)
        name = findViewById(R.id.nameArea)
        birth = findViewById(R.id.birthArea)
        maleBox = findViewById(R.id.male)
        femaleBox = findViewById(R.id.female)
        joinBtn = findViewById(R.id.joinButton)
        checkBtn = findViewById(R.id.doubleCheck)
        emailError=findViewById(R.id.wrong_message1)
        passError=findViewById(R.id.wrong_message2)
        cpassError=findViewById(R.id.wrong_message3)

        join()


        joinBtn.setOnClickListener {
//            회원가입 가능 여부 확인 후 활성화 된다면 데이터가 서버에 저장 되도록
            Toast.makeText(this,"aaa",Toast.LENGTH_SHORT).show()
        }
    }

    private fun join() :Boolean {
        var isOk=true

        email.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}


            override fun afterTextChanged(s: Editable?) {
                if (s?.length==0) { isOk=false }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    email.setBackgroundResource(R.drawable.background_rectangle_red)
                    emailError.setText(R.string.wrong_email)
                    isOk=false
                } else {
                    email.setBackgroundResource(R.drawable.background_rectangle)
                    emailError.text=""
                    isOk=true
                }
            }
        })

        pass.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length==0) { isOk=false}
                if (!checkPass(s.toString()).matches()) {
                    pass.setBackgroundResource(R.drawable.background_rectangle_red)
                    passError.setText(R.string.wrong_pass)
                    isOk=false
                } else {
                    pass.setBackgroundResource(R.drawable.background_rectangle)
                    passError.text=""
                    isOk=true
                }
            }
        })

        return isOk
    }

    
    
//    비밀번호 유효성 확인
    private fun checkPass(s:String): Matcher {
        val pwPattern : String ="^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,15}.\$"
        val matcher :Matcher=Pattern.compile(pwPattern).matcher(s)
        return matcher
    }

}








