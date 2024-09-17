package code.madlife.foodfirstver.presentation.core.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.databinding.LayoutDialogConfirmBinding

class DialogConfirmCustom(
    context: Context,
    private val content: String?,
    private val textConfirm: String?,
    private val onLogoutClick: () -> Unit
) : Dialog(context) {
    private lateinit var binding: LayoutDialogConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        binding = LayoutDialogConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.content.text = content
        binding.confirmButton.text = textConfirm


        binding.close.setOnClickListener {
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.confirmButton.setOnClickListener {
            onLogoutClick.invoke()
            dismiss()
        }

    }

    companion object {
        fun create(
            context: Context,
            textConfirm: String? = context.getString(R.string.out),
            content: String?,
            onLogoutClick: () -> Unit
        ): DialogConfirmCustom {
            return DialogConfirmCustom(context, content, textConfirm, onLogoutClick)
        }
    }
}