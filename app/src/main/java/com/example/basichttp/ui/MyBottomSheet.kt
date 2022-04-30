package com.example.basichttp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basichttp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheet :BottomSheetDialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.modal_bottom_sheet_content, container, false)
    }
    companion object{
        const val TAG = "ModalBottomSheet"
    }
}