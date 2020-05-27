package hoangpoly.vn.nc_lab6;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DiaDiem {
    @PrimaryKey
    @NonNull
    public String maDiaDiem;

    @ColumnInfo(name = "kinhDo")
    public Double kinhDo;

    @ColumnInfo(name = "viDo")
    public Double viDo;

    @ColumnInfo(name = "tenDiaDiem")
    public String tenDiaDiem;


    public DiaDiem(){

    }
}
