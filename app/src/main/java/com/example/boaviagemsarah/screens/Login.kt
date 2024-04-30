package com.example.boaviagemsarah.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun telaLogin(onCadUsuario: ()->Unit, onLogin: () ->Unit){

    var visibi = remember {
        mutableStateOf(false)
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    var login = remember {
        mutableStateOf("")
    }

    var pass = remember {
        mutableStateOf("")
    }

    var coroutineScope = rememberCoroutineScope()

    val focus = LocalFocusManager.current


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .padding(it)
                .background(Color.LightGray)

        ) {
            Image(
                painter = painterResource(id = com.example.boaviagemsarah.R.drawable.camera),
                contentDescription = "Camera",
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(350.dp)
                    .fillMaxWidth()
                    .padding(top = 0.dp, start = 15.dp)
            )

            Text(
                text = "Usuário",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()

            )

            OutlinedTextField(
                value = login.value,
                onValueChange = { login.value = it },
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Senha",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()

            )

            OutlinedTextField(
                value = pass.value,
                onValueChange = { pass.value = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation =
                if (visibi.value)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

                trailingIcon = {
                    IconButton(onClick = {
                        visibi.value = !visibi.value
                    }) {
                        if (visibi.value)
                            Icon(
                                painterResource(id = com.example.boaviagemsarah.R.drawable.visible), ""
                            )
                        else
                            Icon(
                                painterResource(id = com.example.boaviagemsarah.R.drawable.invisible), ""
                            )
                    }
                },
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            )

            Button(
                onClick = {
                    if (pass.value == "admin" && login.value == "admin")
                        onLogin()
                    else {
                        coroutineScope.launch {
                            focus.clearFocus()
                            snackbarHostState.showSnackbar(
                                message = "login ou Senha errados",
                                withDismissAction = true
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 22.sp
                )
            }

            Button(
                onClick = { onCadUsuario() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text(text = "Registrar novo Usuário")
            }
        }
    }
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()