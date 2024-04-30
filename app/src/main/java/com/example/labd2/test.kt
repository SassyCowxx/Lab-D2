//package com.example.labd2
//
//operator fun minusAssign(polynomial_2: Wielomian){
//    var listW: MutableList<Double> = mutableListOf()
//    var degreeW1 = this.StopienWielomianu()
//    var degreeW2 = polynomial_2.StopienWielomianu()
//    var difference: Int = abs(degreeW1 - degreeW2)
//    if (degreeW1 != degreeW2){
//        if (degreeW1 > degreeW2){
//            listW = this.listOfFactors.subList(0, difference).toMutableList()
//            for ( i in 0..degreeW2){
//                listW.add(this.listOfFactors[difference + i] - polynomial_2.listOfFactors[i])
//            }
//        } else {
//            listW = polynomial_2.listOfFactors.subList(0, difference).toMutableList()
//            for (i in 0..listW.size-1){
//                listW[i] *= -1.0
//            }
//            for (i in 0..degreeW1) {
//                listW.add(this.listOfFactors[i] - polynomial_2.listOfFactors[difference + i])
//            }
//        }
//
//    } else{
//        for ( i in 0..degreeW1){
//            listW.add(this.listOfFactors[i] - polynomial_2.listOfFactors[i])
//        }
//    }
//
//    this.listOfFactors = listW
//}
//
////    newPolynomial -= Polynomial_2
////    println(newPolynomial.TekstWielomian())