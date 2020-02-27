package co.lucaspinazzola.example.ui.base

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import co.lucaspinazzola.example.ui.main.MainActivity
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren


abstract class BaseFragment : Fragment() {

    @Suppress("SetterBackingFieldAssignment")
    var mainLoadingIndicatorVisibilityLd = (activity as? MainActivity)?.binding?.visibility
        set(value) {
            (activity as MainActivity).binding.visibility = value
        }

    lateinit var handler: Handler

    protected val parentJob = SupervisorJob()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        handler = Handler(Looper.getMainLooper())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        parentJob.cancelChildren()
    }

    fun showToast(message: String?) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            // Current Thread is Main Thread.
            val toast = Toast.makeText(activity, message, Toast.LENGTH_LONG)
            val v : TextView = toast.view.findViewById(android.R.id.message)
            v.gravity = Gravity.CENTER
            toast.show()
        } else {
            handler.post {
                val toast = Toast.makeText(activity, message, Toast.LENGTH_LONG)
                val v : TextView = toast.view.findViewById(android.R.id.message)
                v.gravity = Gravity.CENTER
                toast.show()
            }
        }
    }

    fun showError(message: String?, showIfNullOrBlank: Boolean = false) {
        if (message!=null || (showIfNullOrBlank && message.isNullOrBlank())) {
            showToast(message ?: "")
        }
    }
}
