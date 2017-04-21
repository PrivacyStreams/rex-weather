package mu.node.rexweather.app.Services;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import com.github.privacystreams.core.Callback;
import com.github.privacystreams.core.UQI;
import com.github.privacystreams.core.purposes.Purpose;
import com.github.privacystreams.location.Geolocation;
import com.github.privacystreams.location.LatLng;

import rx.Observable;
import rx.Subscriber;

/**
 * Implement an Rx-style location service by wrapping the Android LocationManager and providing
 * the location result as an Observable.
 */
public class LocationService {
//    private final LocationManager mLocationManager;
    private final Context mContext;

    public LocationService(Context context) {
        mContext = context;
    }

    public Observable<LatLng> getLocation() {
        return Observable.create(new Observable.OnSubscribe<LatLng>() {
            @Override
            public void call(final Subscriber<? super LatLng> subscriber) {
                UQI uqi = new UQI(mContext);
                uqi.getData(Geolocation.asCurrent(Geolocation.LEVEL_CITY), Purpose.UTILITY("check weather"))
                        .ifPresent("lat_lng", new Callback<LatLng>() {
                            @Override
                            protected void onInput(LatLng input) {
                                subscriber.onNext(input);
                                subscriber.onCompleted();
                            }
                        });
            }
        });
    }
}