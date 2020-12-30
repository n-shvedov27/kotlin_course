package com.shvedov.livinir.presentation.screen.user_info

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.shvedov.livinir.R
import com.shvedov.livinir.databinding.FragmentUserInfoBinding
import com.shvedov.livinir.di.app.DaggerAppComponent
import com.shvedov.livinir.presentation.AuthService
import com.shvedov.livinir.presentation.core_ui.BaseFragment
import com.shvedov.livinir.presentation.extension.requireActivityAs
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class UserInfoFragment : BaseFragment<UserInfoViewModel>() {

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }

    private lateinit var currentUserId: String
    private lateinit var binding: FragmentUserInfoBinding

    override val viewModel: UserInfoViewModel by injectViewModel()
    override val layoutResId = R.layout.fragment_user_info

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.create().userInfoComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentUserId = UserInfoFragmentArgs.fromBundle(requireArguments()).currentUserId

        binding  = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_user_info)
        binding.userInfoFragmentUploadAvatarBtn.setOnClickListener {
            requestPermission()
            openImageChooser()
        }
        binding.userInfoFragmentLogoutBtn.setOnClickListener {
            requireActivityAs<AuthService>().onLogout()
        }
        binding.userInfoVM = viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                REQUEST_CODE_IMAGE_PICKER -> {

                    val uri = data?.data!!
                    binding.userInfoFragmentUploadAvatarBtn.setImageURI(uri)
                    uploadAvatarToServer(uri)
                }
            }
        }
    }

    private fun uploadAvatarToServer(selectedImageUri: Uri) {

        val contentResolver = requireActivity().contentResolver

        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(selectedImageUri, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(requireActivity().cacheDir, contentResolver.getFileName(selectedImageUri))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)


        viewModel.uploadAvatar(file)
    }



    private fun openImageChooser() {

        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            val mimeType = arrayOf("image/jpeg", "image/png")
            putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
        }

        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
    }
}


fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}