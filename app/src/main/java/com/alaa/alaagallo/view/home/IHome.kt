package com.alaa.alaagallo.view.home

import com.alaa.alaagallo.model.newnumbers.Data


interface IHome {
    fun openActivity() {}
    fun importantNumbers() {}
    fun suggestPair() {}
    fun pairs() {}
    fun searchCompa() {}
    fun sendSubCategoriesData(position: String, id: String, url: String)
    fun sendNumbersToDetailsNumbers(listNumbers: Data)
}