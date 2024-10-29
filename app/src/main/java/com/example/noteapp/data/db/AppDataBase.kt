
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteModel::class], version = 6)
abstract class AppDataBase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}