package ru.turizmryazan.utils

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import ru.turizmryazan.R
import java.lang.Exception

class InternalLink {

    fun openMap(lat: String?, lon: String?, requireActivity: FragmentActivity) {
        if (!lat.isNullOrEmpty() && !lon.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(Utils.getYandexMapUri(lat, lon))

            try {
                if (intent.resolveActivity(requireActivity.getPackageManager()) != null) {
                    requireActivity.startActivity(intent)
                } else {
                    Toast.makeText(
                        requireActivity,
                        requireActivity.getString(R.string.yandex_map_not_install),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Log.d(Constants.LOGS_TAG, e.message ?: "")
            }
        }
    }

    fun openSite(site: String?, requireActivity: FragmentActivity) {
        if (!site.isNullOrEmpty()) {
            val intent: Intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(site)
            requireActivity.startActivity(intent)
        }
    }

    fun openEmail(email: String?, requireActivity: FragmentActivity) {
        if (!email.isNullOrEmpty()) {
            val intent: Intent = Intent(Intent.ACTION_SENDTO)
            val mailTo: String = "mailto:" + email
            intent.data = Uri.parse(mailTo)
            requireActivity.startActivity(intent)
        }
    }

    fun openPhone(phone: Long?, requireActivity: FragmentActivity) {
        phone?.let {
            val intent = Intent(Intent.ACTION_DIAL)
            val tel = "tel:" + phone.toString()
            intent.data = Uri.parse(tel)
            requireActivity.startActivity(intent)
        }
    }
}