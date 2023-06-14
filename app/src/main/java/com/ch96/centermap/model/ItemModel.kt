package com.ch96.centermap.model

import android.content.Context
import androidx.databinding.ObservableField

class ItemModel constructor(val context: Context) {
    // 프로그레스 바 진행값
    var progress = ObservableField<String>()
}