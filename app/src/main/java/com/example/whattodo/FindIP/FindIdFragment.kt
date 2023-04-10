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
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var birth: EditText
    private lateinit var genderGruop: RadioGroup
    private lateinit var male: RadioButton
    private lateinit var female: RadioButton
    private lateinit var button: Button
    private var nameFlag=false
    private var emailFlag=false
    private var birthFlag=false
    private var genderFlag=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_id, container, false)
        name = view.findViewById(R.id.nameArea)
        email = view.findViewById(R.id.emailArea)
        birth = view.findViewById(R.id.birthArea)
        genderGruop = view.findViewById(R.id.radioGroup2)
        male = view.findViewById(R.id.male)
        female = view.findViewById(R.id.female)
        button = view.findViewById(R.id.emailButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s!=null) {
                    when {
                        s.isEmpty() -> nameFlag=false
                        else -> nameFlag=true
                    }
                }
            }
        })
        email.addTextChangedListener(object : TextWatcher {
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

        birth.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s!=null) {
                    when {
                        s.isEmpty()->birthFlag=false
                        else -> birthFlag=true
                    }
                }
            }
        })
        genderGruop.setOnCheckedChangeListener { _, checkedId ->
            genderFlag= when(checkedId) {
                R.id.male->true
                R.id.female->true
                else -> false
            }
            checkFlag()
        }
    }




    override fun onStart() {
        super.onStart()
        button.setOnClickListener {
            val userName: String = name.text.toString()
            val userEmail: String = email.text.toString()
            val userBirth: String = birth.text.toString()
            val userGender=when {
                male.isChecked->male.text.toString()
                female.isChecked -> female.text.toString()
                else -> null
            }
        }

    }
    private fun checkFlag() {
        button.isEnabled=nameFlag&&emailFlag&&birthFlag&&genderFlag
    }

}