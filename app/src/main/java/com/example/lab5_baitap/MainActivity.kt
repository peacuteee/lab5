package com.example.lab5_baitap

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.lab5_baitap.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClearErrorOnTyping()
        setupButtons()
    }

    // Tự động tắt thông báo lỗi đỏ ngay khi người dùng nhập lại
    private fun setupClearErrorOnTyping() {
        binding.etFullName.doAfterTextChanged { binding.tilFullName.error = null }
        binding.etEmail.doAfterTextChanged { binding.tilEmail.error = null }
        binding.etPhone.doAfterTextChanged { binding.tilPhone.error = null }
        binding.etUsername.doAfterTextChanged { binding.tilUsername.error = null }
        binding.etPassword.doAfterTextChanged { binding.tilPassword.error = null }
    }

    private fun setupButtons() {
        // Xử lý nút Đăng ký
        binding.btnRegister.setOnClickListener {
            if (validateForm()) {
                // Tạo đối tượng User từ các trường đã nhập hợp lệ
                val newUser = User(
                    fullName = binding.etFullName.text.toString().trim(),
                    email = binding.etEmail.text.toString().trim(),
                    phone = binding.etPhone.text.toString().trim(),
                    username = binding.etUsername.text.toString().trim(),
                    password = binding.etPassword.text.toString()
                )

                // Success -> Chuyển sang màn hình Trang chủ và truyền đối tượng User
                val intent = Intent(this, MainActivity2_Trangchu::class.java)
                intent.putExtra("EXTRA_USER", newUser)
                startActivity(intent)
            } else {
                // Fail -> Hiện Snackbar báo lỗi
                Snackbar.make(
                    binding.root,
                    "Đăng ký thất bại! Vui lòng kiểm tra lại các thông tin báo đỏ.",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(Color.parseColor("#E74C3C"))
                    .setTextColor(Color.WHITE)
                    .show()
            }
        }

        // Xử lý nút Huỷ -> Xoá trắng các trường để nhập lại
        binding.btnCancel.setOnClickListener {
            clearForm()
            Snackbar.make(
                binding.root,
                "Đã xoá trắng các trường thông tin",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString()

        // 1. Validate Họ tên (tối thiểu 3 ký tự, không chứa số)
        if (fullName.isEmpty()) {
            binding.tilFullName.error = "Họ và tên không được để trống"
            isValid = false
        } else if (fullName.length < 3 || !fullName.matches(Regex("^[\\p{L} .'-]+$"))) {
            binding.tilFullName.error = "Họ tên phải từ 3 ký tự và chỉ chứa chữ cái"
            isValid = false
        }

        // 2. Validate Email
        if (email.isEmpty()) {
            binding.tilEmail.error = "Email không được để trống"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Định dạng email không hợp lệ (VD: sv@ute.vn)"
            isValid = false
        }

        // 3. Validate Số điện thoại (10 số, đầu 03, 05, 07, 08, 09)
        if (phone.isEmpty()) {
            binding.tilPhone.error = "Số điện thoại không được để trống"
            isValid = false
        } else if (!phone.matches(Regex("^(03|05|07|08|09)\\d{8}$"))) {
            binding.tilPhone.error = "SĐT phải gồm 10 số (bắt đầu bằng 03, 05, 07, 08, 09)"
            isValid = false
        }

        // 4. Validate Tên đăng nhập (tối thiểu 4 ký tự, không khoảng trắng)
        if (username.isEmpty()) {
            binding.tilUsername.error = "Tên đăng nhập không được để trống"
            isValid = false
        } else if (!username.matches(Regex("^[a-zA-Z0-9_]{4,20}$"))) {
            binding.tilUsername.error = "Username từ 4-20 ký tự, không chứa dấu cách"
            isValid = false
        }

        // 5. Validate Mật khẩu (tối thiểu 6 ký tự)
        if (password.isEmpty()) {
            binding.tilPassword.error = "Mật khẩu không được để trống"
            isValid = false
        } else if (password.length < 6) {
            binding.tilPassword.error = "Mật khẩu phải có ít nhất 6 ký tự"
            isValid = false
        }

        return isValid
    }

    private fun clearForm() {
        binding.etFullName.text?.clear()
        binding.etEmail.text?.clear()
        binding.etPhone.text?.clear()
        binding.etUsername.text?.clear()
        binding.etPassword.text?.clear()

        binding.tilFullName.error = null
        binding.tilEmail.error = null
        binding.tilPhone.error = null
        binding.tilUsername.error = null
        binding.tilPassword.error = null

        binding.etFullName.requestFocus()
    }
}