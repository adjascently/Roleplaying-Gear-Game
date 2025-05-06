import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class BattleTest {


    //                1. Assigning Items to Players


    // 1a. assignPlayerHeadGearTest
    @Test
    public void testAssignPlayerHeadGear() {
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        HeadGear headGear1 = new HeadGear("Mighty", "Helmet", 0, 10);
        HeadGear headGear2 = new HeadGear("Swift", "Hat", 0, 8);

        player1.assignGearToPlayer(headGear1);
        player2.assignGearToPlayer(headGear2);

        assertEquals(headGear1, player1.getHeadGear());
        assertEquals(headGear2, player2.getHeadGear());
    }

    // 1b. assignPlayerHandGearsTest
    @Test
    public void testAssignPlayerHandGears() {
        Player player = new Player("Charlie");
        HandGear handGear1 = new HandGear("Sharp", "Sword", 15, 0);
        HandGear handGear2 = new HandGear("Sturdy", "Shield", 10, 0);

        player.assignGearToPlayer(handGear1);
        player.assignGearToPlayer(handGear2);

        assertEquals(2, player.getHandGears().size());
        assertTrue(player.getHandGears().contains(handGear1));
        assertTrue(player.getHandGears().contains(handGear2));
    }

    // 1c. assignPlayerFootWearsTest
    @Test
    public void testAssignPlayerFootWears() {
        Player player = new Player("Diana");
        FootWear footWear1 = new FootWear("Speedy", "Boots", 5, 5);
        FootWear footWear2 = new FootWear("Power", "Sneakers", 7, 3);

        player.assignGearToPlayer(footWear1);
        player.assignGearToPlayer(footWear2);

        assertEquals(2, player.getFootWears().size());
        assertTrue(player.getFootWears().contains(footWear1));
        assertTrue(player.getFootWears().contains(footWear2));
    }


    //       2. Item Combination and Restriction Tests

    // 2a. testCombineDifferentItemTypes
    @Test(expected = IllegalArgumentException.class)
    public void testCombineDifferentItemTypes() {
        HeadGear headGear = new HeadGear("Mighty", "Helmet", 0, 10);
        HandGear handGear = new HandGear("Sharp", "Sword", 15, 0);
        headGear.combine(handGear);
    }

    // 2b. testEquipHeadGear
    @Test(expected = IllegalStateException.class)
    public void testEquipHeadGearRestriction() {
        Player player = new Player("Eve");
        HeadGear headGear1 = new HeadGear("Mighty", "Helmet", 0, 10);
        HeadGear headGear2 = new HeadGear("Swift", "Hat", 0, 8);

        player.assignGearToPlayer(headGear1);
        // Attempting to assign a second head gear should throw exception
        player.assignGearToPlayer(headGear2);
    }

    // 2c. testEquipHandGearBeyondLimit
    @Test
    public void testEquipHandGearBeyondLimit() {
        Player player = new Player("Frank");
        HandGear handGear1 = new HandGear("Sharp", "Sword", 15, 0);
        HandGear handGear2 = new HandGear("Sturdy", "Shield", 10, 0);
        HandGear handGear3 = new HandGear("Agile", "Gloves", 5, 0);

        player.assignGearToPlayer(handGear1);
        player.assignGearToPlayer(handGear2);
        player.assignGearToPlayer(handGear3);

        // The hand gear list size should remain 2 because the third should combine
        assertEquals(2, player.getHandGears().size());

        // Check if a combined gear is present with attack equal to handGear1 + handGear3
        boolean foundCombined = false;
        for (Gear gear : player.getHandGears()) {
            if (gear.getAttackStrength() == (handGear1.getAttackStrength() + handGear3.getAttackStrength())) {
                foundCombined = true;
                break;
            }
        }
        assertTrue("Combined gear not found in hand gears", foundCombined);
    }

    // 2d. testEquipFootWearBeyondLimit
    @Test
    public void testEquipFootWearBeyondLimit() {
        Player player = new Player("Grace");
        FootWear footWear1 = new FootWear("Speedy", "Boots", 5, 5);
        FootWear footWear2 = new FootWear("Power", "Sneakers", 7, 3);
        FootWear footWear3 = new FootWear("Dynamic", "Hoverboard", 3, 4);

        player.assignGearToPlayer(footWear1);
        player.assignGearToPlayer(footWear2);
        player.assignGearToPlayer(footWear3);

        assertEquals(2, player.getFootWears().size());

        boolean foundCombined = false;
        for (Gear gear : player.getFootWears()) {
            int combinedAttack = footWear1.getAttackStrength() + footWear3.getAttackStrength();
            int combinedDefense = footWear1.getDefenseStrength() + footWear3.getDefenseStrength();
            if (gear.getAttackStrength() == combinedAttack && gear.getDefenseStrength() == combinedDefense) {
                foundCombined = true;
                break;
            }
        }
        assertTrue("Combined gear not found in foot wears", foundCombined);
    }


    //            3. Battle Initialization and Setup

    // 3a. testBattleInitialization
    @Test
    public void testBattleInitialization() {
        Player player1 = new Player("Henry");
        Player player2 = new Player("Irene");
        List<Gear> gears = new ArrayList<>();
        gears.add(new HeadGear("Mighty", "Helmet", 0, 10));
        gears.add(new HandGear("Sharp", "Sword", 15, 0));

        Battle battle = new Battle(player1, player2, gears);
        assertEquals(player1, battle.getPlayer1());
        assertEquals(player2, battle.getPlayer2());
        assertEquals(gears, battle.getBattleGears());
    }

    // For testing null players we use try-catch to check for exceptions.
    @Test
    public void testBattleInitializationWithNullPlayer1() {
        Player player = new Player("Jack");
        List<Gear> gears = new ArrayList<>();
        gears.add(new HandGear("Sharp", "Sword", 15, 0));

        try {
            new Battle(null, player, gears);
            fail("Expected IllegalArgumentException for null player1");
        } catch (IllegalArgumentException e) {
            // Expected exception.
        }
    }

    @Test
    public void testBattleInitializationWithNullPlayer2() {
        Player player = new Player("Jack");
        List<Gear> gears = new ArrayList<>();
        gears.add(new HandGear("Sharp", "Sword", 15, 0));

        try {
            new Battle(player, null, gears);
            fail("Expected IllegalArgumentException for null player2");
        } catch (IllegalArgumentException e) {
            // Expected exception.
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBattleInitializationWithNullGearList() {
        Player player1 = new Player("Karen");
        Player player2 = new Player("Leo");
        new Battle(player1, player2, null);
    }

    // 3d. testAddGear
    @Test
    public void testAddGear() {
        Player player1 = new Player("Mona");
        Player player2 = new Player("Nate");
        Battle battle = new Battle(player1, player2);
        Gear handGear = new HandGear("Sharp", "Sword", 15, 0);
        battle.addGear(handGear);
        assertTrue(battle.getBattleGears().contains(handGear));
    }

    // 3e. testAddDuplicateGear
    @Test
    public void testAddDuplicateGear() {
        Player player1 = new Player("Olivia");
        Player player2 = new Player("Pete");
        Battle battle = new Battle(player1, player2);
        Gear headGear = new HeadGear("Mighty", "Helmet", 0, 10);
        battle.addGear(headGear);
        try {
            battle.addGear(headGear);
            fail("Expected IllegalArgumentException for duplicate gear");
        } catch (IllegalArgumentException e) {
            // Expected exception.
        }
    }


    //        4. Dressing Players with the Best Gear

    // 4a. testDressPlayers
    @Test
    public void testDressPlayers() {
        Player player1 = new Player("Quinn");
        Player player2 = new Player("Rita");

        List<Gear> gears = new ArrayList<>();
        gears.add(new HeadGear("Mighty", "Helmet", 0, 10));
        gears.add(new HeadGear("Swift", "Hat", 0, 8));
        gears.add(new HandGear("Sharp", "Sword", 15, 0));
        gears.add(new HandGear("Sturdy", "Shield", 10, 0));
        gears.add(new FootWear("Speedy", "Boots", 5, 5));
        gears.add(new FootWear("Power", "Sneakers", 7, 3));

        Battle battle = new Battle(player1, player2, gears);
        battle.dressPlayers();

        assertNotNull(player1.getHeadGear());
        assertNotNull(player2.getHeadGear());
        assertFalse(player1.getHandGears().isEmpty());
        assertFalse(player2.getHandGears().isEmpty());
        assertFalse(player1.getFootWears().isEmpty());
        assertFalse(player2.getFootWears().isEmpty());
    }

    //4b. testFindBestGearForPlayer
    @Test
    public void testFindBestGearForPlayer() {
        Player player = new Player("Steve");
        Player other = new Player("Tom");

        List<Gear> gears = new ArrayList<>();
        HeadGear headGear = new HeadGear("Elite", "Helmet", 0, 12);
        gears.add(headGear);
        gears.add(new HandGear("Basic", "Sword", 10, 0));

        Battle battle = new Battle(player, other, gears);
        battle.dressPlayers();

        // Now, since Steve’s head gear slot is empty and a head gear is available,
        // he should receive the head gear.
        assertEquals(headGear, player.getHeadGear());
    }


    // 5a. testBattleOutcome_Player1Wins
    @Test
    public void testBattleOutcome_Player1Wins() {
        Player player1 = new Player("Uma", 10, 10);
        Player player2 = new Player("Vince", 5, 5);

        player1.assignGearToPlayer(new HandGear("Sharp", "Sword", 15, 0));
        player1.assignGearToPlayer(new FootWear("Sturdy", "Boots", 5, 5));

        player2.assignGearToPlayer(new HandGear("Basic", "Sword", 10, 0));
        player2.assignGearToPlayer(new FootWear("Light", "Sneakers", 3, 3));

        Battle battle = new Battle(player1, player2);
        String outcome = battle.battleOutcome();

        assertTrue(outcome.contains("Player 1 WON"));
    }

    // 5b. testBattleOutcome_Player2Wins
    @Test
    public void testBattleOutcome_Player2Wins() {
        Player player1 = new Player("Wendy", 5, 5);
        Player player2 = new Player("Xavier", 10, 10);

        player1.assignGearToPlayer(new HandGear("Basic", "Sword", 10, 0));
        player1.assignGearToPlayer(new FootWear("Light", "Boots", 3, 3));

        player2.assignGearToPlayer(new HandGear("Sharp", "Sword", 15, 0));
        player2.assignGearToPlayer(new FootWear("Sturdy", "Sneakers", 7, 3));

        Battle battle = new Battle(player1, player2);
        String outcome = battle.battleOutcome();

        assertTrue(outcome.contains("Player 2 WON"));
    }

    // 5c. testBattleOutcome_Draw
    @Test
    public void testBattleOutcome_Draw() {
        Player player1 = new Player("Yara", 10, 10);
        Player player2 = new Player("Zack", 10, 10);

        player1.assignGearToPlayer(new HandGear("Equal", "Sword", 10, 0));
        player1.assignGearToPlayer(new FootWear("Equal", "Boots", 5, 5));

        player2.assignGearToPlayer(new HandGear("Equal", "Sword", 10, 0));
        player2.assignGearToPlayer(new FootWear("Equal", "Boots", 5, 5));

        Battle battle = new Battle(player1, player2);
        String outcome = battle.battleOutcome();

        assertTrue(outcome.contains("TIE"));
    }


    //   6. Character State Verification

    // 6a. testPrintCharacterState
    @Test
    public void testPrintCharacterState() {
        Player player = new Player("Alice");
        HeadGear headGear = new HeadGear("Mighty", "Helmet", 0, 10);
        HandGear handGear = new HandGear("Sharp", "Sword", 15, 0);
        FootWear footWear = new FootWear("Speedy", "Boots", 5, 5);

        player.assignGearToPlayer(headGear);
        player.assignGearToPlayer(handGear);
        player.assignGearToPlayer(footWear);

        Battle dummyBattle = new Battle(player, new Player("Bob"));

        // Capture the output of printCharacterState
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent));

        dummyBattle.printCharacterState(player);

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("Player: Alice"));
        assertTrue(output.contains("Helmet"));
        assertTrue(output.contains("Sword"));
        assertTrue(output.contains("Boots"));
        assertTrue(output.contains("Attack Strength:"));
        assertTrue(output.contains("Defense Strength:"));
    }

    // 6b. Test that the Battle constructor rejects the same Player for both slots.
    @Test(expected = IllegalArgumentException.class)
    public void testBattleWithSamePlayers() {
        Player p = new Player("SamePlayer");
        // This should throw IllegalArgumentException (players cannot be the same).
        new Battle(p, p, new ArrayList<Gear>());
    }

    // 6c. Test that dressPlayers() throws if there are no gears available.
    @Test(expected = IllegalStateException.class)
    public void testDressPlayersNoGears() {
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        List<Gear> emptyGearList = new ArrayList<>();  // no gears

        Battle battle = new Battle(p1, p2, emptyGearList);
        // This should throw IllegalStateException ("No gears available to distribute.").
        battle.dressPlayers();
    }

    // 6d. Test combine() with two gears of equal stats to hit the tie-break path in BaseGear.
    @Test
    public void testCombineEqualStats() {
        // Both have the same attack & defense, which triggers the random tie-break logic.
        HandGear gear1 = new HandGear("Adjective1", "Sword", 10, 0);
        HandGear gear2 = new HandGear("Adjective2", "Sword", 10, 0);

        Gear combined = gear1.combine(gear2);

        // Attack = sum of both
        assertEquals(20, combined.getAttackStrength());
        // HandGear has zero defense
        assertEquals(0, combined.getDefenseStrength());

        // We can’t predict the exact fullName or adjective because of the random tie-break,
        // but we can confirm the gear is not null and is still HandGear.
        assertNotNull(combined);
        assertTrue("Combined gear should be a HandGear", combined instanceof HandGear);
    }


    // Verifying that the HeadGearType enum is properly defined.

    @Test
    public void testHeadGearTypeEnum() {
        // Retrieve all enum constants from HeadGearType.
        HeadGearType[] types = HeadGearType.values();
        // Assert that there is at least one constant defined.
        assertTrue("Expected at least one HeadGearType constant", types.length > 0);

        // For example, if you know your enum has HELMET, you can do:
        HeadGearType helmet = HeadGearType.valueOf("HELMET");
        assertEquals(HeadGearType.HELMET, helmet);
    }

    //This test ensures that the HandGearType enum is correctly implemented.
    @Test
    public void testHandGearTypeEnum() {
// Retrieve all constants defined in HandGearType.
        HandGearType[] types = HandGearType.values();
        assertTrue("Expected at least one HandGearType constant", types.length > 0);

        // If your enum has SWORD, do:
        HandGearType sword = HandGearType.valueOf("SWORD");
        assertEquals(HandGearType.SWORD, sword);
    }

//This test checks that the FootWearType enum is defined properly.
    @Test
    public void testFootWearTypeEnum() {
// Retrieve all enum constants from FootWearType.
        FootWearType[] types = FootWearType.values();
        assertTrue("Expected at least one FootWearType constant", types.length > 0);

        // If your enum has BOOTS, do:
        FootWearType boots = FootWearType.valueOf("BOOTS");
        assertEquals(FootWearType.BOOTS, boots);
    }

    //Validating the HeadGear constructor and its factory method newGear()
    
    // 1) Test constructor edge cases and newGear() factory
    @Test
    public void testHeadGearConstructorAndFactory() {
        // Normal creation
        HeadGear hg = new HeadGear("Elite", "Helmet", 0, 10);
        assertEquals(0, hg.getAttackStrength());
        assertEquals(10, hg.getDefenseStrength());
        assertEquals("Elite", hg.getAdjective());
        assertEquals("Helmet", hg.getNoun());
        assertEquals(GearType.HEAD, hg.getType());

        // The newGear() method is used when combining or creating new HeadGear
        Gear newHg = hg.newGear("NewAdj", "NewHelmet", 0, 20);
        assertTrue("Expected a HeadGear instance", newHg instanceof HeadGear);
        assertEquals(0, newHg.getAttackStrength());
        assertEquals(20, newHg.getDefenseStrength());
        assertEquals("NewAdj", newHg.getAdjective());
        assertEquals("NewHelmet", newHg.getNoun());
    }

    // 2) Test toString() coverage
    @Test
    public void testHeadGearToString() {
        HeadGear hg = new HeadGear("Mighty", "Visor", 0, 15);
        String hgString = hg.toString();
        // Example: "HeadGear (noun='Visor', adjective='Mighty', attack=0, defense=15)"
        assertTrue(hgString.contains("HeadGear ("));
        assertTrue(hgString.contains("Mighty"));
        assertTrue(hgString.contains("Visor"));
        assertTrue(hgString.contains("attack=0"));
        assertTrue(hgString.contains("defense=15"));
    }
}
