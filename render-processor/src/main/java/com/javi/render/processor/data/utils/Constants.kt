package com.javi.render.processor.data.utils

const val PACKAGE_ROOT = "com.dynamic"
const val PACKAGE_FACTORIES = "$PACKAGE_ROOT.factory"
const val PACKAGE_DI = "$PACKAGE_ROOT.di"

const val PACKAGE_HILT_SINGLETON_COMPONENT = "dagger.hilt.components.SingletonComponent"

const val PARENT_MODEL_FILE_NAME = "ComponentModel"
const val DI_MODULE_FILE_NAME = "RenderModule"

const val PROP_PARENT_MODEL_RENDER_NAME = "render"
const val PROP_PARENT_MODEL_INDEX_NAME = "index"
const val PROP_PARENT_MODEL_RESOURCE_NAME = "resource"

const val PACKAGE_MOSHI = "com.squareup.moshi"
const val PACKAGE_PARENT_MODEL = "$PACKAGE_FACTORIES.$PARENT_MODEL_FILE_NAME"


const val PARENT_MODEL_COMMENT = "This class is a wrapper from a data class to a " +
        "response data structure like ComponentModel. Each ...ParentModel resolves the data type of the resource."
const val DI_MODULE_COMMENT = "This class is a Hilt module that provides" +
        "\nthe Moshi instance and settings for all parent models."