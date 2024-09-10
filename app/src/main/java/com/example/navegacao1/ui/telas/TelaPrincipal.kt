package com.example.navegacao1.ui.telas

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.navegacao1.model.dados.Endereco
import com.example.navegacao1.model.dados.RetrofitClient
import com.example.navegacao1.model.dados.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun TelaPrincipal(modifier: Modifier = Modifier, onLogoffClick: () -> Unit) {
    val scope = rememberCoroutineScope()

    var nome by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var idToRemove by remember { mutableStateOf("") }
    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }


    LaunchedEffect(Unit) {
        scope.launch {
            usuarios = getUsuarios()
        }
    }

    Column(modifier = modifier.fillMaxWidth(),
           horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Tela Principal")


        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") }
        )
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(onClick = {
            scope.launch {
                adicionarUsuario(Usuario(nome = nome, senha = senha))
                usuarios = getUsuarios()
            }
        }) {
            Text("Adicionar Usuário")
        }

        OutlinedTextField(
            value = idToRemove,
            onValueChange = { idToRemove = it },
            label = { Text("ID do Usuário para Remover") }
        )

        Button(onClick = {
            scope.launch {
                removerUsuario(idToRemove)
                usuarios = getUsuarios()
            }
        }) {
            Text("Remover Usuário")
        }

        LazyColumn {
            items(usuarios) { usuario ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(text = "Nome: ${usuario.nome}")
                        Text(text = "ID: ${usuario.id}")
                    }
                }
            }
        }
    }
}

suspend fun adicionarUsuario(usuario: Usuario) {
    withContext(Dispatchers.IO) {
        RetrofitClient.usuarioService.adicionar(usuario)
    }
}

suspend fun removerUsuario(id: String) {
    withContext(Dispatchers.IO) {
        RetrofitClient.usuarioService.remover(id)
    }
}

suspend fun getUsuarios(): List<Usuario> {
    return withContext(Dispatchers.IO) {
        RetrofitClient.usuarioService.listar()
    }
}
