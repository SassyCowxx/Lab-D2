package com.example.labd2

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Klasa abstrakcyjna Sequence zawiera wspólne właściwości i metody klas DNASequence, RNASequence i ProteinSeuquence.
 * Służy do budowy klas odpowiadających sekwencjom DNA, RNA i aminokwasów. Posiada identyfikator oraz sekwencję znaków.
 * @author Sasza Tokar
 */
abstract class Sequence{

    abstract var identifier: String
    abstract var data: String
    abstract val validChars: List<Char>
    abstract var length: Int

    /**
     * Funkcja toString zwraca tekstową reprezentację (obiekt typu String) sekwencji w formacie FASTA.
     * @author Sasza Tokar
     */
    override fun toString(): String {
        return ">$identifier\n$data"
    }

    /**
     * Funkcja mutate zamienia zasadę na zadanej pozycji w sekwencji na podaną.
     * @author Sasza Tokar
     * @param position - indeks pozycji, którą chce się zamienić
     * @param value - symbol, na jaki chce się zamienić wybrany element w sekwencji
     * @throws IllegalArgumentException jeśli podano pustą sekwencję lub sekwencja zawiera niedozwolone znaki
     */
    open fun mutate(position: Int, value: Char){
        if (position < this.data.length){
            if (this.validChars.contains(value)){
                val builder = StringBuilder(data)
                builder.setCharAt(position, value.uppercaseChar())
                this.data = builder.toString()
            }
            else throw IllegalArgumentException("Sekwencja nie może zawierać podanego znaku.")
        }
        else throw IllegalArgumentException("Podana pozycja wykracza poza długość sekwencji.")
    }

    /**
     * Funkcja findMotif zwraca listę pozycji (obiekt typu MutableList<Int>, na których znaleziony został podany motyw w obrębie sekwencji.
     * Funkcja nie bierze pod uwagę trójkowości kodu.
     * @author Sasza Tokar
     * @param motif ciąg znaków, który ma zostać wyszukany w sekwencji
     * @return lista zawierająca numery pozycji, na których została znaleziona podana sekwencja.
     */
    open fun findMotif(motif: String): MutableList<Int> {
        var a = 0
        val found = mutableListOf<Int>()
        var currentMotif = ""
        val train = mutableListOf<Int>()
        for (i in motif.indices){
            train += i
        }

        while (a + motif.length <= this.data.length){
            for (i in train){
                currentMotif += this.data[i + a].toString()
            }
            if (currentMotif == motif.uppercase()) found += a
            currentMotif = ""
            a++
        }
        return found
    }
}

/**
 * Klasa DNASequence służy do tworzenia obiektów symulujących sekwencję DNA. Posiada identyfikator oraz sekwencję znaków.
 * @author Sasza Tokar
 * @throws IllegalArgumentException jeśli podano pustą sekwencję lub sekwencja zawiera niedozwolone znaki
 */
class DNASequence(override var identifier: String, override var data: String): Sequence() {
    override val validChars = listOf('A', 'C', 'G', 'T')
    override var length = data.length

    init {
        this.identifier = identifier.lowercase()
        this.data = data.uppercase()
        if (this.data.isEmpty()) throw IllegalArgumentException("Podano pustą sekwencję DNA.")
        for (character in data) {
            if (!validChars.contains(character)) {
                throw IllegalArgumentException("W sekwencji DNA nie mogą wystąpić znaki inne niż A, C, G, T.")
            }
        }
    }

    /**
     * Funkcja complement zwraca sekwencję DNA (obiekt typu String) komplementarną do wybranej sekwencji DNA.
     * @author Sasza Tokar
     * @throws IllegalArgumentException jeśli sekwencja zawiera niedozwolone znaki
     */
    fun complement(): String{
        val change = mapOf(
            "A" to "T",
            "T" to "A",
            "C" to "G",
            "G" to "C"
        )
        var newSequence = ""
        for (character in data) {
            if (!this.validChars.contains(character)) {
                throw IllegalArgumentException("W sekwencji DNA nie mogą wystąpić znaki inne niż A, C, G, T.")
            }
        }

        for (character in this.data){
            newSequence += change[character.uppercase()]
        }
        return newSequence
    }

    /**
     * Funkcja transcribe zwraca sekwencję RNA (obiekt klasy RNASequence) komplementarną do wybranej sekwencji DNA.
     * @author Sasza Tokar
     * @throws IllegalArgumentException jeśli sekwencja zawiera niedozwolone znaki
     */
    fun transcribe(): RNASequence{
        val change = mapOf(
            "A" to "U",
            "T" to "A",
            "C" to "G",
            "G" to "C"
        )
        var newSequence = ""
        for (character in data) {
            if (!this.validChars.contains(character)) {
                throw IllegalArgumentException("W sekwencji DNA nie mogą wystąpić znaki inne niż A, C, G, T.")
            }
        }

        for (character in this.data){
            newSequence += change[character.uppercase()]
        }
        return RNASequence(this.identifier, newSequence)
    }
}

/**
 * Klasa RNASequence służy do tworzenia obiektów symulujących sekwencję RNA. Posiada identyfikator oraz sekwencję znaków.
 * @author Sasza Tokar
 * @throws IllegalArgumentException jeśli podano pustą sekwencję lub sekwencja zawiera niedozwolone znaki
 */
class RNASequence(override var identifier: String, override var data: String): Sequence() {
    override val validChars = listOf('A', 'C', 'G', 'U')
    override var length = data.length

    init {
        this.identifier = identifier.lowercase()
        this.data = data.uppercase()
        if (this.data.isEmpty()) throw IllegalArgumentException("Podano pustą sekwencję RNA.")
        for (character in data) {
            if (!validChars.contains(character)) {
                throw IllegalArgumentException("W sekwencji RNA nie mogą wystąpić znaki inne niż A, C, G, U.")
            }
        }
    }

    /**
     * Funkcja translate zwraca sekwencję aminokwasów (obiekt typu ProteinSequence) na podstawie podanej sekwencji RNA.
     * @author Sasza Tokar
     * @throws IllegalArgumentException jeśli sekwencja zawiera niedozwolone znaki
     */
    fun translate(): ProteinSequence {
        val change = mapOf(
            "UUU" to "F", "UUC" to "F", "UUA" to "L", "UUG" to "L",
            "CUU" to "L", "CUC" to "L", "CUA" to "L", "CUG" to "L",
            "AUU" to "I", "AUC" to "I", "AUA" to "I", "AUG" to "M",
            "GUU" to "V", "GUC" to "V", "GUA" to "V", "GUG" to "V",
            "UCU" to "S", "UCC" to "S", "UCA" to "S", "UCG" to "S",
            "CCU" to "P", "CCC" to "P", "CCA" to "P", "CCG" to "P",
            "ACU" to "T", "ACC" to "T", "ACA" to "T", "ACG" to "T",
            "GCU" to "A", "GCC" to "A", "GCA" to "A", "GCG" to "A",
            "UAU" to "Y", "UAC" to "Y", "UAA" to "*", "UAG" to "*",
            "CAU" to "H", "CAC" to "H", "CAA" to "Q", "CAG" to "Q",
            "AAU" to "N", "AAC" to "N", "AAA" to "K", "AAG" to "K",
            "GAU" to "D", "GAC" to "D", "GAA" to "E", "GAG" to "E",
            "UGU" to "C", "UGC" to "C", "UGA" to "*", "UGG" to "W",
            "CGU" to "R", "CGC" to "R", "CGA" to "R", "CGG" to "R",
            "AGU" to "S", "AGC" to "S", "AGA" to "R", "AGG" to "R",
            "GGU" to "G", "GGC" to "G", "GGA" to "G", "GGG" to "G"
        )
        var newSequence = ""
        for (character in data) {
            if (!this.validChars.contains(character)) {
                throw IllegalArgumentException("W sekwencji RNA nie mogą wystąpić znaki inne niż A, C, G, U.")
            }
        }
        val repeats = this.data.length / 3
        var codon: String
        var a = 0
        repeat(repeats){
            codon = (this.data[a].toString() + this.data[a + 1] + this.data[a + 2])
            newSequence += change[codon]
            a += 3
        }
        return ProteinSequence(this.identifier, newSequence)
    }
}

/**
 * Klasa RNASequence służy do tworzenia obiektów symulujących sekwencję aminokwasów. Posiada identyfikator oraz sekwencję znaków.
 * @author Sasza Tokar
 * @throws IllegalArgumentException jeśli podano pustą sekwencję lub sekwencja zawiera niedozwolone znaki
 */
class ProteinSequence(override var identifier: String, override var data: String): Sequence() {
    override val validChars = listOf('A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', '*')
    override var length = data.length

    init {
        this.identifier = identifier.lowercase()
        this.data = data.uppercase()
        if (this.data.isEmpty()) throw IllegalArgumentException("Podano pustą sekwencję białka.")
        for (character in data) {
            if (!validChars.contains(character)) {
                throw IllegalArgumentException("W podanej sekwencji białka wystąpiły niedozwolone znaki.")
            }
        }
    }
}

class DNASequenceTest {

    @Test
    fun testToString(){
        val oczekiwanyWynik = ">sekwencja1\nACGT"
        assertEquals(oczekiwanyWynik, DNASequence("sekwencja1", "ACGT").toString())
    }

    @Test
    fun testMutate(){
        val oczekiwanyWynik = ">sekwencja1\nATGT"
        val sekwencja1 = DNASequence("sekwencja1", "ACGT")
            sekwencja1.mutate(1, 'T')
        assertEquals(oczekiwanyWynik, sekwencja1.toString())
    }

    @Test
    fun testFindMotif(){
        val oczekiwanyWynik = listOf(1)
        val sekwencja1 = DNASequence("sekwencja1", "ACGT")
        assertEquals(oczekiwanyWynik, sekwencja1.findMotif("CGT"))
    }

    @Test
    fun testComplement(){
        val oczekiwanyWynik = "TGCAG"
        val sekwencja1 = DNASequence("sekwencja1", "ACGTC")
        assertEquals(oczekiwanyWynik, sekwencja1.complement())
    }

    @Test
    fun testTranscribe(){
        val oczekiwanyWynik = RNASequence("sekwencja1", "UGCAG").toString()
        val sekwencja1 = DNASequence("sekwencja1", "ACGTC")
        assertEquals(oczekiwanyWynik, sekwencja1.transcribe().toString())
    }
}

class RNASequenceTest {

    @Test
    fun testToString(){
        val oczekiwanyWynik = ">sekwencja2\nCAGUUA"
        assertEquals(oczekiwanyWynik, RNASequence("sekwencja2", "CAGUUA").toString())
    }

    @Test
    fun testMutate(){
        val oczekiwanyWynik = ">sekwencja2\nCAGGUA"
        val sekwencja2 = RNASequence("sekwencja2", "CAGUUA")
        sekwencja2.mutate(3, 'G')
        assertEquals(oczekiwanyWynik, sekwencja2.toString())
    }

    @Test
    fun testFindMotif(){
        val oczekiwanyWynik = listOf(1)
        val sekwencja2 = RNASequence("sekwencja2", "CAGUUA")
        assertEquals(oczekiwanyWynik, sekwencja2.findMotif("AGU"))
    }

    @Test
    fun testTranslate(){
        val oczekiwanyWynik = ProteinSequence("sekwencja2","QL").toString()
        val sekwencja2 = RNASequence("sekwencja2", "CAGUUA")
        assertEquals(oczekiwanyWynik, sekwencja2.translate().toString())
    }
}

class ProteinSequenceTest {

    @Test
    fun testToString(){
        val oczekiwanyWynik = ">sekwencja3\nAVQCL"
        assertEquals(oczekiwanyWynik, ProteinSequence("sekwencja3", "AVQCL").toString())
    }

    @Test
    fun testMutate(){
        val oczekiwanyWynik = ">sekwencja3\nAVQCC"
        val sekwencja3 = ProteinSequence("sekwencja3", "AVQCL")
        sekwencja3.mutate(4, 'C')
        assertEquals(oczekiwanyWynik, sekwencja3.toString())
    }

    @Test
    fun testFindMotif(){
        val oczekiwanyWynik = listOf(1)
        val sekwencja3 = ProteinSequence("sekwencja3", "AVQCL")
            assertEquals(oczekiwanyWynik, sekwencja3.findMotif("VQCL"))
    }
}

fun main() {
    val sekwencjaDNA = DNASequence("sekwencja1", "ATACCCGATGCGATATCGAAAATAGATCTCA")
    println(sekwencjaDNA.toString())
    sekwencjaDNA.mutate(2, 'A')
    println(sekwencjaDNA.toString())
    println(sekwencjaDNA.findMotif("GAT"))
    println(sekwencjaDNA.complement())
    println()
    val sekwencjaRNA = sekwencjaDNA.transcribe()
    println(sekwencjaRNA.toString())
    println()
    val sekwencjaBialek = sekwencjaRNA.translate()
    println(sekwencjaBialek.toString())

    DNASequenceTest().testToString()
    DNASequenceTest().testMutate()
    DNASequenceTest().testFindMotif()
    DNASequenceTest().testComplement()
    DNASequenceTest().testTranscribe()

    RNASequenceTest().testToString()
    RNASequenceTest().testMutate()
    RNASequenceTest().testFindMotif()
    RNASequenceTest().testTranslate()

    ProteinSequenceTest().testToString()
    ProteinSequenceTest().testMutate()
    ProteinSequenceTest().testFindMotif()
}
