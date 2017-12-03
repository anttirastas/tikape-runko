/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author antti
 */
public class AnnosRaakaAine implements Comparable<AnnosRaakaAine> {
    
    private Integer id;
    private Integer annosId;
    private String annosNimi;
    private Integer raakaAineId;
    private String raakaAineNimi;
    private Integer jarjestys;
    private String maara;
    private String ohje;

    public AnnosRaakaAine(Integer id, Integer annosId, String annosNimi, Integer raakaAineId, String raakaAineNimi, Integer jarjestys, String maara, String ohje) {
        this.id = id;
        this.annosId = annosId;
        this.annosNimi = annosNimi;
        this.raakaAineId = raakaAineId;
        this.raakaAineNimi = raakaAineNimi;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAnnosId() {
        return annosId;
    }

    public String getAnnosNimi() {
        return annosNimi;
    }

    public Integer getRaakaAineId() {
        return raakaAineId;
    }

    public String getRaakaAineNimi() {
        return raakaAineNimi;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public String getMaara() {
        return maara;
    }

    public String getOhje() {
        return ohje;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAnnosId(Integer annosId) {
        this.annosId = annosId;
    }

    public void setRaakaAineId(Integer raakaAineId) {
        this.raakaAineId = raakaAineId;
    }

    public void setJarjestys(Integer jarjestys) {
        this.jarjestys = jarjestys;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }
    
    public int compareTo(AnnosRaakaAine a) {
        return this.jarjestys - a.getJarjestys();
    }
    
}
