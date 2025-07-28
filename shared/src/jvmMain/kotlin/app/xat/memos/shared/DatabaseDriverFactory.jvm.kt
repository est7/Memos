package app.xat.memos.shared

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.Properties

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return JdbcSqliteDriver(
            JdbcSqliteDriver.IN_MEMORY,
            Properties().apply { put("foreign_keys", "true") }
        ).apply {
            AppDatabase.Schema.create(this)
        }
    }
}