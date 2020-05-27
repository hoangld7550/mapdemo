package hoangpoly.vn.nc_lab6;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = DiaDiem.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DiaDiemDAO diaDiemDAO();
}
