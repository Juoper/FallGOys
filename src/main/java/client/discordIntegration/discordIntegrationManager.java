package client.discordIntegration;

import client.MainClient;
import client.connection.ClientConnectionManager;
import communication.messages.Ping;
import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.DiscordEventHandler;
import de.jcm.discordgamesdk.activity.Activity;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class discordIntegrationManager extends DiscordEventHandler {
    public Activity activity;
    public Core core;

    public discordIntegrationManager() {
        File discordLibrary = new File("src/main/resources/discord_game_sdk.dll");
        if (discordLibrary == null) {
            System.err.println("Error loading Discord SDK.");
            System.exit(-1);
        }
        // Initialize the Core
        Core.init(discordLibrary);


        // Set parameters for the Core
        try (CreateParams params = new CreateParams()) {
            params.registerEventHandler(this);
            params.setClientID(941419472202387566L);
            params.setFlags(CreateParams.getDefaultFlags());

            // Create the Core
            core = new Core(params);
            // Create the Activity
            activity = new Activity();

            activity.setDetails("Running an example");
            activity.setState("and having fun");

            // Setting a start time causes an "elapsed" field to appear
            activity.timestamps().setStart(Instant.now());

            // We are in a party with 10 out of 100 people.
            activity.party().size().setMaxSize(10);
            activity.party().size().setCurrentSize(1);

            // Make a "cool" image show up
            activity.assets().setLargeImage("ingame");

            // Setting a join secret and a party ID causes an "Ask to Join" button to appear
            //activity.party().setID("Party1123!");
            //activity.secrets().setJoinSecret("Join!");

            // Finally, update the current activity to our activity
            core.activityManager().updateActivity(activity);


            // Run callbacks forever
            while (true) {
                core.runCallbacks();
                try {
                    // Sleep a bit to save CPU
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    @Override
    public void onActivityJoin(String secret) {
    }

    public void updatePresence(IrichPresenceStates object){
        core.activityManager().updateActivity(object.getActivity());
    }

}
