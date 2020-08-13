import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mylogin.ui.main.MainActivity
import com.example.mylogin.manager.ManagerActivity
import com.example.mylogin.ui.login.CreateUserActivity
import com.example.mylogin.ui.login.LoginActivity

fun Fragment.slideNextFragment(fragment: Fragment,
                               @IdRes fragmentContentId: Int,
                               @AnimRes enter: Int? = null,
                               @AnimRes exit: Int? = null,
                               @AnimRes popEnter: Int? = null,
                               @AnimRes popExit: Int? = null,
                               vararg sharedElements: Pair<View, String>) {
    if (isAdded) {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        enter?.let { exit?.let { it1 -> popEnter?.let { it2 -> popExit?.let { it3 -> fragmentTransaction?.setCustomAnimations(it, it1, it2, it3) } } } }
        fragmentTransaction?.addToBackStack(fragment.javaClass.name)
        for (i in sharedElements.indices) {
            val sharedElement = sharedElements[i]
            fragmentTransaction?.addSharedElement(sharedElement.first, sharedElement.second)
        }
        fragmentTransaction?.replace(fragmentContentId, fragment)?.commitAllowingStateLoss()
    }
}

fun Fragment.slidePreviousFragment() {
    fragmentManager?.popBackStackImmediate()
}

fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(
            Context
            .INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}
fun String.isEmail(): Boolean {
    val p = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)\$".toRegex()
    return matches(p)
}

/**
 * Extension method to write preferences.
 */
inline fun SharedPreferences.edit(preferApply: Boolean = false, f: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.f()
    if (preferApply) editor.apply() else editor.commit()
}

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}
/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}
/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, tag: String) {
    supportFragmentManager.transact {
        add(fragment, tag)
    }
}

inline fun <reified T> FragmentActivity.getTopFragment(): T?
        = supportFragmentManager.fragments.firstOrNull()?.let { it as? T }

fun Context.startHomeActivity() =
    Intent(this, MainActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startLoginActivity() =
    Intent(this, LoginActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startManagerActivity() =
    Intent(this, ManagerActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
fun Context.startCreateUserActivity() =
    Intent(this, CreateUserActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun getScreenWidth(context: Context): Int {
    val metrics = getDisplayMetrics(context)
    return metrics.widthPixels
}

@SuppressLint("WrongConstant")
fun getDisplayMetrics(context: Context): DisplayMetrics {
    return if (context is Activity) {
        val metrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metrics)
        metrics
    } else {
        val wm = context.getSystemService("window") as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        metrics
    }
}