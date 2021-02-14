package com.vancerver.targeting;
@Service
public class CampaignService {
    // Add a basic logger that logs to the console
    private static Logger LOG = LoggerFactory
            .getLogger(CampaignService.class);
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
        for(int segment : campaign.getSegments()){
            for(String userSegment : userSegments){
                if(segment == Integer.parseInt(userSegment)) ++relevance;
            }
        }
        return relevance;
    }
}
