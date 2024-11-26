package com.example.proj3.pages

import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proj3.ui.theme.Blue500
import com.google.firebase.database.FirebaseDatabase

@Composable
fun Screen2(
    navController: NavController,
    database: FirebaseDatabase
) {

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().background(brush = verticalGradient(
        color = listOf(
            Color(0xFFE73C00),
            Color(0xFFF86D09),
            Color(0xFFFFCB2E),

            )
    )
    ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ){
    Text(text = "To jest ekran 2", fontWeight = FontWeight.Bold , fontSize = 48.sp)
    Spacer(modifier = Modifier.height(64.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(64.dp))
        ElevatedButton(
            onClick = {
                submitData(name, age, database)
                keyboardController?.hide()
            },
            modifier = Modifier,
            enabled = true,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Blue500,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = null)
            Text("Submit")
        }


    ElevatedButton(
        onClick = {
            navController.navigate("screen3")
        },
        modifier = Modifier,
        enabled = true,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Blue500,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = "Navigate to Screen 3")
    }
}
}
@Preview
@Composable
fun PreviewScreen2(){
    MaterialTheme {
        Surface {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Naviaget to screen 3")
            }
        }

    }
}

@Composable
private fun verticalGradient(
    color: List<Color>
): Brush {
    return Brush.linearGradient(
        colors = color,
        start = Offset.Zero,
        end = Offset(0f , Float.POSITIVE_INFINITY)
    )
}

private fun submitData(
    name: String,
    age: String,
    database: FirebaseDatabase) {
    val myRef = database.getReference("user")

    // Push data to Firebase Realtime Database
    val userId = myRef.push().key
    val user = mapOf("name" to name, "age" to age)
    if (userId != null) {
        myRef.child(userId).setValue(user)
    }
}