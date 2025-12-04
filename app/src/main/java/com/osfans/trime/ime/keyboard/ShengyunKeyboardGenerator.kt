package com.osfans.trime.ime.keyboard

import android.content.Context
import com.osfans.trime.data.theme.Theme
import com.osfans.trime.data.theme.model.TextKeyboard
import com.osfans.trime.data.theme.model.TextKeyboard.TextKey
import com.osfans.trime.ime.keyboard.KeyBehavior
import timber.log.Timber
import java.io.InputStream

/**
 * 声韵输入法键盘动态生成器
 * 根据当前声母动态生成对应的韵母键盘
 */
class ShengyunKeyboardGenerator(
    private val context: Context,
    private val theme: Theme
) {
    private data class FinalsLayout(
        val layout: List<List<Any>>, // String or List<String>
        val functionKeys: Map<String, Map<String, Any>>
    )

    companion object {
        private var cachedFinalsMap: Map<String, List<String>>? = null
        private var cachedLayoutTemplate: FinalsLayout? = null
    }

    private var finalsMap: Map<String, List<String>> = emptyMap()
    private var layoutTemplate: FinalsLayout? = null

    init {
        if (cachedFinalsMap == null || cachedLayoutTemplate == null) {
            loadConfiguration()
        }
        finalsMap = cachedFinalsMap ?: emptyMap()
        layoutTemplate = cachedLayoutTemplate
    }

    /**
     * 加载配置文件：
     * 1. shengyun_pinyin.tsv - 拼音表(从generator/pinyin.tsv链接)
     * 2. shengyun_layout.yaml - 布局模板
     */
    private fun loadConfiguration() {
        try {
            // Load finals map from TSV
            // TSV format: Row 0 = initials (headers), Col 0 = finals
            // Each cell contains the pinyin (initial + final), or "-" if invalid
            context.assets.open("shared/shengyun_pinyin.tsv").bufferedReader().use { reader ->
                val lines = reader.readLines()
                if (lines.isEmpty()) return@use
                
                // Parse header row (initials)
                val initials = lines[0].split("\t").drop(1) // Skip first column (which is "er")
                val finalsMapBuilder = mutableMapOf<String, MutableList<String>>()
                
                // Initialize map with "zero" for standalone finals
                finalsMapBuilder["zero"] = mutableListOf()
                
                // Initialize map for each initial
                initials.forEach { initial ->
                    if (initial.isNotEmpty() && initial != "-") {
                        finalsMapBuilder[initial] = mutableListOf()
                    }
                }
                
                // Parse data rows
                lines.drop(1).forEach { line ->
                    val cells = line.split("\t")
                    if (cells.isEmpty()) return@forEach
                    
                    val final = cells[0] // First column is the final
                    
                    // Check each initial column
                    cells.drop(1).forEachIndexed { index, cell ->
                        if (index < initials.size && cell.isNotEmpty() && cell != "-") {
                            val initial = initials[index]
                            finalsMapBuilder[initial]?.add(final)
                        }
                    }
                    
                    // If this final appears standalone (like "er", "a", "o", etc.)
                    // Add it to the "zero" initial
                    if (cells.size > initials.size + 1) {
                        val lastCell = cells.last()
                        if (lastCell.isNotEmpty() && lastCell != "-") {
                            finalsMapBuilder["zero"]?.add(final)
                        }
                    } else if (cells.size == initials.size + 1) {
                        // Check if the final itself is in the last column
                        val lastCell = cells.last()
                        if (lastCell == final || (lastCell.isNotEmpty() && lastCell != "-" && !lastCell.contains(final))) {
                            finalsMapBuilder["zero"]?.add(final)
                        }
                    }
                }
                
                cachedFinalsMap = finalsMapBuilder.mapValues { it.value.toList() }
            }

            // Hardcoded layout template (replacing YAML loading)
            val layout = listOf(
                listOf("er", "an", "ian", "a", "o", "ie", "ao", "ai"),
                listOf("i", "en", listOf("uan", "van"), "ing", "e", listOf("iao", "ua"), "ou", "ei"),
                listOf("u", "in", "ang", listOf("ong", "iong"), "iu", "uo", "ia", "⇧"),
                listOf("v", listOf("un", "vn"), "eng", listOf("iang", "uang"), "uai", listOf("ui", "ve"), "␣", "⌫")
            )
            
            val functionKeys = mapOf(
                "⇧" to mapOf("label" to "⇧", "width" to 12.5, "send_bindings" to false, "hilited" to true),
                "␣" to mapOf("label" to "␣", "action" to "space", "width" to 12.5),
                "⌫" to mapOf("label" to "⌫", "action" to "backspace", "width" to 12.5, "repeat" to true)
            )
            
            cachedLayoutTemplate = FinalsLayout(layout, functionKeys)
            
        } catch (e: Exception) {
            Timber.e(e, "Failed to load Shengyun configuration")
        }
    }

    /**
     * 为指定声母生成韵母键盘
     * @param initial 声母（如 "b", "p", "zh", "zero"）
     * @return 生成的键盘实例
     */
    fun generateKeyboard(initial: String): Keyboard {
        val availableFinals = finalsMap[initial] ?: emptyList()
        val keys = mutableListOf<TextKey>()

        val template = layoutTemplate ?: return Keyboard(theme, null) // Return empty if failed to load

        // 基于布局模板生成按键
        template.layout.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, slot ->
                val key = when (slot) {
                    is String -> {
                        if (rowIndex == 0 && colIndex == 0 && initial != "zero") {
                            // Special handling for top-left key: show initial and switch back
                            createInitialLabelKey(initial)
                        } else if (template.functionKeys.containsKey(slot)) {
                            createFunctionKey(slot, template.functionKeys[slot]!!)
                        } else if (slot.isEmpty()) {
                            createEmptyKey()
                        } else {
                            // 普通韵母
                            if (slot in availableFinals) {
                                createFinalKey(slot, initial)
                            } else {
                                createEmptyKey()
                            }
                        }
                    }
                    is List<*> -> {
                        // 互斥韵母：选择第一个可用的
                        // slot is List<String>
                        val options = slot.filterIsInstance<String>()
                        val selected = options.firstOrNull { it in availableFinals }
                        if (selected != null) {
                            createFinalKey(selected, initial)
                        } else {
                            createEmptyKey()
                        }
                    }
                    else -> createEmptyKey()
                }
                keys.add(key)
            }
        }

        val keyboardConfig = TextKeyboard(
            name = "韵母层-$initial",
            author = "ShengyunGenerator",
            width = 12.5f,
            height = 70f,
            keyboardHeight = 0,
            keyboardHeightLand = 0,
            autoHeightIndex = 0,
            horizontalGap = 0,
            verticalGap = 0,
            roundCorner = 0f,
            columns = -1,
            asciiMode = false,
            resetAsciiMode = false,
            labelTransform = TextKeyboard.LabelTransform.NONE,
            lock = true,
            once = true,
            asciiKeyboard = "",
            landscapeKeyboard = "",
            landscapeSplitPercent = 0,
            keyTextOffsetX = 0f,
            keyTextOffsetY = 0f,
            keySymbolOffsetX = 0f,
            keySymbolOffsetY = 0f,
            keyHintOffsetX = 0f,
            keyHintOffsetY = 0f,
            keyPressOffsetX = 0,
            keyPressOffsetY = 0,
            importPreset = "",
            keys = keys
        )

        return Keyboard(theme, keyboardConfig)
    }

    private fun createFunctionKey(name: String, config: Map<String, Any>): TextKey {
        val label = config["label"] as? String ?: name
        val width = (config["width"] as? Number)?.toFloat() ?: 12.5f
        
        val click = when (name) {
            "⇧" -> "{command: keyboard, option: shengyun_initials}"
            "␣" -> "space"
            "⌫" -> "BackSpace"
            else -> config["action"] as? String ?: name
        }
        
        val sendBindings = when (name) {
            "⇧" -> config["send_bindings"] as? Boolean ?: false
            else -> true
        }
        
        return TextKey(
            width = width,
            height = 0f,
            roundCorner = 0f,
            label = label,
            labelSymbol = "",
            hint = "",
            click = click,
            sendBindings = sendBindings,
            keyTextSize = 0f,
            symbolTextSize = 0f,
            keyTextOffsetX = 0f,
            keyTextOffsetY = 0f,
            keySymbolOffsetX = 0f,
            keySymbolOffsetY = 0f,
            keyHintOffsetX = 0f,
            keyHintOffsetY = 0f,
            keyPressOffsetX = 0,
            keyPressOffsetY = 0,
            keyTextColor = "",
            keyBackColor = "",
            keySymbolColor = "",
            hlKeyTextColor = "",
            hlKeyBackColor = "",
            hlKeySymbolColor = "",
            popup = emptyList(),
            behaviors = mapOf(KeyBehavior.CLICK to click)
        )
    }

    private fun createEmptyKey(): TextKey {
        return TextKey(
            width = 12.5f,
            height = 0f,
            roundCorner = 0f,
            label = " ",
            labelSymbol = "",
            hint = "",
            click = "",
            sendBindings = true,
            keyTextSize = 0f,
            symbolTextSize = 0f,
            keyTextOffsetX = 0f,
            keyTextOffsetY = 0f,
            keySymbolOffsetX = 0f,
            keySymbolOffsetY = 0f,
            keyHintOffsetX = 0f,
            keyHintOffsetY = 0f,
            keyPressOffsetX = 0,
            keyPressOffsetY = 0,
            keyTextColor = "",
            keyBackColor = "",
            keySymbolColor = "",
            hlKeyTextColor = "",
            hlKeyBackColor = "",
            hlKeySymbolColor = "",
            popup = emptyList(),
            behaviors = emptyMap()
        )
    }

    private fun createInitialLabelKey(initial: String): TextKey {
        val click = "{command: keyboard, option: shengyun_initials}"
        return TextKey(
            width = 12.5f,
            height = 0f,
            roundCorner = 0f,
            label = initial,
            labelSymbol = "",
            hint = "",
            click = click,
            sendBindings = true,
            keyTextSize = 0f,
            symbolTextSize = 0f,
            keyTextOffsetX = 0f,
            keyTextOffsetY = 0f,
            keySymbolOffsetX = 0f,
            keySymbolOffsetY = 0f,
            keyHintOffsetX = 0f,
            keyHintOffsetY = 0f,
            keyPressOffsetX = 0,
            keyPressOffsetY = 0,
            keyTextColor = "",
            keyBackColor = "",
            keySymbolColor = "",
            hlKeyTextColor = "",
            hlKeyBackColor = "",
            hlKeySymbolColor = "",
            popup = emptyList(),
            behaviors = mapOf(KeyBehavior.CLICK to click)
        )
    }

    private fun createFinalKey(
        final: String,
        initial: String
    ): TextKey {
        val pinyin = if (initial == "zero") final else initial + final
        val click = "{commit: $pinyin, command: keyboard, option: shengyun_initials}"
        
        return TextKey(
            width = 12.5f,
            height = 0f,
            roundCorner = 0f,
            label = final,
            labelSymbol = "",
            hint = "",
            click = click,
            sendBindings = true,
            keyTextSize = 0f,
            symbolTextSize = 0f,
            keyTextOffsetX = 0f,
            keyTextOffsetY = 0f,
            keySymbolOffsetX = 0f,
            keySymbolOffsetY = 0f,
            keyHintOffsetX = 0f,
            keyHintOffsetY = 0f,
            keyPressOffsetX = 0,
            keyPressOffsetY = 0,
            keyTextColor = "",
            keyBackColor = "",
            keySymbolColor = "",
            hlKeyTextColor = "",
            hlKeyBackColor = "",
            hlKeySymbolColor = "",
            popup = emptyList(),
            behaviors = mapOf(KeyBehavior.CLICK to click)
        )
    }
}

