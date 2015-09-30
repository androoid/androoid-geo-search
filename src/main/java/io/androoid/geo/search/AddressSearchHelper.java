package io.androoid.geo.search;

import java.io.IOException;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;
import org.osmdroid.util.GeoPoint;

import android.os.AsyncTask;
import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.model.Address;

/**
 *
 * This Utility class provides the necessary methods to obtain GeoPoint from an
 * existing address.
 *
 * This class will be invoked by some Activity that wants to obtain a GeoPoint
 * using a provided address.
 *
 * Uses Nominatim Java API to get GeoPoints from an existing address.
 *
 * @author Juan Carlos García
 * @since 1.0
 */
public class AddressSearchHelper extends AsyncTask<String, Integer, GeoPoint> {

	public AsyncGeoResponse delegate = null;
	private JsonNominatimClient nominatiumClient = new JsonNominatimClient(new DefaultHttpClient(),
			"geo-search-library@androoid.io");

	/**
	 * Method that returns GEO information about some address
	 *
	 * @param address
	 * @return
	 */
	@Override
	protected GeoPoint doInBackground(String... address) {
		try {
			List<Address> result = nominatiumClient.search(address[0]);

			for (Address resultAddress : result) {
				return new GeoPoint(resultAddress.getLatitude(), resultAddress.getLongitude());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(GeoPoint result) {
		delegate.processFinish(result);
	}
}