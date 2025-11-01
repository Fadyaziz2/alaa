@file:Suppress("DEPRECATION")

package com.alaa.alaagallo.view.registration

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.helpers.LaunchingActivity
import com.alaa.alaagallo.base.helpers.Resource
import com.alaa.alaagallo.base.helpers.extensions.makeLongToast
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.view.home.Home
import com.alaa.alaagallo.view.home.HomeActivity

class LoginActivity : FragmentActivity() {

    private val noAccount: TextView get() = findViewById(R.id.tv_dont_have_account)
    private val imgPass: ImageView get() = findViewById(R.id.imgPass)
    private val whats: ImageView get() = findViewById(R.id.whats_app)
    private val imgNoPass: ImageView get() = findViewById(R.id.imgNoPass)
    private val login: Button get() = findViewById(R.id.btn_login)
    private val phoneNumber: TextView get() = findViewById(R.id.phone_number)
    private val emailTextView: TextView get() = findViewById(R.id.et_email)
    private val passwordTextView: TextView get() = findViewById(R.id.et_password)
    private val passwordEd: EditText get() = findViewById(R.id.et_password)
    private val loading: ProgressBar get() = findViewById(R.id.loading)
    val launchingActivity = LaunchingActivity.getInstance(this)

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {
        loading.visibility = View.GONE
        login.visibility = View.VISIBLE

        noAccount.setOnClickListener {
            //openActivity(this, SignUpActivity::class.java)
            //finishAffinity()
        }

        login.setOnClickListener {
            val email = emailTextView.text.toString()
            val password = passwordTextView.text.toString()

            login(email, password)

        }

        imgPass.setOnClickListener {
            it.visibility = View.GONE
            imgNoPass.visibility = View.VISIBLE
            passwordEd.transformationMethod = PasswordTransformationMethod()
        }
        imgNoPass.setOnClickListener {
            it.visibility = View.GONE
            imgPass.visibility = View.VISIBLE
            passwordTextView.transformationMethod = null
        }
        phoneNumber.setOnClickListener {
            goToWhats()
        }
        whats.setOnClickListener {
            goToWhats()
        }
    }

    private fun goToWhats(){
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=+2${phoneNumber.text}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
    private fun login(email: String, password: String) {
        if (email.trim().isNotEmpty() && password.trim().isNotEmpty()) {
            viewModel.login(
                email, password
            ).observe(this, Observer {
                when(it.status) {
                    Resource.Status.SUCCESS -> {
                        loading.visibility = View.GONE
//                        getSharedPreferences(this).edit().putString("token", Home.token).apply()
                        getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
                            .edit()
                            .putString("token", Home.token)
                            .putString("name", Home.name)
                            .putInt("id", Home.id)
                            .putString("end_date", Home.expirationDate)
                            .apply()
                        openActivity(this, HomeActivity::class.java)
                        finishAffinity()
                    }
                    Resource.Status.ERROR -> {
//                        Toast.makeText(this, "من فضلك ادخل بيانات صحيحة", Toast.LENGTH_LONG).show()
                        login.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                    }
                    Resource.Status.LOADING -> {
                        loading.visibility = View.VISIBLE
                        login.visibility = View.GONE
                    }
                    Resource.Status.EMPTY -> {}
                }
            })
        }else{
            makeLongToast("من فضلك ادخل بيانات صحيحة")
        }
    }
}

