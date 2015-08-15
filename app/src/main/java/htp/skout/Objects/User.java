package htp.skout.Objects;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Matthew on 8/15/2015.
 */
public class User {
    private String userName;
    private LatLng location;
    private Bitmap userAvatar = null;

    public User(String userName){this.userName = userName; }

    public void setAvatar(Bitmap bitmap){ this.userAvatar = bitmap; }

    public Bitmap getUserAvatar(){ return userAvatar; }

    public String getUserName(){ return userName; }

    public LatLng getLocation(){ return location; }

    public void setLocation(LatLng coordinates) { location = coordinates; }
}
