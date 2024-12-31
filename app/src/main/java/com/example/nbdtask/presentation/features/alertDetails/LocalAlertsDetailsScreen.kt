@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.example.nbdtask.presentation.features.alertDetails

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.nbdtask.R
import com.example.nbdtask.presentation.features.ReminderWorker
import com.example.nbdtask.utils.TIME_KEY
import com.example.nbdtask.utils.WORKER_NAME
import com.example.nbdtask.utils.common.ToolBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.util.concurrent.TimeUnit


/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocalAlertsDetailsScreen(
    state: LocalAlertsDetailsState,
    onNavigationRequested: (navigationEffect: LocalAlertDetailsSideEffects.Navigation) -> Unit
) {

    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val showCancelButton = remember { mutableStateOf(false) }
    val workManager = WorkManager.getInstance(context)

    val notificationsPermissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )

    if (ReminderWorker.isWorkScheduled(context = context, time = state.time)) {
        showCancelButton.value = true
    }

    Scaffold(
        topBar = {
            ToolBar(
                showCancel = showCancelButton,
                titleStringId = R.string.alert_details,
                onBackPressed = {
                    onNavigationRequested.invoke(LocalAlertDetailsSideEffects.Navigation.CloseScreen)
                }, onCancelNotification = {
                    cancelWorker(workManager, state.time)
                    Toast.makeText(
                        context,
                        context.getString(R.string.worker_canceled_successfully),
                        Toast.LENGTH_LONG
                    ).show()
                }, onCancelAllNotification = {}
            )
        }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(colorResource(id = R.color.white)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 20.dp, end = 20.dp),
                text = "Schedule me after ${state.time} seconds",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start, fontSize = 16.sp
            )

            TextButton(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                onClick = {
                    if (notificationsPermissionState.status.isGranted) {
                        showDialog.value = true
                    } else {
                        notificationsPermissionState.launchPermissionRequest()
                    }
                },
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.gray_light))
            ) {
                Text(
                    color = Color.Black,
                    text = stringResource(R.string.schedule_this_notification_txt).uppercase()
                )
            }
        }
    }

    if (showDialog.value) {
        ShowConfirmationDialog(
            onConfirm = {
                runAlertNotificationWorker(
                    workManager = workManager,
                    time = state.time
                )
                showDialog.value = false
            },
            onDismiss = { showDialog.value = false }
        )
    }
}

@Composable
fun ShowConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss.invoke()
        },
        title = {
            Text(text = stringResource(id = R.string.schedule_notification_txt))
        },
        text = {
            Text(stringResource(id = R.string.confirmation_msg))
        },
        confirmButton = {
            Button(
                onClick = { onConfirm.invoke() },
                shape = RoundedCornerShape(25),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.black))
            ) {
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.yes).uppercase()
                )
            }
        }
    )
}

fun runAlertNotificationWorker(workManager: WorkManager, time: Long) {
    //Note: repeatInterval must be >= MIN_PERIODIC_INTERVAL_MILLIS (15 minutes)
    val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
        repeatInterval = 15,
        TimeUnit.MINUTES
    )
        .setInitialDelay(time, TimeUnit.SECONDS)
        .setInputData(
            Data.Builder()
                .putLong(TIME_KEY, time)
                .build()
        )
        .build()

    workManager.enqueueUniquePeriodicWork(
        "$WORKER_NAME$time",
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )

}

fun cancelWorker(workManager: WorkManager, time: Long) {
    workManager.cancelUniqueWork("$WORKER_NAME$time")
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun UsersScreenPreview() {
    LocalAlertsDetailsScreen(state = LocalAlertsDetailsState(time = 0),
        onNavigationRequested = {})
}