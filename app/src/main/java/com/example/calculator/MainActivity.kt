package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                CalculatorApp()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CalculatorApp(){
    Calculator()

}

fun calcula(expressao: String): Float {
    var currentNumber = "" // Armazena o número atual
    var result = 0f // Armazena o resultado final, inicializando como Float
    var operator = '+' // Começamos com a operação de soma por padrão

    // Percorrer cada caractere da string
    for (i in 0 until expressao.length) {
        val currentChar = expressao[i]

        if (currentChar.isDigit() || currentChar == '.') {
            // Se for um dígito ou ponto decimal, adicionar ao número atual
            currentNumber += currentChar
        } else {
            // Quando encontramos um operador, realizamos o cálculo
            result = when (operator) {
                '+' -> result + currentNumber.toFloat()
                '-' -> result - currentNumber.toFloat()
                'x' -> result * currentNumber.toFloat()
                '÷' -> result / currentNumber.toFloat()
                '%' -> result % currentNumber.toFloat()
                else -> result
            }

            // Atualiza o operador e limpa o número atual para o próximo
            operator = currentChar
            currentNumber = ""
        }
    }



    // Processar o último número após o último operador
    result = when (operator) {
        '+' -> result + currentNumber.toFloat()
        '-' -> result - currentNumber.toFloat()
        'x' -> result * currentNumber.toFloat()
        '÷' -> result / currentNumber.toFloat()
        '%' -> result % currentNumber.toFloat()
        else -> result
    }

    return result
}


@Composable
fun Botao(texto: String, cor: Color, acao: () -> Unit) {
    Button(
        onClick = acao,
        modifier = Modifier.size(85.dp), // Define o tamanho do botão circular
        shape = CircleShape, // Define o formato circular do botão
        colors = ButtonDefaults.buttonColors(containerColor = cor)
    ) {
        Text(
            text = texto,
            fontSize = 35.sp
        )
    }
}


@Composable
fun Calculator(modifier: Modifier = Modifier) {
    var visor by remember { mutableStateOf("52+36") }
    var result by remember { mutableStateOf("") }
    var ultimo = visor.lastOrNull()
    val cor1 = Color(red = 170, green = 210, blue = 180) // Verde suave
    val cor2 = Color(red = 40, green = 60, blue = 90)    // Azul profundo
    val cor3 = Color(red = 255, green = 140, blue = 60)  // Laranja vibrante


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray) // Fundo DarkGray aplicado
            .padding(13.dp), // Padding ao redor do conteúdo
        contentAlignment = Alignment.BottomCenter // Conteúdo alinhado na parte de baixo
    ) {



        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp), // Espaçamento entre as linhas
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth() // Garante que a coluna ocupe a largura total
        ) {

                Text(
                    text = visor,
                    style = TextStyle(fontSize = 50.sp, color = Color.Gray),
                    modifier = Modifier.align(Alignment.End)
                )


            Spacer(modifier = Modifier.size(20.dp))

                Text(
                text = result,
                style = TextStyle(fontSize = 70.sp, color = Color.LightGray),
                modifier = Modifier.align(Alignment.End)
                 )

            Spacer(modifier = Modifier.size(40.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (visor.isNotEmpty()) {
                        visor = visor.dropLast(1)
                    } },
                    modifier = Modifier.size(85.dp), // Define o tamanho do botão circular
                    shape = CircleShape, // Define o formato circular do botão
                    colors = ButtonDefaults.buttonColors(containerColor = cor3)
                ) {
                    Text(
                        text = "DEL",
                        fontSize = 19.sp // Define o tamanho do texto dentro do botão
                    )
                }
                Botao(texto = "C", cor = cor3) { visor = ""
                result = ""}
                Botao(texto = "%", cor = cor2) { visor += "%" }
                Botao(texto = "÷", cor = cor2) {
                    if(result == "" && visor !="")
                        visor += "÷"

                    if(result != "") {
                        visor = result
                        result = ""
                        visor += "÷"
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Botao(texto = "1", cor = cor1) { visor += "1" }
                Botao(texto = "2", cor = cor1) { visor += "2" }
                Botao(texto = "3", cor = cor1) { visor += "3" }
                Botao(texto = "x", cor = cor2) {
                    if(result == "" && visor !="")
                        visor += "x"

                    else {
                        visor = result
                        result = ""
                        visor += "x"
                    }
                }
            }

            // Segunda linha de botões
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Botao(texto = "4", cor = cor1) { visor += "4" }
                Botao(texto = "5", cor = cor1) { visor += "5"}
                Botao(texto = "6", cor = cor1) { visor += "6" }
                Botao(texto = "+", cor = cor2) {
                    if(result == "" && visor !="")
                    visor += "+"

                    else {
                        visor = result
                        result = ""
                        visor += "+"
                    }
            }
            }

            // Terceira linha de botões
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Botao(texto = "7", cor = cor1) { visor += "7"}
                Botao(texto = "8", cor = cor1) { visor += "8" }
                Botao(texto = "9", cor = cor1) { visor += "9" }
                Botao(texto = "-", cor = cor2) {
                    if(result == "" && visor != "")
                        visor += "-"
                    else {
                        visor = result
                        result = ""
                        visor += "-"
                    }
                }
            }

            // Quarta linha de botões
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Botao(texto = ".", cor = cor2) { visor += "." }
                Botao(texto = "0", cor = cor1) { visor += "0" }
                Button(
                    onClick = {
                        val resultado = calcula(visor)
                        result = if (resultado % 1 == 0f) {
                            resultado.toInt().toString() // Se o número for inteiro, remove o ".0"
                        } else {
                            resultado.toString() // Se for decimal, mantém o ponto e os decimais
                        }
                        visor = ""
                    },
                    modifier = Modifier
                        .width(185.dp)
                        .height(85.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = cor3)
                ) {
                    Text(
                        text = "=",
                        fontSize = 40.sp // Define o tamanho do texto dentro do botão
                    )
                }
            }
            Spacer(modifier = Modifier.size(5.dp))
        }
    }

}
