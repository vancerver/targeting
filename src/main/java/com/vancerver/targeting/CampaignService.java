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
}
