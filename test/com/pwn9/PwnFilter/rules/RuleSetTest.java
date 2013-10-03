package com.pwn9.PwnFilter.rules;

import com.pwn9.PwnFilter.FilterState;
import com.pwn9.PwnFilter.PwnFilter;
import com.pwn9.PwnFilter.listener.FilterListener;
import com.pwn9.PwnFilter.util.LogManager;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for RuleSets
 * User: ptoal
 * Date: 13-05-04
 * Time: 11:28 AM
 */

public class RuleSetTest {

    RuleManager ruleManager;
    RuleChain rs;
    Player mockPlayer;
    PwnFilter mockPlugin;

    @Before
    public void setUp() throws Exception {
        ruleManager = RuleManager.getInstance(mockPlugin);
        File testFile = new File(this.getClass().getClassLoader().getResource("testrules.txt").getPath());
        ruleManager.setRuleDir(new File(testFile.getParent()));
        rs = ruleManager.getRuleChain("testrules.txt");
        LogManager.getInstance(Logger.getAnonymousLogger(),new File("/tmp/test"));
    }

    @Test
    public void testLoadRules() {
        assertTrue(rs.loadConfigFile());
    }

    @Test
    public void testApplyRules() {
        rs.loadConfigFile();
        FilterState testState = new FilterState(mockPlugin,"This is a test", null, new FilterListener() {
            @Override
            public String getShortName() {
                return "TEST";  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public RuleChain getRuleChain() {
                return ruleManager.getRuleChain("testrules.txt");  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isActive() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void activate(Configuration config) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void shutdown() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        rs.execute(testState);
        System.out.println(rs.ruleCount());
        assertEquals("This WAS a test", testState.message.getPlainString());
    }

    @After
    public void tearDown() throws Exception {
     // TODO: Anything?  Probably not.
    }


}
