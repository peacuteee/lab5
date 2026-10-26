package com.example.lab5_baitap

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5_baitap.databinding.ActivityMainActivity2TrangchuBinding

class MainActivity2_Trangchu : AppCompatActivity() {

    private lateinit var binding: ActivityMainActivity2TrangchuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainActivity2TrangchuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nhận đối tượng User từ Intent (hỗ trợ cả Android mới và cũ)
        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("EXTRA_USER", User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("EXTRA_USER") as? User
        }

        // Hiển thị thông tin người dùng lên màn hình Trang chủ
        user?.let {
            binding.tvUserInfo.text = """
                Họ và tên: ${it.fullName}
                Email: ${it.email}
                Số điện thoại: ${it.phone}
                Tên đăng nhập: ${it.username}
                Mật khẩu: ${"*".repeat(it.password.length)}
            """.trimIndent()
        }

        // Nút quay lại màn hình Đăng ký
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}