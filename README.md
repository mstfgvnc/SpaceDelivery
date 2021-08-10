# Space Delivery

Aim; To transport the space suits needed by the stations to the warehouses by traveling between stations on different planets.

Used Technologies: **Android, Kotlin, MVVM, Room, Navigation, Fragments, Data Binding, Retrofit, RxJava, LiveData, Shared Preferences, Dagger-Hilt, ViewPager2, RecyclerView, SearchView**


 ![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/1a.jpg?raw=true)  ![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/1b.jpg?raw=true) ![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/1c.jpg?raw=true)




## Create Spaceship

* The spaceship consists of 3 main features; Speed, Material Capacity and Endurance.
* When creating the spaceship, these three features must be distributed so that the sum of the three is 15 points, and any of the features cannot be zero. In addition, the name of the spaceship must be entered.

![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/2a.jpg?raw=true)   ![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/2b.jpg?raw=true)   ![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/2c.jpg?raw=true)

* With the points distributed, the spaceship features are generated as follows ;

Space Suits (SS) = Capacity * 10000

Universal Space Time (UST) = Speed * 20

Endurance Time ( ET ) = Endurance * 10000


* The spaceship name and properties are saved in the database with **SharedPreferences**. Thus, when the application is reopened or when the Create Spaceship screen is returned, the last saved Spaceship and its features are displayed.

* Values of properties are determined using **Seek Bar**. Seek Bars are set up to be adjusted between 1 and 13 values. Thus, a minimum and maximum value construct was created.




## Space Stations

* Space stations and their features are gotten from **API** with **Retrofit** and saved to **Room Databese** and transferred to **RecycylerView**.

![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/3a.jpg?raw=true)

* The desired station can be reached by searching with **SearchView**.

![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/3b.jpg?raw=true)

* Stations can be added or removed from favorites.
* The starting station is planet Earth.
* When you go to a station, the current location becomes that station. The distance to that station is subtracted from the spaceship 's ET and the number of supplies the station needs is deducted from the spaceship 's SS.
* In order to go to a station, there must be enough UST to go to that station and SS as much as the station needs.
* The available stations are renewed after each trip.


![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/3c.jpg?raw=true)

* Return to Earth when there are not enough UST or SS.
* ET indicates how long the spaceship will take damage over how many time periods. (For example, 10000 ET indicates that the spaceship will take damage every 10 seconds.)
* The power of the spaceship is 100. It decreases by 10 per ET period and returns to Earth when it drops to 0.


![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/3d.jpg?raw=true) ![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/3e.jpg?raw=true) ![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/3g.jpg?raw=true)
* The mission is completed successfully when all stations are visited and all needs are met.

![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/3f.jpg?raw=true)

##  Favorite Stations


* Favorite stations set in the Space Stations section are displayed in this section.
* Transition organization made with **ViewPager2**.
* The station's name, capacity and distance from Earth are displayed.
* The desired station can be removed from favorites.


![enter image description here](https://github.com/mstfgvnc/8afcfcf1e2316a76d8f9ca65fe6da51d/blob/master/ss/1c.jpg?raw=true)



