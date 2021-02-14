package com.vancerver.targeting;

import com.vancerver.targeting.model.Campaign;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CampaignServiceTest {

    @Test
    void startApplication() {
        try {
            List<Campaign> campaigns = CampaignService.loadCampaignsFile(new String[]{"campaign.txt"});

            // Testing with blank input should return no campaign
            assertThat(CampaignService.determineBestCampaignForUserSegments(new String[]{}, campaigns)).isEqualTo("no campaign");

            // Testing with one segment that is not in a campaign should return no campaign
            assertThat(CampaignService.determineBestCampaignForUserSegments(new String[]{"66856"}, campaigns)).isEqualTo("no campaign");

            // Targeting a specific campaign should return that campaign
            assertThat(CampaignService.determineBestCampaignForUserSegments(
                    new String[]{"12777", "15288", "11953", "21980", "13062", "20612", "19641"}, campaigns)).isEqualTo("facto");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
