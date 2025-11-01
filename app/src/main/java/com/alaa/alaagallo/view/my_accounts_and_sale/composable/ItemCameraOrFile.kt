package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.FileFromUri
import java.io.File

@Composable
fun ItemCameraOrFile(file: Uri?, isNew: Boolean = true, onCaptureImage: (Uri?, File?) -> Unit) {
    val context = LocalContext.current
    var currentImageUri by remember { mutableStateOf<Uri?>(null) }
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            currentImageUri?.let {
                FileFromUri.getFile(context, currentImageUri!!, true)?.let { file ->
                    onCaptureImage(currentImageUri!!, file)
                }
            }

        }
    }
    var showDeleteDialog by remember { mutableStateOf(false) }

    fun launchCamera() {
        val imageFile = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
        val imageUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )

        currentImageUri = imageUri // Store latest URI
        takePictureLauncher.launch(imageUri)
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchCamera()
        } else {
            Toast.makeText(context, "نحتاج الاذن للوصول للكاميرة", Toast.LENGTH_SHORT).show()
        }
    }
    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                FileFromUri.getFile(context, uri, true)
                    ?.let {
                        onCaptureImage(uri, it)
                    }
            }
        }
    )

    fun requestCameraPermissionAndOpen() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            launchCamera()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
    // Permission launcher for Gallery
    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted → Open gallery
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else {
            Toast.makeText(context, "نريد الاذن للوصول الى الوسائط", Toast.LENGTH_SHORT).show()
        }
    }

    fun requestGalleryPermissionAndOpen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API 33+)
            galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            // Android 9 and below (API 28-)
            galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            // Android 10-12 → No permission needed, directly open gallery
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
    Box(Modifier.fillMaxSize()) {
        if (file == null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures {
                            requestGalleryPermissionAndOpen()
                        }
                    },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_file),
                        contentDescription = null,
                        tint = Theme.colors.greyBorder2,
                        modifier = Modifier.size(18.dp, 20.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "الحاق ملف",
                        style = Theme.typography.formContent,
                        color = Theme.colors.greyBorder2,
                        modifier = Modifier.height(19.dp)
                    )
                }
                Spacer(Modifier.width(40.dp))
                Column(
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures {
                            requestCameraPermissionAndOpen()
                        }
                    },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = null,
                        tint = Theme.colors.greyBorder2,
                        modifier = Modifier.size(18.dp, 20.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "فتح الكاميرا",
                        style = Theme.typography.formContent,
                        color = Theme.colors.greyBorder2,
                        modifier = Modifier.height(19.dp)
                    )
                }
            }
        } else {
            AsyncImage(
                model = file,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds
            )
            Row(
                verticalAlignment = Alignment.Top, modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.Transparent
                    )
                    .padding(10.dp)
            ) {
                if (isNew) {
                    Box(
                        Modifier
                            .size(26.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color.White)
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    showDeleteDialog = true
                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = null,
                            tint = Theme.colors.red,
                            modifier = Modifier.size(12.dp, 12.dp)
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                Box(
                    Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Black)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                requestGalleryPermissionAndOpen()
                            }
                        }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_file),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(13.dp, 14.dp)
                    )
                }
                Spacer(Modifier.width(20.dp))
                Box(
                    Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Black)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                requestCameraPermissionAndOpen()
                            }
                        }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(13.dp, 12.dp)
                    )
                }
            }
        }
        if (showDeleteDialog) {
            Dialog(onDismissRequest = { showDeleteDialog = false }) {
                Box(
                    Modifier
                        .fillMaxWidth(0.8f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("هل تريد حذف الصورة؟", style = Theme.typography.titleDialog)
                        Spacer(Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            NegativeDialogButton(text = "نعم") {
                                onCaptureImage(null, null)
                                showDeleteDialog = false
                            }
                            Spacer(Modifier.width(20.dp))
                            NegativeDialogButton(text = "لا", textColor = Color.DarkGray) {
                                showDeleteDialog = false
                            }
                        }
                    }
                }
            }
        }
    }

}