package Admin.ModelsAM;

import android.text.TextUtils;
import android.util.Patterns;

public class TaiKhoanAdmin {

    String id ;
    String TenTK ;
    String MaQuanTri ;
    String MatKhau ;

    public TaiKhoanAdmin(String id, String tenTK, String maQuanTri, String matKhau) {
        this.id = id;
        TenTK = tenTK;
        MaQuanTri = maQuanTri;
        MatKhau = matKhau;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTenTK(String tenTK) {
        TenTK = tenTK;
    }

    public void setMaQuanTri(String maQuanTri) {
        MaQuanTri = maQuanTri;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getId() {
        return id;
    }

    public String getTenTK() {
        return TenTK;
    }

    public String getMaQuanTri() {
        return MaQuanTri;
    }

    public String getMatKhau() {
        return MatKhau;
    }


    public Boolean isValidPassword(){
        return !TextUtils.isEmpty(MatKhau)&& MatKhau.length() >= 6;
    }
    public Boolean isValidUsename(){
        return !TextUtils.isEmpty(TenTK) && TenTK.length() >= 4;
    }
}
