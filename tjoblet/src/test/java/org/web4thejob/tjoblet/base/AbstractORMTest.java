package org.web4thejob.tjoblet.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web4thejob.context.ContextUtil;
import org.web4thejob.module.JobletInstaller;
import org.web4thejob.orm.DatasourceProperties;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:org/web4thejob/conf/orm-config.xml"})
public abstract class AbstractORMTest {
    private static boolean initialized;

    @Before
    public void setUp() {

        if (!initialized && !ContextUtil.getSystemJoblet().isInstalled()) {
            Properties datasource = new Properties();
            try {
                datasource
                        .load(new ClassPathResource(DatasourceProperties.PATH)
                                .getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            JobletInstaller jobletInstaller;
            jobletInstaller = ContextUtil.getBean(JobletInstaller.class);
            jobletInstaller.setConnectionInfo(datasource);
            List<Exception> errors = jobletInstaller.installAll();
            if (!errors.isEmpty()) {
                throw new RuntimeException(
                        "Test Context initialization failed.");
            }
            initialized = true;
        }

        ContextUtil.getMRS().refreshMetaCache();

    }
}