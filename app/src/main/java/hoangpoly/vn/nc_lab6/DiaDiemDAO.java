package hoangpoly.vn.nc_lab6;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DiaDiemDAO {
    @Query("SELECT * FROM diadiem")
    List<DiaDiem> getDiaDiem();

    @Query("SELECT * FROM diadiem WHERE maDiaDiem= :ma")
    List<DiaDiem> getDiaDiemTheoMa(String ma);

    @Insert()
    long[] insertDiaDiem(DiaDiem... diaDiems);

    @Delete()
    int deleteDiaDiem(DiaDiem diaDiem);

    @Update()
    int updateDiaDiem(DiaDiem diaDiem);

}
