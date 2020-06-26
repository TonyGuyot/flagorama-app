# Architecture

This app uses the MVVM architecture with the repository pattern. It is derived from the
architecture described in [Guide to app architecture](https://developer.android.com/jetpack/guide).

![MVVM](./images/mvvm.png "MVVM architecture")

* the **view** component is represented by a Fragment
* the **view model** component is represented by an instance of `ViewModel`
* the **model** component is the repository

![Repository](./images/repository.png "Repository pattern")

The **Repository** class is the single entry-point to the data system.
