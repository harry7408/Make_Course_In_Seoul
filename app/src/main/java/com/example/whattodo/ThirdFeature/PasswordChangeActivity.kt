package com.example.whattodo.ThirdFeature

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityPasswordChangeBinding

class PasswordChangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordChangeBinding
    private var oldFlag = false
    private var newFlag = false
    private var checkFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "비밀번호 변경"
        checkInput()

        binding.changePassButton.setOnClickListener {
            /* 서버에 비밀번호 변경하는 기능과 연결하기*/





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

    private fun checkInput() {
        binding.oldPassEditText.addTextChangedListener { text ->
            if (text != null) {
                when {
                    text.isEmpty() -> oldFlag = false
                }
            }
        }
        binding.newPassEditText.addTextChangedListener { text ->
            if (text != null) {
                when {
                    text.isEmpty() -> newFlag = false
                }
            }
        }
        binding.newCheckEditText.addTextChangedListener { text ->
            if (text != null) {
                when {
                    text.isEmpty() -> checkFlag = false
                    !text.equals(binding.newPassEditText.text.toString()) -> {
                        Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                        checkFlag = false
                    }
                }
            }
            checkFlag()
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

    fun checkFlag() {
        binding.changePassButton.isEnabled =
            oldFlag && newFlag && checkFlag
    }

}