package u.icomp.mycountries.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Array;

public class Country implements Serializable {



    @SerializedName("name")
    public String name;

    @SerializedName("capital")
    public String capital;

    @SerializedName("region")
    public String region;

    @SerializedName("subregion")
    public String subregion;

 //   @SerializedName("latitude")
 //   public String latitude;

 //   @SerializedName("longitude")
  //  public String longitude;

//    @SerializedName("latlng")
//    public Array latlng;

  /*  public Country(long id, String name, String capital, String region, String subregion, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subregion = subregion;
        this.latitude =  latitude;
        this.longitude = longitude;
    }
*/
  /*  public Country(long id, String name, String capital, String region, String subregion, Array latlng) {
        this.id = id;
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subregion = subregion;
        this.latlng =  latlng;
    } */

    public Country(String name, String capital, String region, String subregion) {

        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subregion = subregion;
    }

    @Override
    public String toString() {
        return "Country{" +
                " name='" + name + '\'' +
                " capital='" + capital + '\'' +
                '}';
    }



    public String getName() {
        return name;
    }
    public String getCapital() {
        return capital;
    }
    public String getRegion() {
        return region;
    }
    public String getSubregion() {
        return subregion;
    }


}
