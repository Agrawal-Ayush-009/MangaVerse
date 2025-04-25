package com.flatify.mangaverse.presentation.ui.SignupScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatify.android.PrimaryButton
import com.flatify.mangaverse.R
import com.flatify.mangaverse.presentation.ui.components.BottomOutlineTextField
import com.flatify.mangaverse.presentation.ui.theme.BackgroundColor
import com.flatify.mangaverse.presentation.ui.theme.ButtonColor2
import com.flatify.mangaverse.presentation.ui.theme.ErrorRed
import com.flatify.mangaverse.presentation.ui.theme.GramatikaTrial
import com.flatify.mangaverse.utils.RoomUiState

@Composable
fun SignupScreen(
    viewModel: AuthViewModel,
    onSignInClick: () -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val authUiState by viewModel.authUiState.collectAsState()

    val isSignedIn by remember { viewModel::isSignedIn }

    LaunchedEffect(isSignedIn) {
        if(isSignedIn != null){
            onSignInClick()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "Welcome back!",
                fontSize = 32.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = GramatikaTrial
            )
            Text(
                modifier = Modifier.padding(top = 32.dp, start = 20.dp),
                text =  "Email",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontFamily = GramatikaTrial
            )
            BottomOutlineTextField(
                value = email,
                onValueChange = {email = it},
                placeholder = "Enter your Email"
            )
            Text(
                modifier = Modifier.padding(top = 32.dp, start = 20.dp),
                text =  "Password",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontFamily = GramatikaTrial
            )
            BottomOutlineTextField(
                value = password,
                onValueChange = {password = it},
                placeholder = "Enter your Password"
            )
            Spacer(modifier = Modifier.height(48.dp))
            PrimaryButton(
                text = "SignUp",
                onClick = {
                    viewModel.singIn(email = email, password = password)
                }
            )
            when(authUiState){
                is RoomUiState.Error -> {
                    Row(
                        modifier = Modifier
                            .padding(20.dp, 10.dp, 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start

                    ) {
                        Image(
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(12.dp),
                            painter = painterResource(id = R.drawable.lucide_info),
                            contentDescription = "trophy",
                            alignment = Alignment.Center,
                            colorFilter = ColorFilter.tint(color = ErrorRed)
                        )

                        Text(
                            text = "OTP is invalid",
                            fontSize = 12.sp,
                            fontFamily = GramatikaTrial,
                            fontWeight = FontWeight.Normal,
                            color = ErrorRed
                        )
                    }
                }
                is RoomUiState.Idle -> {}
                is RoomUiState.Loading -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 10.dp, 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = ButtonColor2
                        )
                    }
                }
                is RoomUiState.Success -> {
                    onSignInClick()
                }
            }
        }
    }
}