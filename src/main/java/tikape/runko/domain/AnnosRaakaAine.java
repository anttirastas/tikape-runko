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
    private Integer raakaAineId;
    private Integer jarjestys;
    private String maara;
    private String ohje;

    public AnnosRaakaAine(Integer id, Integer annosId, Integer raakaAineId, Integer jarjestys, String maara, String ohje) {
        this.id = id;
        this.annosId = annosId;
        this.raakaAineId = raakaAineId;
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

    public Integer getRaakaAineId() {
        return raakaAineId;
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
