package com.example.whattodo.ThirdFeature

import android.app.ProgressDialog.show
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.whattodo.*
import com.example.whattodo.databinding.ActivityPasswordChangeBinding
import com.example.whattodo.datas.User
import com.example.whattodo.network.RetrofitAPI
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.security.auth.callback.Callback


private const val TAG = "PasswordChangeActivity"

class PasswordChangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordChangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "비밀번호 변경"

        val sharedPreferences = getSharedPreferences(USER_INFO, MODE_PRIVATE)
        val currentPass = sharedPreferences.getString(PASS, "")
        binding.changePassButton.setOnClickListener {
            /* 서버에 비밀번호 변경하는 기능과 연결하기*/
            val currentUser = User(
                sharedPreferences.getString(UID,null),
                sharedPreferences.getString(ID, null),
                sharedPreferences.getString(PASS, null),
                sharedPreferences.getString(NAME, null),
                null,
                sharedPreferences.getString(EMAIL, null),
                sharedPreferences.getString(BDAY, null),
                sharedPreferences.getString(GENDER, null),
                sharedPreferences.getFloat(FATIGUE, 0.0f).toDouble(),
                sharedPreferences.getFloat(EXOTIC, 0.0f).toDouble(),
                sharedPreferences.getFloat(ACTIVITY, 0.0f).toDouble(),
            )

            if (!binding.oldPassEditText.text.equals(currentPass).not()) {
                Toast.makeText(this, "기존 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else if (checkPass(binding.newPassEditText.text.toString()).matches().not()) {
                Toast.makeText(this, "비밀번호 형식에 맞게 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (!binding.newPassEditText.text.equals(binding.newCheckEditText.text).not()) {
                Toast.makeText(this, "확인 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else {

                /* 서버와 통신해서 비밀번호 수정하는 부분 */
                val passChangeCall = RetrofitAPI.changeService.change(
                    currentUser,
                    binding.newPassEditText.text.toString()
                )
                    .enqueue(object : retrofit2.Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.isSuccessful) {
                                Log.e(TAG, "${response.body()}")
                                with(getSharedPreferences(USER_INFO, MODE_PRIVATE).edit()) {
                                    putString(PASS, response.body()?.password)
                                }.apply()
                            } else {
                                Log.e(TAG, "null return")
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.e(TAG, "통신자체 실패")
                        }
                    })
                AlertDialog.Builder(this).run {
                    setMessage("비밀번호 수정 완료")
                    setPositiveButton(
                        R.string.ok,
                        DialogInterface.OnClickListener { _, _ ->
                            this@PasswordChangeActivity.finish()
                        })
                    create()
                    show()
                }
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkPass(s: String): Matcher {
        val pwPattern: String = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,16}.\$"
        val matcher: Matcher = Pattern.compile(pwPattern).matcher(s)
        return matcher
    }


}