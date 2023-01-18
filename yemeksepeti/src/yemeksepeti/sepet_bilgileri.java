package yemeksepeti;

public class sepet_bilgileri {

    private String icerik;
    private float fiyat;

    public sepet_bilgileri(String icerik, float fiyat) {
        this.icerik = icerik;
        this.fiyat = fiyat;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public float getFiyat() {
        return fiyat;
    }

    public void setFiyat(float fiyat) {
        this.fiyat = fiyat;
    }

}
