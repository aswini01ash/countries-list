package com.example.countires.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.countires.presentation.viewmodel.CountryViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(
    countryName: String,
    onBackClick: () -> Unit,
    viewModel: CountryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val country = uiState.countries.find { it.name.common == countryName }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        TopAppBar(
            title = { Text("Country Details") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading...")
                    }
                }
            }
            country != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    AsyncImage(
                        model = country.flags.png,
                        contentDescription = "Flag of ${country.name.common}",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = country.name.common,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = country.name.official,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    DetailRow(
                        title = "Capital City",
                        value = country.capital?.joinToString(", ") ?: "N/A"
                    )

                    DetailRow(
                        title = "Official Languages",
                        value = country.languages?.values?.joinToString(", ") ?: "N/A"
                    )

                    DetailRow(
                        title = "Currencies",
                        value = country.currencies?.entries?.joinToString(", ") { "${it.value.name} (${it.value.symbol ?: ""})" }
                            ?: "N/A"
                    )

                    DetailRow(
                        title = "Timezones",
                        value = country.timezones.joinToString(", ")
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Country not found")
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    title: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}