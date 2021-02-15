package com.vancerver.targeting;

import com.vancerver.targeting.model.Campaign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Service
public class CampaignService {
    // Add a basic logger that logs to the console
    private static Logger LOG = LoggerFactory
            .getLogger(CampaignService.class);

    /**
     * Loads the campaigns file specified in args[0] and then starts user input loop.
     *
     * @param args program arguments from main method
     * @throws FileNotFoundException
     */
    public static void startApplication(String args[]) throws FileNotFoundException {
        startUserInputLoop(loadCampaignsFile(args));
    }

    /**
     * Reads from the file specified in args[0] to create a List of Campaign
     *
     * @param args program arguments from main method
     * @return List of Campaign loaded from the provided file
     * @throws FileNotFoundException
     */
    static List<Campaign> loadCampaignsFile(String args[]) throws FileNotFoundException {
        if(args.length < 1) {
            LOG.info("Error: Must provide file");
        }

        InputStream inputStream = null;
        List<Campaign> campaigns; // Hold List of Campaign obtained from reading file.
        try {
            inputStream = new FileInputStream(
                    new File(CampaignService.class.getClassLoader().getResource(args[0]).getFile()));
            campaigns = CampaignService.getCampaignListFromInputStream(inputStream);
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return campaigns;
    }

    /**
     * Does an infinite loop asking the user to provide a space separated list of segment numbers
     *
     * @param campaigns List of Campaigns loaded into the system
     */
    private static void startUserInputLoop(List<Campaign> campaigns){
        Scanner sc= new Scanner(System.in);    //System.in is a standard input stream

        // For now, just keep looping user input prompt forever
        while (true)
            LOG.info(CampaignService.determineBestCampaignForUserSegments(sc.nextLine().split(" "), campaigns));
    }

    /**
     * Determines the relevance of a campaign for a specific user.
     *
     * @param campaign Campaign object to calculate on
     * @param userSegments String array of segment numbers for the user.
     * @return int representing the relevance of this campaign for the user.
     */
    private static int calculateCampaignRelevance(Campaign campaign, String userSegments[]){
        int relevance = 0;

        // For every segment in the provided campaign, test if there is a match for this segment in the userSegments
        for(int segment : campaign.getSegments()){
            for(String userSegment : userSegments){
                if(segment == Integer.parseInt(userSegment)) ++relevance;
            }
        }
        return relevance;
    }

    /**
     * Crates a List of Campaign objects from the provided InputStream object.
     *
     * @param inputStream the InputStream for the loaded file
     * @return a List of Campaign read from the provided InputStream
     * @throws IOException
     */
    private static List<Campaign> getCampaignListFromInputStream(InputStream inputStream) {
        List<Campaign> campaigns = new LinkedList<>(); // The method result

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line; // Holds the current line being read

            while ((line = br.readLine()) != null) {
                // Turn campaignInfo into array of String by splitting on spaces.
                // Based on required file format, the first String in this array will be the campaign name and
                // subsequent String will be the segment numbers
                String campaignInfo[] = line.split(" ");

                // Hold an array of integers representing the segments of the campaign. The size of this is one less
                // than the number of words in the line because the first word is the title.
                int segments[] = new int[campaignInfo.length - 1];

                // Populate segments[] using data in campaignInfo[]
                for(int i = 1 ; i < campaignInfo.length; i++) segments[i - 1] = Integer.parseInt(campaignInfo[i]);

                campaigns.add(new Campaign(campaignInfo[0], segments));

            }
        } catch (IOException e){
            LOG.error("Error with format of provided Campaign");
        }
        return campaigns;
    }

    /**
     * Determines which campaign is best given an input of segments.
     *
     * @param segments Array of Strings, each representing a segment number.
     * @param campaigns List of Campaigns loaded at startup.
     * @return String of the name for the best campaign for this user.
     */
    static String determineBestCampaignForUserSegments(String[] segments, List<Campaign> campaigns){
        List<Campaign> bestCampaigns = new LinkedList<>();
        int relevanceOfBestCampaign = 0; // Hold the number of segment matches for the best campaign found so far.

        // Determine the relevance of every campaign and fill the bestCampaigns list with only the most relevant.
        for(Campaign campaign : campaigns){
            int relevance = calculateCampaignRelevance(campaign, segments);

            if(relevance > relevanceOfBestCampaign){
                relevanceOfBestCampaign = relevance;
                // Remove any older, less relevant campaigns.
                bestCampaigns.clear();
                bestCampaigns.add(campaign);
            } else if(relevance == relevanceOfBestCampaign && relevance != 0){
                // Add another option to bestCampaigns
                bestCampaigns.add(campaign);
            }
        }

        // Display a random campaign from the list of best campaigns to prevent starving a specific campaign.
        return (!bestCampaigns.isEmpty()) ?
                bestCampaigns.get(new Random().nextInt(bestCampaigns.size())).getCampaignName()
                : "no campaign";

    }
}
