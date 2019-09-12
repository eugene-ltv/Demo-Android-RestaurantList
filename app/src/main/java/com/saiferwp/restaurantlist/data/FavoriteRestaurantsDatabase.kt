package com.saiferwp.restaurantlist.data

import androidx.room.*

@Entity
data class FavoriteRestaurant(
    @PrimaryKey val name: String
)

@Dao
interface FavoriteRestaurantDao {
    @Query("SELECT * FROM FavoriteRestaurant")
    fun getAll(): List<FavoriteRestaurant>

    @Insert
    fun insertAll(vararg users: FavoriteRestaurant)

    @Delete
    fun delete(user: FavoriteRestaurant)
}

@Database(entities = [FavoriteRestaurant::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteRestaurantDao(): FavoriteRestaurantDao
}