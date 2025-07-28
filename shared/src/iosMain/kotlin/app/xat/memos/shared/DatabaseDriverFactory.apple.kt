package app.xat.memos.shared

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.driver.native.wrapConnection
import app.xat.memos.DB_NAME
import co.touchlab.sqliter.DatabaseConfiguration

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        val driver = NativeSqliteDriver(
            DatabaseConfiguration(
                name = DB_NAME,
                version = AppDatabase.Schema.version.toInt(),
                create = { connection ->
                    wrapConnection(connection) { AppDatabase.Schema.create(it) }
                },
                upgrade = { connection, oldVersion, newVersion ->
                    wrapConnection(connection) {
                        AppDatabase.Schema.migrate(
                            it,
                            oldVersion.toLong(),
                            newVersion.toLong()
                        )
                    }
                },
                extendedConfig = DatabaseConfiguration.Extended(
                    foreignKeyConstraints = true
                )
            )
        )
        return driver
    }
}