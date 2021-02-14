package com.vancerver.targeting.model;

import java.util.List;
public class Campaign {
    // The name of the campaign
    private String campaignName;
    private int[] segments;

    public Campaign(String campaignName, int... segments){
        this.campaignName = campaignName;
        this.segments = segments;
    }

    public int[] getSegments() {
        return segments;
    }

    public void setSegments(int[] segments) {
        this.segments = segments;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }
}
