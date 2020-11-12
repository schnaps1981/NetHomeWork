package com.example.nethomework.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.nethomework.MutableSafeLiveData
import com.example.nethomework.R
import com.example.nethomework.network.Network
import com.example.nethomework.network.models.ImageResponse
import com.example.nethomework.network.safeRun
import com.example.nethomework.toMultipart
import kotlinx.android.synthetic.main.fmt_upload_image.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FragmentUploadImage : Fragment(R.layout.fmt_upload_image) {

    private val network = Network()

    private val args: FragmentUploadImageArgs by navArgs()

    private val isUploading = MutableSafeLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btnSend.setOnClickListener {
            isUploading.postValue(true)

            CoroutineScope(Dispatchers.IO).launch {

                val file = args.imageFile.path?.let { File(it) } ?: return@launch

                val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

                val result = safeRun({
                    network.imgurApi.uploadPhoto(
                        title = etTilte.text.toString().toMultipart("title"),
                        description = etDescription.text.toString().toMultipart("description"),
                        image = MultipartBody.Part.createFormData(
                            "image",
                            file.name,
                            requestBody
                        )
                    )
                }, ImageResponse.EMPTY)

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),

                        if (result.hasError()) {
                            "Не загружено! ошибка -> ${result.error?.localizedMessage}"
                        } else {
                            "Загружено успешно! ссылка -> ${result.data.data.link}"
                        },

                        Toast.LENGTH_SHORT
                    ).show()

                    if (!result.hasError()) {
                        etLink.setText(result.data.data.link)
                    }

                    isUploading.postValue(false)
                }
            }
        }

        isUploading.observe(viewLifecycleOwner, Observer {
            btnSend.visibility = if (it) View.GONE else View.VISIBLE
            progressBar.visibility = if (!it) View.GONE else View.VISIBLE
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(SAVE_STATE_UPLOAD_PROGRESS_KEY, isUploading.value)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.getBoolean(SAVE_STATE_UPLOAD_PROGRESS_KEY)?.let {
            isUploading.postValue(it)
        }
    }

    companion object {
        const val SAVE_STATE_UPLOAD_PROGRESS_KEY = "SAVE_STATE_UPLOAD_PROGRESS_KEY"
    }
}
