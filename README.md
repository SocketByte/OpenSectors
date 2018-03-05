# OpenSectors ![version](https://img.shields.io/badge/version-1.0-blue.svg) [![Build Status](https://travis-ci.org/SocketByte/OpenSectors.svg?branch=master)](https://travis-ci.org/SocketByte/OpenSectors)
OpenSectors allow you to split your world into different servers
using Kryonet and other networking solutions. This plugin splits your
world into few pieces and each piece is different server. Everything
is properly synchronized so it feels like it is one server. It allows you to
have more players playing on one world, because it is actually working
on few servers, so nothing will stop you from having 3000+ players on a single
world!

It also supports basic authentication systems like checking password
during the sector connection. That way people who want to
connect using their own software to **your server** and do something
bad to it, can't do it!

This presentation will be quite long, so here you have quick links to everything.
- [Features](#features)
- [Future of the project](#what-will-be-added?)
- [How it works?](#how-it-works-/-installation?)
- [Do I need redis?](#do-i-need-redis-or-something-similar?)
- [How do I use it?](#how-do-i-use-it-with-my-other-plugins?)
- [Configuration](#configuration)
- [API](#api)
- [Contribute or Donate](#contribution)
- [License](#license)

## Features
* Basic time synchronization over all the servers
* Going through sector border and "teleporting" between sectors
* Super basic chat system
* Simple, but powerful API
* Performance (**Kryonet / Kryo** instead of Redis)
* Configuration (with JSON support)
* Security
* Advanced logging systems
* ActionBar support
* Built-in fast MySQL connection and API (using HikariCP)
* Possibilities

## What will be added?
Probably a lot of things, main thing I want to add is weather
synchronization and possibility to add own time/weather like synchronizations
easily through the API.

## How it works / installation?
The project actually contains two projects - linker and system.
Both are really important for everything to work.

You put `OpenSectorLinker` on each of your sectors,
and `OpenSectorSystem` on your BungeeCord (or one of it's forks) server.

Both contain useful configurations, and you should check them!

Remember, you want to unlock port `23904` and `23905` (or other you specify in configuration)
because they're used to send data over the sector network.

## Do I need redis or something similar?
**No**! Absolutely not. It's main advantage over it's competitors
is - no additional libraries or unconventional databases needed. It's just... plug & play.

You just need a MySQL server which is pretty standard today.

## How do I use it with my other plugins?
You need to rewrite them or ask developers to do it for you.
There's no way to make this differently. 70% of plugins need to be
synchronized over the sector network to work properly.

Fortunatelly, `OpenSectors` provide super easy API which
makes this task much easier for end-user.

You can edit existing plugins to make them work with `OpenSectors`
which will require minimal Java and networking knowledge.

**_But!_** I can edit or write plugins which will support `OpenSectors`
for you! You need to contact me directly though.

## Configuration
Configuration is pretty simple. The main problem is to provide
valid coordinates for your sectors. You can use ready to use configuration
below, or create your own. Unfortunatelly, `OpenSectors` don't provide
ANY of location scaling systems or similar currently.
#### Linker configuration  
```yaml
   server-id: 0
   proxy-address: "127.0.0.1"
   proxy-port-tcp: 23904
   proxy-port-udp: 23905
   proxy-connection-timeout: 5000
   kryonet-logging: NONE
   auth-password: "CHANGE_THIS_TO_YOUR_PASSWORD___IT_NEEDS_TO_BE_SECURE!"
   # Messages
   sector-welcome-message:
     - ''
     - '  &aWelcome on sector &7%name%&a (id: &7%id%&a)'
     - ''
   sector-break-message: '&cYou can not destroy blocks that close to the sector border!'
   sector-place-message: '&cYou can not build blocks that close to the sector border!'
   sector-ignite-message: '&cYou can not ignite blocks that close to the sector border!'
   sector-bucket-message: '&cYou can not pour out water/lava that close to the sector border!'
   sector-border-close: '&2&lSector border: &f&l%distance%'
   ```
#### System configuration
```json
{
  "password": "CHANGE_THIS_TO_YOUR_PASSWORD___IT_NEEDS_TO_BE_SECURE!",
  "sectorSize": 500,
  "sectors": 9,
  "portTCP": 23904,
  "portUDP": 23905,
  "bukkitTimeFrequency": 500,
  "bukkitTimeIncremental": 10,
  "border": 1500,
  "sqlController": {
    "host": "localhost",
    "port": 3306,
    "user": "root",
    "password": "",
    "database": "opensectors"
  },
  "serverControllers": [
    {
      "id": 0,
      "port": 25566,
      "name": "center",
      "x": 0,
      "z": 0
    },
    {
      "id": 1,
      "port": 25567,
      "name": "n",
      "x": 0,
      "z": -1000
    },
    {
      "id": 2,
      "port": 25568,
      "name": "s",
      "x": 0,
      "z": 1000
    },
    {
      "id": 3,
      "port": 25569,
      "name": "w",
      "x": -1000,
      "z": 0
    },
    {
      "id": 4,
      "port": 25570,
      "name": "e",
      "x": 1000,
      "z": 0
    },
    {
      "id": 5,
      "port": 25571,
      "name": "nw",
      "x": -1000,
      "z": -1000
    },
    {
      "id": 6,
      "port": 25572,
      "name": "ne",
      "x": 1000,
      "z": -1000
    },
    {
      "id": 7,
      "port": 25573,
      "name": "sw",
      "x": -1000,
      "z": 1000
    },
    {
      "id": 8,
      "port": 25574,
      "name": "se",
      "x": 1000,
      "z": 1000
    }
  ]
}
```
#### BungeeCord configuration
```yaml
servers:
  center:
    motd: OpenSectors
    address: localhost:25566
    restricted: false
  n:
    motd: North Sector
    address: localhost:25567
    restricted: false
  s:
    motd: South Sector
    address: localhost:25568
    restricted: false
  w:
    motd: West Sector
    address: localhost:25569
    restricted: false
  e:
    motd: East Sector
    address: localhost:25570
    restricted: false
  nw:
    motd: North-West Sector
    address: localhost:25571
    restricted: false
  ne:
    motd: North-East Sector
    address: localhost:25572
    restricted: false
  sw:
    motd: South-West Sector
    address: localhost:25573
    restricted: false
  se:
    motd: South-East Sector
    address: localhost:25574
    restricted: false
```
## API
Yes, that's what you wanted, don't ya? :D

API is fairly easy, and with Javadocs/code on github it's even easier.
But I will tell you how to do basic things using `OpenSectors` API system.
API will be definitely richer in the future, remember that!

#### Linker
Add this code to your maven dependencies.
```xml
    <repositories>
        <repository>
            <id>opengui</id>
            <url>http://repo.socketbyte.pl/repository/nexus-releases</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>pl.socketbyte</groupId>
            <artifactId>OpenSectorLinker</artifactId>
            <version>1.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```
And of course Spigot/Bukkit engine too!
#### System
or this code when you make plugin for system:
```xml
    <repositories>
        <repository>
            <id>opengui</id>
            <url>http://repo.socketbyte.pl/repository/nexus-releases</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>pl.socketbyte</groupId>
            <artifactId>OpenSectorSystem</artifactId>
            <version>1.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```

There is few types of API calls you can make.
Firstly, I would want you to know about packet catching and sending. It is
very important in the long term. You want to send a lot of packets and data, and catch it!

##### This part of the API (packet sending/catching) is also working on system side!

You can register your own `Packet` extended classes easily,
and send them over the network (and/or catch them in the listener)
It is fairly easy, look;
```java
PacketExtender packetExtender = SectorAPI.createPacketExtender(PacketExampleTest.class);
packetExtender.setPacketAdapter((connection, packet) ->
        System.out.println("Received PacketExampleTest from the proxy server."));
```
In order this to work, you need to register this class on system plugin too.
```java
SectorAPI.register(PacketExampleTest.class);
```
or just create `PacketExtender` like above.

What is `PacketExampleTest`? It's a class, which extend `Packet` class.
```java
public class PacketExampleTest extends Packet {

    private String test;

    public PacketExampleTest() {

    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

}
```
Remember, it needs to be absolutely the same on both sides.
Congratulations! Now you can send it using:
```java
PacketExampleTest exampleTest = new PacketExampleTest();
exampleTest.setTest("Hello, World!");
SectorAPI.sendTCP(exampleTest);
```
You can also send UDP packets.
```java
SectorAPI.sendUDP(exampleTest);
```

You can also register something like pub/sub in Redis. What do I mean?
You can register specific channels and send custom payload packet which contain some data.
It is less convinient than using `PacketExtender` but it can be better sometimes.
```java
SectorAPI.registerPayloadChannel("TEST_CHANNEL", (connection, packet) -> {
    System.out.println("Received PacketCustomPayload (TEST_CHANNEL) as a LINKER.");
    System.out.println(packet);
});
```
You can do the same on the system side. (You don't need to register anything!)
Now you can send something, like
```java
PacketCustomPayload customPayload = new PacketCustomPayload();
customPayload.setChannel("TEST_CHANNEL");
customPayload.setData(new Object[] { "Hello", "World!" });
SectorAPI.sendTCP(customPayload);
```

##### This part is only for Linkers (who would want to use it on system side though)
This is... MySQL API! How awesome, right?
Firstly, you need to make a Query packet.
```java
PacketQuery query = new PacketQuery();
query.setQuery("INSERT INTO something VALUES(?, ?)");
```
...set replacements...
```java
Map<Integer, Object> replacements = new HashMap<>();
replacements.put(1, "a nice value");
replacements.put(2, "even nicer value");
```
... and apply & send them!
```java
query.setReplacements(replacements);
SectorAPI.sendQuery(query);
```
Congratulations, you executed a query on the system side
and now your values are in the global database! Amazing!
But, what if you want a result...? like... `ResultSet`?
It works with this API too!

Make an execute query
```java
PacketQueryExecute queryExecute = new PacketQueryExecute();
queryExecute.setQuery("SELECT * FROM something WHERE fancier=?");

replacements = new HashMap<>();
replacements.put(1, "even nicer value");

queryExecute.setReplacements(replacements);
```
... and!
```java
SectorAPI.sendQuery(queryExecute, packetQueryExecute -> {
    SerializableResultSet resultSet = packetQueryExecute.getResultSet();
    while (resultSet.next()) {
        System.out.println("Received from SQL:  " + resultSet.getString(0)
                + " -> " + resultSet.getString(1));
    }
});
```
You create a query which contains an interface which is your callback.
Confusing, right? Not really, it just returns you a modified packet
with `SerializableResultSet` (something which is similar to real `ResultSet` but cached and serializable)

That's all for the basic API part! You have some other functions but for that
you need to check javadocs!

You can get javadocs here: https://socketbyte.pl/javadocs/

(and select proper project)

## Contribution
Yes! Of course you can contribute in `OpenSectors`

Either helping with code, or supporting the project with donation.

You can donate here:

![https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=H5N9GMVH464Y8](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)

You will be mentioned in the `README.md` file as soon as you donate something. (Every donation really appreciated)

## License
Project is licensed under `MIT License`.