package com.zoomulus.servers;

/**
 * ServerPort - Provides access to server port numbers.
 */
public class ServerPort
{
    /**
     * PortNumber - Enumerated type of some well-known ports.
     * Not meant to be exhaustive.
     */
    public enum PortNumber
    {
        ECHO(7),
        FTP(21),
        SSH(22),
        TELNET(23),
        SMTP(25),
        DNS(53),
        TFTP(69),
        HTTP(80),
        POP3(110),
        SFTP(115),
        SNMP(161),
        IRC(194),
        LDAP(389),
        HTTPS(443)
        ;
        
        private final int portNumber;
        public int value() { return portNumber; }
        private PortNumber(int value) { portNumber = value; }
    }
    
    /**
     * Convert a low-numbered port to a port number in the Zoomulus range (22000-23999).
     * @param portNumber
     * @return If portNumber is a privileged port, a corresponding port in the Zoomulus range;
     * otherwise, the same portNumber is returned.
     */
    public static int ZoomulusPort(final PortNumber portNumber)
    {
        return portNumber.value() <= 1024 ? 22000 + portNumber.value() : portNumber.value();
    }
}
