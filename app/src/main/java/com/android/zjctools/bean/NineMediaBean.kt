package com.android.zjctools.bean

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.android.zjctools.model.ZMedia
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class NineMediaBean (
    var medias: List<ZMedia> = mutableListOf()
   ):Parcelable{}