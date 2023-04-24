package com.example.whattodo.FindIP

import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.text.Editable
import android.text.TextWatcher
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
        }
    }

    private fun checkFlag() {
        binding.findButton.isEnabled = nameFlag && emailFlag && birthFlag && genderFlag
    }

}