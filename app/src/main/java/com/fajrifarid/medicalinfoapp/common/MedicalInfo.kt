package com.fajrifarid.medicalinfoapp.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class MedicalInfo(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "Hospital",
    val address: String = "Jl. Jendral Sudirman No. 123, RT5/RW5, Semanggi, Jakarta Selatan",
    val phoneNumber: String = "+62 888-9999-0000"
):Parcelable
