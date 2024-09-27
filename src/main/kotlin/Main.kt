import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

private val LightColors = lightColors(
    primary = Color(0xFF6200EE),
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val DarkColors = darkColors(
    primary = Color(0xFFBB86FC),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun CalculatorApp() {
    var isDarkTheme by remember { mutableStateOf(false) }

    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val onNumberClick = { number: String ->
        input += number
    }

    val onOperatorClick = { operator: String ->
        if (input.isNotEmpty() && input.last() !in listOf('+', '-', '*', '/')) {
            input += operator
        }
    }

    val onEqualsClick = {
        try {
            result = eval(input)
        } catch (e: Exception) {
            result = "Ошибка"
        }
    }

    val onClearClick = {
        input = ""
        result = ""
    }

    MaterialTheme(
        colors = if (isDarkTheme) DarkColors else LightColors,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { Text("Введите выражение") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Результат: $result",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(16.dp)
                )


                Column {
                    Row {
                        Button(onClick = { onNumberClick("1") }, modifier = Modifier.padding(4.dp)) { Text("1") }
                        Button(onClick = { onNumberClick("2") }, modifier = Modifier.padding(4.dp)) { Text("2") }
                        Button(onClick = { onNumberClick("3") }, modifier = Modifier.padding(4.dp)) { Text("3") }
                        Button(onClick = { onOperatorClick("+") }, modifier = Modifier.padding(4.dp)) { Text("+") }
                    }
                    Row {
                        Button(onClick = { onNumberClick("4") }, modifier = Modifier.padding(4.dp)) { Text("4") }
                        Button(onClick = { onNumberClick("5") }, modifier = Modifier.padding(4.dp)) { Text("5") }
                        Button(onClick = { onNumberClick("6") }, modifier = Modifier.padding(4.dp)) { Text("6") }
                        Button(onClick = { onOperatorClick("-") }, modifier = Modifier.padding(4.dp)) { Text("-") }
                    }
                    Row {
                        Button(onClick = { onNumberClick("7") }, modifier = Modifier.padding(4.dp)) { Text("7") }
                        Button(onClick = { onNumberClick("8") }, modifier = Modifier.padding(4.dp)) { Text("8") }
                        Button(onClick = { onNumberClick("9") }, modifier = Modifier.padding(4.dp)) { Text("9") }
                        Button(onClick = { onOperatorClick("*") }, modifier = Modifier.padding(4.dp)) { Text("*") }
                    }
                    Row {
                        Button(onClick = { onNumberClick("0") }, modifier = Modifier.padding(4.dp)) { Text("0") }
                        Button(onClick = { onClearClick() }, modifier = Modifier.padding(4.dp)) { Text("C") }
                        Button(onClick = { onEqualsClick() }, modifier = Modifier.padding(4.dp)) { Text("=") }
                        Button(onClick = { onOperatorClick("/") }, modifier = Modifier.padding(4.dp)) { Text("/") }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                }

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(onClick = { isDarkTheme = !isDarkTheme }) {
                        Text("Arigatoouuu")
                    }
                }

            }
        }
    }



}

@Composable
@Preview
fun AppPreview() {
    CalculatorApp()
}

fun eval(expression: String): String {
    return try {
        val result = expression.split(Regex("(?<=[-+*/])|(?=[-+*/])"))
        var total = result[0].toDouble()
        var operator = ""
        result.forEachIndexed { index, value ->
            when (value) {
                "+" -> operator = "+"
                "-" -> operator = "-"
                "*" -> operator = "*"
                "/" -> operator = "/"
                else -> {
                    if (operator.isNotEmpty()) {
                        total = when (operator) {
                            "+" -> total + value.toDouble()
                            "-" -> total - value.toDouble()
                            "*" -> total * value.toDouble()
                            "/" -> total / value.toDouble()
                            else -> total
                        }
                        operator = ""
                    }
                }
            }
        }

        if (total % 1 == 0.0) {
            total.toInt().toString()
        } else {
            total.toString()
        }
    } catch (e: Exception) {
        "Ошибка"
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "ARIGATOOOU") {
        CalculatorApp()
    }
}
