package com.example.whattodo.FindingIdPass

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.whattodo.R
import com.example.whattodo.databinding.FragmentFindPassBinding
import com.example.whattodo.datas.User
import com.example.whattodo.network.RetrofitAPI
import retrofit2.Call
import retrofit2.Response

/* 비밀번호 찾기 프래그먼트 */
private const val TAG = "FindIdPassFragment"

class FindPassFragment : Fragment() {

    private lateinit var binding: FragmentFindPassBinding
    private var idFlag = false
    private var nameFlag = false
    private var emailFlag = false
    private var birthFlag = false
    private var genderFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPassBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*아이디 입력여부 확인*/
        binding.idArea.addTextChangedListener { text->
            if (text != null) {
                emailFlag = when {
                    text.isEmpty() -> false
                    else -> true
                }
            }
        }
        /* 이름 입력 여부 확인 */
        binding.nameArea.addTextChangedListener { text->
            if (text != null) {
                nameFlag = when {
                    text.isEmpty() -> false
                    else -> true
                }
            }
        }

        /*이메일 입력 여부 확인 */
        binding.emailArea.addTextChangedListener { text->
            if (text != null) {
                idFlag = when {
                    text.isEmpty() -> false
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches() -> false
                    else -> true
                }
            }
        }
       /* 생일 입력 여부 확인*/
        binding.birthArea.addTextChangedListener { text->
            if (text != null) {
                birthFlag = when {
                    text.isEmpty() -> false
                    else -> true
                }
            }
        }
        /* 성별 체크 여부 확인 */
        binding.genderGroup.setOnCheckedChangeListener { _, checkedId ->
            genderFlag = when (checkedId) {
                R.id.male -> true
                R.id.female -> true
                else -> false
            }
            checkFlag()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.passButton.setOnClickListener {
            val userId = binding.idArea.text.toString()
            val userName = binding.nameArea.text.toString()
            val userEmail = binding.emailArea.text.toString()
            val userBirth = binding.birthArea.text.toString()
            val userGender = when {
                binding.male.isChecked -> binding.male.text.toString()
                else -> binding.female.text.toString()
            }
            val userData = User(
                userId, null, userEmail,
                userName, userBirth, userGender,
                null,null,null
            ,null)
            val findPassCall = RetrofitAPI.findService.findPass(userData)
            findPassCall.enqueue(object : retrofit2.Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "success")
                        val receiveData = response.body()
                        val builder=AlertDialog.Builder(context)
                        builder.setTitle("비밀번호")
                        builder.setMessage("${receiveData?.password.toString()} 입니다")
                        builder.setPositiveButton(R.string.ok,
                            DialogInterface.OnClickListener { dialog, which ->
                            activity!!.finish()
                        })
                        builder.create()
                        builder.show()
                    } else {
                        Log.d(TAG, "WHY not")
                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    val builder=AlertDialog.Builder(context)
                    builder.setMessage("일치하는 정보가 없습니다.")
                    builder.setPositiveButton(R.string.ok,null)
                    builder.create()
                    builder.show()
                }
            })
        }
    }

    private fun checkFlag() {
        binding.passButton.isEnabled = emailFlag && nameFlag && emailFlag && birthFlag && genderFlag
    }
}