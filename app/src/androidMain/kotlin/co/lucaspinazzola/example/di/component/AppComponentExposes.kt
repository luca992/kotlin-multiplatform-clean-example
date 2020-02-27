package co.lucaspinazzola.example.di.component

import co.lucaspinazzola.example.di.module.AppModule
import co.lucaspinazzola.example.di.module.DataModule

interface AppComponentExposes : DataModule.Exposes, AppModule.Exposes
