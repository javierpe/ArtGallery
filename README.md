 # Art Gallery with Server Driven UI
 [![version](https://img.shields.io/badge/version-1.0.2-yellow.svg)](https://semver.org)

 ![row](https://user-images.githubusercontent.com/7097754/169336238-335db542-3178-4834-951d-0eb7d0cbbfac.png)

 ## Pending tasks

 - [x] Support multiple screens
 - [x] Improve tooltips visualization
 - [x] Compose navigation
 - [x] Render process mechanism
 - [x] Package refactor and naming
 - [x] Unit test
 - [x] Macrobenchmark test
 - [x] Android CI
 - [x] KSP processor to avoid boilerplate code

 Art Gallery is based on Server Driven UI, only provide a JSON with a UI definition.

 > The SDUI allows you send new features and create
 > reusable componentes as possible. 

 ### JSON Definition

 Main structure:
 ```json
 {
     "header": [...]
     "body": [...]
 }
 ```

 To add new componente only follow this definition:
 ```json
 {
     "body": [
         {
             "render": "component_name",
             "index": 0,
             "resource": {
                 ... A model definition for this component.
             }
         }
     ]
 }
 ```

### Magic annotations

With KSP we extend the functionality to annotations to avoid boilerplate code. Only follow this:

1. Your data class should have @RenderClass annotation and provide it the render type like  RenderType.BANNER or whatever you need.

2. If you want transform that model when the resource is available you can add a render factory class that extends of DynamicListRender<BannerCarouselModel> and provide @RenderFactory annotation with a model class as parameter like BannerCarouselModel::class. This process transform your model before UI use it.

3. Finaly, you should have a factory that will be used to create the UI for that single component, should extends of DynamicListFactory and provide @AdapterFactory annotation.

Note: _ComponentModel_ is a data class that contains your element definition, _render_, _index_ and _resource_

 And thats it...

 ### Skeletons
 <img src="https://user-images.githubusercontent.com/7097754/185690462-8ce16cc5-7437-4432-b160-be3414e7ad24.png" width="342.5" height="633.5"/>

 ### Home screen
 <img src="https://user-images.githubusercontent.com/7097754/185686746-88c7f7ac-e8f4-47e5-b464-c3408183cd96.png" width="342.5" height="633.5"/>

 ### Tooltips
 <img src="https://user-images.githubusercontent.com/7097754/185691070-6f950308-a677-49cb-b8db-50751c8fe086.png" width="342.5" height="633.5"/>

 ### Tooltip sequence
 <img src="https://user-images.githubusercontent.com/7097754/185699798-47430b25-2f9f-4a12-ae0f-29d59339afff.mp4" width="360" height="780"/>

 ### MotionLayout
 <img src="https://user-images.githubusercontent.com/7097754/185695027-092f14e5-0236-48e2-99c1-6877f418d1c7.mp4" width="360" height="780"/>

 ### Navigation
 <img src="https://user-images.githubusercontent.com/7097754/185700856-c89c81a5-2880-4e3e-b75d-743c55291a8e.mp4" width="360" height="780"/>

 ### Scroll actions
 <img src="https://user-images.githubusercontent.com/7097754/185701348-b80f92ae-01e7-4381-9c6b-7a7d30aa9617.mp4" width="360" height="780"/>

 ## Libraries used

 - [Compose navigation](https://developer.android.com/jetpack/compose/navigation?hl=es-419) - Recommended for Google to navigate between compose screens
 - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419) - Dependency injection
 - [Coil](https://coil-kt.github.io/coil/compose/) - Image loader for compose
 - [Lottie](https://github.com/airbnb/lottie/blob/master/android-compose.md) - Lottie animations for compose
 - [Gson](https://github.com/google/gson) - Serialization/Deserializaton JSON to convert Java Objects into JSON and back
 - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?hl=es-419) - Modern Android preferences
 - [Room](https://developer.android.com/jetpack/androidx/releases/room?gclid=CjwKCAjw6fyXBhBgEiwAhhiZsjAF2biSAQEU8zfC58pfv7u2Z-B6Hbysd4PlQtYZH_KZSvWyMRhd3BoCIV8QAvD_BwE&gclsrc=aw.ds) - Data persistence
 - [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAjw6fyXBhBgEiwAhhiZshbizxlJ4fvLaIjjt3SZerY3SnmCgygwltc7iBUlIApiwcC7IHmEexoC7PsQAvD_BwE&gclsrc=aw.ds) - Modern Android UI development
