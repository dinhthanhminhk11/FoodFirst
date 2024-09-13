package code.madlife.foodfirstver.presentation.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.presentation.core.common.Constants.TIME_HEADER_DETAIL
import code.madlife.foodfirstver.presentation.core.common.Constants.TIME_SERVER
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit


@SuppressLint("SimpleDateFormat")
object TimeUtils {
    fun convertTimeToString(time: Long): String {
        val df = SimpleDateFormat("dd/MM/yyyy")
        var timeConvert = time
        if (time.toString().length < 12) timeConvert = time * 1000
        return df.format((timeConvert))

    }

    fun getNotifyTimePostNews(timeInput: Long?, context: Context?): String {
        var time = ""
        var nTime: Int
        try {
            if (context != null && timeInput != null && timeInput > 0) {
                nTime = ((System.currentTimeMillis() - timeInput) / 1000 / 60).toInt()
                if (nTime < 1) {
                    time = context.getString(R.string.text_recently)
                } else if (nTime < 60) {
                    time = nTime.toString() + " " + context.getString(R.string.text_minute_ago)
                } else if (nTime / 60 < 24) {
                    nTime /= 60
                    time = nTime.toString() + " " + context.getString(R.string.text_hour_ago)
                } else if (nTime / 60 / 24 < 30) {
                    nTime = nTime / 60 / 24
                    time = nTime.toString() + " " + context.getString(R.string.text_day_ago)
                } else {
                    time = convertTimeToString2(timeInput).toString()
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return time
    }

    fun convertTimeToString2(time: Long?): String? {
        @SuppressLint("SimpleDateFormat") val df = SimpleDateFormat("dd/MM/yyyy")
        return df.format(time)
    }

    fun getTimeStamp(day: Int = 0, month: Int = 0, year: Int = 0): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, day)
        calendar.add(Calendar.MONTH, month)
        calendar.add(Calendar.YEAR, year)
        return calendar.time.time
    }

    fun convertTimeServerToAgo(dataDate: String?): String? {
        if (dataDate.isNullOrEmpty()) return ""
        var convTime: String? = null
        val suffix = "trước"
        try {
            val dateFormat = SimpleDateFormat(TIME_SERVER)
            val pasTime = dateFormat.parse(dataDate)
            val nowTime = Date()
            assert(pasTime != null)
            val dateDiff = nowTime.time - pasTime!!.time
            val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day = TimeUnit.MILLISECONDS.toDays(dateDiff)
            convTime = if (second < 60) {
                //                convTime = second + " giây " + suffix;
                "Vừa xong"
            } else if (minute < 60) {
                "$minute phút $suffix"
            } else if (hour < 24) {
                "$hour giờ $suffix"
            } else if (day >= 7) {
                if (day > 360) {
                    (day / 360).toString() + " năm " + suffix
                } else if (day > 30) {
                    (day / 30).toString() + " tháng " + suffix
                } else {
                    (day / 7).toString() + " tuần " + suffix
                }
            } else {
                "$day ngày $suffix"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.message!!)
        }
        return convTime
    }

    fun convertTimeServerToAgo(dataDate: String?, format: String): String? {
        if (dataDate.isNullOrEmpty()) return ""
        var convTime: String? = null
        val suffix = "trước"
        try {
            val dateFormat = SimpleDateFormat(format)
            val pasTime = dateFormat.parse(dataDate)
            val nowTime = Date()
            assert(pasTime != null)
            val dateDiff = nowTime.time - pasTime!!.time
            val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day = TimeUnit.MILLISECONDS.toDays(dateDiff)
            convTime = if (second < 60) {
                //                convTime = second + " giây " + suffix;
                "Vừa xong"
            } else if (minute < 60) {
                "$minute phút $suffix"
            } else if (hour < 24) {
                "$hour giờ $suffix"
            } else if (day >= 7) {
                if (day > 360) {
                    (day / 360).toString() + " năm " + suffix
                } else if (day > 30) {
                    (day / 30).toString() + " tháng " + suffix
                } else {
                    (day / 7).toString() + " tuần " + suffix
                }
            } else {
                "$day ngày $suffix"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.message!!)
        }
        return convTime
    }

    fun timeLongToDate(time: Long?): String {
        if (time == null)
            return ""
        val outPutFormat = SimpleDateFormat(TIME_HEADER_DETAIL)
        return outPutFormat.format(time) ?: ""
    }


    fun convertTimeDetail(dataDate: String?): String? {
        if (dataDate.isNullOrEmpty()) return ""
        var convTime: String? = null
        try {
            @SuppressLint("SimpleDateFormat") val dateFormat = SimpleDateFormat(TIME_SERVER)
            val pasTime = dateFormat.parse(dataDate)
            convTime = timeLongToDate(pasTime.time) + " GTM+7"
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.message!!)
        }
        return convTime
    }

    fun convertSecondToTime(sec: Long): String {
        val suffix = "trước"
        val passTime = Date(sec)
        val nowTime = Date()
        val dateDiff = nowTime.time - passTime.time
        val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day = TimeUnit.MILLISECONDS.toDays(dateDiff)
        val convTime = if (second < 60) {
            //                convTime = second + " giây " + suffix;
            "Vừa xong"
        } else if (minute < 60) {
            "$minute phút $suffix"
        } else if (hour < 24) {
            "$hour giờ $suffix"
        } else if (day >= 7) {
            if (day > 360) {
                (day / 360).toString() + " năm " + suffix
            } else if (day > 30) {
                (day / 30).toString() + " tháng " + suffix
            } else {
                (day / 7).toString() + " tuần " + suffix
            }
        } else {
            "$day ngày $suffix"
        }
        return convTime
    }

    fun convertStringToTime(dataDate: String?): String? {
        if (dataDate.isNullOrEmpty()) return ""
        var convTime: String? = null
        val suffix = "trước"
        try {
            @SuppressLint("SimpleDateFormat") val dateFormat = SimpleDateFormat(TIME_SERVER)
            val pasTime = dateFormat.parse(dataDate)
            val nowTime = Date()
            assert(pasTime != null)
            val dateDiff = nowTime.time - pasTime!!.time
            val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day = TimeUnit.MILLISECONDS.toDays(dateDiff)
            convTime = if (second < 60) {
                //                convTime = second + " giây " + suffix;
                "Vừa xong"
            } else if (minute < 60) {
                "$minute phút $suffix"
            } else if (hour < 24) {
                "$hour giờ $suffix"
            } else if (day >= 7) {
                if (day > 360) {
                    (day / 360).toString() + " năm " + suffix
                } else if (day > 30) {
                    (day / 30).toString() + " tháng " + suffix
                } else {
                    (day / 7).toString() + " tuần " + suffix
                }
            } else {
                "$day ngày $suffix"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.message!!)
        }
        return convTime
    }

    fun convertTimeNotifyOs(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun converterTest(string: String?): String? {
        return try {
            val sdf = SimpleDateFormat("HH:mm:ss.SS")
            val sdf2 = SimpleDateFormat("HH:mm:ss")
            val sdf3 = SimpleDateFormat("mm:ss")
            val date = string?.let { sdf.parse(it) }
            val out = date?.let {
                if (it.hours <= 0) {
                    sdf3.format(it)
                } else {
                    sdf2.format(it)
                }
            }
            out
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun timeStampToTimeShow(time: Long?): String {
        if (time == null)
            return ""
        val outPutFormat = SimpleDateFormat("dd/MM/yyyy")
        return outPutFormat.format(time) ?: ""
    }

    fun ddMMyyToTimeStamp(time: String?): Long? {
        try {
            if (time.isNullOrEmpty())
                return null
            val inputFormat = SimpleDateFormat("dd/MM/yyyy")
            return inputFormat.parse(time)?.time
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun timeStampToTimeFormat(time: Long?, format: String): String {
        if (time == null)
            return ""
        val outPutFormat = SimpleDateFormat(format)
        return outPutFormat.format(time) ?: ""
    }

    fun durationServerToSecond(time: String?): Long? {
        try {
            if (time.isNullOrEmpty()) return null
            val list = time.split(":")
            val hour = list[0].toInt()
            val minutes = list[1].toInt()
            val sec = list[2].substring(0, 2).toInt()
            return hour * 3600L + minutes * 60L + sec
        } catch (e: Exception) {
            return null
        }

    }

    fun convertTimeToTime(time: String?, input: String, output: String): String? {
        if (time.isNullOrEmpty())
            return null
        return try {
            val inputFormat = SimpleDateFormat(input)
            val timeStampInput = inputFormat.parse(time)
            val outPutFormat = SimpleDateFormat(output)
            outPutFormat.format(timeStampInput)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun checkInRange18h30To6h30NextDay(): Boolean {
        val calendar = Calendar.getInstance()
        val currentTimestamp = calendar.time.time
        // Thiết lập thời gian cho 18h30 tối ngày hiện tại
        calendar.set(Calendar.HOUR_OF_DAY, 18)
        calendar.set(Calendar.MINUTE, 30)
        calendar.set(Calendar.SECOND, 0)

        val time1830Timestamp = calendar.time.time

        // Tăng ngày lên 1 và thiết lập thời gian cho 6h30 sáng ngày hôm sau
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 6)
        calendar.set(Calendar.MINUTE, 30)

        val time630NextDayTimestamp = calendar.time.time

        return currentTimestamp in time1830Timestamp..time630NextDayTimestamp
    }

    fun checkInRange6h30To18h30(): Boolean {
        val calendar = Calendar.getInstance()
        val currentTimestamp = calendar.time.time
        //  thiết lập thời gian cho 6h30
        calendar.set(Calendar.HOUR_OF_DAY, 6)
        calendar.set(Calendar.MINUTE, 30)
        val time630NextDayTimestamp = calendar.time.time

        // Thiết lập thời gian cho 18h30
        calendar.set(Calendar.HOUR_OF_DAY, if (true) 14 else 18)
        calendar.set(Calendar.MINUTE, 30)
        val time1830Timestamp = calendar.time.time

        return currentTimestamp in time630NextDayTimestamp..time1830Timestamp
    }
}