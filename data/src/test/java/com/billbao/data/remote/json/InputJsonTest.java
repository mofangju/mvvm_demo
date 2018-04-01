package com.billbao.data.remote.json;

import com.billbao.data.utils.DataUtils;
import com.billbao.data.model.Player;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class InputJsonTest {
    private List<TeamJson> mTeams;

    @Before
    public void setUp() throws IOException {
        InputStream stream = InputJsonTest.class.getResourceAsStream("/input.json");
        mTeams = DataUtils.fromJsonStream(stream, new TypeToken<ArrayList<TeamJson>>(){}.getType());
        stream.close();
    }

    @Test
    public void verifyFirstTeamFromInputJson() {
        TeamJson team = mTeams.get(0);
        assertThat(team.getFullName(), is("Boston Celtics"));
        assertThat(team.getWins(), is(45));
        assertThat(team.getLosses(), is(20));

        List<PlayerJson> players = team.getPlayers();
        Player player = players.get(0);
        assertThat(player.getFirstName(), is("Kadeem"));
        assertThat(player.getLastName(), is("Allen"));
        assertThat(player.getPosition(), is("SG"));
        assertThat(player.getNumber(), is(45));
    }

    @Test
    public void verifyPlayersCountInEachTeam() {
        for (TeamJson team: mTeams) {
            List<PlayerJson> players = team.getPlayers();
            assertThat(players.size(), is(lessThanOrEqualTo(17)));
        }
    }
}