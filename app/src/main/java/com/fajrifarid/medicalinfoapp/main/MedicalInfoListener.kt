package com.fajrifarid.medicalinfoapp.main

import com.fajrifarid.medicalinfoapp.common.MedicalInfo

interface MedicalInfoListener {
    fun onPhoneNumberClicked(phoneNumber: String)
    fun onEditClicked(item: MedicalInfo)
    fun onDeleteClicked(item: MedicalInfo)
}

