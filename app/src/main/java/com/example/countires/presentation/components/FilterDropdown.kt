package com.example.countires.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.countires.R

@Composable
fun FilterDropdown(
    regions: List<String>,
    selectedRegion: String,
    onRegionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Button(
            onClick = { expanded = true },
            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_small)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = dimensionResource(R.dimen.elevation_none),
                pressedElevation = dimensionResource(R.dimen.elevation_none),
                hoveredElevation = dimensionResource(R.dimen.elevation_none),
                focusedElevation = dimensionResource(R.dimen.elevation_none)
            ),
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_tune),
                contentDescription = stringResource(R.string.filter_desc),
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_small))
            )

            if (selectedRegion != stringResource(R.string.all_regions)) {
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))
                Text(
                    text = selectedRegion,
                    fontSize = dimensionResource(R.dimen.text_size_small).value.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            regions.forEach { region ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = region,
                            fontSize = dimensionResource(R.dimen.text_size_small).value.sp,
                            color = if (region == selectedRegion) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.Black
                            }
                        )
                    },
                    onClick = {
                        onRegionSelected(region)
                        expanded = false
                    }
                )
            }
        }
    }
}