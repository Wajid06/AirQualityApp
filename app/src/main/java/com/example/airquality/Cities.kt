package com.example.airquality

data class City(val name: String, val latitude: Double, val longitude: Double)

val cities = listOf(
    City("London", 51.5072, -0.1276),
    City("Manchester", 53.4808, -2.2426),
    City("Edinburgh", 55.9533, -3.1883),
    City("Cardiff", 51.4816, -3.1791),
    City("Belfast", 54.5973, -5.9301),
    City("Birmingham", 52.4862, -1.89),
    City(name="Glasgow", latitude=55.8642, longitude=-4.2518),
    City(name="Leeds", latitude=53.8008, longitude=-1.5491),
    City(name="Sheffield", latitude=53.3863, longitude=-1.4737),
    City(name="Liverpool", latitude=53.4084, longitude=-2.9916),
    City(name="Bristol", latitude=51.4545, longitude=-2.5879),
    City(name="Newcastle", latitude=54.9783, longitude=-1.6178),
    City(name="Nottingham", latitude=52.9575, longitude=-1.1553),
    City(name="Aberdeen", latitude=52.6376, longitude=-1.1398),


)