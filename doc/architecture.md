# Architecture

## References

This app uses the MVVM architecture with the repository pattern. It is an extended work from the
architecture described in [Guide to app architecture](https://developer.android.com/jetpack/guide).
It uses also some ideas from [Eli-Fox, LEGO-Catalog](https://github.com/Eli-Fox/LEGO-Catalog).

## About model entities

Usually examples of Android app architectures are mixing everything in the model entities. This is a
bad practice: database or JSON specific annotations are then carried in the whole application.
Worse some fields exist only because they have some implementation purpose for the database for
example. Those fields shouldn't be seen by the UI.

In Flagorama, the model entities are pure business objects: they do not carry any implementation or
storage/access information. Both the database and the network access have their own entities with
all the required annotations and sometimes specific fields. Mapper classes provide the necessary
conversions between the different types of entities.

![Model](./images/model.png "Model classes")

The pure data class is a Kotlin data class and contains only type declaration that are useful in
the business logic of the application:

```kotlin
data class Country(val code: Alpha3Code, val name: String, val flagUrl: String)
```

The corresponding network access data class contains information that is relevant only to the JSON
parser:

```kotlin
data class RestCountry(
    @field:SerializedName("alpha3Code") val code: Alpha3Code,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("flag") val flagUrl: String
)
```

The corresponding database data class contains information that is relevant only to the database
parser:

```kotlin
@Entity
data class CountryEntity(
    @PrimaryKey val code: Alpha3Code,
    @ColumnInfo(name = "region_code") val regionCode: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "flag_url") val flagUrl: String
)
```

## General overview

![MVVM](./images/mvvm.png "MVVM architecture")

Fragment
:  * **View** part of the MVVM architecture
   * handle only the UI
   * has no logic

ViewModel
:  * **ViewModel** part of the MVVM architecture
   * is an instance of `androidx.lifecycle.ViewModel.ViewModel`
   * performs the binding between the UI and the source of data
   * transforms raw data to make it suitable for display, if necessary
   
Repository
:  * **Model** part of the MVVM architecture
   * is a singleton
   * provides a unique entry point to all data access
   * abstracts several possible sources of data
   * defines a data workflow

![Repository](./images/repository.png "Repository pattern")

Classes in blue are always specific to the application. Classes in orange are utility classes that
are independent of the application.

Repository
:  The single entry-point to the data layer. It uses several data sources coupled to a strategy.

LocalDataSource
:  Encapsulates the access to the database. Provides conversion between database entities and
business logic objects (with the `Mapper` inner class). Provides error management.

DAO (Data Access Object)
:  This is an interface defining the requests to be performed on the database. Manipulates only
database entities.

Database
:  Generates the implementation classes from the DAOs to enable access to the database.

RemoteDataSource
:  Encapsulates the access to the network service. Provides conversion between communication
entities and business logic objects (with the `Mapper` inner class).

BaseRemoteDataSource
:  Abstract class. Provides generic HTTP handling.

Service
:  This is an interface defining the queries to be performed over the network. Manipulates only
communication entities.

Strategy
:  Defines the type of workflow used. For example: database first strategy.

## Implementation in Flagorama

Flagorama has three screens using this architecture:

![Screens](./images/screens.png "Screen workflow")

### Home screen

The home screen provides a list of world regions (continents) to select from. This is the simplest 
screen: since the list is fixed, there is no data retrieval here and therefore no repository. A view
model is used nonetheless as it provides a simple example.

![Home MVVM](./images/mvvm-home.png "MVVM architecture for Home screen")

* The view model (HomeViewModel) exports the data (the list of regions) using **LiveData**.
* The UI (HomeFragment) observes this data and uses it to populate a list.

### Region screen

The region screen displays the list of countries and their flags corresponding to the region
selected in the previous screen.

![Region MVVM](./images/mvvm-region.png "MVVM architecture for Region screen")

![Region repository](./images/repository-region.png "Repository for Region screen")

### Country screen

The country screen displays details about the country selected in the previous screen.

![Country MVVM](./images/mvvm-country.png "MVVM architecture for Country screen")

![Country repository](./images/repository-country.png "Repository for Country screen")
