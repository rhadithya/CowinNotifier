package io.adi.cowinnotifier;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.adi.cowinnotifier.models.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CowinRestClient {
    static Logger logger = LoggerFactory.getLogger(CowinRestClient.class);

    private DataStore dataStore;
    private static String base_uri = "https://cdn-api.co-vin.in/api";

    CowinRestClient(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public List<VaccineSearchResult> checkAvailabilityByStateAndDistrict(String stateName, String districtName, Date date, int age, boolean searchAll) {
        State state = getState(stateName);
        District district = getDistrict(state.getStateId(), districtName);
        return checkAvailability(district.getDistrictId(), date, age, searchAll);
    }

    private List<VaccineSearchResult> checkAvailability(int districtId, Date date, int age, boolean searchAll) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String uri = base_uri + "/v2/appointment/sessions/public/calendarByDistrict" +
                "?district_id=" + districtId + "&date=" + dateFormat.format(date);

        HttpGet httpget = new HttpGet(uri);
        httpget.addHeader("User-Agent", "CowinNotifier");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        List<VaccineSearchResult> result = new ArrayList<>();
        try {
            response = httpclient.execute(httpget);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            Root root = objectMapper.readValue(response.getEntity().getContent(), Root.class);
            if (root != null) {
                List<Center> centers = root.getCenters();
                for(Center center: centers) {
                    List<Session> sessions = center.getSessions();
                    for (Session session: sessions) {
                        if (searchAll) {
                            result.add(new VaccineSearchResult(center, session));
                        } else if (session.getMinAgeLimit() >= age && session.getAvailableCapacity() > 0) {
                            result.add(new VaccineSearchResult(center, session));
                        }
                    }
                }
            }
            response.close();
        } catch (IOException e) {
            logger.error("Error in checkAvailability districtId={} date={} age={} searchAll={}", districtId, date, age, searchAll, e);
        }
        return result;
    }

    private State getState(String stateName) {
        String uri = base_uri + "/v2/admin/location/states";

        HttpGet httpget = new HttpGet(uri);
        httpget.addHeader("User-Agent", "CowinNotifier");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        State result = null;
        try {
            response = httpclient.execute(httpget);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            StateRoot root = objectMapper.readValue(response.getEntity().getContent(), StateRoot.class);
            if (root != null) {
                List<State> states = root.getStates();
                for(State state: states) {
                    if (state.getStateName().equals(stateName))
                        result = state;
                }
            }
            response.close();
        } catch (IOException e) {
            logger.error("Error in getState stateName={}", stateName, e);
        }
        return result;
    }

    private District getDistrict(int stateId, String districtName) {
        String uri = base_uri + "/v2/admin/location/districts/" + stateId;

        HttpGet httpget = new HttpGet(uri);
        httpget.addHeader("User-Agent", "CowinNotifier");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        District result = null;
        try {
            response = httpclient.execute(httpget);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            DistrictRoot root = objectMapper.readValue(response.getEntity().getContent(), DistrictRoot.class);
            if (root != null) {
                List<District> districts = root.getDistricts();
                for(District district: districts) {
                    if (district.getDistrictName().equals(districtName))
                        result = district;
                }
            }
            response.close();
        } catch (IOException e) {
            logger.error("Error in getDistrict stateId={} districtName", stateId, districtName, e);
        }
        return result;
    }
}
