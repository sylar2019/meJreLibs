
##[NeuronRobotics/nrjavaserial](https://github.com/NeuronRobotics/nrjavaserial)

A Java Serial Port system. This is a fork of the RXTX project that uses in jar loading of the native code.

###Some of the features

- A simplified serial port object called NRSerialPort. See below for an example.

- Self-deployment of native libraries (all native code is stored inside the JAR and deployed at runtime). No more manual installation of native code.

- Arm Cortex support (Gumstix).

- Android (3.x or lower, requires a rooted phone to access the serial hardware).

- This feature is depricated by changes in Android permissions moving forward with 4.x

- Single Makefile compile which simplifies the compilation of project binaries.

- Gradle support for JAR creation.

- Removal of partially-implemented RXTX code to streamline the library for just serial port access.

- Full Eclipse integration for testing application code against sources.

- RFC 2217 support provided by incorporating the jvser library.

- RS485 support for Linux

