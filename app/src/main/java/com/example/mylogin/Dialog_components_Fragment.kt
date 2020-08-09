package com.example.mylogin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mylogin.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_erro.*


class Dialog_components_Fragment : Fragment() {

    companion object {

        var textErro = String()
        var iconSuccess: Boolean = false

        fun newInstance(text: String, icon: Boolean): Dialog_components_Fragment {
            textErro = text
            iconSuccess = icon
            return Dialog_components_Fragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_erro, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        txt_erro.text = textErro

        btnErroFinish.setOnClickListener {
            if (iconSuccess) {
                activity?.supportFragmentManager?.popBackStack()
                Intent(view?.context, LoginActivity::class.java).also {
                    view?.context?.startActivity(it)
                }
            }else

                activity?.supportFragmentManager?.popBackStack()
        }

        if (iconSuccess) {
            appCompatImageView.background = resources.getDrawable(R.drawable.ic_like)
        } else
            appCompatImageView.background = resources.getDrawable(R.drawable.ic_alerta)


    }

}