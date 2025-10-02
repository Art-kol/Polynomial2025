/*
package ru.smak.math.polynomial

import ru.smak.math.neq

class Newton : Polynomial {

    private val dots = mutableMapOf<Double, Double>()
    private var remember_div_diffs = mutableListOf<MutableList<Double>>()

    constructor() : super()

    constructor(p: Map<Double, Double>) : super() {
        dots.clear()
        p.entries.forEach { entry ->
            dots[entry.key] = entry.value
        }
        reconstruct()
    }

    private fun reconstruct(fromAdd: Boolean = false) {
        if (dots.isEmpty()) {
            coeffs = mapOf(0 to 0.0)
            remember_div_diffs.clear()
        } else {
            coeffs = construct(dots, fromAdd).coeffs
        }
    }

    private fun construct(pts: Map<Double, Double>, fromAdd: Boolean = false): Polynomial {
        val d_ds = divided_diffs(pts, fromAdd)

        return d_ds.indices.fold(Polynomial()) { acc, i ->
            acc + d_ds[i] * product_of_xs(pts, i)
        }
    }

    private fun divided_diffs(points: Map<Double, Double>, fromAdd: Boolean = false): List<Double> {
        if (fromAdd && remember_div_diffs.isNotEmpty() && remember_div_diffs.size == points.size - 1) {
            val sortedKeys = points.keys.sorted()
            val newX = sortedKeys.last()
            val newY = points[newX]!!
            val n = points.size

            val newRow = MutableList(n) { 0.0 }
            newRow[0] = newY
            remember_div_diffs.add(newRow)

            for (i in 0 until remember_div_diffs.size - 1) {
                while (remember_div_diffs[i].size < n) {
                    remember_div_diffs[i].add(0.0)
                }
            }

            for (j in 1 until n) {
                for (i in 0 until n - j) {
                    remember_div_diffs[i][j] = (remember_div_diffs[i + 1][j - 1] - remember_div_diffs[i][j - 1]) /
                            (sortedKeys[i + j] - sortedKeys[i])
                }
            }

            return List(n) { remember_div_diffs[0][it] }
        }
        else {
            val n = points.size
            val s_keys = points.keys.sorted()
            val values = s_keys.map { points[it]!! }

            remember_div_diffs.clear()

            for (i in 0 until n) {
                val row = MutableList(n) { 0.0 }
                row[0] = values[i]
                remember_div_diffs.add(row)
            }

            for (j in 1 until n) {
                for (i in 0 until n - j) {
                    remember_div_diffs[i][j] = (remember_div_diffs[i + 1][j - 1] - remember_div_diffs[i][j - 1]) /
                            (s_keys[i + j] - s_keys[i])
                }
            }

            return List(n) { remember_div_diffs[0][it] }
        }
    }

    private fun product_of_xs(points: Map<Double, Double>, k: Int): Polynomial {
        val s_keys = points.keys.sorted()
        return (0 until k).fold(Polynomial(1.0)) { acc, i ->
            acc * Polynomial(-s_keys[i], 1.0)
        }
    }

    fun addDot(x: Double, y: Double): Boolean {
        if (dots.containsKey(x)) {
            return false
        } else {
            dots[x] = y
            reconstruct(true)
            return true
        }
    }

    fun removeDot(x: Double): Boolean {
        if (dots.containsKey(x)) {
            dots.remove(x)
            remember_div_diffs.clear()
            reconstruct()
            return true
        } else {
            return false
        }
    }

    fun dotsCopy(): Map<Double, Double> = dots.toMap()
} */

package ru.smak.math.polynomial

import ru.smak.math.neq

class Newton : Polynomial {

    private val dots = mutableMapOf<Double, Double>()

    constructor() : super()

    constructor(p: Map<Double, Double>) : super() {
        dots.clear()
        p.entries.forEach { entry ->
            dots[entry.key] = entry.value
        }
        reconstruct()
    }

    private fun reconstruct() {
        if (dots.isEmpty()) {
            coeffs = mapOf(0 to 0.0)
        } else {
            coeffs = construct(dots).coeffs
        }
    }

    private fun construct(pts: Map<Double, Double>): Polynomial {
        val d_ds = divided_diffs(pts)
        return d_ds.indices.fold(Polynomial()) { acc, i ->
            acc + d_ds[i] * product_of_xs(pts, i)
        }
    }

    private fun divided_diffs(points: Map<Double, Double>): List<Double> {
        val n = points.size
        // Берем ключи в произвольном порядке (как они хранятся в Map)
        val keys = points.keys.toList()
        val values = keys.map { points[it]!! }

        val diffTable = Array(n) { DoubleArray(n) { 0.0 } }

        for (i in 0 until n) {
            diffTable[i][0] = values[i]
        }

        for (j in 1 until n) {
            for (i in 0 until n - j) {
                diffTable[i][j] = (diffTable[i + 1][j - 1] - diffTable[i][j - 1]) /
                        (keys[i + j] - keys[i])
            }
        }

        return List(n) { diffTable[0][it] }
    }

    private fun product_of_xs(points: Map<Double, Double>, k: Int): Polynomial {
        // Берем ключи в том же порядке, что и в divided_diffs
        val keys = points.keys.toList()
        return (0 until k).fold(Polynomial(1.0)) { acc, i ->
            acc * Polynomial(-keys[i], 1.0)
        }
    }

    fun addDot(x: Double, y: Double): Boolean {
        if (dots.containsKey(x)) {
            return false
        } else {
            dots[x] = y
            reconstruct()
            return true
        }
    }

    fun removeDot(x: Double): Boolean {
        if (dots.containsKey(x)) {
            dots.remove(x)
            reconstruct()
            return true
        } else {
            return false
        }
    }

    fun dotsCopy(): Map<Double, Double> = dots.toMap()
}

/* package ru.smak.math.polynomial

import ru.smak.math.neq

class Newton : Polynomial {

    private val dots = mutableMapOf<Double, Double>()

    constructor() : super()

    constructor(p: Map<Double, Double>) : super() {
        dots.clear()
        p.entries.forEach { entry ->
            dots[entry.key] = entry.value
        }

        reconstruct()
    }

    private fun reconstruct() {
        if (dots.isEmpty()) {
            coeffs = mapOf(0 to 0.0)
        } else {
            coeffs = construct(dots).coeffs
        }
    }

    private fun construct(pts: Map<Double, Double>): Polynomial {
        val d_ds = divided_diffs(pts)

        return d_ds.indices.fold(Polynomial()) { acc, i ->
            acc + d_ds[i] * product_of_xs(pts, i)
        }
    }

    private fun divided_diffs(points: Map<Double, Double>): List<Double> {
            val n = points.size
            val s_keys = points.keys.sorted()
            val values = s_keys.map { points[it]!! }

            val diffTable = Array(n) { DoubleArray(n) { 0.0 } }

            for (i in 0 until n) {
                diffTable[i][0] = values[i]
            }

            for (j in 1 until n) {
                for (i in 0 until n - j) {
                    diffTable[i][j] = (diffTable[i + 1][j - 1] - diffTable[i][j - 1]) / (s_keys[i + j] - s_keys[i])
                }
            }


            return List(n) { diffTable[0][it] }
        }

    private fun product_of_xs(points: Map<Double, Double>, k: Int): Polynomial {
        val s_keys = points.keys.sorted()
        return (0 until k).fold(Polynomial(1.0)) { acc, i ->
            acc * Polynomial(-s_keys[i], 1.0)
        }
    }

    fun addDot(x: Double, y: Double) : Boolean {
        if (dots.containsKey(x)) {
            return false
        }
        else{
            dots[x] = y
            reconstruct()
            return true
        }
    }

    fun removeDot(x: Double): Boolean {
        if (dots.containsKey(x)) {
            dots.remove(x)
            reconstruct()
            return true
        } else {
            return false
        }
    }

    fun dotsCopy(): Map<Double, Double> = dots.toMap()
} */


/* package ru.smak.math.polynomial

import ru.smak.math.neq

class Newton : Polynomial {

    private val dots = mutableMapOf<Double, Double>()

    constructor() : super()

    constructor(p: Map<Double, Double>) : super() {
        dots.clear()
        p.entries.forEach { entry ->
            dots[entry.key] = entry.value
        }
        
        reconstruct()
    }

    private fun reconstruct() {
        if (dots.isEmpty()) {
            coeffs = mapOf(0 to 0.0)
        } else {
            coeffs = construct(dots).coeffs
        }
    }

    private fun construct(pts: Map<Double, Double>): Polynomial {
        val d_ds = divided_diffs(pts)

        return d_ds.indices.fold(Polynomial()) { acc, i ->
            acc + d_ds[i] * product_of_xs(pts, i)
        }
    }

    private fun divided_diffs(points: Map<Double, Double>): List<Double> {
        val n = points.size
        val differences = mutableListOf<Double>()

        val s_keys = points.keys.sorted()
        val values = s_keys.map { points[it]!! }

        val tempDiffs = values.toMutableList()
        differences.add(tempDiffs[0])

        for (k in 1 until n) {
            for (i in 0 until n - k) {
                tempDiffs[i] = (tempDiffs[i + 1] - tempDiffs[i]) / (s_keys[i + k] - s_keys[i])
            }
            differences.add(tempDiffs[0])
        }

        return differences
    }

    private fun product_of_xs(points: Map<Double, Double>, k: Int): Polynomial {
        val s_keys = points.keys.sorted()

        return (0 until k).fold(Polynomial(1.0)) { acc, i ->
            acc * Polynomial(-s_keys[i], 1.0)
        }
    }

    fun addDot(x: Double, y: Double) : Boolean {
        if (dots.containsKey(x)) {
            return false
        }
        else{
            dots[x] = y
            reconstruct()
            return true
        }

    }

    fun removeDot(x: Double): Boolean {
        if (dots.containsKey(x)) {
            dots.remove(x)
            reconstruct()
            return true

        } else {
            return false
        }
    }

    fun dotsCopy(): Map<Double, Double> = dots.toMap()
} */

/* class Newton(private val points: Map<Double, Double>) : Polynomial() {

    private val dots = mutableMapOf<Double, Double>()

    init {
        coeffs = construct(points).coeffs
    }

    fun getPoints(): Map<Double, Double> = dots.toMap()

    constructor() : super()                                     // Вызов родительского конструктора

    constructor(points: Map<Double, Double>) : super() {
        dots.putAll(points)
        reconstruct()
    }

    private fun construct(pts: Map<Double, Double>): Polynomial {
        val d_ds = divided_diffs(pts)

        return d_ds.indices.fold(Polynomial()) { acc, i ->
            acc + d_ds[i] * product_of_xs(pts, i)
        }
    }

    private fun divided_diffs(points: Map<Double, Double>): List<Double> {
        val n = points.size
        val differences = mutableListOf<Double>()

        val s_keys = points.keys.sorted()
        val values = s_keys.map { points[it]!! }

        val tempDiffs = values.toMutableList()
        differences.add(tempDiffs[0])

        for (k in 1 until n) {
            for (i in 0 until n - k) {
                tempDiffs[i] = (tempDiffs[i + 1] - tempDiffs[i]) / (s_keys[i + k] - s_keys[i])
            }
            differences.add(tempDiffs[0])
        }

        return differences
    }

    private fun product_of_xs(points: Map<Double, Double>, k: Int): Polynomial {
        val s_keys = points.keys.sorted()

        return (0 until k).fold(Polynomial(1.0)) { acc, i ->
            acc * Polynomial(-s_keys[i], 1.0)
        }
    }
} */


/*
class Newton (private val points: Map<Double, Double>) : Polynomial() {
    init{
        coeffs = points.keys.fold(Polynomial()){acc, v ->
            acc + points[v]!! * fundamental(v)
        }.coeffs
    }

    private fun divided_difference(pts: Map<Double, Double>): List<Polynomial> {

        // Раскомментить, если захотим хранить все f(x1), f(x1, x2), ...
        //val d_ds = mutableListOf<Polynomial>() // Сохраняем f(x1,x2,...,xn)
        // !!! ПРИСВОИТЬ НАЧАЛЬНЫЕ ЗНАЧЕНИЯ КАК У y-ков
        val n = pts.keys.size // Сохраняем размер приходных точек
        val d_ds = mutableListOf<Polynomial>() // Храним ответ
        // !!! Добавить 1 значение как у y1

        val s_keys = pts.keys.sorted()
        val values = s_keys.map { pts[it]!! }


        val keys_to_x = mutableListOf<Double>() // Список x до xi

        val temp_ds = mutableListOf<Polynomial>().apply {
            addAll(values)
        }
        // Сами частные суммы

        for (x in pts.keys) {
            keys_to_x.add(x)                    // Добавляем xi
            val k = keys_to_x.size // Какое кол-во переменных мы обрабатываем на итерации


            // Расчет всех частичных сумм от i переменных для будующей итерации
            for (i in 0 until n + 1 - k) {
                temp_ds[i] = (temp_ds[i] - temp_ds[i + 1]) / (pts.keys[i] - pts.keys[i + k])
            }
            drop last element in list(temp_ds)
            add to d_ds temp_ds[0]
        }

        return d_ds
    }
} */

/* package ru.smak.math.polynomial

import ru.smak.math.neq

class Newton : Polynomial {

    private val dots = mutableMapOf<Double, Double>()
    private var dividedDifferences: MutableList<Double> = mutableListOf()
    private var sortedX: List<Double> = listOf()

    constructor() : super()

    constructor(p: Map<Double, Double>) : super() {
        dots.clear()
        p.entries.forEach { entry ->
            dots[entry.key] = entry.value
        }

        reconstruct()
    }

    private fun reconstruct() {
        if (dots.isEmpty()) {
            coeffs = mapOf(0 to 0.0)
            dividedDifferences.clear()
            sortedX = listOf()
        } else {
            coeffs = construct(dots).coeffs
        }
    }

    private fun construct(pts: Map<Double, Double>): Polynomial {
        val d_ds = divided_diffs(pts)
        dividedDifferences = d_ds.toMutableList()
        sortedX = pts.keys.sorted()

        return d_ds.indices.fold(Polynomial()) { acc, i ->
            acc + d_ds[i] * product_of_xs(pts, i)
        }
    }

    private fun divided_diffs(points: Map<Double, Double>): List<Double> {
        val n = points.size
        val differences = mutableListOf<Double>()

        val s_keys = points.keys.sorted()
        val values = s_keys.map { points[it]!! }

        val tempDiffs = values.toMutableList()
        differences.add(tempDiffs[0])

        for (k in 1 until n) {
            for (i in 0 until n - k) {
                tempDiffs[i] = (tempDiffs[i + 1] - tempDiffs[i]) / (s_keys[i + k] - s_keys[i])
            }
            differences.add(tempDiffs[0])
        }

        return differences
    }

    private fun product_of_xs(points: Map<Double, Double>, k: Int): Polynomial {
        val s_keys = points.keys.sorted()

        return (0 until k).fold(Polynomial(1.0)) { acc, i ->
            acc * Polynomial(-s_keys[i], 1.0)
        }
    }

    fun addDot(x: Double, y: Double) : Boolean {
        if (dots.containsKey(x)) {
            return false
        }
        else{
            dots[x] = y

            if (dots.size == 1) {
                reconstruct()
            }
            else {
                updateWithNewDot(x, y)
            }
            return true
        }
    }

    private fun updateWithNewDot(newX: Double, newY: Double) {
        // Обновляем sortedX с новой точкой
        sortedX = (sortedX + newX).sorted()

        // Вычисляем новые разделенные разности на основе старых
        val newDividedDiffs = computeNewDividedDifferences(newX, newY)
        dividedDifferences = newDividedDiffs.toMutableList()

        // Строим новый полином: L_{n+1}(x) = L_n(x) + f[x_0,...,x_{n+1}] * (x-x_0)...(x-x_n)
        val newCoeffs = buildPolynomialFromDividedDiffs()
        coeffs = newCoeffs
    }

    private fun computeNewDividedDifferences(newX: Double, newY: Double): List<Double> {
        val n = dots.size - 1 // старый размер
        val newDividedDiffs = dividedDifferences.toMutableList()

        // Временный массив для вычисления новых разностей
        val temp = mutableListOf<Double>()
        temp.add(newY)

        // Вычисляем разделенные разности для новой точки
        for (i in 0 until n) {
            val nextTemp = mutableListOf<Double>()
            for (j in 0 until temp.size - 1) {
                val diff = (temp[j + 1] - temp[j]) / (newX - sortedX[j])
                nextTemp.add(diff)
            }
            // Добавляем существующую разделенную разность из предыдущих вычислений
            if (i < dividedDifferences.size - 1) {
                val existingDiff = (temp.last() - dividedDifferences[i]) / (newX - sortedX[i])
                nextTemp.add(existingDiff)
            }
            temp.clear()
            temp.addAll(nextTemp)
        }

        // Последняя разделенная разность
        val lastDiff = (temp[0] - dividedDifferences.last()) / (newX - sortedX.last())
        newDividedDiffs.add(lastDiff)

        return newDividedDiffs
    }

    private fun buildPolynomialFromDividedDiffs(): Map<Int, Double> {
        // Начинаем с текущего полинома
        val currentPoly = Polynomial(coeffs)

        // Вычисляем произведение (x - x_0)(x - x_1)...(x - x_{n-1})
        val productPoly = (0 until sortedX.size - 1).fold(Polynomial(1.0)) { acc, i ->
            acc * Polynomial(-sortedX[i], 1.0)
        }

        // Новый полином: L_{n+1}(x) = L_n(x) + f[x_0,...,x_n] * product
        val newPoly = currentPoly + dividedDifferences.last() * productPoly

        return newPoly.coeffs
    }

    fun removeDot(x: Double): Boolean {
        if (dots.containsKey(x)) {
            dots.remove(x)
            reconstruct()
            return true
        } else {
            return false
        }
    }

    fun dotsCopy(): Map<Double, Double> = dots.toMap()

    fun getDividedDifferences(): List<Double> = dividedDifferences.toList()
} */