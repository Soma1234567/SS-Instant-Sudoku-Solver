package com.soma.sudokusolver

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.soma.sudokusolver.data.GameViewModel
import com.soma.sudokusolver.ui.theme.SudokuSolverTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SudokuSolverTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    
                   Scaffold(
                       topBar = {
                           TopAppBar(title = {
                               Text(text = "Sudoku Solver", color = Color.White)
                           },
                               colors = TopAppBarDefaults.mediumTopAppBarColors(
                                   containerColor = Color(0xFF5F9EA0)
                               ))
                       }
                   ) {
                       it.calculateBottomPadding()
                       LazyBoard()
                   }

                }
            }
        }
    }
}
//@Composable
//fun Board(viewModel: GameViewModel = viewModel()){
//    val board  by remember {
//        mutableStateOf(Array(9){CharArray(9){'1'}})
//    }
//    val context = LocalContext.current
//    val rowselected = remember {
//        mutableStateOf(-1)
//    }
//    val colSelected = remember {
//        mutableStateOf(-1)
//    }
//    val selectedNumber = remember {
//        mutableStateOf(-1)
//    }
//    LaunchedEffect(key1 = board){
//
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        for (i in 0..8){
//            Row(
//
//            ) {
//
//                for (j in 0..8){
//                    Numbers(Modifier.then(
//                        if(i==rowselected.value && j==colSelected.value){
//                            Modifier.background(Color(0xFF89CFF0))
//                        }
//                        else{
//                            Modifier
//                        }
//                    ),text = board[i][j].toString(),i,j) {
//                        rowselected.value = i
//                        colSelected.value = j
//                    }
//                }
//            }
//        }
//        Row(
//            modifier = Modifier
//                .padding(horizontal = 20.dp, vertical = 30.dp)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            for (i in 0..4){
//                SelectNumber(text = i.toString()) {
//                    if(rowselected.value!=-1 && colSelected.value!=-1){
//                        Toast.makeText(context,i.toString()[0].toString(),Toast.LENGTH_SHORT).show()
//                        val newboard = board
//                        newboard[rowselected.value][colSelected.value] = i.toString()[0]
//
//                    }
//                }
//            }
//        }
//        Row(
//            modifier = Modifier
//                .padding(horizontal = 20.dp, vertical = 0.dp)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            for (i in 5..9){
//                SelectNumber(text = i.toString()) {
//                    if(rowselected.value!=-1 && colSelected.value!=-1){
//
//                            Toast.makeText(context,i.toString()[0].toString(),Toast.LENGTH_SHORT).show()
//                            val newboard = board
//                            newboard[rowselected.value][colSelected.value] = i.toString()[0]
//
//
//
//                    }
//                }
//            }
//        }
//    }
//}
@Composable
fun Numbers(modifier: Modifier,text:String,row:Int,col:Int,textColor:Color,onClick:()->Unit){
    Box(modifier = modifier
        .size(40.dp)
        .drawBehind {
            val width = this.size.width
            val height = this.size.height
            drawLine(
                color = Color.Black,
                start = Offset(width, 0f),
                end = Offset(width, height),
                strokeWidth = if ((col + 1) % 3 == 0) 4f else 2f
            )
            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(width, 0f),
                strokeWidth = if ((row) % 3 == 0) 4f else 2f
            )
            if (col == 0) {
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, 0f),
                    end = Offset(0f, height),
                    strokeWidth = 4f
                )
            }
            if (row == 8) {
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, height),
                    end = Offset(width, height),
                    strokeWidth = 4f
                )
            }

        }
        .clickable {
            onClick()
        },
        contentAlignment = Alignment.Center){
        Text(text = text, fontSize = 20.sp, color = textColor)
    }
}

@Composable
fun SelectNumber(text:String,status:Int,onClick: () -> Unit){
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(
                color = Color(0xFF7CB9E8).copy(alpha = 0.8f),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                if (status == 0) {
                    onClick()
                } else if (status == 1) {
                    Toast
                        .makeText(context, "Soduku is solving Be patient.", Toast.LENGTH_SHORT)
                        .show()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 22.sp, color = Color.White)
    }
}

@Composable
fun LazyBoard(viewModel: GameViewModel = viewModel()){
    val state  = viewModel.isvalid.value
    val status = viewModel.isfinding.value
    val context = LocalContext.current
    var board = remember {
        mutableStateListOf(
            ' ',' ',' ',' ',' ',' ',' ',' ',' ',
            ' ',' ',' ',' ',' ',' ',' ',' ',' ',
            ' ',' ',' ',' ',' ',' ',' ',' ',' ',
            ' ',' ',' ',' ',' ',' ',' ',' ',' ',
            ' ',' ',' ',' ',' ',' ',' ',' ',' ',
            ' ',' ',' ',' ',' ',' ',' ',' ',' ',
            ' ',' ',' ',' ',' ',' ',' ',' ',' ',
            ' ',' ',' ',' ',' ',' ',' ',' ',' ',
            ' ',' ',' ',' ',' ',' ',' ',' ',' ')
    }
    val rowselected = remember {
        mutableStateOf(-1)
    }
    val colSelected = remember {
        mutableStateOf(-1)
    }
    var background = Color(0xFF7CB9E8).copy(alpha = 0.8f)
    if(status==1){
        background = Color(0xFFFA8128)
    }
    var given = remember {
        mutableStateListOf<Int>()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(9),
            contentPadding = PaddingValues(10.dp)
        ){
            items(
                count = 81
            ) {
                Numbers(Modifier.then(
                    if((it/9==rowselected.value && it%9==colSelected.value) || it in given){
                        Modifier.background(if(state) Color(0xFF89CFF0).copy(alpha = 0.8f) else Color.Red.copy(alpha = 0.7f))
                    }
                    else{
                        Modifier
                    }
                ),text = board[it].toString(),it/9,it%9,
                    textColor = Color.Black) {
                        rowselected.value = it/9
                        colSelected.value = it%9


                }
            }
        }
        if(status==2){
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Solved in : ", fontSize = 18.sp)
                Text(text = "${(viewModel.timetaken.value)/1000.0} seconds", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
        if(status!=2){
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (i in 1..5){
                    SelectNumber(text = i.toString(), status = status) {
                        if((rowselected.value!=-1 && colSelected.value!=-1)){
                            board[rowselected.value*9+colSelected.value] = i.toString()[0]
                            given.add(rowselected.value*9+colSelected.value%9)
                            viewModel.checkMistakes(board)

                        }
                        else{
                            Toast.makeText(context,"Please select a box first",Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 0.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (i in 6..10){
                    SelectNumber(text = if(i!=10) i.toString() else "x", status = status) {
                        if(rowselected.value!=-1 && colSelected.value!=-1){
                            if(i!=10){
                                board[rowselected.value*9+colSelected.value] = i.toString()[0]
                                given.add(rowselected.value*9+colSelected.value%9)
                                viewModel.checkMistakes(board)
                            }
                            else{
                                board[rowselected.value*9+colSelected.value%9] = ' '
                                given.remove(rowselected.value*9+colSelected.value%9)
                                viewModel.checkMistakes(board)
                            }

                        }
                        else{
                            Toast.makeText(context,"Please select a box first",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

//        Row(
//            modifier = Modifier
//                .padding(horizontal = 20.dp, vertical = 15.dp)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceAround
//        ) {
//            for (i in 7..9){
//                SelectNumber(text = i.toString(), status = status) {
//                    if(rowselected.value!=-1 && colSelected.value!=-1){
//                        board[rowselected.value*9+colSelected.value] = i.toString()[0]
//                        viewModel.checkMistakes(board)
//
//                    }
//                    else{
//                        Toast.makeText(context,"Please select a box first",Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .height(45.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
                    if (status == 0) {
                        if (state) {
                            viewModel.sudokuSolver(board)
                        } else {
                            Toast
                                .makeText(context, "Sudoku is wrong!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }


                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(status==0){
                Text(text = "Solve", fontSize = 20.sp, color = Color.White)
            }
            else if(status==1){
                Text(text = "Solving", fontSize = 20.sp)
            }
            else{
                Text(text = "Solved", fontSize = 20.sp, color = Color.White)
                Spacer(modifier = Modifier.width(20.dp))
                Image(painter = painterResource(id = R.drawable.checkmark), contentDescription = null,
                    modifier = Modifier.size(30.dp))
            }
            
        }
        if(status==2){

            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(color = Color(0xFF7CB9E8), shape = RoundedCornerShape(10.dp))
                    .clickable {
                        viewModel.reset()
                        rowselected.value = -1
                        colSelected.value = -1
                        given.clear()
                        for (i in 0..80) {
                            board[i] = ' '
                        }


                    }
                    ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Solve Another", fontSize = 20.sp, color = Color.White)
            }
        }
    }

}