package com.cmpt276.Map;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cmpt276.Game.*;
import com.cmpt276.Character.*;

public class GuardStationTest {
    @Test
    /**
     * Testing if GuardStation's EnterEffect is decreasing score by correct amount
     * and if it is changing its hasPunishment boolean to false afterwards
     */
    public void enterEffectTest()
    {
        MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
        Game game = new Game(gen);
        Thief thief = game.getThief();
        GuardStation guardStation = new GuardStation(0, new Position(0,0));
        guardStation.enterEffect(thief);
        assertTrue("EnterEffect score decrease failed", game.getScore() == -20);
        assertTrue("hasPunishment switch failed", guardStation.stillHasPunishment() == false);
    }
}
