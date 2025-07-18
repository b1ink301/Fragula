/*
 * Copyright 2023 Fragula contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fragula2.sample.compose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.fragula2.sample.R
import com.fragula2.sample.compose.ui.getChats

@Composable
fun ProfileScreen(navController: NavController, chatId: String) {
    val chat = getChats().find { it.id == chatId } ?: error("Chat not found")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(chat.image),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 56.dp)
                .size(102.dp)
                .clip(CircleShape),
        )
        Text(
            text = chat.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 42.dp, end = 42.dp),
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        Text(
            text = stringResource(R.string.lorem_ipsum),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 42.dp, end = 42.dp),
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
        )
        SocialButton(
            text = "Instagram",
            onClick = { navController.navigate("tab/Instagram") },
            modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
        )
        SocialButton(
            text = "Facebook",
            onClick = { navController.navigate("tab/Facebook") },
            modifier = Modifier.padding(top = 12.dp, start = 24.dp, end = 24.dp),
        )
        SocialButton(
            text = "Telegram",
            onClick = { navController.navigate("tab/Telegram") },
            modifier = Modifier.padding(top = 12.dp, start = 24.dp, end = 24.dp),
        )
    }
}

@Composable
fun SocialButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
    ) {
        Text(
            text = text.uppercase(),
            color = MaterialTheme.colors.secondary,
            fontSize = 16.sp,
        )
    }
}