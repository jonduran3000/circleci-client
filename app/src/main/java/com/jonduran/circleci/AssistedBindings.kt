package com.jonduran.circleci

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_AssistedBindings::class])
interface AssistedBindings