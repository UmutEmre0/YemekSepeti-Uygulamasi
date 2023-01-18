package yemeksepeti;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class kullanici_islemleri {

    public String ad;
    public String soyad;
    public String email;
    public String sifre;
    public String icerik_yazdir;
    public float fiyat_yazdir;

    private Connection con = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    public void UyeEkle(String ad, String soyad, String email, String sifre) {

        String sorgu = "Insert Into kullanicilar (ad,soyad,email,sifre) VALUES(?,?,?,?)";

        try {
            preparedStatement = con.prepareStatement(sorgu);

            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, sifre);

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(kullanici_islemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sepetEkle(String icerik, float fiyat) {

        String sorgu = "Insert Into sepet (icerik,fiyat) VALUES(?,?)";

        try {
            preparedStatement = con.prepareStatement(sorgu);

            preparedStatement.setString(1, icerik);
            preparedStatement.setFloat(2, fiyat);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(kullanici_islemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<sepet_bilgileri> sepetteGoster() {

        ArrayList<sepet_bilgileri> cikti = new ArrayList<sepet_bilgileri>();

        try {

            statement = con.createStatement();
            String sorgu = "SELECT * FROM sepet ";
            ResultSet rs = statement.executeQuery(sorgu);

            while (rs.next()) {
                icerik_yazdir = rs.getString("icerik");
                fiyat_yazdir = rs.getFloat("fiyat");
                cikti.add(new sepet_bilgileri(icerik_yazdir, fiyat_yazdir));

            }
            return cikti;

        } catch (SQLException ex) {
            Logger.getLogger(kullanici_islemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void sepetSil(){
        
        String sorgu = "Delete from sepet";
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(kullanici_islemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void bilgileriGoruntule() {

        giris_yap gy = new giris_yap();

        String sorgu = "SELECT * FROM kullanicilar WHERE email =" + "'" + gy.email + "'";
        try {

            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);

            while (rs.next()) {
                ad = rs.getString("ad");
                soyad = rs.getString("soyad");
                email = rs.getString("email");
                sifre = rs.getString("sifre");
            }

        } catch (SQLException ex) {
            Logger.getLogger(kullanici_islemleri.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean girisYap(String email, String sifre) {

        String sorgu = "Select * From kullanicilar where email = ? and sifre = ?";
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, sifre);

            ResultSet rs = preparedStatement.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(kullanici_islemleri.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public kullanici_islemleri() {

        String url = "jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.db_ismi + "?useUnicode=true&characterEncoding=utf8";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException ex) {                  //1

            System.out.println("DRIVER BULUNAMADI...");
        }
        try {
            con = DriverManager.getConnection(url, Database.kullanici_adi, Database.parola);
            System.out.println("BAGLANTI BASARILI...");
        } catch (SQLException ex) {
            System.out.println("BAGLANTI BASARISIZ...");
            ex.printStackTrace();

        }
    }

}
