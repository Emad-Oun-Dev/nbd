@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.example.nbdtask.presentation.features.alertDetails

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.example.nbdtask.presentation.features.localAlerts.ReminderWorker
import com.example.nbdtask.utils.TIME_KEY
import com.example.nbdtask.utils.WORKER_NAME
import java.util.concurrent.TimeUnit


/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocalAlertsDetailsScreen(
    state: LocalAlertsDetailsState
) {

    val context = LocalContext.current

    val workManager = WorkManager.getInstance(context)

    runAlertNotificationWorker(
        workManager = workManager,
        time = state.time
    )

    Scaffold(
        topBar = { ToolBar() }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(colorResource(id = R.color.white)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 20.dp, end = 20.dp),
                text = state.time.toString(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center, fontSize = 16.sp
            )

            TextButton(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                onClick = {
                    cancelWorker(workManager)
                },
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_200))
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(R.string.cancel_notification).uppercase()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar() {
    TopAppBar(title = {
        Text(
            text = stringResource(id = R.string.alert_details),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            fontSize = 16.sp,
            color = colorResource(id = R.color.black),
            fontWeight = FontWeight.Bold
        )

    },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.purple_500))
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
        WORKER_NAME,
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )

}

fun cancelWorker(workManager: WorkManager) {
    workManager.cancelUniqueWork(WORKER_NAME)
}

@Preview(showBackground = true)
@Composable
fun UsersScreenPreview() {
    LocalAlertsDetailsScreen(state = LocalAlertsDetailsState(time = 0))
}