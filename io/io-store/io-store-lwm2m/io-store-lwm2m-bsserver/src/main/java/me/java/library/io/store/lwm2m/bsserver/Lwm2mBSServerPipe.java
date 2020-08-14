package me.java.library.io.store.lwm2m.bsserver;

import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.pipe.BasePipe;
import me.java.library.io.store.lwm2m.bsserver.servlet.BootstrapServlet;
import me.java.library.io.store.lwm2m.bsserver.servlet.ServerServlet;
import me.java.library.utils.base.ExceptionUtils;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.leshan.core.LwM2m;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.StaticModel;
import org.eclipse.leshan.core.util.SecurityUtil;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.californium.bootstrap.LeshanBootstrapServer;
import org.eclipse.leshan.server.californium.bootstrap.LeshanBootstrapServerBuilder;

import java.io.File;
import java.net.InetSocketAddress;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.lwm2m.bsserver.Lwm2mBSServerPipe
 * @createDate : 2020/8/14
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class Lwm2mBSServerPipe extends BasePipe<Lwm2mBSServerParams> {

    LeshanBootstrapServer bsServer;
    Server server;

    public Lwm2mBSServerPipe(Lwm2mBSServerParams params) {
        super(params);
    }

    @Override
    protected boolean onStart() throws Exception {
        initServer(
                params.getWebAddress(),
                params.getWebPort(),
                params.getLocalAddress(),
                params.getLocalPort(),
                params.getSecureLocalAddress(),
                params.getSecureLocalPort(),
                params.getModelsFolderPath(),
                params.getConfigFilename(),
                params.isSupportDeprecatedCiphers(),
                params.getPublicKey(),
                params.getPrivateKey(),
                params.getCertificate(),
                params.getTrustStore()
        );
        return true;
    }

    @Override
    protected boolean onStop() throws Exception {
        bsServer.stop();
        server.stop();
        return false;
    }

    @Override
    protected boolean onSend(Cmd request) throws Exception {
        ExceptionUtils.notSupportMethod();
        return false;
    }

    @Override
    protected Cmd onSyncSend(Cmd request, long timeout, TimeUnit unit) throws Exception {
        ExceptionUtils.notSupportMethod();
        return null;
    }


    void initServer(String webAddress,
                    int webPort,
                    String localAddress,
                    Integer localPort,
                    String secureLocalAddress,
                    Integer secureLocalPort,
                    String modelsFolderPath,
                    String configFilename,
                    boolean supportDeprecatedCiphers,
                    PublicKey publicKey,
                    PrivateKey privateKey,
                    X509Certificate certificate,
                    List<Certificate> trustStore) throws Exception {
        // Create Models
        List<ObjectModel> models = ObjectLoader.loadDefault();
        if (modelsFolderPath != null) {
            models.addAll(ObjectLoader.loadObjectsFromDir(new File(modelsFolderPath)));
        }

        // Prepare and start bootstrap server
        LeshanBootstrapServerBuilder builder = new LeshanBootstrapServerBuilder();
        JSONFileBootstrapStore bsStore = new JSONFileBootstrapStore(configFilename);
        builder.setConfigStore(bsStore);
        builder.setSecurityStore(new BootstrapConfigSecurityStore(bsStore));
        builder.setModel(new StaticModel(models));

        // Create DTLS Config
        DtlsConnectorConfig.Builder dtlsConfig = new DtlsConnectorConfig.Builder();
        dtlsConfig.setRecommendedCipherSuitesOnly(!supportDeprecatedCiphers);

        // Create credentials;
        X509Certificate serverCertificate = null;
        if (certificate != null) {
            // use X.509 mode (+ RPK)
            serverCertificate = certificate;
            builder.setPrivateKey(privateKey);
            builder.setCertificateChain(new X509Certificate[]{serverCertificate});
        } else if (publicKey != null) {
            // use RPK
            builder.setPublicKey(publicKey);
            builder.setPrivateKey(privateKey);
        } else {
            try {
                PrivateKey embeddedPrivateKey = SecurityUtil.privateKey
                        .readFromResource("credentials/bsserver_privkey.der");
                serverCertificate = SecurityUtil.certificate.readFromResource("credentials/bsserver_cert.der");
                builder.setPrivateKey(embeddedPrivateKey);
                builder.setCertificateChain(new X509Certificate[]{serverCertificate});
            } catch (Exception e) {
                logger.error("Unable to load embedded X.509 certificate.", e);
                System.exit(-1);
            }
        }

        // Define trust store
        if (serverCertificate != null) {
            if (trustStore != null && !trustStore.isEmpty()) {
                builder.setTrustedCertificates(trustStore.toArray(new Certificate[0]));
            } else {
                // by default trust all
                builder.setTrustedCertificates(new X509Certificate[0]);
            }
        }

        // Set DTLS Config
        builder.setDtlsConfig(dtlsConfig);

        // Create CoAP Config
        NetworkConfig coapConfig;
        File configFile = new File(NetworkConfig.DEFAULT_FILE_NAME);
        if (configFile.isFile()) {
            coapConfig = new NetworkConfig();
            coapConfig.load(configFile);
        } else {
            coapConfig = LeshanServerBuilder.createDefaultNetworkConfig();
            coapConfig.store(configFile);
        }
        builder.setCoapConfig(coapConfig);

        // ports from CoAP Config if needed
        builder.setLocalAddress(localAddress,
                localPort == null ? coapConfig.getInt(NetworkConfig.Keys.COAP_PORT, LwM2m.DEFAULT_COAP_PORT) : localPort);
        builder.setLocalSecureAddress(secureLocalAddress,
                secureLocalPort == null ? coapConfig.getInt(NetworkConfig.Keys.COAP_SECURE_PORT, LwM2m.DEFAULT_COAP_SECURE_PORT)
                        : secureLocalPort);

        bsServer = builder.build();
        bsServer.start();

        // Now prepare and start jetty
        InetSocketAddress jettyAddr;
        if (webAddress == null) {
            jettyAddr = new InetSocketAddress(webPort);
        } else {
            jettyAddr = new InetSocketAddress(webAddress, webPort);
        }
        server = new Server(jettyAddr);
        WebAppContext root = new WebAppContext();

        root.setContextPath("/");
        root.setResourceBase(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("webapp")).toExternalForm());
        root.setParentLoaderPriority(true);

        ServletHolder bsServletHolder = new ServletHolder(new BootstrapServlet(bsStore));
        root.addServlet(bsServletHolder, "/api/bootstrap/*");

        ServletHolder serverServletHolder;
        if (publicKey != null) {
            serverServletHolder = new ServletHolder(new ServerServlet(bsServer, publicKey));
        } else {
            serverServletHolder = new ServletHolder(new ServerServlet(bsServer, serverCertificate));
        }
        root.addServlet(serverServletHolder, "/api/server/*");

        server.setHandler(root);

        server.start();
        logger.info("Web server started at {}.", server.getURI());
    }
}
