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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.countires.R
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
            title = { Text(stringResource(R.string.country_details_title)) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_button_desc)
                    )
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
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))
                        Text(stringResource(R.string.loading))
                    }
                }
            }
            country != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    AsyncImage(
                        model = country.flags.png,
                        contentDescription = stringResource(R.string.flag_of, country.name.common),
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(R.dimen.flag_size_large))
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_small)))
                            .background(Color.LightGray)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_xl)))

                    Text(
                        text = country.name.common,
                        fontSize = dimensionResource(R.dimen.text_size_xxl).value.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

                    Text(
                        text = country.name.official,
                        fontSize = dimensionResource(R.dimen.text_size_medium).value.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_xl)))

                    DetailRow(
                        title = stringResource(R.string.capital_city),
                        value = country.capital?.joinToString(", ")
                            ?: stringResource(R.string.not_available)
                    )

                    DetailRow(
                        title = stringResource(R.string.official_languages),
                        value = country.languages?.values?.joinToString(", ")
                            ?: stringResource(R.string.not_available)
                    )

                    // Fixed the currency formatting issue
                    val currencyFormat = stringResource(R.string.currency_format)
                    DetailRow(
                        title = stringResource(R.string.currencies),
                        value = country.currencies?.entries?.joinToString(", ") { entry ->
                            // Use String.format instead of stringResource inside lambda
                            currencyFormat.format(
                                entry.value.name,
                                entry.value.symbol ?: ""
                            )
                        } ?: stringResource(R.string.not_available)
                    )

                    DetailRow(
                        title = stringResource(R.string.timezones),
                        value = country.timezones.joinToString(", ")
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_xl)))
                }
            }
            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.country_not_found))
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
            .padding(vertical = dimensionResource(R.dimen.spacing_medium))
    ) {
        Text(
            text = title,
            fontSize = dimensionResource(R.dimen.text_size_small).value.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
        Text(
            text = value,
            fontSize = dimensionResource(R.dimen.text_size_medium).value.sp,
            color = Color.Black
        )
    }
}