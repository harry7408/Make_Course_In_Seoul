package com.example.whattodo.FindIP

import android.os.Bundle
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
import com.example.whattodo.R
import org.w3c.dom.Text

class FindPassFragment : Fragment() {
    private lateinit var email: EditText
    private lateinit var name: EditText
    private lateinit var nickname: EditText
    private lateinit var birth: EditText
    private lateinit var genderGroup: RadioGroup
    private lateinit var male: RadioButton
    private lateinit var female: RadioButton
    private lateinit var button: Button
    private var emailFlag=false
    private var nameFlag=false
    private var nickNameFlag=false
    private var birthFlag=false
    private var genderFalg=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_pass, container, false)
        email = view.findViewById(R.id.emailArea)
        name = view.findViewById(R.id.nameArea)
        nickname = view.findViewById(R.id.nickNameArea)
        birth = view.findViewById(R.id.birthArea)
        genderGroup = view.findViewById(R.id.radioGroup)
        male = view.findViewById(R.id.male)
        female = view.findViewById(R.id.female)
        button = view.findViewById(R.id.passButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s!=null) {
                    emailFlag = when {
                        s.isEmpty() -> false
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches() -> false
                        else -> true
                    }
                }
            }
        })

        name.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s!=null) {
                    nameFlag=when {
                        s.isEmpty()->false
                        else->true
                    }
                }
            }
        })

        nickname.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s!=null) {
                    nickNameFlag=when {
                        s.isEmpty()->false
                        else -> true
                    }
                }
            }
        })

        birth.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s!=null) {
                    birthFlag=when {
                        s.isEmpty()->false
                        else -> true
                    }
                }
            }
        })

        genderGroup.setOnCheckedChangeListener { _, checkedId ->
            genderFalg = when(checkedId) {
                R.id.male -> true
                R.id.female -> true
                else -> false
            }
            checkFlag()
        }

    }

    override fun onStart() {
        super.onStart()
        button.setOnClickListener {
            val userEmail = email.text.toString()
            val userName = name.text.toString()
            val userNickName = nickname.text.toString()
            val userBirth = birth.text.toString()
            val userGender = when {
                male.isChecked -> male.text.toString()
                else -> female.text.toString()
            }
        }


    }
    private fun checkFlag() {
        button.isEnabled=emailFlag&&nameFlag&&nickNameFlag&&birthFlag&&genderFalg
    }
}