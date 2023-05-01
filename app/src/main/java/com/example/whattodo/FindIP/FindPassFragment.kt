package com.example.whattodo.FindIP

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Paint.Join
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.whattodo.R
import com.example.whattodo.databinding.FragmentFindPassBinding
import com.example.whattodo.dto.JoinData
import com.example.whattodo.network.RetrofitAPI
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response

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
        binding.idArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    idFlag = when {
                        s.isEmpty() -> false
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches() -> false
                        else -> true
                    }
                }
            }
        })

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

        binding.emailArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    emailFlag = when {
                        s.isEmpty() -> false
                        else -> true
                    }
                }
            }
        })

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
            val userData = JoinData(
                userId, null, userEmail,
                userName, userBirth, userGender
            )
            val findPassCall = RetrofitAPI.findService.findPass(userData)
            findPassCall.enqueue(object : retrofit2.Callback<JoinData> {
                override fun onResponse(call: Call<JoinData>, response: Response<JoinData>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "success")
                        val receiveData = response.body()
                        val builder=AlertDialog.Builder(context)
                        builder.setTitle("비밀번호")
                        builder.setMessage("${receiveData?.password.toString()}입니다")
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

                override fun onFailure(call: Call<JoinData>, t: Throwable) {
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