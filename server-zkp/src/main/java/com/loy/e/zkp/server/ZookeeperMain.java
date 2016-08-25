package com.loy.e.zkp.server;

import org.apache.log4j.PropertyConfigurator;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig.ConfigException;
import org.apache.zookeeper.server.quorum.QuorumPeerMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class ZookeeperMain extends QuorumPeerMain {
    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperMain.class);

    public static void main(String[] args) {
        try {
            PropertyConfigurator.configure(
                    ResourceUtils.getFile("classpath:conf/log4j.properties").getAbsolutePath());
            ZookeeperMain main = new ZookeeperMain();
            String path = ResourceUtils.getFile("classpath:conf/zoo.cfg").getAbsolutePath();
            String[] arg = { path };
            main.initializeAndRun(arg);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid arguments, exiting abnormally", e);
            System.exit(2);
        } catch (ConfigException e) {
            LOG.error("Invalid config, exiting abnormally", e);
            System.err.println("Invalid config, exiting abnormally");
            System.exit(2);
        } catch (Exception e) {
            LOG.error("Unexpected exception, exiting abnormally", e);
            System.exit(1);
        }
        LOG.info("Exiting normally");
        System.exit(0);

    }
}
