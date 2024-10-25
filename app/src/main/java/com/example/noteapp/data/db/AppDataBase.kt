
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteModel::class], version = 3)
abstract class AppDataBase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}