package me.java.library.io.store.lwm2m.client;

import com.google.common.collect.Maps;
import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.pipe.BasePipe;
import me.java.library.io.store.lwm2m.common.LeshanUtils;
import me.java.library.utils.base.ExceptionUtils;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.elements.Connector;
import org.eclipse.californium.scandium.DTLSConnector;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.californium.scandium.dtls.*;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.engine.DefaultRegistrationEngineFactory;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mInstanceEnabler;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.client.resource.listener.ObjectsListenerAdapter;
import org.eclipse.leshan.core.californium.DefaultEndpointFactory;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.StaticModel;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.util.Hex;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.eclipse.leshan.client.object.Security.*;
import static org.eclipse.leshan.core.LwM2mId.SECURITY;
import static org.eclipse.leshan.core.LwM2mId.SERVER;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.lwm2m.client.Lwm2mClientPipe
 * @createDate : 2020/7/31
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class Lwm2mClientPipe extends BasePipe<Lwm2mClientParams> implements Lwm2mClient {

    LeshanClient client;
    LwM2mModel model;
    Map<Integer, LwM2mInstanceEnabler[]> instances = Maps.newHashMap();

    public Lwm2mClientPipe(Lwm2mClientParams params) {
        super(params);
    }

    /**
     * 初始化客户端设备实例、传感器实例
     *
     * @param objectId @see eg: {LwM2mId#DEVICE}
     * @param enablers
     */
    public void initInstances(int objectId, LwM2mInstanceEnabler... enablers) {
        instances.put(objectId, enablers);
    }

    @Override
    protected boolean onStart() throws Exception {
        startClient(
                params.getEndpoint(),
                params.getLocalAddress(),
                params.getLocalPort(),
                params.isNeedBootstrap(),
                params.getAdditionalAttributes(),
                params.getBsAdditionalAttributes(),
                params.getLifetime(),
                params.getCommunicationPeriod(),
                params.getServerUri(),
                params.getPskIdentity(),
                params.getPskKey(),
                params.getClientPrivateKey(),
                params.getClientPublicKey(),
                params.getServerPublicKey(),
                params.getClientCertificate(),
                params.getServerCertificate(),
                params.isSupportOldFormat(),
                params.isSupportDeprecatedCiphers(),
                params.isReconnectOnUpdate(),
                params.isForceFullhandshake(),
                params.getModelsFolderPath()
        );
        return true;
    }

    @Override
    protected boolean onStop() throws Exception {
        client.stop(true);
        return true;
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

    @Override
    public void createObject(int objectId) {
        if (client == null) {
            return;
        }

        if (client.getObjectTree().getObjectEnabler(objectId) != null) {
            logger.info("Object {} already enabled.", objectId);
        }
        if (model.getObjectModel(objectId) == null) {
            logger.info("Unable to enable Object {} : there no model for this.", objectId);
        } else {
            ObjectsInitializer objectsInitializer = new ObjectsInitializer(model);
            objectsInitializer.setDummyInstancesForObject(objectId);
            LwM2mObjectEnabler object = objectsInitializer.create(objectId);
            client.getObjectTree().addObjectEnabler(object);
        }
    }

    @Override
    public void deleteObject(int objectId) {
        if (client == null) {
            return;
        }

        if (objectId == 0 || objectId == 3) {
            logger.info("Object {} can not be disabled.", objectId);
        } else if (client.getObjectTree().getObjectEnabler(objectId) == null) {
            logger.info("Object {} is not enabled.", objectId);
        } else {
            client.getObjectTree().removeObjectEnabler(objectId);
        }
    }

    @Override
    public void update() {
        if (client == null) {
            return;
        }

        client.triggerRegistrationUpdate();
    }

    @Override
    public void addInstanceEnabler(int objectId, AbstractInstanceEnabler instanceEnabler) {

    }

    private void startClient(String endpoint,
                             String localAddress,
                             int localPort,
                             boolean needBootstrap,
                             Map<String, String> additionalAttributes,
                             Map<String, String> bsAdditionalAttributes,
                             int lifetime,
                             Integer communicationPeriod,
                             String serverUri,
                             byte[] pskIdentity,
                             byte[] pskKey,
                             PrivateKey clientPrivateKey,
                             PublicKey clientPublicKey,
                             PublicKey serverPublicKey,
                             X509Certificate clientCertificate,
                             X509Certificate serverCertificate,
                             boolean supportOldFormat,
                             boolean supportDeprecatedCiphers,
                             boolean reconnectOnUpdate,
                             boolean forceFullhandshake,
                             String modelsFolderPath) throws CertificateEncodingException {


        // Initialize model
        List<ObjectModel> models = ObjectLoader.loadDefault();
        models.addAll(LeshanUtils.getModelsFromResources());
        if (modelsFolderPath != null) {
            models.addAll(ObjectLoader.loadObjectsFromDir(new File(modelsFolderPath), true));
        }

        // Initialize object list
        model = new StaticModel(models);
        final ObjectsInitializer initializer = new ObjectsInitializer(model);

        if (needBootstrap) {
            if (pskIdentity != null) {
                initializer.setInstancesForObject(SECURITY, pskBootstrap(serverUri, pskIdentity, pskKey));
            } else if (clientPublicKey != null) {
                initializer.setInstancesForObject(SECURITY, rpkBootstrap(serverUri, clientPublicKey.getEncoded(), clientPrivateKey.getEncoded(), serverPublicKey.getEncoded()));
            } else if (clientCertificate != null) {
                initializer.setInstancesForObject(SECURITY, x509Bootstrap(serverUri, clientCertificate.getEncoded(), clientPrivateKey.getEncoded(), serverCertificate.getEncoded()));
            } else {
                initializer.setInstancesForObject(SECURITY, noSecBootstap(serverUri));
            }

            initializer.setClassForObject(SERVER, Server.class);
        } else {
            if (pskIdentity != null) {
                initializer.setInstancesForObject(SECURITY, psk(serverUri, 123, pskIdentity, pskKey));
            } else if (clientPublicKey != null) {
                initializer.setInstancesForObject(SECURITY, rpk(serverUri, 123, clientPublicKey.getEncoded(), clientPrivateKey.getEncoded(), serverPublicKey.getEncoded()));
            } else if (clientCertificate != null) {
                initializer.setInstancesForObject(SECURITY, x509(serverUri, 123, clientCertificate.getEncoded(), clientPrivateKey.getEncoded(), serverCertificate.getEncoded()));
            } else {
                initializer.setInstancesForObject(SECURITY, noSec(serverUri, 123));
            }
            initializer.setInstancesForObject(SERVER, new Server(123, lifetime, BindingMode.U, false));
        }

        //注册实例（设备、传感器...）
        instances.forEach(initializer::setInstancesForObject);
        //generate LwM2mObjectEnabler
        List<LwM2mObjectEnabler> enablers = initializer.createAll();

        // Create CoAP Config
        NetworkConfig coapConfig;
        File configFile = new File(NetworkConfig.DEFAULT_FILE_NAME);
        if (configFile.isFile()) {
            coapConfig = new NetworkConfig();
            coapConfig.load(configFile);
        } else {
            coapConfig = LeshanClientBuilder.createDefaultNetworkConfig();
            coapConfig.store(configFile);
        }

        // Create DTLS Config
        DtlsConnectorConfig.Builder dtlsConfig = new DtlsConnectorConfig.Builder();
        dtlsConfig.setRecommendedCipherSuitesOnly(!supportDeprecatedCiphers);

        // Configure Registration Engine
        DefaultRegistrationEngineFactory engineFactory = new DefaultRegistrationEngineFactory();
        engineFactory.setCommunicationPeriod(communicationPeriod);
        engineFactory.setReconnectOnUpdate(reconnectOnUpdate);
        engineFactory.setResumeOnConnect(!forceFullhandshake);

        // configure EndpointFactory
        DefaultEndpointFactory endpointFactory = new DefaultEndpointFactory("LWM2M CLIENT") {
            @Override
            protected Connector createSecuredConnector(DtlsConnectorConfig dtlsConfig) {

                return new DTLSConnector(dtlsConfig) {
                    @Override
                    protected void onInitializeHandshaker(Handshaker handshaker) {
                        handshaker.addSessionListener(new SessionAdapter() {

                            @Override
                            public void handshakeStarted(Handshaker handshaker) throws HandshakeException {
                                if (handshaker instanceof ResumingServerHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by server : STARTED ...");
                                } else if (handshaker instanceof ServerHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by server : STARTED ...");
                                } else if (handshaker instanceof ResumingClientHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by client : STARTED ...");
                                } else if (handshaker instanceof ClientHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by client : STARTED ...");
                                }
                            }

                            @Override
                            public void sessionEstablished(Handshaker handshaker, DTLSSession establishedSession)
                                    throws HandshakeException {
                                if (handshaker instanceof ResumingServerHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by server : SUCCEED");
                                } else if (handshaker instanceof ServerHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by server : SUCCEED");
                                } else if (handshaker instanceof ResumingClientHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by client : SUCCEED");
                                } else if (handshaker instanceof ClientHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by client : SUCCEED");
                                }
                            }

                            @Override
                            public void handshakeFailed(Handshaker handshaker, Throwable error) {
                                // get cause
                                String cause;
                                if (error != null) {
                                    if (error.getMessage() != null) {
                                        cause = error.getMessage();
                                    } else {
                                        cause = error.getClass().getName();
                                    }
                                } else {
                                    cause = "unknown cause";
                                }

                                if (handshaker instanceof ServerHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by server : FAILED ({})", cause);
                                } else if (handshaker instanceof ResumingServerHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by server : FAILED ({})", cause);
                                } else if (handshaker instanceof ClientHandshaker) {
                                    logger.info("DTLS Full Handshake initiated by client : FAILED ({})", cause);
                                } else if (handshaker instanceof ResumingClientHandshaker) {
                                    logger.info("DTLS abbreviated Handshake initiated by client : FAILED ({})", cause);
                                }
                            }
                        });
                    }
                };
            }
        };

        // Create client
        LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);
        builder.setLocalAddress(localAddress, localPort);
        builder.setObjects(enablers);
        builder.setCoapConfig(coapConfig);
        builder.setDtlsConfig(dtlsConfig);
        builder.setRegistrationEngineFactory(engineFactory);
        builder.setEndpointFactory(endpointFactory);
        if (supportOldFormat) {
            builder.setDecoder(new DefaultLwM2mNodeDecoder(true));
            builder.setEncoder(new DefaultLwM2mNodeEncoder(true));
        }
        builder.setAdditionalAttributes(additionalAttributes);
        builder.setBootstrapAdditionalAttributes(bsAdditionalAttributes);
        final LeshanClient client = builder.build();

        client.getObjectTree().addListener(new ObjectsListenerAdapter() {
            @Override
            public void objectRemoved(LwM2mObjectEnabler object) {
                logger.info("Object {} disabled.", object.getId());
            }

            @Override
            public void objectAdded(LwM2mObjectEnabler object) {
                logger.info("Object {} enabled.", object.getId());
            }
        });

        // Display client public key to easily add it in demo servers.
        if (clientPublicKey != null) {
            PublicKey rawPublicKey = clientPublicKey;
            if (rawPublicKey instanceof ECPublicKey) {
                ECPublicKey ecPublicKey = (ECPublicKey) rawPublicKey;
                // Get x coordinate
                byte[] x = ecPublicKey.getW().getAffineX().toByteArray();
                if (x[0] == 0) {
                    x = Arrays.copyOfRange(x, 1, x.length);
                }

                // Get Y coordinate
                byte[] y = ecPublicKey.getW().getAffineY().toByteArray();
                if (y[0] == 0) {
                    y = Arrays.copyOfRange(y, 1, y.length);
                }

                // Get Curves params
                String params = ecPublicKey.getParams().toString();

                logger.info(
                        "Client uses RPK : \n Elliptic Curve parameters  : {} \n Public x coord : {} \n Public y coord : {} \n Public Key (Hex): {} \n Private Key (Hex): {}",
                        params, Hex.encodeHexString(x), Hex.encodeHexString(y),
                        Hex.encodeHexString(rawPublicKey.getEncoded()),
                        Hex.encodeHexString(clientPrivateKey.getEncoded()));

            } else {
                throw new IllegalStateException("Unsupported Public Key Format (only ECPublicKey supported).");
            }
        }

        // Display X509 credentials to easily at it in demo servers.
        if (clientCertificate != null) {
            logger.info("Client uses X509 : \n X509 Certificate (Hex): {} \n Private Key (Hex): {}",
                    Hex.encodeHexString(clientCertificate.getEncoded()),
                    Hex.encodeHexString(clientPrivateKey.getEncoded()));
        }

        client.start();

    }
}
