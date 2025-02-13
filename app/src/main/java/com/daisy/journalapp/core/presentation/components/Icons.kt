package com.daisy.journalapp.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.daisy.journalapp.R

val ArrowLeftIcon: ImageVector
    get() = Icons.AutoMirrored.Rounded.KeyboardArrowLeft

val PersonIcon: ImageVector
    get() = Icons.Rounded.Person

val EmailIcon: ImageVector
    get() = Icons.Rounded.MailOutline

val LockIcon: ImageVector
    get() = Icons.Outlined.Lock

val CheckIcon: ImageVector
    get() = Icons.Rounded.Check

val CloseIcon: ImageVector
    get() = Icons.Rounded.Close

val EyeClosedIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.eye_closed)

val EyeOpenedIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.eye_opened)

