package com.example.moviedbapplication.common.extensions

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.moviedbapplication.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

@SuppressLint("InflateParams")
fun Fragment.setUpBottomSheetDialog(
    onSendClick: (String) -> Unit,
) {
    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.fragment_forgot_password, null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val eMail = view.findViewById<EditText>(R.id.edit_text_send_email)
    val buttonSend = view.findViewById<AppCompatButton>(R.id.button_send)
    val buttonCancel = view.findViewById<AppCompatButton>(R.id.button_cancel)

    buttonSend.setOnClickListener {
        val email = eMail.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }

    buttonCancel.setOnClickListener {
        dialog.dismiss()
    }
}