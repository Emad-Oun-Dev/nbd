package com.example.nbdtask.utils.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nbdtask.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(
    showCancel: MutableState<Boolean> = mutableStateOf(false),
    cancelAll: MutableState<Boolean> = mutableStateOf(false),
    titleStringId: Int,
    onBackPressed: () -> Unit,
    onCancelNotification: () -> Unit,
    onCancelAllNotification: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = titleStringId),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                fontSize = 16.sp,
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.Bold
            )

        },
        navigationIcon = {
            IconButton(onClick = {
                onBackPressed.invoke()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        colors =
        TopAppBarDefaults.largeTopAppBarColors(containerColor = colorResource(id = R.color.gray_light)),
        actions = {
            if (showCancel.value) {
                TextButton(
                    onClick = {
                        onCancelNotification.invoke()
                    },
                ) {
                    Text(text = stringResource(id = R.string.cancel),
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold)
                }
            }

            if (cancelAll.value){
                TextButton(
                    onClick = {
                        onCancelAllNotification.invoke()
                    },
                ) {
                    Text(text = stringResource(id = R.string.cancel_all),
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    )
}
