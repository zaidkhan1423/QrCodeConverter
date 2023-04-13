package com.zaidkhan.qrcode.view.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zaidkhan.qrcode.R
import com.zaidkhan.qrcode.databinding.FragmentScanBinding
import com.zaidkhan.qrcode.view.activity.WebPageActivity


class ScanFragment : Fragment() {

    private lateinit var binding: FragmentScanBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var packageManager: PackageManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val scannerView = binding.scannerView
        codeScanner = CodeScanner(requireContext(), scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                showDialogOne(it.text)
            }
        }
        codeScanner.errorCallback = ErrorCallback {
            requireActivity().runOnUiThread {
                Toast.makeText(activity, "Camera Error : ${it.message}", Toast.LENGTH_LONG).show()
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
        checkPermission()
    }

    private fun showDialogOne(QrText: String) {

        val dialog = BottomSheetDialog(requireContext(),R.style.bottomSheetStyle)
        dialog.setContentView(R.layout.bottom_sheet_dialog)
        val textInBottomSheet = dialog.findViewById<TextView>(R.id.QrText)
        val searchOnInternet = dialog.findViewById<ImageView>(R.id.searchOnInternet)
        searchOnInternet?.setOnClickListener {
            var URL = QrText
            var package_name = "com.android.chrome";
            openCustomTab(URL,package_name)
        }
        textInBottomSheet?.text = QrText
        dialog.show()
    }

    private fun openCustomTab(URL: String,package_name: String){
        val builder = CustomTabsIntent.Builder()

        // to set the toolbar color use CustomTabColorSchemeParams
        // since CustomTabsIntent.Builder().setToolBarColor() is deprecated

        val params = CustomTabColorSchemeParams.Builder()
        params.setToolbarColor(ContextCompat.getColor(requireContext(), R.color.sky_blue))
        builder.setDefaultColorSchemeParams(params.build())

        // shows the title of web-page in toolbar
        builder.setShowTitle(true)

        // setShareState(CustomTabsIntent.SHARE_STATE_ON) will add a menu to share the web-page
        builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)

        // To modify the close button, use
        // builder.setCloseButtonIcon(bitmap)

        // to set weather instant apps is enabled for the custom tab or not, use
        builder.setInstantAppsEnabled(true)

        //  To use animations use -
        //  builder.setStartAnimations(this, android.R.anim.start_in_anim, android.R.anim.start_out_anim)
        //  builder.setExitAnimations(this, android.R.anim.exit_in_anim, android.R.anim.exit_out_anim)
        val customBuilder = builder.build()

        if (this.isPackageInstalled(package_name)) {
            // if chrome is available use chrome custom tabs
            customBuilder.intent.setPackage(package_name)
            try {
                customBuilder.launchUrl(requireContext(), Uri.parse(URL))
            }catch (e: Exception){
                customBuilder.launchUrl(requireContext(), Uri.parse("https://www.google.com/search?q=$URL"))
            }
        } else {
            // if not available use WebView to launch the url
            Toast.makeText(requireContext(),"Hello",Toast.LENGTH_LONG).show()
            val intent = Intent(context, WebPageActivity::class.java)
            intent.putExtra("URL",URL)
            context?.startActivity(intent)
        }
    }

    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager = activity?.packageManager!!
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                1112
            )
        } else {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1112 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            codeScanner.startPreview()
        } else {
            Toast.makeText(activity, "Can't Scan Until You Give The Permission", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}

