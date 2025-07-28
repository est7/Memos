package app.xat.memos.shared

import app.cash.sqldelight.db.SqlDriver

/**
 *
 * @author: est8
 * @date: 7/24/25
 */
expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}