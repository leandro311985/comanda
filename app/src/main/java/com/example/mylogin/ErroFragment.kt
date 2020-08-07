package com.example.mylogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_erro.*


class ErroFragment : Fragment() {

    companion object {

        var textErro = String()

        fun newInstance(text: String): ErroFragment {
            textErro = text
            return ErroFragment()
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
            activity?.supportFragmentManager?.popBackStack()
        }


    }

}