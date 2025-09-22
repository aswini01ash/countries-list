package com.example.countires.presentation.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.countires.R
import com.example.countires.presentation.components.CountryCard
import com.example.countires.presentation.components.CountrySearchBar
import com.example.countires.presentation.components.FilterDropdown
import com.example.countires.presentation.viewmodel.CountryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    onCountryClick: (String) -> Unit,
    viewModel: CountryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.country_explorer_title),
                        fontSize = dimensionResource(R.dimen.text_size_xl).value.sp,
                        fontWeight = FontWeight.Bold
                    )

                    FilterDropdown(
                        regions = uiState.regions,
                        selectedRegion = uiState.selectedRegion,
                        onRegionSelected = viewModel::updateSelectedRegion
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        CountrySearchBar(
            searchQuery = uiState.searchQuery,
            onSearchQueryChange = viewModel::updateSearchQuery,
            placeholder = if (uiState.searchQuery.isEmpty()) {
                stringResource(R.string.search_countries)
            } else {
                stringResource(R.string.search_by_country_region)
            }
        )

        Box(modifier = Modifier.fillMaxSize()) {
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
                            Text(stringResource(R.string.loading_countries))
                        }
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(dimensionResource(R.dimen.spacing_xxl))
                        ) {
                            Text(
                                text = stringResource(R.string.error_something_wrong),
                                fontSize = dimensionResource(R.dimen.text_size_large).value.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))
                            Text(
                                text = uiState.error ?: stringResource(R.string.error_unknown),
                                textAlign = TextAlign.Center,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))
                            Button(
                                onClick = {
                                    viewModel.clearError()
                                    viewModel.loadCountries()
                                }
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null)
                                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))
                                Text(stringResource(R.string.retry))
                            }
                        }
                    }
                }

                uiState.filteredCountries.isEmpty() && uiState.countries.isNotEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(dimensionResource(R.dimen.spacing_xxl))
                        ) {
                            Text(
                                text = stringResource(R.string.no_countries_found),
                                fontSize = dimensionResource(R.dimen.text_size_large).value.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = stringResource(R.string.try_adjusting_search),
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn {
                        items(
                            items = uiState.filteredCountries,
                            key = { it.name.common }
                        ) { country ->
                            CountryCard(
                                country = country,
                                onClick = { onCountryClick(country.name.common) }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))
                        }
                    }
                }
            }
        }
    }
}