package co.lucaspinazzola.example.di

import co.lucaspinazzola.example.di.modules.AppModule
import co.lucaspinazzola.example.di.modules.DataModule

interface AppComponentExposes : DataModule.Exposes, AppModule.Exposes
