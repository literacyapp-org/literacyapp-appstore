package ai.elimu.appstore.model.appsynchronization;

import com.google.gson.annotations.SerializedName;

public class AppStatusModel {

    @SerializedName("applicationStatus")
    private String applicationStatus;

    @SerializedName("numeracySkills")
    private String[] numeracySkills;

    @SerializedName("packageName")
    private String packageName;

    @SerializedName("id")
    private long applicationId;

    @SerializedName("locale")
    private String locale;

    @SerializedName("literacySkills")
    private String[] literacySkills;

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public String[] getNumeracySkills() {
        return numeracySkills;
    }

    public String getPackageName() {
        return packageName;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public String getLocale() {
        return locale;
    }

    public String[] getLiteracySkills() {
        return literacySkills;
    }
}
