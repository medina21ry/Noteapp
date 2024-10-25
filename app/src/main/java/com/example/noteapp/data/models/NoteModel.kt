
import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteModel")
data class NoteModel(
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val color: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
