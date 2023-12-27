package com.android.zjctools.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.android.zjctools.model.ZContact


object ZContactUtils {

    /**
     * 获取系统全部联系人的api方法
     *
     * @param context
     * @return
     */
    fun loadContactList(context: Context): MutableList<ZContact> {
        val resolver: ContentResolver = context.contentResolver
        // 1.查询raw_contacts表，把联系人的id取出来
        val uri: Uri = Uri.parse("content://com.android.contacts/raw_contacts")
        val datauri: Uri = Uri.parse("content://com.android.contacts/data")
        val infos: MutableList<ZContact> = mutableListOf()
        val cursor: Cursor = resolver.query(
            uri, arrayOf("contact_id"),
            null, null, null
        ) ?: return infos


        while (cursor.moveToNext()) {
            val id: String = cursor.getString(0)
            if (id != null) {
                val info = ZContact()
                info.id=id
                // 2.根据联系人的id，查询data表，把这个id的数据取出来
                // 系统api查询data表的时候不是真正的查询的data表，而是查询data表的视图
                val dataCursor: Cursor? = resolver.query(
                    datauri, arrayOf(
                        "data1", "mimetype"
                    ), "raw_contact_id=?", arrayOf(id), null
                )
                if(dataCursor==null){

                }else {
                    while (dataCursor.moveToNext()) {
                        val data1: String = dataCursor.getString(0)
                        val mimetype: String = dataCursor.getString(1)
                        if ("vnd.android.cursor.item/name" == mimetype) {
                            println("姓名=$data1")
                            info.name=data1
                        } else if ("vnd.android.cursor.item/email_v2" == mimetype) {
                            println("邮箱=$data1")
                            info.email=data1
                        } else if ("vnd.android.cursor.item/phone_v2" == data1) {
                            println("电话=$data1")
                            info.phone=data1
                        } else if ("vnd.android.cursor.item/im" == mimetype) {
                            println("QQ=$data1")
                            info.qq=data1
                        }
                    }
                }
                infos.add(info)
                println("-------")
                dataCursor?.close()
            }
        }
        cursor?.close()
        return infos
    }


}