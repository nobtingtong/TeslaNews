package com.scg.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.scg.core.extension.toDateTimeDisplay
import com.scg.detail.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            getString("title")?.also {
                _binding?.textTitle?.text = it
            }
            getString("description")?.also {
                _binding?.textDescription?.text = it
            }
            getString("date")?.also {
                _binding?.textDate?.text = "Updated:" + it.toDateTimeDisplay()
            }
            getString("imageUrl")?.also {
                _binding?.imgBanner?.load(it)
            }
        }
    }
}