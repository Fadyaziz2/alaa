package com.alaa.alaagallo.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import com.alaa.domain.entity.ProductEntity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import kotlin.math.roundToInt

class BarcodeLabelBuilder {

    fun buildPreview(
        product: ProductEntity,
        showPrice: Boolean,
        density: Float,
    ): Bitmap? {
        return buildInternal(product, showPrice, 1, density)
    }

    fun buildForPrinting(
        product: ProductEntity,
        showPrice: Boolean,
        copies: Int,
        density: Float,
    ): Bitmap? {
        if (copies <= 0) return null
        return buildInternal(product, showPrice, copies, density)
    }

    private fun buildInternal(
        product: ProductEntity,
        showPrice: Boolean,
        copies: Int,
        density: Float,
    ): Bitmap? {
        if (product.barcode.isBlank()) return null
        val width = (260 * density).roundToInt().coerceAtLeast(200)
        val heightPerLabel = (140 * density).roundToInt().coerceAtLeast(120)
        val barcodeWidth = (220 * density).roundToInt().coerceAtLeast(180)
        val barcodeHeight = (70 * density).roundToInt().coerceAtLeast(60)
        val barcodeBitmap = createBarcodeBitmap(product.barcode, barcodeWidth, barcodeHeight) ?: return null
        val totalHeight = heightPerLabel * copies
        val resultBitmap = Bitmap.createBitmap(width, totalHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap)
        canvas.drawColor(Color.WHITE)
        val namePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = 14f * density
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT_BOLD
        }
        val secondaryPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = 12f * density
            textAlign = Paint.Align.CENTER
        }
        val dividerPaint = Paint().apply {
            color = Color.parseColor("#E0E0E0")
            strokeWidth = density
        }
        val trimmedName = product.name.takeIf { it.length <= 28 } ?: product.name.take(28) + "…"
        val barcodeLeft = (width - barcodeWidth) / 2f
        val basePrice = product.sellingPrice.takeIf { it > 0 } ?: product.price.takeIf { it > 0 }

        repeat(copies) { index ->
            val top = index * heightPerLabel
            canvas.drawBitmap(barcodeBitmap, barcodeLeft, top + 12f * density, null)
            val nameY = top + barcodeHeight + 32f * density
            canvas.drawText(trimmedName, width / 2f, nameY, namePaint)
            if (showPrice && basePrice != null) {
                val priceText = "سعر: $basePrice"
                canvas.drawText(priceText, width / 2f, nameY + 18f * density, secondaryPaint)
            }
            canvas.drawText(product.barcode, width / 2f, top + heightPerLabel - 12f * density, secondaryPaint)
            if (index < copies - 1) {
                canvas.drawLine(0f, (top + heightPerLabel).toFloat(), width.toFloat(), (top + heightPerLabel).toFloat(), dividerPaint)
            }
        }
        return resultBitmap
    }

    private fun createBarcodeBitmap(content: String, width: Int, height: Int): Bitmap? {
        return try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, width, height, null)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    val color = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
                    bitmap.setPixel(x, y, color)
                }
            }
            bitmap
        } catch (e: Exception) {
            null
        }
    }
}
