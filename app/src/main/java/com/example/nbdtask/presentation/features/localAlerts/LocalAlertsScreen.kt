@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

package com.example.nbdtask.presentation.features.localAlerts

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.work.WorkManager
import com.example.nbdtask.MainActivity
import com.example.nbdtask.R
import com.example.nbdtask.core.base.SIDE_EFFECTS_KEY
import com.example.nbdtask.presentation.features.ReminderWorker
import com.example.nbdtask.utils.common.Progress
import com.example.nbdtask.utils.common.ToolBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocalAlertsScreen(
    state: LocalAlertsState,
    effectFlow: Flow<LocalAlertsSideEffects>?,
    onEventSent: (event: LocalAlertsEvent) -> Unit,
    onNavigationRequested: (navigationEffect: LocalAlertsSideEffects.Navigation) -> Unit
) {

    val context = LocalContext.current
    val showReloadButton = remember { mutableStateOf(false) }
    val showCancelAllButton = remember { mutableStateOf(true) }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is LocalAlertsSideEffects.ShowErrorMsg -> {
                    Toast.makeText(context, effect.msg, Toast.LENGTH_LONG).show()
                }

                is LocalAlertsSideEffects.ShowMsg -> {
                    Toast.makeText(context, effect.msg, Toast.LENGTH_LONG).show()
                }

                is LocalAlertsSideEffects.Navigation.OpenDetailsScreen -> {
                    onNavigationRequested(effect)
                }

                else -> {}
            }
        }?.collect()
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onEventSent.invoke(LocalAlertsEvent.ReCallApi)
    }

    when {
        state.isLoading -> {
            Progress()
        }

        else -> {}
    }

    Scaffold(
        topBar = {
            val activity: MainActivity = LocalContext.current as MainActivity
            ToolBar(
                cancelAll = showCancelAllButton,
                titleStringId = R.string.wave_line_test_txt,
                onBackPressed = {
                    activity.finish()
                },
                onCancelAllNotification = {
                    WorkManager.getInstance(context).cancelAllWork()
                    Toast.makeText(
                        context,
                        context.getString(R.string.worker_canceled_successfully),
                        Toast.LENGTH_LONG
                    ).show()
                }, onCancelNotification = {} )
        }
    ) {
        Column(Modifier.padding(it)) {
            if (showReloadButton.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(50.dp)
                            .padding(horizontal = 50.dp),
                        onClick = { onEventSent(LocalAlertsEvent.GetAllLocalAlerts) },
                        shape = RoundedCornerShape(25),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.black))
                    ) {
                        Text(
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.try_again_txt).uppercase()
                        )
                    }
                }
            } else {
                LazyColumn(
                    state = rememberLazyListState(), modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    items(state.localAlertsList) { item ->
                        AnimatedVisibility(state.localAlertsList.isNotEmpty()) {
                            Column(modifier = Modifier.animateContentSize { _, _ -> }) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .combinedClickable(
                                            onClick = {
                                                onEventSent(
                                                    LocalAlertsEvent.OpenDetailsScreen(
                                                        item.timeInSeconds ?: 0L
                                                    )
                                                )
                                            },
                                        ),
                                    shape = CardDefaults.shape,
                                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = colorResource(
                                            id = R.color.white
                                        )
                                    )
                                ) {

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(20.dp)
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(5.dp),
                                            text = stringResource(id = R.string.name_txt).plus(" ")
                                                .plus(item.title ?: ""),
                                            fontSize = 16.sp,
                                            color = colorResource(id = R.color.black),
                                            fontWeight = FontWeight.Normal
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun UsersScreenPreview() {
    LocalAlertsScreen(state = LocalAlertsState(isLoading = false),
        effectFlow = null, onEventSent = {}, onNavigationRequested = {})
}