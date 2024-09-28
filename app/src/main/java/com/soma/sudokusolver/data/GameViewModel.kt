package com.soma.sudokusolver.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class GameViewModel:ViewModel() {
    var isvalid = mutableStateOf(
        true
    )
    var isfinding = mutableStateOf(
        0
    )
    var timetaken = mutableStateOf(0L)
    var success = mutableStateOf(
        false
    )

    fun sudokuSolver(board: MutableList<Char>){
           viewModelScope.launch {
               isfinding.value = 1
               timetaken.value = measureTimeMillis {
                   solveSudoku(board, 0, 0,System.currentTimeMillis())
               }
               isfinding.value = 2
           }
       }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun checkMistakes(board:List<Char>){
        viewModelScope.launch {
            val result = isvalid(board)
            isvalid.value = result//isvalid.value+if(!result) -1 else +1
        }
    }
    suspend fun isvalid(board: List<Char>):Boolean{
        for (i in 0..80){

            if(board[i]!=' ' && !check(board,i/9,i%9,board[i],i)){
                return false
            }
            delay(4)
        }
        return true
    }
    suspend fun check(board:List<Char>,row:Int,col:Int,char:Char,ind:Int):Boolean{
        //check for row and column
        for (k in 0 until 9){
            if(row*9+k%9!=ind && board[row*9+k%9]==char){
                Log.d("MyError","In row  ${ind} ${row*9+k%9}")
                return false
            }
            if(k*9+col%9!=ind && board[k*9+col%9]==char){
                Log.d("MyError","In Col  ${ind} ${row*9+k%9}")
                return false
            }
        }
        val gridrow = (row/3)*3
        val gridcol = (col/3)*3
        for (m in gridrow until (gridrow+3)){
            for (n in gridcol until (gridcol+3)){
                if(m*9+n%9!=ind && board[m*9+n%9]==char){
                    Log.d("MyError","In grid")
                    return false
                }
            }
        }
        return true
    }
    fun reset(){
        isfinding.value = 0
    }

    suspend fun solveSudoku(sudoku:MutableList<Char>,i:Int,j:Int,time:Long):Boolean{
        var i1 = i
        var j1 = j
        if(j>=9){
            i1+=1
            j1 = 0
        }
        if(i1==9){
            return true
        }
        if(sudoku[i1*9+j1%9]!=' '){
            return solveSudoku(sudoku,i1,j1+1,time)
        }
        for(k in 1..9){
            var success = false
            sudoku[i1*9+j1%9] = k.toString()[0]
            if(check(sudoku,i1,j1,k.toString()[0],i1*9+j1%9)){
                if(System.currentTimeMillis()-time>2000){
                    delay(10)
                    success = solveSudoku(sudoku,i1,j1+1,System.currentTimeMillis())

                }
                else{
                    success = solveSudoku(sudoku,i1,j1+1,time)
                }

            }
            if(success){
                return true
            }
        }
        sudoku[i1*9+j1%9] = ' '
        return false
    }
}