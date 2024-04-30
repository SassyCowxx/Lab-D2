package com.example.labd2

import kotlin.math.pow
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Klasa Wielomian reprezentuje wielomian dowolnego stopnia. Jako argument przyjmuje listę liczb całkowitych (List<Int>).
 * @author Sasza Tokar
 * @param listaWspolczynnikow - lista współczynników wielomianu (liczby całkowite) w kolejności od najwyższej potęgi
 * @throws IllegalArgumentException jeśli podano pustą listę współczynników wielomianu
 */
class Wielomian(private var listaWspolczynnikow: MutableList<Int>) {

    init {
        if (listaWspolczynnikow == emptyList<Int>()) {
            throw IllegalArgumentException("Podano niewłaściwy argument wejściowy klasy Wielomian")
        }
        while (listaWspolczynnikow.first() == 0 && listaWspolczynnikow.size > 1){
            listaWspolczynnikow.removeAt(0)
        }
    }
    /**
     * Funkcja stopien zwraca stopień podanego wielomianu - obiektu klasy Wielomian.
     * @author Sasza Tokar
     * @return
     * @throws IllegalArgumentException jeśli podano wielomian o pustej liście współczynników
     */
    fun stopien(): Int {
        if (listaWspolczynnikow.isNotEmpty()) {
            return listaWspolczynnikow.size - 1
        } else throw IllegalArgumentException("Podano niewłaściwy wielomian - bez współczynników.")
    }

    /**
     * Funkcja wyswietl zwraca tekstową reprezentację wielomianu w formie W(x) = a_n x^n + a_n-1 x^n-1 + ... + a_1 x + a_0.
     * @author Sasza Tokar
     * @return tekstowa reprezentacja podanego wielomianu.
     * @throws IllegalArgumentException jeśli podano wielomian o pustej liście współczynników
     */
    fun wyswietl(): String {
        var reprezentacja = ""
        var potega = listaWspolczynnikow.size - 1
        var i = 0
        if (listaWspolczynnikow.isNotEmpty()) {
            reprezentacja += if (potega > 1) ("W(x) = ${listaWspolczynnikow[i]}x^$potega")
            else if (potega == 1) ("W(x) = ${listaWspolczynnikow[i]}x")
            else ("W(x) = ${listaWspolczynnikow[i]}")
            potega--
            i++
            while (potega >= 0) {
                if (listaWspolczynnikow[i] != 0) {
                    reprezentacja += if (potega > 1) {
                        if (listaWspolczynnikow[i] >= 0) (" + ${listaWspolczynnikow[i]}x^$potega")
                        else (" - ${abs(listaWspolczynnikow[i])}x^$potega")
                    } else if (potega == 1) {
                        if (listaWspolczynnikow[i] >= 0) (" + ${abs(listaWspolczynnikow[i])}x")
                        else (" - ${abs(listaWspolczynnikow[i])}x")
                    } else {
                        if (listaWspolczynnikow[i] >= 0) (" + ${listaWspolczynnikow[i]}")
                        else (" - ${abs(listaWspolczynnikow[i])}")
                    }
                }
                potega--
                i++
            }
            return reprezentacja
        }
        else throw IllegalArgumentException("Podano niewłaściwy wielomian - bez współczynników.")
    }

    /**
     * Operator wywołania zwraca wartość podanego wielomianu (obiekt klasy Wielomian) dla zadanej wartości x.
     * @author Sasza Tokar
     * @param x Wartość parametru x dla wielomianu W(x)
     * @return Wartość wielomianu W(x) dla zadaniego parametru x.
     * @throws IllegalArgumentException jeśli podano wielomian o pustej liście współczynników
     */
    operator fun invoke(x: Int): Int {
        var wynik = 0.0
        if (this.listaWspolczynnikow.isNotEmpty()) {
            var potega = listaWspolczynnikow.size - 1
            while (potega >= 0) {
                for (wspolczynnik in listaWspolczynnikow) {
                    wynik += wspolczynnik * x.toDouble().pow(potega)
                    potega--
                }
            }
            return wynik.toInt()
        }
        else throw IllegalArgumentException("Podano niewłaściwy wielomian - bez współczynników.")
    }

    /**
     * Operator wywołania plus zwraca wielomian (obiekt klasy Wielomian) o współczynnikach wynikających z sumy dwóch wielomianów.
     * @author Sasza Tokar
     * @param wielomian2 - wielomian, który ma zostać dodany do pierwszego podanego wielomianu
     * @return wynik dodania dwóch wielomianów (obiekt klasy Wielomian)
     * @throws IllegalArgumentException jeśli podano wielomian o pustej liście współczynników
     */
    operator fun plus(wielomian2: Wielomian): Wielomian {
        if (this.listaWspolczynnikow.isNotEmpty() && wielomian2.listaWspolczynnikow.isNotEmpty()) {
            val wspolczynnikiNowe = mutableListOf<Int>()
            if (this.listaWspolczynnikow.size >= wielomian2.listaWspolczynnikow.size) {
                for (wspolczynnik in this.listaWspolczynnikow) {
                    wspolczynnikiNowe.add(wspolczynnik)
                }
                for (pozycja in (this.listaWspolczynnikow.size - wielomian2.listaWspolczynnikow.size..<this.listaWspolczynnikow.size)) {
                    wspolczynnikiNowe[pozycja] += wielomian2.listaWspolczynnikow[pozycja - (this.listaWspolczynnikow.size - wielomian2.listaWspolczynnikow.size)]
                }
            } else {
                for (wspolczynnik in wielomian2.listaWspolczynnikow) {
                    wspolczynnikiNowe.add(wspolczynnik)
                }
                for (pozycja in (wielomian2.listaWspolczynnikow.size - this.listaWspolczynnikow.size..<wielomian2.listaWspolczynnikow.size)) {
                    wspolczynnikiNowe[pozycja] += this.listaWspolczynnikow[pozycja - (wielomian2.listaWspolczynnikow.size - this.listaWspolczynnikow.size)]
                }
            }

            while (wspolczynnikiNowe.first() == 0 && wspolczynnikiNowe.size > 1){
                wspolczynnikiNowe.removeAt(0)
            }
            return if (wspolczynnikiNowe.isNotEmpty()) Wielomian(wspolczynnikiNowe)
            else Wielomian(mutableListOf(0))
        }
        else throw IllegalArgumentException("Podano co najmniej 1 niewłaściwy wielomian.")
    }

    /**
     * Operator wywołania minus zwraca wielomian (obiekt klasy Wielomian) o współczynnikach wynikających z różnicy dwóch wielomianów.
     * @author Sasza Tokar
     * @param wielomian2 - wielomian, który ma zostać dodany do pierwszego podanego wielomianu
     * @return wynik odejmowania dwóch wielomianów (obiekt klasy Wielomian)
     * @throws IllegalArgumentException jeśli podano wielomian o pustej liście współczynników
     */
    operator fun minus(wielomian2: Wielomian): Wielomian {
        if (this.listaWspolczynnikow.isNotEmpty() && wielomian2.listaWspolczynnikow.isNotEmpty()) {
            val wspolczynnikiNowe = mutableListOf<Int>()
            if (this.listaWspolczynnikow.size >= wielomian2.listaWspolczynnikow.size) {
                for (wspolczynnik in this.listaWspolczynnikow) {
                    wspolczynnikiNowe.add(wspolczynnik)
                }
                for (pozycja in (this.listaWspolczynnikow.size - wielomian2.listaWspolczynnikow.size..<this.listaWspolczynnikow.size)) {
                    wspolczynnikiNowe[pozycja] -= wielomian2.listaWspolczynnikow[pozycja - (this.listaWspolczynnikow.size - wielomian2.listaWspolczynnikow.size)]
                }
            } else {
                repeat(wielomian2.listaWspolczynnikow.size - this.listaWspolczynnikow.size){
                    wspolczynnikiNowe += 0
                }
                for (wspolczynnik in this.listaWspolczynnikow) {
                    wspolczynnikiNowe.add(wspolczynnik)
                }
                for (pozycja in wielomian2.listaWspolczynnikow.indices) {
                    wspolczynnikiNowe[pozycja] -= wielomian2.listaWspolczynnikow[pozycja]
                }
            }

            while (wspolczynnikiNowe.first() == 0 && wspolczynnikiNowe.size > 1){
                wspolczynnikiNowe.removeAt(0)
            }
            return if (wspolczynnikiNowe.isNotEmpty()) Wielomian(wspolczynnikiNowe)
            else Wielomian(mutableListOf(0))
        }
        else throw IllegalArgumentException("Podano co najmniej 1 niewłaściwy wielomian.")
    }

    /**
     * Operator wywołania times zwraca wielomian (obiekt klasy Wielomian) o współczynnikach wynikających z iloczynu dwóch wielomianów.
     * @author Sasza Tokar
     * @param wielomian2 - wielomian, który ma zostać dodany do pierwszego podanego wielomianu
     * @return wynik mnożenia dwóch wielomianów (obiekt klasy Wielomian)
     * @throws IllegalArgumentException jeśli podano wielomian o pustej liście współczynników
     */
    operator fun times(wielomian2: Wielomian): Wielomian{
        if (this.listaWspolczynnikow.isNotEmpty() && wielomian2.listaWspolczynnikow.isNotEmpty()) {
            val wspolczynnikiNowe =
                MutableList(this.listaWspolczynnikow.size + wielomian2.listaWspolczynnikow.size - 1) { 0 }
            var c = 1
            var a: Int

            for (i in this.listaWspolczynnikow.size - 1 downTo 0) {
                a = this.listaWspolczynnikow.size + wielomian2.listaWspolczynnikow.size - 1 - c
                for (j in wielomian2.listaWspolczynnikow.size - 1 downTo 0) {
                    wspolczynnikiNowe[a] += this.listaWspolczynnikow[i] * wielomian2.listaWspolczynnikow[j]
                    a--
                }
                c++
            }
            while (wspolczynnikiNowe.first() == 0 && wspolczynnikiNowe.size > 1){
                wspolczynnikiNowe.removeAt(0)
            }
            return Wielomian(wspolczynnikiNowe)
        }
        else throw IllegalArgumentException("Podano co najmniej 1 niewłaściwy wielomian.")
    }

    /**
     * Operator wywołania plusAssign zwraca wielomian (obiekt klasy Wielomian) o współczynnikach wynikających z sumy dwóch wielomianów.
     * @author Sasza Tokar
     * @param wielomian2 - wielomian, który ma zostać dodany do pierwotnego wielomianu
     * @return wynik dodawania dwóch wielomianów (obiekt klasy Wielomian)
     * @throws IllegalArgumentException jeśli podano wielomian o pustej liście współczynników
     */
    operator fun plusAssign(wielomian2: Wielomian) {
        if (this.listaWspolczynnikow.isNotEmpty() && wielomian2.listaWspolczynnikow.isNotEmpty()) {
            val wspolczynnikiNowe = mutableListOf<Int>()
            if (this.listaWspolczynnikow.size >= wielomian2.listaWspolczynnikow.size) {
                for (wspolczynnik in this.listaWspolczynnikow) {
                    wspolczynnikiNowe.add(wspolczynnik)
                }
                for (pozycja in (this.listaWspolczynnikow.size - wielomian2.listaWspolczynnikow.size..<this.listaWspolczynnikow.size)) {
                    wspolczynnikiNowe[pozycja] += wielomian2.listaWspolczynnikow[pozycja - (this.listaWspolczynnikow.size - wielomian2.listaWspolczynnikow.size)]
                }
            } else {
                for (wspolczynnik in wielomian2.listaWspolczynnikow) {
                    wspolczynnikiNowe.add(wspolczynnik)
                }
                for (pozycja in (wielomian2.listaWspolczynnikow.size - this.listaWspolczynnikow.size..<wielomian2.listaWspolczynnikow.size)) {
                    wspolczynnikiNowe[pozycja] += this.listaWspolczynnikow[pozycja - (wielomian2.listaWspolczynnikow.size - this.listaWspolczynnikow.size)]
                }
            }

            while (wspolczynnikiNowe.first() == 0 && wspolczynnikiNowe.size > 1){
                wspolczynnikiNowe.removeAt(0)
            }

            while (wspolczynnikiNowe.first() == 0 && wspolczynnikiNowe.size > 1){
                wspolczynnikiNowe.removeAt(0)
            }

            if (wspolczynnikiNowe.isNotEmpty()) this.listaWspolczynnikow = wspolczynnikiNowe
            else this.listaWspolczynnikow = mutableListOf(0)
        }
        else throw IllegalArgumentException("Podano co najmniej 1 niewłaściwy wielomian.")
    }

    /**
     * Operator wywołania minusAssign zwraca wielomian (obiekt klasy Wielomian) o współczynnikach wynikających z różnicy dwóch wielomianów.
     * @author Sasza Tokar
     * @param wielomian2 - wielomian, który ma zostać dodany do pierwotnego wielomianu
     * @return wynik odejmowania dwóch wielomianów (obiekt klasy Wielomian)
     * @throws IllegalArgumentException jeśli podano wielomian o pustej liście współczynników
     */
    operator fun minusAssign(wielomian2: Wielomian) {
        if (this.listaWspolczynnikow.isNotEmpty() && wielomian2.listaWspolczynnikow.isNotEmpty()) {
            val wspolczynnikiNowe = mutableListOf<Int>()
            if (this.listaWspolczynnikow.size >= wielomian2.listaWspolczynnikow.size) {
                for (wspolczynnik in this.listaWspolczynnikow) {
                    wspolczynnikiNowe.add(wspolczynnik)
                }
                for (pozycja in (this.listaWspolczynnikow.size - wielomian2.listaWspolczynnikow.size..<this.listaWspolczynnikow.size)) {
                    wspolczynnikiNowe[pozycja] -= wielomian2.listaWspolczynnikow[pozycja - (this.listaWspolczynnikow.size - wielomian2.listaWspolczynnikow.size)]
                }
            } else {
                repeat(wielomian2.listaWspolczynnikow.size - this.listaWspolczynnikow.size){
                    wspolczynnikiNowe += 0
                }
                for (wspolczynnik in this.listaWspolczynnikow) {
                    wspolczynnikiNowe.add(wspolczynnik)
                }
                for (pozycja in wielomian2.listaWspolczynnikow.indices) {
                    wspolczynnikiNowe[pozycja] -= wielomian2.listaWspolczynnikow[pozycja]
                }
            }

            while (wspolczynnikiNowe.first() == 0 && wspolczynnikiNowe.size > 1){
                wspolczynnikiNowe.removeAt(0)
            }
            if (wspolczynnikiNowe.isNotEmpty()) this.listaWspolczynnikow = wspolczynnikiNowe
            else this.listaWspolczynnikow = mutableListOf(0)
        }
        else throw IllegalArgumentException("Podano co najmniej 1 niewłaściwy wielomian.")
    }

    /**
     * Operator wywołania timesAssign zwraca wielomian (obiekt klasy Wielomian) o współczynnikach wynikających z iloczynu dwóch wielomianów.
     * @author Sasza Tokar
     * @param wielomian2 - wielomian, który ma zostać dodany do pierwotnego wielomianu
     * @return wynik mnożenia dwóch wielomianów (obiekt klasy Wielomian)
     * @throws IllegalArgumentException jeśli podano wielomian o pustej liście współczynników
     */
    operator fun timesAssign(wielomian2: Wielomian){
        if (this.listaWspolczynnikow.isNotEmpty() && wielomian2.listaWspolczynnikow.isNotEmpty()) {
            val wspolczynnikiNowe =
                MutableList(this.listaWspolczynnikow.size + wielomian2.listaWspolczynnikow.size - 1) { 0 }
            var c = 1
            var a: Int

            for (i in this.listaWspolczynnikow.size - 1 downTo 0) {
                a = this.listaWspolczynnikow.size + wielomian2.listaWspolczynnikow.size - 1 - c
                for (j in wielomian2.listaWspolczynnikow.size - 1 downTo 0) {
                    wspolczynnikiNowe[a] += this.listaWspolczynnikow[i] * wielomian2.listaWspolczynnikow[j]
                    a--
                }
                c++
            }
            while (wspolczynnikiNowe.first() == 0 && wspolczynnikiNowe.size > 1){
                wspolczynnikiNowe.removeAt(0)
            }
            this.listaWspolczynnikow = wspolczynnikiNowe
        }
        else throw IllegalArgumentException("Podano co najmniej 1 niewłaściwy wielomian.")
    }
}

class TestWielomian {

    @Test
    fun testStopien() {
        val oczekiwanyWynik = 3
        assertEquals(oczekiwanyWynik, Wielomian(mutableListOf(1, 2, 3, 4)).stopien())
    }

    @Test
    fun testWyswietl() {
        val oczekiwanyWynik = "W(x) = 7x^4 - 5x^3 - 2x^2 + 7x - 19"
        assertEquals(oczekiwanyWynik, Wielomian(mutableListOf(7, -5, -2, 7, -19)).wyswietl())
    }

    @Test
    fun testWywolania() {
        val oczekiwanyWynik = 5
        assertEquals(oczekiwanyWynik, Wielomian(mutableListOf(2, -2, 1))(2))
    }

    @Test
    fun testDodawania() {
        val oczekiwanyWynik = Wielomian(mutableListOf(4, 4, 4)).wyswietl()
        assertEquals(
            oczekiwanyWynik,
            ((Wielomian(mutableListOf(1, 2, 3)) + Wielomian(mutableListOf(3, 2, 1))).wyswietl())
        )
    }

    @Test
    fun testOdejmowania() {
        val oczekiwanyWynik = Wielomian(mutableListOf(3, 0, 1)).wyswietl()
        assertEquals(
            oczekiwanyWynik,
            ((Wielomian(mutableListOf(4, 2, 0)) - Wielomian(mutableListOf(1, 2, -1))).wyswietl())
        )
    }

    @Test
    fun testMnozenia() {
        val oczekiwanyWynik = Wielomian(mutableListOf(3, 11, 15, 9, 2)).wyswietl()
        assertEquals(
            oczekiwanyWynik,
            ((Wielomian(mutableListOf(1, 2, 1)) * Wielomian(mutableListOf(3, 5, 2))).wyswietl())
        )
    }

    @Test
    fun testDodawaniaAssign() {
        val oczekiwanyWynik = Wielomian(mutableListOf(4, 4, 4)).wyswietl()
        val wielomianDodawanie = Wielomian(mutableListOf(1, 2, 3))
        wielomianDodawanie += Wielomian(mutableListOf(3, 2, 1))
        assertEquals(oczekiwanyWynik, wielomianDodawanie.wyswietl())
    }


    @Test
    fun testOdejmowaniaAssign() {
        val oczekiwanyWynik = Wielomian(mutableListOf(3, 0, 1)).wyswietl()
        val wielomianOdejmowanie = Wielomian(mutableListOf(4, 2, 0))
        wielomianOdejmowanie -= Wielomian(mutableListOf(1, 2, -1))
        assertEquals(oczekiwanyWynik, wielomianOdejmowanie.wyswietl())
    }

    @Test
    fun testMnozeniaAssign() {
        val oczekiwanyWynik = Wielomian(mutableListOf(3, 11, 15, 9, 2)).wyswietl()
        val wielomianMnozenie = Wielomian(mutableListOf(1, 2, 1))
        wielomianMnozenie *= Wielomian(mutableListOf(3, 5, 2))
        assertEquals(oczekiwanyWynik, wielomianMnozenie.wyswietl())

}
}

fun main(){
    val wspolczynniki1 = mutableListOf(4, 0, 0, 0)
    val wspolczynniki2 = mutableListOf(7, -5, -2, 7, -19)
    val wspolczynniki3 = mutableListOf(21)

    val wielomian1 = Wielomian(wspolczynniki1)
    val wielomian2 = Wielomian(wspolczynniki2)
    val wielomian3 = Wielomian(wspolczynniki3)

    println(wielomian1.stopien())
    println(wielomian1.wyswietl())
    println(wielomian2.stopien())
    println(wielomian2.wyswietl())
    println(wielomian3.stopien())
    println(wielomian3.wyswietl())

    try {
        val wspolczynniki4 = mutableListOf<Int>()
        Wielomian(wspolczynniki4)
    }
    catch (ex: IllegalArgumentException){
        println("Wystąpil błąd $ex")
    }
    try {
        Wielomian(mutableListOf()).stopien()
    }
    catch (ex: IllegalArgumentException){
        println("Wystąpił błąd $ex")
    }
    try {
        Wielomian(mutableListOf()).wyswietl()
    }
    catch (ex: IllegalArgumentException){
        println("Wystąpił błąd $ex")
    }

    println(wielomian2(3))

    TestWielomian().testStopien()
    TestWielomian().testWyswietl()
    TestWielomian().testWywolania()
    TestWielomian().testDodawania()
    TestWielomian().testOdejmowania()
    TestWielomian().testMnozenia()
    TestWielomian().testDodawaniaAssign()
    TestWielomian().testOdejmowaniaAssign()
    TestWielomian().testMnozeniaAssign()

    println((wielomian1 + wielomian2).wyswietl())
    println((wielomian2 + wielomian1).wyswietl())
    println((wielomian2 + wielomian2).wyswietl())

    println((wielomian1 - wielomian2).wyswietl())
    println((wielomian2 - wielomian1).wyswietl())
    println((wielomian2 - wielomian2).wyswietl())

    println((wielomian1 * wielomian2).wyswietl())
    println((wielomian1 * (wielomian2 - wielomian2)).wyswietl())

    val wielomian4 = Wielomian(mutableListOf(2, -6, 6, 10))
    wielomian4 += wielomian2
    println(wielomian4.wyswietl())

    wielomian4 -= wielomian2
    println(wielomian4.wyswietl())

    wielomian4 *= wielomian1
    println(wielomian4.wyswietl())
}