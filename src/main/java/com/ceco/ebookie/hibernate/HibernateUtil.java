package com.ceco.ebookie.hibernate;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.io.Closeables;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.ConcurrentUtils;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility methods for obtaining sessions from hibernate.
 *
 * @see #getSession()
 * @see #getStatelessSession()
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 02-Jan-2016
 */
public final class HibernateUtil {

    private static final File APP_PROPERTIES_FILE = new File("src/main/resources/app.properties");

    private HibernateUtil() {

    }

    private static final Logger LOG = LoggerFactory
            .getLogger(HibernateUtil.class);

    private static final Configuration CFG;

    private static String cfgResource;

    /**
     * Sets the username that will be used when connecting to the database
     * through hibernate.
     *
     * <p>
     * If this property is not set, the one from the hibernate.cfg.xml file will
     * be used.
     * </p>
     *
     * @param username
     *            the username to be used
     */
    public static void setConnectionsUser(String username) {
        Preconditions.checkArgument(StringUtils.isNotBlank(username),
                "Username cannot be empty!");
        CFG.setProperty(Environment.USER, username);
    }

    /**
     * Sets the password that will be used when connecting to the database
     * through hibernate.
     *
     * <p>
     * If this property is not set, the one from the hibernate.cfg.xml file will
     * be used.
     * </p>
     *
     * @param password
     *            the password to be used
     */
    public static void setConnectionsPassword(String password) {
        CFG.setProperty(Environment.PASS, password);
    }

    static {
        cfgResource = "/hibernate.cfg.xml";
        CFG = new Configuration(); // .configure();
        CFG.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
    }
    private static final LazyInitializer<SessionFactory> FACTORY_INITIALIZER = new LazyInitializer<SessionFactory>() {

        @Override
        protected SessionFactory initialize() {
            try {
                Properties properties = loadFile(APP_PROPERTIES_FILE);
                String dbUsername = properties.getProperty("db.username");
                if (!Strings.isNullOrEmpty(dbUsername)) {
                    setConnectionsUser(dbUsername);
                    LOG.info("Using the username from the configuration api to connect to the database");
                }
                String dbPassword = properties.getProperty("db.password");
                if (!Strings.isNullOrEmpty(dbPassword)) {
                    setConnectionsPassword(dbPassword);
                    LOG.info("Using the password from the configuration api to connect to the database");
                }
                CFG.configure(cfgResource);
                ServiceRegistry registry = new ServiceRegistryBuilder()
                        .applySettings(CFG.getProperties())
                        .buildServiceRegistry();
                return CFG.buildSessionFactory(registry);
            } catch (Exception ex) {
                LOG.error("Error while building session factory!", ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
    };

    /**
     * Provides a custom value for the Hibernate configuration resource. The
     * default value is "/hibernate.cfg.xml".
     *
     * @param name
     *            a Java resource name pointing to Hibernate configuration
     */
    public static void setConfigResource(String name) {
        cfgResource = name;
    }

    /**
     * Gets the sessionfactory behind this utility class.
     *
     * @return the sessionfactory behind this utility class
     */
    public static SessionFactory getSessionFactory() {
        return ConcurrentUtils.initializeUnchecked(FACTORY_INITIALIZER);
    }

    /**
     * Gets a new session from hibernate.
     *
     * @return a new session from hibernate
     * @throws HibernateException
     *             if a session could not be acquired
     */
    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    /**
     * Gets a stateless session from hibernate.
     *
     * @return a new stateless session from hibernate
     * @throws HibernateException
     *             if a session could not be acquired
     * @see {@link StatelessSession}
     */
    public static StatelessSession getStatelessSession() {
        return getSessionFactory().openStatelessSession();
    }

    /**
     * A convenient method to load the properties from a given file.
     *
     * NOTE:This will automatically close the input stream that's opened.
     *
     * @param file
     *            the file from which to import more properties
     * @throws IOException
     *             if there was an error while reading the file
     */
    public static Properties loadFile(File file) throws IOException {
        FileInputStream in = null;
        Properties props = new Properties();
        try {
            in = new FileInputStream(file);
            props.load(in);
        } finally {
            Closeables.closeQuietly(in);
        }

        return props;
    }
}
