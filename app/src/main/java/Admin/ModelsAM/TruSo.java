package Admin.ModelsAM;

public class TruSo {

    String id;
    float KinhDo;
    float ViDo;
    String ThongTin;

    public TruSo(String id, float kinhDo, float viDo, String thongTin) {
        this.id = id;
        KinhDo = kinhDo;
        ViDo = viDo;
        ThongTin = thongTin;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKinhDo(float kinhDo) {
        KinhDo = kinhDo;
    }

    public void setViDo(float viDo) {
        ViDo = viDo;
    }

    public void setThongTin(String thongTin) {
        ThongTin = thongTin;
    }

    public String getId() {
        return id;
    }

    public float getKinhDo() {
        return KinhDo;
    }

    public float getViDo() {
        return ViDo;
    }

    public String getThongTin() {
        return ThongTin;
    }
}
