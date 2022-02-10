package client.discordIntegration.richPresenceStates;

import client.discordIntegration.IrichPresenceStates;
import de.jcm.discordgamesdk.activity.Activity;

public class InLobby implements IrichPresenceStates {

    private String state = "In Game";
    private String details = "Competitive";
    private final String largeImageKey = "blub";
    private String partyId = "asdf";
    private int partySize = 1;
    private int partyMax = 5;
    private String gameId = "MTI4NzM0OjFpMmhuZToxMjMxMjM= ";


    public InLobby(String state, String details, int partySize, int partyMax) {
        this.state = state;
        this.details = details;
        this.partySize = partySize;
        this.partyMax = partyMax;
    }




    @Override
    public Activity getActivity() {
        Activity activity = new Activity();
        activity.party().size().setCurrentSize(partySize);
        activity.party().size().setMaxSize(partyMax);

        activity.setState(state);
        activity.setDetails(details);

        activity.assets().setLargeImage(largeImageKey);


        return activity;
    }
}
