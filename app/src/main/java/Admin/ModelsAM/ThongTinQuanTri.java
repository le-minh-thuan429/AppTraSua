package Admin.ModelsAM;

public class ThongTinQuanTri {
    public ThongTinQuanTri(String maQT, String hoTen, String soDienThoai, String email, byte[] anh, String id) {
        MaQT = maQT;
        HoTen = hoTen;
        SoDienThoai = soDienThoai;
        Email = email;
        Anh = anh;
        this.id = id;
    }

    String MaQT;
    String HoTen;
    String SoDienThoai;
    String Email;
    private byte[] Anh;

    public void setMaQT(String maQT) {
        MaQT = maQT;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setAnh(byte[] anh) {
        Anh = anh;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaQT() {
        return MaQT;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public byte[] getAnh() {
        return Anh;
    }

    public String getId() {
        return id;
    }

    String id;
}
