package com.example.proj3.pages

import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.times
import com.example.proj3.ui.theme.Blue900
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun Screen3(
    navController: NavController,
    database: FirebaseDatabase
) {

    var userList by remember { mutableStateOf(emptyList<User>()) }
    LaunchedEffect(key1 = true) {
        // Load users from Firebase Realtime Database
        val myRef = database.getReference("user")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children.mapNotNull { it.getValue(User::class.java) }
                userList = users
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }


    Column(
        modifier = Modifier.fillMaxSize().background(
            brush = verticalGradient(
                color = listOf(
                    Color(0xFFE73C00),
                    Color(0xFFF86D09),
                    Color(0xFFFFCB2E),

                    )
            )
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {


        Text(text = "To jest ekran 3", fontWeight = FontWeight.Bold, fontSize = 48.sp)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxWidth()
                    .height(8 * 60.dp) // Assuming each item is around 60.dp tall

        ) {
            items(userList) { user ->
                UserListItem(user)
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedButton(
            onClick = {

                navController.navigate("screen2")
            },
            modifier = Modifier,
            enabled = true,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Blue900,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Navigate to Screen 2")
        }

    }
}



@Composable
private fun verticalGradient(
    color: List<Color>
):Brush{
      return Brush.linearGradient(
          colors = color,
          start = Offset.Zero,
          end = Offset(0f , Float.POSITIVE_INFINITY)
      )
}

@Composable
fun UserListItem(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Name: ${user.name}")
        Text(text = "Age: ${user.age}")
    }
}