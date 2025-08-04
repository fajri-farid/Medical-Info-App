package com.fajrifarid.medicalinfoapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.fajrifarid.medicalinfoapp.common.MedicalInfo

class DataManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("medical_info_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_MEDICAL_INFO_LIST = "medical_info_list"
    }

    fun saveMedicalInfoList(medicalInfoList: List<MedicalInfo>) {
        val jsonString = gson.toJson(medicalInfoList)
        sharedPreferences.edit()
            .putString(KEY_MEDICAL_INFO_LIST, jsonString)
            .apply()
    }

    fun getMedicalInfoList(): MutableList<MedicalInfo> {
        val jsonString = sharedPreferences.getString(KEY_MEDICAL_INFO_LIST, null)
        return if (jsonString != null) {
            val type = object : TypeToken<List<MedicalInfo>>() {}.type
            gson.fromJson<List<MedicalInfo>>(jsonString, type).toMutableList()
        } else {
            mutableListOf(
                MedicalInfo(name = "Hospital 1", address = "Jl. Kesehatan No. 1", phoneNumber = "021-1111"),
                MedicalInfo(name = "Hospital 2", address = "Jl. Medis No. 2", phoneNumber = "021-2222")
            )
        }
    }

    fun addMedicalInfo(medicalInfo: MedicalInfo) {
        val currentList = getMedicalInfoList()
        currentList.add(medicalInfo)
        saveMedicalInfoList(currentList)
    }

    // Hapus data
    fun removeMedicalInfo(medicalInfo: MedicalInfo) {
        val currentList = getMedicalInfoList()
        currentList.remove(medicalInfo)
        saveMedicalInfoList(currentList)
    }

    fun updateMedicalInfo(oldInfo: MedicalInfo, newInfo: MedicalInfo) {
        val currentList = getMedicalInfoList()
        val index = currentList.indexOf(oldInfo)
        if (index != -1) {
            currentList[index] = newInfo
            saveMedicalInfoList(currentList)
        }
    }

    fun clearAllData() {
        sharedPreferences.edit()
            .remove(KEY_MEDICAL_INFO_LIST)
            .apply()
    }
}