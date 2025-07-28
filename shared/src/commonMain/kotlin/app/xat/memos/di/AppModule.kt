package app.xat.memos.di

import app.xat.memos.data.repository.SubscriptionRepositoryImpl
import app.xat.memos.domain.repository.SubscriptionRepository
import app.xat.memos.shared.AppDatabase
import app.xat.memos.shared.DatabaseDriverFactory
import org.koin.dsl.module

val appModule = module {

    // Database
    single<AppDatabase> {
        AppDatabase(get<DatabaseDriverFactory>().create())
    }

    // Repository
    single<SubscriptionRepository> {
        SubscriptionRepositoryImpl(get())
    }


}