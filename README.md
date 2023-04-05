 # Server Driven UI with Jetpack Compose   [![version](https://img.shields.io/badge/version-1.1.0-yellow.svg)](https://semver.org)
 
![Artboard](https://user-images.githubusercontent.com/7097754/216787352-2ab8fa54-62de-411c-84d5-765f9c87136f.png)

 ## Features

 - [x] Tooltips
 - [x] Compose navigation by
 - [x] Macrobenchmark test
 - [x] KSP processor
 
 Art Gallery is based on Server Driven UI, only provide a JSON and UI definition.

 > The SDUI allows you send new features and create
 > reusable componentes as possible. 

 ### Definition

1. Each JSON component must follow the following definition:
   1. `render` is an enum of RenderType and is the record of each component in the list.
   2. `index` is the component's position in the list and must not be duplicated.
   3. `resource` is a JSON object and therefore can be converted to some data class.

![Definition](https://user-images.githubusercontent.com/7097754/229964052-a2069a24-b386-42e9-a666-5a0e3a3eb7aa.png)

2. You must create a model capable of containing all these components, for example `DataContentModel`, here the header and body of our JSON are specified.
![Definition](https://user-images.githubusercontent.com/7097754/229964357-668ff6fd-afae-4983-8ac7-7d7db1760454.png)
![DataContentModel](https://user-images.githubusercontent.com/7097754/229964225-e305f830-2530-42ed-8768-133a5af2a5bb.png)

### Do it!

To create a new UI component you must follow the following steps:

1. In the RenderType class you must register the name of the new component
2. Create a model specific to the `resource` you need to convert
3. Add the `@RenderClass` annotation to the model and pass the new render type you created as a parameter

![RenderClass](https://user-images.githubusercontent.com/7097754/229964442-857246bc-6cd4-4dbf-b369-5a552d0c4f6f.png)

4. Create a render factory class that extends `DynamicListFactory` and that will take care of managing the component
   1. The class asks for a list of compatible renders, add the one you created.
   2. If that component has an onboarding tooltip then add `hasShowCaseConfigured = true`
   3. In the `CreateComponent` function add your composable element
   4. In the `CreateSkeleton` function create a plain copy of your composable element, this is a skeleton!

5. On the main screen add the `ContextView` component as follows
   1. `title` is the title of the screen
   2. `viewModel` you must create a ViewModel that extends from `ContextViewModel` that contains the screen information
   3. `destinationsNavigator` is a navigation instance
      1. `dynamicListRequestModel` here goes a `DynamicListRequestModel` object or via `rememberDynamicListRequestState`

![ContextView](https://user-images.githubusercontent.com/7097754/229964545-78096ce6-b286-4bbe-98e7-56a7c8e0aab0.png)

 ## Used libraries

 - [Compose Destinations](https://github.com/raamcosta/compose-destinations) - By @raamcosta es una gran librería para navegar en compose
 - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419) - Dependency injection
 - [Coil](https://coil-kt.github.io/coil/compose/) - Image loader for compose
 - [Lottie](https://github.com/airbnb/lottie/blob/master/android-compose.md) - Lottie animations for compose
 - [Moshi](https://github.com/square/moshi) - JSON Serialization/Deserializaton
 - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?hl=es-419) - Modern Android preferences
 - [Room](https://developer.android.com/jetpack/androidx/releases/room?gclid=CjwKCAjw6fyXBhBgEiwAhhiZsjAF2biSAQEU8zfC58pfv7u2Z-B6Hbysd4PlQtYZH_KZSvWyMRhd3BoCIV8QAvD_BwE&gclsrc=aw.ds) - Data persistence
 - [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAjw6fyXBhBgEiwAhhiZshbizxlJ4fvLaIjjt3SZerY3SnmCgygwltc7iBUlIApiwcC7IHmEexoC7PsQAvD_BwE&gclsrc=aw.ds) - Modern Android UI development
