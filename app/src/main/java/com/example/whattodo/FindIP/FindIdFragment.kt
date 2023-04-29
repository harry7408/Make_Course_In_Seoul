package com.example.whattodo.FindIP

import android.app.AlertDialog
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
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
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.example.whattodo.R
import com.example.whattodo.databinding.FragmentFindIdBinding
import com.example.whattodo.dto.JoinData
import com.example.whattodo.network.RetrofitAPI
import retrofit2.Call
import retrofit2.Response

private const val TAG = "FindIdFragment"

class FindIdFragment : Fragment() {

    private lateinit var binding: FragmentFindIdBinding

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
        binding = FragmentFindIdBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nameArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    when {
                        s.isEmpty() -> nameFlag = false
                        else -> nameFlag = true
                    }
                }
            }
        })
        binding.emailArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    when {
                        s.isEmpty() -> {
                            emailFlag = false
                        }
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches() -> {
                            emailFlag = false
                        }
                        else -> {
                            emailFlag = true
                        }

                    }
                }
            }
        })

        binding.birthArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    when {
                        s.isEmpty() -> birthFlag = false
                        else -> birthFlag = true
                    }
                }
            }
        })
        binding.genderArea.setOnCheckedChangeListener { _, checkedId ->
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
        binding.findButton.setOnClickListener {
            val userName: String = binding.nameArea.text.toString()
            val userEmail: String = binding.emailArea.text.toString()
            val userBirth: String = binding.birthArea.text.toString()
            val userGender = when {
                binding.male.isChecked -> binding.male.text.toString()
                binding.female.isChecked -> binding.female.text.toString()
                else -> null
            }
            val userData = JoinData(
                null, null, userEmail,
                userName, userBirth, userGender
            )
            val findIdCall = RetrofitAPI.findService.findId()
            findIdCall.enqueue(object : retrofit2.Callback<JoinData> {
                override fun onResponse(call: Call<JoinData>, response: Response<JoinData>) {
                    if (response.isSuccessful) {
                        val receiveData = response.body()
                        AlertDialog.Builder(context).run {
                            setTitle("아이디")
                            setMessage("${receiveData!!.memberId}")
                            setPositiveButton(R.string.ok, null)
                            show()
                        }
                    } else {
                        Log.d(TAG, "why not")
                    }
                }

                override fun onFailure(call: Call<JoinData>, t: Throwable) {
                    Log.d(TAG, "sad")
                    call.cancel()
                }
            })
        }
    }

    private fun checkFlag() {
        binding.findButton.isEnabled = nameFlag && emailFlag && birthFlag && genderFlag
    }

}