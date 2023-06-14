package com.ch96.centermap.viewmodel

import android.content.Context
import androidx.databinding.ObservableField
import com.ch96.centermap.model.ItemModel

class ViewModel(context: Context) {

    //view와 연결할 model 역할 클래스 참조변수
    var itemModel = ItemModel(context)


    // 프로그레스 바 진행값 변경 메소드
    fun setProgress(s:CharSequence, start:Int, end:Int, count:Int) {
        itemModel.progress = ObservableField<String>()
    }
}