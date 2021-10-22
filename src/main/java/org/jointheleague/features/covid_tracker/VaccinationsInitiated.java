
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class VaccinationsInitiated {

    @SerializedName("sources")
    @Expose
    private List<Source__10> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__10> anomalies = null;

    public List<Source__10> getSources() {
        return sources;
    }

    public void setSources(List<Source__10> sources) {
        this.sources = sources;
    }

    public List<Anomaly__10> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__10> anomalies) {
        this.anomalies = anomalies;
    }

}